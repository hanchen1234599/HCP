package com.hcp.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hcp.data.BaseData;

public class XmlUtil {
	public static BaseData readByFile(String fileName) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(fileName));
			Element node = document.getRootElement();
			return read2BaseData(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BaseData readByString(String xml) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(xml));
			Element node = document.getRootElement();
			return read2BaseData(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BaseData readTempXmlByFile(String fileName) throws Exception {
		//Long curTime1 = new Date().getTime();
		BaseData bsData = readByFile(fileName);
		//Long curTime = new Date().getTime();
		BaseData tableData = new BaseData(null);
		tableData.createObject();
		String tableName = (String) bsData.getObject("mElem").getObject("Worksheet").getObject(1).getObject("mAttr")
				.getObject("Name").getBaseValue();
		//System.out.println("table name is " + tableName);
		BaseData rowsData = bsData.getObject("mElem").getObject("Worksheet").getObject(1).getObject("mElem")
				.getObject("Table").getObject(1).getObject("mElem").getObject("Row");
		Iterator<Entry<Object, BaseData>> rowIt = rowsData.iterator();
		while (rowIt.hasNext()) {
			BaseData columsData = rowIt.next().getValue().getObject("mElem").getObject("Cell");
			Iterator<Entry<Object, BaseData>> columnIt = columsData.iterator();
			BaseData tempRowData = new BaseData(null);
			tempRowData.createObject();
			int curID = 0;
			while (columnIt.hasNext()) {
				BaseData columData = columnIt.next().getValue();
				BaseData fieldNameData = columData.getObject("mElem").getObject("NamedCell").getObject(1)
						.getObject("mAttr").getObject("Name");
				BaseData fieldTypeData = columData.getObject("mElem").getObject("Data").getObject(1).getObject("mAttr")
						.getObject("Type");
				String fieldName = (String) fieldNameData.getBaseValue();
				String fieldType = (String) fieldTypeData.getBaseValue();
				Object fieldValue = null;
				if (fieldType.equals("Number")) {
					Long value = 0L;
					BaseData valueData = columData.getObject("mElem").getObject("Data").getObject(1)
							.getObject("mValue");
					if (valueData != null && valueData.getBaseValue() != null)
						value = Long.parseLong((String) valueData.getBaseValue());
					fieldValue = value;
				} else if (fieldType.equals("String")) {
					String value = "";
					BaseData valueData = columData.getObject("mElem").getObject("Data").getObject(1)
							.getObject("mValue");
					if (valueData != null && valueData.getBaseValue() != null)
						value = (String) valueData.getBaseValue();
					fieldValue = value;
				} else
					continue;
				if (fieldName.equals("mID")) {
					if (fieldValue instanceof Long)
						curID = ((Long) fieldValue).intValue();
				} else {
					tempRowData.putObject(fieldName, fieldValue);
				}
			}
			if (curID != 0)
				tableData.putObject(curID, tempRowData);
		}
		//System.out.println(new Date().getTime() - curTime1);
		//System.out.println(new Date().getTime() - curTime);
		return tableData;
	}

	public static BaseData read2BaseData(Element node) throws Exception {
		BaseData bsData = new BaseData(null);
		bsData.createObject();
		BaseData attrData = null;
		BaseData elemData = null;
		Iterator<Attribute> it = node.attributeIterator();
		while (it.hasNext()) {
			if (attrData == null) {
				attrData = new BaseData(null);
				attrData.createObject();
				bsData.putObject("mAttr", attrData);
			}
			Attribute attr = it.next();
			attrData.putObject(attr.getName(), attr.getValue());
		}
		if (!(node.getTextTrim().equals(""))) {
			bsData.putObject("mValue", new BaseData(node.getText()));
		}
		Iterator<Element> nodeIt = node.elementIterator();
		HashMap<String, Integer> map = new HashMap<>();
		while (nodeIt.hasNext()) {
			Element nodeE = nodeIt.next();
			if (nodeE.getName() != null) {
				if (elemData == null) {
					elemData = new BaseData(null);
					elemData.createObject();
					bsData.putObject("mElem", elemData);
				}

				if (map.get(nodeE.getName()) == null) {
					map.put(nodeE.getName(), 1);
					BaseData tempData = new BaseData(null);
					tempData.createObject();
					tempData.putObject(map.get(nodeE.getName()), read2BaseData(nodeE));
					elemData.putObject(nodeE.getName(), tempData);
				} else {
					map.put(nodeE.getName(), map.get(nodeE.getName()) + 1);
					elemData.getObject(nodeE.getName()).putObject(map.get(nodeE.getName()), read2BaseData(nodeE));
				}
			}
		}
		return bsData;
	}

}


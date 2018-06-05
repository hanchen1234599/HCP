package com.hcp.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.hcp.data.BaseData;

/**
 * version 1.0.1 JsonUtil 功能： 1.支持读取文件中的json文件 2。支持从Xml转换成json
 */

public class JsonUtil {
	public static JSONObject readByFile(String fileName) {
		File file = new File(fileName);
		FileReader fr;
		JSONObject jsonValue = null;
		try {
			fr = new FileReader(file);
			jsonValue = (JSONObject) JSONValue.parse(fr);
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(jsonValue);
		return jsonValue;
	}

	public static JSONObject readByString(String jsonStr) {
		JSONObject jsonValue = (JSONObject) JSONValue.parse(jsonStr);
		System.out.println(jsonValue);
		return jsonValue;
	}

	public static BaseData jsonObj2BaseData(Object jsonValue) {
		if(jsonValue ==null)
			return null;
		
		try {
			BaseData bsData = new BaseData(null);
			bsData.createObject();
			if (jsonValue instanceof JSONObject) {
				JSONObject tempObject = (JSONObject) jsonValue;
				Iterator<Map.Entry<String, Object>> it = tempObject.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Object> map = it.next();
					if(map.getValue() instanceof JSONObject ||map.getValue() instanceof JSONArray ) {
						bsData.putObject(map.getKey(), jsonObj2BaseData(map.getValue()));
					}else {
						bsData.putObject(map.getKey(), map.getValue());
					}
				}
			} else if (jsonValue instanceof JSONArray) {
				JSONArray tempObject = (JSONArray) jsonValue;
				Iterator<Object> it = tempObject.iterator();
				int key = 1;
				while(it.hasNext()) {
					Object value = it.next();
					if(value instanceof JSONObject || value instanceof JSONArray) {
						bsData.putObject(key, jsonObj2BaseData(value));
					}else {
						bsData.putObject(key, value);
					}
					key ++;
				}
			}
			return bsData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BaseData readToBaseDataByFile(String fileName) {
		File file = new File(fileName);
		FileReader fr;
		Object jsonValue = null;
		try {
			fr = new FileReader(file);
			jsonValue = (Object) JSONValue.parse(fr);
			fr.close();
			return jsonObj2BaseData(jsonValue);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static BaseData readToBaseDataByString(String json) {
			JSONObject jsonValue = (JSONObject) JSONValue.parse(json);
			return jsonObj2BaseData(jsonValue);
	}

	public static String toString(JSONObject jsonObject) {
		return jsonObject.toString();
	}
}

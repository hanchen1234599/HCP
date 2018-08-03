package com.hcp.util;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import com.hcp.proto.BaseDataProtos;

public class BaseData {
	Object mValue = null;
	Map<Object, BaseData> mObject = null;
	boolean mIsBaseObject = false;

	public BaseData(Object value) throws Exception {
		if (value instanceof BaseData) {
			BaseData tempObject = (BaseData) value;
			if (tempObject.mIsBaseObject == true) {
				this.mObject = tempObject.mObject;
				this.mIsBaseObject = true;
			} else {
				this.mValue = tempObject.mValue;
			}
		} else {
			if (value == null) {
				mValue = null;
			} else if (value instanceof Integer || value instanceof Float || value instanceof Double
					|| value instanceof String || value instanceof Boolean || value instanceof Long
					|| value instanceof Short) {
				mValue = value;
			} else {
				mValue = null;
				System.out.println("BaseData function BaseData, arg type error.");
				throw new Exception();
			}
		}
	}

	public BaseData createObject() {
		mIsBaseObject = true;
		mObject = new ConcurrentHashMap<Object, BaseData>();
		return this;
	}

	public Iterator<Map.Entry<Object, BaseData>> iterator() {
		if (mIsBaseObject == false)
			return null;
		return mObject.entrySet().iterator();
	}

	public BaseData putObject(Object key, Object value) throws Exception {
		if (mIsBaseObject == true && (key instanceof Integer || key instanceof String)) {
			if (value instanceof BaseData) {
				if (!(((BaseData) value).mIsBaseObject == false && ((BaseData) value).mValue == null))
					mObject.put(key, (BaseData) value);
				else
					mObject.remove(key);
			} else {
				if (value != null)
					mObject.put(key, new BaseData(value));
				else
					mObject.remove(key);
			}
			return this;
		} else {
			System.out.println("BaseData function putObject, is not object or key type error.");
			throw new Exception();
		}
	}

	/**
	 * this function is so low
	 * 
	 * @throws Exception
	 */
	public BaseData getObject(Object key) throws Exception {
		if (mIsBaseObject == true) {
			BaseData bsData = mObject.get(key);
			if (bsData == null) {
				return new BaseData(null);
			}
			return bsData;
		} else {
			System.out.println("BaseData function getObject, is not object");
			throw new Exception();
		}
	}

	public boolean isObject() {
		return mIsBaseObject;
	}

	public Object getBaseValue() throws Exception {
		if (mIsBaseObject == false)
			return mValue;
		else {
			System.out.println("BaseData function getBaseValue, is object");
			throw new Exception();
		}
	}

	public BaseData setBaseValue(Object value) throws Exception {
		if (mIsBaseObject == false && (value instanceof Integer || value instanceof Float || value instanceof Double
				|| value instanceof String || value instanceof Boolean || value instanceof Long
				|| value instanceof Short))
			return this;
		else {
			System.out.println("BaseData function setBaseValue, is object");
			throw new Exception();
		}
	}

	public LuaValue toLuaValue() {
		if (this.mIsBaseObject == false) {
			if (mValue == null) {
				return LuaValue.NIL;
			} else {
				if (mValue instanceof Integer) {
					return LuaValue.valueOf((int) mValue);
				} else if (mValue instanceof Float) {
					return LuaValue.valueOf((float) mValue);
				} else if (mValue instanceof Double) {
					return LuaValue.valueOf((double) mValue);
				} else if (mValue instanceof String) {
					return LuaValue.valueOf((String) mValue);
				} else if (mValue instanceof Boolean) {
					return LuaValue.valueOf((boolean) mValue);
				} else if (mValue instanceof Long) {
					return LuaValue.valueOf((long) mValue);
				} else if (mValue instanceof Short) {
					return LuaValue.valueOf((Short) mValue);
				}
			}
		} else {
			LuaValue table = new LuaTable();
			for (Map.Entry<Object, BaseData> entry : mObject.entrySet()) {
				BaseData value = entry.getValue();
				Object key = entry.getKey();
				if (key instanceof String) {
					table.set((String) key, value.toLuaValue());
				} else if (key instanceof Integer) {
					table.set((int) key, value.toLuaValue());
				} else
					continue;
			}
			return table;
		}
		return null;
	}

	@SuppressWarnings("unused")
	public static BaseData parseFromLuaValue(LuaValue lv) throws Exception {
		BaseData bsData = null;
		if (lv.istable() == false) {
			if (lv == null) {
				bsData = new BaseData(null);
			} else if (lv.isnil()) {
				bsData = new BaseData(null);
			} else if (lv.isstring()) {
				bsData = new BaseData(lv.tojstring());
			} else if (lv.isint()) {
				bsData = new BaseData(lv.toint());
			} else if (lv.islong()) {
				bsData = new BaseData(lv.tolong());
			} else if (false) {
				bsData = new BaseData(null);
			} else if (lv.isnumber()) {
				bsData = new BaseData(lv.todouble());
			} else if (lv.isboolean()) {
				bsData = new BaseData(lv.toboolean());
			}
		} else {
			bsData = new BaseData(null);
			bsData.createObject();
			LuaTable t = (LuaTable) lv;
			LuaValue[] keys = t.keys();
			for (int i = 0; i < keys.length; i++) {
				LuaValue k = keys[i];
				if (k.isint()) {
					bsData.putObject(k.toint(), parseFromLuaValue(t.get(k)));
				} else if (k.isstring()) {
					bsData.putObject(k.tojstring(), parseFromLuaValue(t.get(k)));
				}
			}
		}
		return bsData;
	}

	public BaseDataProtos.BaseData toProtoBuf() throws Exception {
		BaseDataProtos.BaseData.Builder bsBuilder = BaseDataProtos.BaseData.newBuilder();
		if (mIsBaseObject == false) {
			bsBuilder.setMIsBaseObject(false);
			if (mValue == null) {
				BaseDataProtos.BaseData.BaseValue.Builder bsvBuilder = BaseDataProtos.BaseData.BaseValue.newBuilder();
				bsvBuilder.setMType(0);
				bsBuilder.setMValue(bsvBuilder.build());
				return bsBuilder.build();
			} else {
				BaseDataProtos.BaseData.BaseValue.Builder bsvBuilder = BaseDataProtos.BaseData.BaseValue.newBuilder();
				if (mValue instanceof Integer) {
					bsvBuilder.setBvInt((int) mValue);
					bsvBuilder.setMType(1);
				} else if (mValue instanceof Float) {
					bsvBuilder.setBvFloat((float) mValue);
					bsvBuilder.setMType(2);
				} else if (mValue instanceof Double) {
					bsvBuilder.setBvDouble((double) mValue);
					bsvBuilder.setMType(3);
				} else if (mValue instanceof String) {
					bsvBuilder.setBvString((String) mValue);
					bsvBuilder.setMType(4);
				} else if (mValue instanceof Boolean) {
					bsvBuilder.setBvBool((boolean) mValue);
					bsvBuilder.setMType(5);
				} else if (mValue instanceof Long) {
					bsvBuilder.setBvLong((long) mValue);
					bsvBuilder.setMType(6);
				} else if (mValue instanceof Short) {
					Integer value = ((Short) mValue).intValue();
					bsvBuilder.setBvInt(value);
					bsvBuilder.setMType(1);
				}
				bsBuilder.setMValue(bsvBuilder.build());
				return bsBuilder.build();
			}
		} else {
			bsBuilder.setMIsBaseObject(true);
			for (Map.Entry<Object, BaseData> entry : mObject.entrySet()) {
				BaseData value = entry.getValue();
				Object key = entry.getKey();
				BaseDataProtos.BaseData.BaseValue.Builder keyBuilder = BaseDataProtos.BaseData.BaseValue.newBuilder();
				if (key instanceof String) {
					keyBuilder.setMType(4);
					keyBuilder.setBvString((String) key);

				} else if (key instanceof Integer) {
					keyBuilder.setMType(1);
					keyBuilder.setBvInt((int) key);
				} else
					continue;
				BaseDataProtos.BaseData.ObjectType.Builder objBuilder = BaseDataProtos.BaseData.ObjectType.newBuilder();
				objBuilder.setMKey(keyBuilder.build());
				objBuilder.setMValue(value.toProtoBuf());
				bsBuilder.addMObject(objBuilder.build());
			}
		}
		return bsBuilder.build();
	}

	public static BaseData bytetoBaseData(BaseDataProtos.BaseData proBD) throws Exception {
		BaseData bsData = null;
		if (proBD.getMIsBaseObject() == false) {
			if (proBD.getMValue() == null) {
				bsData = new BaseData(null);
			} else if (proBD.getMValue().getMType() == 0) {
				bsData = new BaseData(null);
			} else if (proBD.getMValue().getMType() == 1) {
				bsData = new BaseData(proBD.getMValue().getBvInt());
			} else if (proBD.getMValue().getMType() == 2) {
				bsData = new BaseData(proBD.getMValue().getBvFloat());
			} else if (proBD.getMValue().getMType() == 3) {
				bsData = new BaseData(proBD.getMValue().getBvDouble());
			} else if (proBD.getMValue().getMType() == 4) {
				bsData = new BaseData(proBD.getMValue().getBvString());
			} else if (proBD.getMValue().getMType() == 5) {
				bsData = new BaseData(proBD.getMValue().getBvBool());
			} else if (proBD.getMValue().getMType() == 6) {
				bsData = new BaseData(proBD.getMValue().getBvLong());
			}
		} else {
			bsData = new BaseData(null);
			bsData.createObject();
			Iterator<BaseDataProtos.BaseData.ObjectType> it = proBD.getMObjectList().iterator();
			while (it.hasNext()) {
				BaseDataProtos.BaseData.ObjectType keyValue = it.next();
				if (keyValue.getMKey().getMType() == 1) {
					bsData.putObject(keyValue.getMKey().getBvInt(), bytetoBaseData(keyValue.getMValue()));
				} else if (keyValue.getMKey().getMType() == 4) {
					bsData.putObject(keyValue.getMKey().getBvString(), bytetoBaseData(keyValue.getMValue()));
				}
			}
		}
		return bsData;
	}

	public String toString() {
		String returnString = "";
		if (mIsBaseObject == false) {
			if (mValue == null)
				returnString = "null";
			else
				returnString = mValue.toString();
		} else {
			returnString += "{";
			String tempString = "";
			for (Map.Entry<Object, BaseData> entry : mObject.entrySet()) {
				returnString += tempString;
				returnString += entry.getKey().toString();
				returnString += ":";
				BaseData value = entry.getValue();
				if (value.mIsBaseObject == false) {
					if (value.mValue == null)
						returnString += "null";
					else
						returnString += value.mValue.toString();
				} else {
					returnString += value.toString();
				}
				tempString = ",";
			}
			returnString += "}";
		}
		return returnString;
	}
}

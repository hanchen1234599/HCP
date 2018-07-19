using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Proto;

namespace CommonData
{
    public class BaseData
    {
        Object mValue = null;
        Dictionary<Object, BaseData> mObject = null;
        bool mIsBaseObject = false;
        public BaseData(Object value)
        {
            if (value is BaseData)
            {
                BaseData tempObject = (BaseData)value;
                if (tempObject.mIsBaseObject == true)
                {
                    this.mObject = tempObject.mObject;
                    this.mIsBaseObject = true;
                }
                else
                {
                    this.mValue = tempObject.mValue;
                }
            }
            else
            {
                if (value == null)
                {
                    mValue = null;
                }
                else if (value is int || value is float || value is double
                      || value is String || value is Boolean || value is long
                      || value is short)
                {
                    mValue = value;
                }
                else
                {
                    mValue = null;
                    Console.Write("HCData function HCData, value type error.");
                }
            }
        }
        public BaseData createObject()
        {
            mIsBaseObject = true;
            mObject = new Dictionary<Object, BaseData>();
            return this;
        }
        public BaseData putObject(Object key, Object value)
        {
            if (mIsBaseObject == true && (key is int || key is String))
            {
                if (value is BaseData)
                {
                    if (!(((BaseData)value).isObject() == false && ((BaseData)value).mValue == null))
                        mObject.Add(key, (BaseData)value);
                    else
                        mObject.Remove(key);
                }
                else
                {
                    if (value != null)
                        mObject.Add(key, new BaseData(value));
                    else
                        mObject.Remove(key);
                }
                return this;
            }
            else
            {
                Console.Write("HCData function putObject, is not object or key type error.");
                throw new Exception();
            }
        }
        public BaseData getObject(Object key)
        {
            if (mIsBaseObject == true)
            {
                BaseData bsData = mObject[key];
                if (bsData == null)
                {
                    return new BaseData(null);
                }
                return bsData;
            }
            else
            {
                Console.Write("HCData function getObject, is not object");
                throw new Exception();
            }
        }
        public bool isObject()
        {
            return mIsBaseObject;
        }
        public Object getBaseValue()
        {
            if (mIsBaseObject == false)
                return mValue;
            else
            {
                Console.Write("HCData function getBaseValue, is object");
                return null;
            }
        }
        public BaseData setBaseValue(Object value)
        {
            if (mIsBaseObject == false && (value is int || value is float || value is double
                    || value is String || value is bool || value is long
                    || value is short))
                return this;
            else
            {
                Console.WriteLine("HCData function getBaseValue, is object");
                throw new Exception();
            }
        }
        public override String ToString()
        {
            String returnString = "";
            if (mIsBaseObject == false)
            {
                if (mValue == null)
                    returnString = "null";
                else
                    returnString = mValue + "";
            }
            else
            {
                returnString += "{";
                String tempString = "";
                foreach (var item in mObject)
                {
                    returnString += tempString;
                    returnString += item.Key + "";
                    returnString += ":";
                    BaseData value = item.Value;
                    if (value.mIsBaseObject == false)
                    {
                        if (value.mValue == null)
                            returnString += "null";
                        else
                            returnString += value.mValue + "";
                    }
                    else
                    {
                        returnString += value.ToString();
                    }
                    tempString = ",";
                }
                returnString += "}";
            }
            return returnString;
        }

        public Proto.BaseData toProtoBuf()
        {
            Proto.BaseData bsData = new Proto.BaseData { };
            if (mIsBaseObject == false)
            {
                bsData.MIsBaseObject = false;

                if (mValue == null)
                {
                    bsData.MValue = new Proto.BaseData.Types.BaseValue { MType = 0 };
                    return bsData;
                }
                else
                {
                    if (mValue is int)
                    {
                        bsData.MValue = new Proto.BaseData.Types.BaseValue { BvInt = (int)mValue, MType = 1 };
                    }
                    else if (mValue is float)
                    {
                        bsData.MValue = new Proto.BaseData.Types.BaseValue { BvFloat = (float)mValue, MType = 2 };
                    }
                    else if (mValue is double)
                    {
                        bsData.MValue = new Proto.BaseData.Types.BaseValue { BvDouble = (double)mValue, MType = 3 };
                    }
                    else if (mValue is String)
                    {
                        bsData.MValue = new Proto.BaseData.Types.BaseValue { BvString = (String)mValue, MType = 4 };
                    }
                    else if (mValue is bool)
                    {
                        bsData.MValue = new Proto.BaseData.Types.BaseValue { BvBool = (bool)mValue, MType = 5 };
                    }
                    else if (mValue is long)
                    {
                        bsData.MValue = new Proto.BaseData.Types.BaseValue { BvLong = (long)mValue, MType = 6 };
                    }
                    else if (mValue is short)
                    {
                        bsData.MValue = new Proto.BaseData.Types.BaseValue { BvInt = (int)mValue, MType = 1 };
                    }
                    return bsData;
                }
            }
            else
            {
                bsData.MIsBaseObject = true;
                foreach (var item in mObject)
                {
                    BaseData value = item.Value;
                    Object key = item.Key;
                    Proto.BaseData.Types.BaseValue keyData = new Proto.BaseData.Types.BaseValue { };
                    if (key is String)
                    {
                        keyData.BvString = ((String)key);
                        keyData.MType = 4;
                    }
                    else if (key is int)
                    {
                        keyData.BvInt = ((int)key);
                        keyData.MType = 1;
                    }
                    else
                        continue;
                    Proto.BaseData.Types.ObjectType objData = new Proto.BaseData.Types.ObjectType { };
                    objData.MKey = keyData;
                    objData.MValue = value.toProtoBuf();
                    bsData.MObject.Add(objData);
                }
            }
            return bsData;
        }

        public static BaseData bytetoBaseData(Proto.BaseData proBD)
        {
            BaseData bsData = null;
            if (proBD.MIsBaseObject != true)
            {
                if (proBD.MValue == null)
                {
                    bsData = new BaseData(null);
                }
                else if (proBD.MValue.MType == 0)
                {
                    bsData = new BaseData(null);
                }
                else if (proBD.MValue.MType == 1)
                {
                    bsData = new BaseData(proBD.MValue.BvInt);
                }
                else if (proBD.MValue.MType == 2)
                {
                    bsData = new BaseData(proBD.MValue.BvFloat);
                }
                else if (proBD.MValue.MType == 3)
                {
                    bsData = new BaseData(proBD.MValue.BvDouble);
                }
                else if (proBD.MValue.MType == 4)
                {
                    bsData = new BaseData(proBD.MValue.BvString);
                }
                else if (proBD.MValue.MType == 5)
                {
                    bsData = new BaseData(proBD.MValue.BvBool);
                }
                else if (proBD.MValue.MType == 6)
                {
                    bsData = new BaseData(proBD.MValue.BvLong);
                }
            }
            else
            {
                bsData = new BaseData(null);
                bsData.createObject();
                int objSize = proBD.MObject.Count;
                for (int i = 0; i < objSize; i++)
                {
                    Proto.BaseData.Types.ObjectType keyValue = proBD.MObject[i];
                    if (keyValue.MKey.MType == 1)
                    {
                        bsData.putObject(keyValue.MKey.BvInt, bytetoBaseData(keyValue.MValue));
                    }
                    else if (keyValue.MKey.MType == 4)
                    {
                        bsData.putObject(keyValue.MKey.BvString, bytetoBaseData(keyValue.MValue));
                    }
                }
            }
            return bsData;
        }

    }
}

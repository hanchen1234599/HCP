package com.hcp.data;

public class Commond {// �����������ģʽΪ������Ӧʽ
	private String mComKey = null;
	private BaseData mComValue = null;
	private Object mResponse = null;
	private Object[] mData = null;
	public Commond(String comKey, BaseData comValue, Object response, Object...objs) {
		this.mComKey = comKey;
		this.mComValue = comValue;
		this.mResponse = null;
		this.mData = objs;
	}
	public String getmComKey() {
		return mComKey;
	}
	public Object getmResponse() {
		return mResponse;
	}

	public Object[] getmData() {
		return mData;
	}
	public Object getObject() {
		return this.mResponse;
	}
	public BaseData getmComValue() {
		return mComValue;
	}
}

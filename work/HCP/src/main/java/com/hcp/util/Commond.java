package com.hcp.util;

public class Commond {// �����������ģʽΪ������Ӧʽ
	private Long mComKey = null;// ��������
	private BaseData mComValue = null; // ��������
	private String funStr = null;

	public Commond(Long comKey, BaseData comValue, String fun) {
		this.mComKey = comKey;
		this.mComValue = comValue;
		this.setFunStr(fun);
	}

	public Long getmComKey() {
		return mComKey;
	}

	public Commond setmComKey(Long mComKey) {
		this.mComKey = mComKey;
		return this;
	}

	public BaseData getmComValue() {
		return mComValue;
	}

	public Commond setmComValue(BaseData mComValue) {
		this.mComValue = mComValue;
		return this;
	}

	public String getFunStr() {
		return funStr;
	}

	public void setFunStr(String funStr) {
		this.funStr = funStr;
	}
}

package com.hcp.util;

public class Commond {// 这条命令设计模式为请求响应式
	private Long mComKey = null;// 命令类型
	private BaseData mComValue = null; // 命令数据
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

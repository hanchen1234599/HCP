package com.hcp.shunt;

import java.util.concurrent.ConcurrentHashMap;

public class Service {
	private long mLogicID = 0;
	private ConcurrentHashMap<String, String> mExecGroup = new ConcurrentHashMap<>();
	
	public long getLogicID() {
		return mLogicID;
	}

	public void addServiceExec(String group, String execName) {
		if(this.mExecGroup.get(group) != null)
			this.mExecGroup.remove(group);
		this.mExecGroup.put(group, execName);
	}
	public String getExec(String name) {
		return this.mExecGroup.get(name);
	}
}
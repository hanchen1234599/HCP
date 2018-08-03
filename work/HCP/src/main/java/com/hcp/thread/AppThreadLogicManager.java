package com.hcp.thread;

import java.util.concurrent.ConcurrentHashMap;

import com.hcp.util.BaseData;

public class AppThreadLogicManager {
	private OnTimerThread mOnTimer = null; // 心跳线程
	private ConcurrentHashMap<String, MysqlThread> mMysql = null;
	private ConcurrentHashMap<String, LogicThread> mLogic = null; // 逻辑线程组


	public AppThreadLogicManager() {
		mLogic = new ConcurrentHashMap<String, LogicThread>();
		mMysql = new ConcurrentHashMap<String, MysqlThread>();
		this.mLogic.put("manager", new LogicThread("manager", "./script/manager"));
		mOnTimer = new OnTimerThread();
	}

	public void addLogicThread(String name, String script, String groupName) {
		mLogic.put(name, new LogicThread(name, script));
//		try {
//			if (threadDesc.getObject(groupName) == null) {
//				threadDesc.putObject(groupName, new BaseData(null));
//				threadDesc.getObject(groupName).createObject();
//			}
//			threadDesc.getObject(groupName).putObject(name, new BaseData(1));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public void addMysqlThread(String name) {
		mMysql.put(name, new MysqlThread(name));
	}

	public OnTimerThread getmOnTimer() {
		return mOnTimer;
	}

	public MysqlThread getmMysqlThread(String name) {
		return mMysql.get(name);
	}

	public ConcurrentHashMap<String, MysqlThread> getmMysql() {
		return mMysql;
	}

	public void setmMysql(ConcurrentHashMap<String, MysqlThread> mMysql) {
		this.mMysql = mMysql;
	}

	public ConcurrentHashMap<String, LogicThread> getmLogic() {
		return mLogic;
	}

	public void setmLogic(ConcurrentHashMap<String, LogicThread> mLogic) {
		this.mLogic = mLogic;
	}

	public LogicThread getmLogicThread(String name) {
		return mLogic.get(name);
	}
}

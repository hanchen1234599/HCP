package com.hcp.thread;

import java.util.concurrent.ConcurrentHashMap;

public class AppThreadLogicManager {
	private OnTimerThread mOnTimer = null; // 心跳线程
	private ManagerThread mManager = null; // 管理线程
	private ConcurrentHashMap<String, MysqlThread> mMysql = null;
	private ConcurrentHashMap<String, LogicThread> mLogic= null; // 逻辑线程组
	public AppThreadLogicManager() {
		mManager = new ManagerThread(this);
		mOnTimer = new OnTimerThread(this);
		mLogic = new ConcurrentHashMap<String, LogicThread>();
		mMysql = new ConcurrentHashMap<String, MysqlThread>();
	}
	
	public void addLogicThread(String name) {
		mLogic.put(name, new LogicThread(this, name));
	}
	
	public void addMysqlThread(String name) {
		mMysql.put(name, new MysqlThread(this, name));
	}
	
	public OnTimerThread getmOnTimer() {
		return mOnTimer;
	}

	public MysqlThread getmMysqlThread(String name) {
		return mMysql.get(name);
	}

	public ManagerThread getmManager() {
		return mManager;
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

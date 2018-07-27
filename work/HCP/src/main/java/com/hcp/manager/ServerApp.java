package com.hcp.manager;

import com.hcp.mysql.HikariCpDB;
import com.hcp.net.Net;
import com.hcp.netty.NettyServer;
import com.hcp.thread.AppThreadLogicManager;

public class ServerApp {
	private static Net mNet = new Net();
	private static AppThreadLogicManager mExecs = null;
	public static void start() {
		mExecs = new AppThreadLogicManager(); // �����߳���
		NettyServer.serverRun(); // ������·�߳���
	}
	public static Net getNet() {
		return mNet;
	}
	public static AppThreadLogicManager getAppExecs() {
		return mExecs;
	}
}

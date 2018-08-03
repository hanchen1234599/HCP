package com.hcp.app;

import java.util.Map.Entry;

import com.hcp.netty.NettyServer;
import com.hcp.shunt.SessionContainer;
import com.hcp.shunt.Shunt;
import com.hcp.thread.AppThreadLogicManager;
import com.hcp.thread.LogicThread;
import com.hcp.util.BaseData;
import com.hcp.util.Commond;

public class ServerApp {
	private static AppThreadLogicManager mExecs = null;
	public static void start() throws Exception {
		mExecs = new AppThreadLogicManager(); // 开启线程组
			Commond commond = new Commond((long) -1,
					new BaseData(null), "serverStart");
			mExecs.getmLogicThread("manager").exec(commond);
			SessionContainer.getInstance();
			Shunt.getInstance();
		NettyServer.serverRun(); // 启动网路线程组
	}
	public static SessionContainer getNet() {
		return SessionContainer.getInstance();
	}
	public static AppThreadLogicManager getAppExecs() {
		return mExecs;
	}
}

package com.hcp.thread;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hcp.data.BaseData;
import com.hcp.data.Commond;

public class OnTimerThread {// 服务器心跳线程
	private boolean mState = false;
	private OnTimerLogic mCommand = null;

	public OnTimerThread(AppThreadLogicManager app) {
		mCommand = new OnTimerLogic(app);
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(mCommand, 0, 5000, TimeUnit.MILLISECONDS);
		this.mState = true;
	}

	public boolean getState() {
		return mState;
	}
}

class OnTimerLogic implements Runnable {// 服务器心跳处理类
	private AppThreadLogicManager mApp = null;

	public OnTimerLogic(AppThreadLogicManager app) {
		this.mApp = app;
	}

	@Override
	public void run() {

		try {
			Commond commond = new Commond("onTimer",
					new BaseData(null).createObject().putObject("mValue", new BaseData(null)), null);
			this.mApp.getmManager().exec(commond);
			for (Entry<String, LogicThread> entry : this.mApp.getmLogic().entrySet()) {
				entry.getValue().exec(commond);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
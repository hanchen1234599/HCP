package com.hcp.thread;

import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hcp.app.ServerApp;
import com.hcp.util.BaseData;
import com.hcp.util.Commond;

public class OnTimerThread {// 服务器心跳线程
	private boolean mState = false;
	private OnTimerLogic mCommand = null;

	public OnTimerThread() {
		mCommand = new OnTimerLogic();
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(mCommand, 0, 5000, TimeUnit.MILLISECONDS);
		this.mState = true;
	}

	public boolean getState() {
		return mState;
	}
}

class OnTimerLogic implements Runnable {// 服务器心跳处理类

	public OnTimerLogic() {
	}

	@Override
	public void run() {
		try {
			Commond commond = new Commond((long) -1,
					new BaseData(null).createObject().putObject("onTimer", new BaseData(null)), "onTimer");
			for (Entry<String, LogicThread> entry : ServerApp.getAppExecs().getmLogic().entrySet()) {
				entry.getValue().exec(commond);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
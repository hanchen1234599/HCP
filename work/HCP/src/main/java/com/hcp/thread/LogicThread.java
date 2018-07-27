package com.hcp.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hcp.data.Commond;
import com.hcp.logicinterface.OnDbreadr;
import com.hcp.oper.AccountManager;

import io.netty.channel.Channel;

public class LogicThread implements ThreadDealInterface {
	private boolean mState = false;
	private ExecutorService mExec = null;
	private LogicThreadDeal mLogic = null;
	private String mName = null;

	public LogicThread(AppThreadLogicManager app, String name) {
		this.mLogic = new LogicThreadDeal(app, this);
		this.mExec = Executors.newSingleThreadExecutor();
		this.mName = name;
		this.mState = true;
	}

	public void exec(Commond commond) {
		mLogic.addCommond(commond);
		mExec.submit(mLogic);
	}

	public void addCommond(Commond commond) {
		this.mLogic.addCommond(commond);
	}

	public String getThreadName() {
		return this.mName;
	}
}

class LogicThreadDeal implements Callable<Integer> {
	private AppThreadLogicManager mApp = null;//
	private ThreadDealInterface mExec = null;
	private ConcurrentLinkedQueue<Commond> mCommonds = new ConcurrentLinkedQueue<Commond>();// 这里存放命令列表

	public LogicThreadDeal(AppThreadLogicManager app, ThreadDealInterface exec) {
		this.mApp = app;
		this.mExec = exec;
	}

	public void addCommond(Commond commond) {
		mCommonds.add(commond);
	}

	@Override
	public Integer call() throws Exception {
		while (mCommonds.isEmpty() != true) {
			Commond commond = mCommonds.poll();
			if(commond.getmComKey().equals("regist")) {
				AccountManager.OnProtocol(this.mExec, commond.getmComValue(), (Channel) commond.getmResponse(), null);	
			}else if(commond.getmComKey().equals("db")) {
				((OnDbreadr)(commond.getmData()[0])).OnDbReader(commond.getmComValue(), null, commond.getmData());
			}
		}

		return null;
	}
}

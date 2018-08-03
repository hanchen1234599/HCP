package com.hcp.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hcp.util.BaseData;
import com.hcp.util.BaseLua;
import com.hcp.util.Commond;

public class LogicThread implements ThreadDealInterface {
	private ExecutorService mExec = null;
	private LogicThreadDeal mLogic = null;
	private String mName = null;
	
	public LogicThread(String name, String script) {
		this.mLogic = new LogicThreadDeal(script, this);
		this.mExec = Executors.newSingleThreadExecutor();
		this.mName = name;
	}
	
	public ExecutorService getExec() {
		return this.mExec;
	}
	
	public void exec(Commond commond) {
		mLogic.addCommond(commond);
		mExec.submit(mLogic);
	}

	public void addCommond(Commond commond) {
		this.mLogic.addCommond(commond);
	}
	
	public void callLuaFun(String funstr, BaseData bd, BaseData callData) {
		this.mLogic.callLuaFun(funstr, bd, callData);
	}
	
	public String getThreadName() {
		return this.mName;
	}
}

class LogicThreadDeal implements Callable<Integer> {
	private ThreadDealInterface mExec = null;
	private ConcurrentLinkedQueue<Commond> mCommonds = new ConcurrentLinkedQueue<Commond>();// 这里存放命令列表
	private BaseLua lua = null;
	
	public LogicThreadDeal(String script, ThreadDealInterface exec) {
		this.mExec = exec;
		this.lua = new BaseLua(script);
		try {
			this.lua.callNoReturn("scriptLoad", (long) -1, new BaseData(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCommond(Commond commond) {
		mCommonds.add(commond);
	}
	
	public void callLuaFun(String funstr, BaseData bd, BaseData callData) {
		this.lua.callNoReturnWithData(funstr, bd, callData);
	}
	
	@Override
	public Integer call() throws Exception {
		while (mCommonds.isEmpty() != true) {
			try {
				Commond commond = mCommonds.poll();
				this.lua.callNoReturn(commond.getFunStr(), commond.getmComKey(), commond.getmComValue());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return null;
	}
}

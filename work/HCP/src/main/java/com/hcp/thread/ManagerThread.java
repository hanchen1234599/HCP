package com.hcp.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hcp.data.BaseData;
import com.hcp.data.Commond;

public class ManagerThread implements ThreadDealInterface {// �����߳�
	private ExecutorService mExec = null;
	private boolean mState = false;
	private ManagerLogic mLogic = null;

	public ManagerThread(AppThreadLogicManager app) {
		mLogic = new ManagerLogic(app);
		mExec = Executors.newSingleThreadExecutor();
		this.mState = true;
	}

	public void exec(Commond commond) {
		if (this.mState == true) {
			mLogic.addCommond(commond);
			mExec.submit(mLogic);
		}
	}

	public void addCommond(Commond commond) {
		mLogic.addCommond(commond);
	}

	public boolean getState() {
		return this.mState;
	}
}

class ManagerLogic implements Callable<Integer> { // �����߳��߼�
	private AppThreadLogicManager mApp = null;
	private ConcurrentLinkedQueue<Commond> mCommonds = new ConcurrentLinkedQueue<Commond>();// �����������б�
	private boolean test = false;

	public ManagerLogic(AppThreadLogicManager app) {
		this.mApp = app;
	}

	public void addCommond(Commond commond) {
		mCommonds.add(commond);
	}

	@Override
	public Integer call() throws Exception {
		while (mCommonds.isEmpty() != true) {
			Commond commond = mCommonds.poll();// ȡ������
			//System.out.println("manger thread deal:" + commond.getmComKey());
		}
		if(test == false) {
			this.mApp.addLogicThread("oper1");// 10 ���߼��߳�
			this.mApp.addLogicThread("oper2");// 10 ���߼��߳�
			this.mApp.addLogicThread("oper3");// 10 ���߼��߳�
			this.mApp.addMysqlThread("data1");// 1�����ݿ��߳�
			test = true;
		}
		return null;
	}
}

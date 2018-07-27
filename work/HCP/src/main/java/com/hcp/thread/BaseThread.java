package com.hcp.thread;

import java.util.concurrent.ExecutorService;

public class BaseThread {
	private ExecutorService mExec = null;

	public ExecutorService getmExec() {
		return mExec;
	}

	public void setmExec(ExecutorService mExec) {
		this.mExec = mExec;
	}
}

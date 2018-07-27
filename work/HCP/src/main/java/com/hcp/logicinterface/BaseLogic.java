package com.hcp.logicinterface;

import com.hcp.thread.ThreadDealInterface;

import io.netty.channel.Channel;

public class BaseLogic {
	private Channel mChannel = null;
	public Channel getmChannel() {
		return mChannel;
	}
	public void setmChannel(Channel mChannel) {
		this.mChannel = mChannel;
	}
	public ThreadDealInterface getmExec() {
		return mExec;
	}
	public void setmExec(ThreadDealInterface mExec) {
		this.mExec = mExec;
	}
	ThreadDealInterface mExec = null;
}

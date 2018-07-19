package com.hcp.net;

import io.netty.channel.Channel;

public class Session extends SessionAdapter {
	private long mSid = 0;
	private Channel mChannel = null;

	public Session(Channel ctx) {
		mSid = super.initSessionID();
		mChannel = ctx;
	}

	public long getSessionID() {
		return mSid;
	}

	public Channel getChannel() {
		return mChannel;
	}
}

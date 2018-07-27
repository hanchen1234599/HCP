package com.hcp.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class Net {
	private static Net mServer = null;
	private Map<Long, Session> mSessions = new ConcurrentHashMap<Long, Session>();
	private Map<Channel, Long> mCtxSid = new ConcurrentHashMap<Channel, Long>();

	public static Net getInstance() {
		if (mServer == null)
			mServer = new Net();
		return mServer;
	}

	public void putSession(Channel channel) {
		Session sess = new Session(channel);
		mSessions.put(sess.getSessionID(), sess);
		mCtxSid.put(channel, sess.getSessionID());
	}

	public void removeSession(Channel channel) {
		long sid = mCtxSid.remove(channel);
		mSessions.remove(sid);
	}
	public long getChannelSid(Channel channel) {
		return mCtxSid.get(channel);
	}
	public Session getSessionBySid(long sid) {
		return mSessions.get(sid);  
	}
}

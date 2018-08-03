package com.hcp.shunt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.netty.channel.Channel;

public class SessionContainer {
	private static SessionContainer mContainer = null;
	private Map<Long, Session> mSessions = new ConcurrentHashMap<Long, Session>();
	private Map<Channel, Long> mCtxSid = new ConcurrentHashMap<Channel, Long>();

	public static SessionContainer getInstance() {
		if (mContainer == null)
			mContainer = new SessionContainer();
		return mContainer;
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

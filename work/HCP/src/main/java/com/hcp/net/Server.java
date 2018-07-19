package com.hcp.net;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

public class Server {
	private static Server mServer = null;
	private Map<Long, Session> mSessions = new HashMap<Long, Session>();

	public static Server getInstance() {
		if (mServer == null)
			mServer = new Server();
		return mServer;
	}

	public void putSession(ChannelHandlerContext ctx) {
		Session sess = new Session(ctx.channel());
		mSessions.put(sess.getSessionID(), sess);
	}

	public void removeSession(ChannelHandlerContext ctx) {
		
	}
}

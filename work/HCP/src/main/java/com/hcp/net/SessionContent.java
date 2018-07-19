package com.hcp.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

public class SessionContent {
	private Map<Long, Session> mapKeyID = new ConcurrentHashMap<Long, Session>();
	private Map<Channel, Session> mapKeyChannel = new ConcurrentHashMap<Channel, Session>();

	public void putSession(Channel channel) {
		Session session = new Session(channel);
		mapKeyID.put(session.getSessionID(), session);
		mapKeyChannel.put(channel, session);
	}

	public void removeSession(Channel channel) {
		Session session = mapKeyChannel.remove(channel);
		if(session != null)
			mapKeyID.remove(session.getSessionID());
	}

	public Session getSessionByID(long sid) {
		return mapKeyID.get(sid);
	}

	public Session getSessionByChannel(Channel channel) {
		return mapKeyChannel.get(channel);
	}
}

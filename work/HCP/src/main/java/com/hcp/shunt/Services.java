package com.hcp.shunt;

import java.util.concurrent.ConcurrentHashMap;

import com.hcp.app.ServerApp;
import com.hcp.thread.ThreadDealInterface;

public class Services implements ShuntIn {
	ConcurrentHashMap<Long, Long> mSid2SerID = new ConcurrentHashMap<Long, Long>();
	ConcurrentHashMap<Long, Long> mSerID2Sid = new ConcurrentHashMap<Long, Long>();
	ConcurrentHashMap<Long, Service> mSerID2Service = new ConcurrentHashMap<Long, Service>();

	@Override
	public Long getLogicId(long sid) {
		return this.mSid2SerID.get(sid);
	}

	public void putUser(long serviceID, long sid) {
		this.mSid2SerID.put(sid, serviceID);
		this.mSerID2Sid.put(serviceID, sid);
		this.mSerID2Service.put(serviceID, new Service());
	}
	@Override
	public Service getService(long logicID) {
		return this.mSerID2Service.get(logicID);
	}
	@Override
	public Long getSid(long logicID) {
		return this.mSerID2Sid.get(logicID);
	}   
}



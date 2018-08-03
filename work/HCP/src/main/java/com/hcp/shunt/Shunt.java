package com.hcp.shunt;

import com.hcp.app.ServerApp;
import com.hcp.thread.ThreadDealInterface;
import com.hcp.util.BaseData;
import com.hcp.util.Commond;

import io.netty.channel.Channel;

public class Shunt {
	private static Shunt mSt = null;
	private Services mServices = new Services();

	public static Shunt getInstance() {
		if (mSt == null)
			mSt = new Shunt();
		return mSt;
	}

	public void OnProto(long sid, BaseData bd) {
		Long serviceID = this.mServices.getLogicId(sid);
		if (serviceID == null) {
			ServerApp.getAppExecs().getmLogic().get("login").exec(new Commond(sid, bd, "onProto"));
		} else {
			try {
				BaseData value = bd.getObject("mV");
				String execName = (String) bd.getObject("mE").getBaseValue();
				if (execName != null)
					ServerApp.getAppExecs().getmLogic().get(this.mServices.getService(serviceID).getExec(execName))
							.exec(new Commond(serviceID, value, "onProto"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void send(long logicId, BaseData bd) {
		Channel ctx = ServerApp.getNet().getSessionBySid(this.mServices.getSid(logicId)).getChannel();
		ctx.write(bd);
		ctx.flush();
	}

	public void sendBySessionID(long sid, BaseData bd) {
		Channel ctx = ServerApp.getNet().getSessionBySid(sid).getChannel();
		ctx.write(bd);
		ctx.flush();
	}

	public Services getServices() {
		return this.mServices;
	}
}

interface ShuntIn {
	Long getLogicId(long sid);

	Long getSid(long logicID);

	Service getService(long logicID);
}
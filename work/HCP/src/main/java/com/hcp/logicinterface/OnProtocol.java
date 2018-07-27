package com.hcp.logicinterface;

import com.hcp.data.BaseData;
import com.hcp.thread.ThreadDealInterface;

import io.netty.channel.Channel;

public interface OnProtocol {
	void OnProtocol(ThreadDealInterface exec, BaseData bd, Channel response, Object[] objs);
}

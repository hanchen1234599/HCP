package com.hcp.logicinterface;

import com.hcp.data.BaseData;
import com.hcp.thread.ThreadDealInterface;

public interface OnDbreadr {
	public void OnDbReader(BaseData bd, ThreadDealInterface exec, Object... objs);
}

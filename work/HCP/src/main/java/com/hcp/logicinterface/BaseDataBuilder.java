package com.hcp.logicinterface;

import com.hcp.data.BaseData;

public interface BaseDataBuilder {
	public BaseData toBaseData() throws Exception;
	public boolean parseFromBaseData(BaseData bd) throws Exception;
}

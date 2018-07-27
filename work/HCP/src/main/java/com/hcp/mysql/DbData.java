package com.hcp.mysql;

import com.hcp.data.BaseData;

public interface DbData {
	public long getOnlyID();
	public void setOnlyID(long roleID);
	public void updateDbData();
	public void parseFromDbData();   
}
 
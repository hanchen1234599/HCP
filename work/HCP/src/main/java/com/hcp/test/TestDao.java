package com.hcp.test;

import com.hcp.data.BaseData;
import com.hcp.mysql.DbData;

public class TestDao implements DbData {
	private long mRoleID;
	private String mRoleName;
	private boolean mSex;
	private int mFraction;
	private BaseData mProp;
	@Override
	public long getOnlyID() {
		return mRoleID;
	}

	@Override
	public void setOnlyID(long roleID) {
		mRoleID = roleID;
		parseFromDbData();
	}

	@Override
	public void updateDbData() {
		
	}

	@Override
	public void parseFromDbData() {
		
	}
}

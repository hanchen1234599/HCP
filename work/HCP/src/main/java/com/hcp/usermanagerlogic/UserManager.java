package com.hcp.usermanagerlogic;

import java.util.HashMap;
import java.util.Map;

import com.hcp.data.BaseData;
import com.hcp.logicinterface.BaseDataBuilder;
import com.hcp.logicinterface.OnDbreadr;
import com.hcp.logicinterface.OnProtocol;
import com.hcp.thread.ThreadDealInterface;

import io.netty.channel.Channel;

public class UserManager implements OnDbreadr, OnProtocol{
	Map<Long, UserPlayer> mUsers = new HashMap<Long, UserPlayer>();

	@Override
	public void OnProtocol(BaseData bd, Channel response, Object... objs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnDbReader(BaseData bd, ThreadDealInterface exec, Object... objs) {
		// TODO Auto-generated method stub
		
	}
}

class UserPlayer implements BaseDataBuilder{
	private long mUserID;
	private long mRoleID;
	private boolean mSex;
	private int mFraction;
	private int mCreateTime;
	private int mLoginTime;
	private int mLoginOutTime;
	
	public long getmUserID() {
		return mUserID;
	}
	public void setmUserID(long mUserID) {
		this.mUserID = mUserID;
	}
	public long getmRoleID() {
		return mRoleID;
	}
	public void setmRoleID(long mRoleID) {
		this.mRoleID = mRoleID;
	}
	public boolean ismSex() {
		return mSex;
	}
	public void setmSex(boolean mSex) {
		this.mSex = mSex;
	}
	public int getmFraction() {
		return mFraction;
	}
	public void setmFraction(int mFraction) {
		this.mFraction = mFraction;
	}
	public int getmCreateTime() {
		return mCreateTime;
	}
	public void setmCreateTime(int mCreateTime) {
		this.mCreateTime = mCreateTime;
	}
	public int getmLoginTime() {
		return mLoginTime;
	}
	public void setmLoginTime(int mLoginTime) {
		this.mLoginTime = mLoginTime;
	}
	public int getmLoginOutTime() {
		return mLoginOutTime;
	}
	public void setmLoginOutTime(int mLoginOutTime) {
		this.mLoginOutTime = mLoginOutTime;
	}
	@Override
	public BaseData toBaseData() throws Exception {
		BaseData bd = new BaseData(null);
		bd.createObject();
		bd.putObject("mUserID", this.mUserID);
		bd.putObject("mRoleID", this.mRoleID);
		bd.putObject("mSex", this.mSex);
		bd.putObject("mFraction", this.mFraction);
		bd.putObject("mCreateTime", this.mCreateTime);
		bd.putObject("mLoginTime", this.mLoginTime);
		bd.putObject("mLoginOutTime", this.mLoginOutTime);
		return bd;
	}
	@Override
	public boolean parseFromBaseData(BaseData bd) throws Exception {
		// TODO Auto-generated method stub
		if(bd== null || bd.isObject() != true) {
			return false;
		}
		if(bd.getObject("mUserID") != null && bd.getObject("mUserID").isObject()== false)
			this.mUserID = (long) bd.getObject("mUserID").getBaseValue();
		if(bd.getObject("mRoleID") != null && bd.getObject("mRoleID").isObject()== false)
			this.mRoleID = (long) bd.getObject("mRoleID").getBaseValue();
		if(bd.getObject("mSex") != null && bd.getObject("mSex").isObject()== false)
			this.mSex = (boolean) bd.getObject("mSex").getBaseValue();
		if(bd.getObject("mFraction") != null && bd.getObject("mFraction").isObject()== false)
			this.mFraction = (int) bd.getObject("mFraction").getBaseValue();
		if(bd.getObject("mCreateTime") != null && bd.getObject("mCreateTime").isObject()== false)
			this.mCreateTime = (int) bd.getObject("mCreateTime").getBaseValue();
		if(bd.getObject("mLoginTime") != null && bd.getObject("mLoginTime").isObject()== false)
			this.mLoginTime = (int) bd.getObject("mLoginTime").getBaseValue();
		if(bd.getObject("mLoginOutTime") != null && bd.getObject("mLoginOutTime").isObject()== false)
			this.mLoginTime = (int) bd.getObject("mLoginOutTime").getBaseValue();
		return true;
	}
}
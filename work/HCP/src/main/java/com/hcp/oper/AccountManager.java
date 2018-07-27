package com.hcp.oper;

import com.hcp.data.BaseData;
import com.hcp.data.Commond;
import com.hcp.logicinterface.BaseLogic;
import com.hcp.logicinterface.OnDbreadr;
import com.hcp.logicinterface.OnProtocol;
import com.hcp.manager.ServerApp;
import com.hcp.thread.ThreadDealInterface;

import io.netty.channel.Channel;

public class AccountManager{
	public static void OnProtocol(ThreadDealInterface exec, BaseData bd, Channel response, Object... objs) {
		// TODO Auto-generated method stub
		new AccountManagerLogic().OnProtocol(exec, bd, response, objs);	
	}

	public static void OnDbReader(BaseData bd, ThreadDealInterface exec, Object... objs) {
		
	}
}

class AccountManagerLogic extends BaseLogic implements  OnDbreadr, OnProtocol {
	
	@Override
	public void OnProtocol(ThreadDealInterface exec, BaseData bd, Channel response, Object... objs) {
		try {
			if(bd.isObject() == true && bd.getObject("regist")!= null) {
				super.setmExec(exec);
				super.setmChannel(response);
				String accountName = (String) bd.getObject("regist").getObject("username").getBaseValue();
				String accountPassWord = (String) bd.getObject("regist").getObject("userpassword").getBaseValue();
				System.out.println("accountName:" + accountName + "  accountPassWord:" + accountPassWord);
				Commond commond = new Commond("select", new BaseData("select * from t1"), exec, this, accountPassWord);
				// 这里进行数据库处理
				ServerApp.getAppExecs().getmMysqlThread("data1").exec(commond);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void OnDbReader(BaseData bd, ThreadDealInterface exec, Object... objs) {
		System.out.println(bd);
	}
}

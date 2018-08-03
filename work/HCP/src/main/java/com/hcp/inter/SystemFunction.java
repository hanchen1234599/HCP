package com.hcp.inter;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import com.hcp.app.ServerApp;
import com.hcp.shunt.Shunt;
import com.hcp.util.BaseData;
import com.hcp.util.JsonUtil;

public class SystemFunction extends TwoArgFunction {
	static class loginSuccess extends TwoArgFunction {
		public LuaValue call(LuaValue sid, LuaValue logicID) {
			Shunt.getInstance().getServices().putUser(logicID.tolong(), sid.tolong());
			return LuaValue.NIL;
		}
	}
	
	static class userAddExec extends ThreeArgFunction{
		public LuaValue call(LuaValue logicID, LuaValue group, LuaValue execname) {
			Shunt.getInstance().getServices().getService(logicID.tolong()).addServiceExec(group.tojstring(), execname.tojstring());
			return LuaValue.NIL;
		}
	}

	static class getJsonObject extends OneArgFunction {
		public LuaValue call(LuaValue path) {
			return JsonUtil.readToBaseDataByFile(path.tojstring()).toLuaValue();
		}
	}

	static class addLogicThread extends OneArgFunction {
		public LuaValue call(LuaValue value) {
			try {
				BaseData bd = BaseData.parseFromLuaValue(value);
				ServerApp.getAppExecs().addLogicThread((String)bd.getObject("mA2").getBaseValue(),
						(String)bd.getObject("mA3").getBaseValue(), (String)bd.getObject("mA1").getBaseValue());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return LuaValue.NIL;
		}
	}

	static class addMysqlThread extends OneArgFunction {
		public LuaValue call(LuaValue value) {
			ServerApp.getAppExecs().addMysqlThread(value.tojstring());
			return LuaValue.NIL;
		}
	}
	
	static class send2User extends TwoArgFunction {
		public LuaValue call(LuaValue luaUserID, LuaValue value) {
			try {
				BaseData data = BaseData.parseFromLuaValue(value);
				Shunt.getInstance().send(luaUserID.tolong(), data);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return LuaValue.NIL;
		}
	}
	
	static class send2Client extends OneArgFunction {
		public LuaValue call(LuaValue value) {
			try {
				BaseData bd = BaseData.parseFromLuaValue(value);
				long sid = (long) bd.getObject("mA1").getBaseValue();
				BaseData data = bd.getObject("mA2");
				Shunt.getInstance().sendBySessionID(sid, data);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return LuaValue.NIL;
		}
	}
	
	static class execQuary extends ThreeArgFunction {
		@Override
		public LuaValue call( LuaValue sqlLua, LuaValue callbackData, LuaValue data) {
			String sql = sqlLua.tojstring();
			BaseData bsd;
			try {
				bsd = BaseData.parseFromLuaValue(callbackData);
				String exec = (String) bsd.getObject(2).getBaseValue();
				String callback = (String) bsd.getObject(1).getBaseValue();
				BaseData vData = BaseData.parseFromLuaValue(data);
				ServerApp.getAppExecs().getmMysqlThread("db2").quarySqlAsync(sql, ( bd )->{
					ServerApp.getAppExecs().getmLogicThread(exec).callLuaFun(callback, bd, vData);
				}, ServerApp.getAppExecs().getmLogicThread(exec).getExec());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return LuaValue.NIL;
		}
	}

	@Override
	public LuaValue call(LuaValue arg1, LuaValue arg2) {
		LuaValue library = tableOf();
		library.set("loginSuccess", new loginSuccess());
		library.set("getJsonObject", new getJsonObject());
		library.set("addLogicThread", new addLogicThread());
		library.set("addMysqlThread", new addMysqlThread());
		library.set("send2User", new send2User());
		library.set("send2Client", new send2Client());
		library.set("execQuary", new execQuary());
		library.set("userAddExec", new userAddExec());
		arg2.set("System", library);
		arg2.get("package").get("loaded").set("System", library);
		return library;
	}
}

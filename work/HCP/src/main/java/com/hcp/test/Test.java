package com.hcp.test;

import java.util.ArrayList;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.*;

import com.hcp.util.BaseData;

public class Test {

	public static void main(String[] args) throws Exception {
		System.out.println("Test main run ....");
		// TODO Auto-generated method stub
		Globals globals = JsePlatform.standardGlobals();
		globals.load(new hyperbolic());
		
		globals.loadfile("./script/test/main.lua").call();
//		//获取无参函数hello
//		LuaValue func = globals.get(LuaValue.valueOf("test1"));
//		LuaValue data = new LuaTable();
//		data.set(1, LuaValue.valueOf("111"));
//		data.set(LuaValue.valueOf(10000), LuaValue.valueOf("111"));
//		data.set(LuaValue.valueOf(1.1), LuaValue.valueOf("111"));
//		//执行hello方法
//		LuaTable a = (LuaTable) func.call(data);
//		BaseData bd = BaseData.parseFromLuaValue(a);
//		//获取带参函数test
//		//LuaValue func1 = globals.get(LuaValue.valueOf("test"));
//		//执行test方法,传入String类型的参数参数
//		//String data = func1.call(LuaValue.valueOf("I'am from Java!")).toString();
//		//打印lua函数回传的数据
//		
//		System.out.println("data return from lua is:" + bd);
//		func.call(bd.toLuaValue());
		System.out.println("Test main end ....");
	}
	
	public static void print(LuaValue a) {
		if(a.istable()) {
			System.out.println(luaTableToString((LuaTable) a));
		}else {
			System.out.println(a.toString());
		}
	}
	
	public static String luaTableToString(LuaTable data) {
		String returnString = "{";
		if(data.istable() == false)
			return "";
		LuaValue[] keys = data.keys();
		for(int i=0;i< keys.length;i++) {
			LuaValue value = data.get(keys[i]);
			if(value.istable()) {
				returnString += (keys[i] + ":" + luaTableToString((LuaTable) value));
			}else {
				returnString += (keys[i] + ":" + value.toString() + "");
			}
			if(i != keys.length - 1)
				returnString += ",";
		}
		returnString += "}";
		return returnString;
	}
}

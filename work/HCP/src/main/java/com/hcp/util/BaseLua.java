package com.hcp.util;

import java.io.File;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.hcp.inter.SystemFunction;
import com.hcp.test.hyperbolic;

public class BaseLua {
	Globals globals = JsePlatform.standardGlobals();
	public String scriptPaht = null;

	public BaseLua(String scriptPaht) {
		globals.load(new SystemFunction());
		this.scriptPaht = scriptPaht;
		loadScript(scriptPaht);
	}
	
	public void callNoReturn(String funstr, Long id, BaseData bd) {
		LuaValue func = globals.get(LuaValue.valueOf(funstr));
		if(func == null)
			System.out.println("fun:" + funstr + "not exist");
		else
			func.call(LuaValue.valueOf(id), bd.toLuaValue());
	}
	public void callNoReturnWithData(String funstr, BaseData bd, BaseData data) {
		LuaValue func = globals.get(LuaValue.valueOf(funstr));
		if(func == null)
			System.out.println("fun:" + funstr + "not exist");
		else
			func.call(bd.toLuaValue(), data.toLuaValue());
	}
	public BaseData callWithReturn(String funstr, Long id, BaseData bd) {
		LuaValue func = globals.get(LuaValue.valueOf(funstr));
		BaseData rbd = null;
		try {
			bd = BaseData.parseFromLuaValue(func.call(LuaValue.valueOf(id), bd.toLuaValue()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rbd;
	}
	
	private void loadScript(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						loadScript(file2.getAbsolutePath());
					} else {
						if (file2.getAbsolutePath().endsWith(".lua"))
							globals.loadfile(file2.getAbsolutePath()).call();
					}
				}

			} else {
				if (file.getAbsolutePath().endsWith(".lua"))
					globals.loadfile(file.getAbsolutePath()).call();
			}
		} else {
			System.out.println("½Å±¾²»´æÔÚ" + path);
		}
	}
}

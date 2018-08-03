package com.hcp.test;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Logger extends TwoArgFunction {

	/** Public constructor.  To be loaded via require(), the library class 
	 * must have a public constructor.
	 */
	public Logger() {}

	/** The implementation of the TwoArgFunction interface.
	 * This will be called once when the library is loaded via require().
	 * @param modname LuaString containing the name used in the call to require().
	 * @param env LuaValue containing the environment for this function.
	 * @return Value that will be returned in the require() call.  In this case, 
	 * it is the library itself.
	 */
	public LuaValue call(LuaValue arg1, LuaValue env) {
		Test.print(arg1);
		//Test.print(arg2);
		return LuaValue.valueOf("11");
	}
}


print('hyperbolic', hyperbolic)
print('hyperbolic.sinh', hyperbolic.sinh)
print('hyperbolic.cosh', hyperbolic.cosh)

-- Try exercising the functions.
print('sinh(0.5)', hyperbolic.sinh(0.5))


-- logger_method:info( a )
-- print(logger_method.TAG)
-- -- 使用绑定类创建类的实例（对象）
-- local logger_instance = luajava.new(logger_method)
-- -- 调用对象方法
-- logger_instance:TestLogger("Test call java in lua1")
----忙鈥斅犆ヂ忊�毭モ�÷矫︹�⒙�
--function hello()
--	print 'hello'
--end
----氓赂娄氓聫鈥毭モ�÷矫︹�⒙�
--function test(str)
--	print('data from java is:'..str)
--	return 'haha'
--end
--
--function test1(data)
--	if type(data) == "table" then
--		PrintTable( data )
--		data["wwwww"] = "你好"
--		data["bbbb"] = {}
--		data["bbbb"]["a"] = 1
--		data["bbbb"]["b"] = false
--		data["bbbb"]["c"] = 1.1
--		return data
--	else
--		print('data from java is:' .. tostring(data))
--	end
--	return nil;
--end
--
--function PrintTable( vTable, vLevel )
--	local indent ="" -- i脣玫陆酶拢卢碌卤脟掳碌梅脫脙脣玫陆酶
--	if vLevel == nil then
--		vLevel = 0
--	end
--	
--	for i = 1, vLevel do
--		indent = indent.." "
--	end
--	
--	local tHasElm = false
--	for k, v in pairs( vTable ) do
--		if tHasElm == false then
--			print( indent.."{" )
--			tHasElm = true
--		end
--		if ( type( v ) == "table" ) then -- type(v) 碌卤脟掳脌脿脨脥脢卤路帽table 脠莽鹿没脢脟拢卢脭貌脨猫脪陋碌脻鹿茅拢卢
--			print( indent.." " .. "" .. k .. ":" )
--			PrintTable( v, vLevel + 1 )
--		else -- 路帽脭貌脰卤陆脫脢盲鲁枚碌卤脟掳脰碌
--			print( indent.." " .. "" .. k .. ":" ..tostring( v ).."" )
--		end
--	end
--
--	if tHasElm == true then
--		print( indent.."}" )
--	end
--end --
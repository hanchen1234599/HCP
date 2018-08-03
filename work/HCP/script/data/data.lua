function scriptLoad(  )
	print( "data main load success" )
end

function onTimer()
	--print( "data onTimer is run ..." )
end

function onProto( vUserID, vData )
	print( vUserID )
	gUtil.printTable(vData)
	System.send2User( vUserID, {mA = 1, mB = 2, mC = 3, mD = "44a", mE = { mF = "f", mA = "aaadd" }} )
end

gUtil = {}
gUtil.printTable = function( vTable, vLevel )
	local indent ="" -- i脣玫陆酶拢卢碌卤脟掳碌梅脫脙脣玫陆酶
	if vLevel == nil then
		vLevel = 0
	end
	
	for i = 1, vLevel do
		indent = indent.." "
	end
	
	local tHasElm = false
	for k, v in pairs( vTable ) do
		if tHasElm == false then
			print( indent.."{" )
			tHasElm = true
		end
		if ( type( v ) == "table" ) then -- type(v) 碌卤脟掳脌脿脨脥脢卤路帽table 脠莽鹿没脢脟拢卢脭貌脨猫脪陋碌脻鹿茅拢卢
			print( indent.." " .. "" .. k .. ":" )
			gUtil.printTable( v, vLevel + 1 )
		else -- 路帽脭貌脰卤陆脫脢盲鲁枚碌卤脟掳脰碌
			print( indent.." " .. "" .. k .. ":" ..tostring( v ).."" )
		end
	end

	if tHasElm == true then
		print( indent.."}" )
	end
end 

gUtil.print = function( vData )
	if type( vData ) == "table" then
		gUtil.printTable(vData)
	else
		print(tostring( vData ))
	end 
end

print( " data main is run  " )
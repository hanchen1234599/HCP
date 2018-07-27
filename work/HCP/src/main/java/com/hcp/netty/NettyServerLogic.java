package com.hcp.netty;

import com.hcp.data.BaseData;
import com.hcp.data.Commond;
import com.hcp.manager.ServerApp;
import com.hcp.xml.XmlUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerLogic extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client channelActivity ");
		ServerApp.getNet().putSession(ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client channelInactive ");
		ServerApp.getNet().removeSession(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("client channelRead ");
		BaseData bsd = (BaseData) msg;
		//System.out.println(bsd);
		//这里处理线程分配的问题
		if(bsd.isObject() != true)
			return;
		if(bsd.getObject("regist") != null) {
			ServerApp.getAppExecs().getmLogicThread("oper1").exec(new Commond("regist", bsd, ctx.channel()));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught " + cause);
	}
}

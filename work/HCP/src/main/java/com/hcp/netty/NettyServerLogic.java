package com.hcp.netty;

import com.hcp.app.ServerApp;
import com.hcp.shunt.SessionContainer;
import com.hcp.shunt.Shunt;
import com.hcp.util.BaseData;
import com.hcp.util.Commond;
import com.hcp.util.XmlUtil;

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
		if(bsd.isObject() != true)
			return;
		Shunt.getInstance().OnProto(ServerApp.getNet().getChannelSid(ctx.channel()), bsd);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught " + cause);
	}
}

package com.hcp.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	public static void serverRun() {
		System.out.println("***********************NettyServer begin***********************");
		EventLoopGroup boss = new NioEventLoopGroup(1);
		// EventLoopGroup worker = new
		// NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
		EventLoopGroup worker = new NioEventLoopGroup(2);
		ServerBootstrap b = new ServerBootstrap();
		b.group(boss, worker).option(ChannelOption.SO_BACKLOG, 1024).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// TODO Auto-generated method stub
						ch.pipeline().addLast("recv data decode", new NettyServerLengthFrameDecode());
						ch.pipeline().addLast("send data encode", new NettyServerLengthFrameEncode());
						ch.pipeline().addLast("data logic", new NettyServerLogic());
					}
				});
		ChannelFuture f = null;
		try {
			f = b.bind(9900).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
		System.out.println("***********************NettyServer end***********************");
	}
}

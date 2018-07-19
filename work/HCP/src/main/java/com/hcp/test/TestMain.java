package com.hcp.test;

import com.hcp.netty.NettyServer;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Server is begin");
		NettyServer.serverRun();
		System.out.println("Server is end");
	}
}

package com.hcp.test;

import com.hcp.manager.ServerApp;
import com.hcp.net.Net;
import com.hcp.netty.NettyServer;
import com.hcp.thread.AppThreadLogicManager;

public class TestMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Server is begin");
		ServerApp.start();
		System.out.println("Server is end");
	}
}

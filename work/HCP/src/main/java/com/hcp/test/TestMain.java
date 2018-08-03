package com.hcp.test;

import com.hcp.app.ServerApp;
import com.hcp.netty.NettyServer;
import com.hcp.shunt.SessionContainer;
import com.hcp.thread.AppThreadLogicManager;

public class TestMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Server is begin");
		try {
			ServerApp.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server is end");
	}
}

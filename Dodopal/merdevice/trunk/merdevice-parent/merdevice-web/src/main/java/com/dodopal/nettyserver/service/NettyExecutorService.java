package com.dodopal.nettyserver.service;

import com.dodopal.nettyserver.netty.ChildChannelHandler;
import com.dodopal.nettyserver.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//@Service
//@Repository
public class NettyExecutorService {
	@Autowired
	private ChildChannelHandler childChannelHandler;
	public void setChildChannelHandler(ChildChannelHandler childChannelHandler) {
		this.childChannelHandler = childChannelHandler;
	}
	public NettyExecutorService(ChildChannelHandler childChannelHandler) {
		System.err.println("---------- Spring自动加载         ---------- ");
		System.err.println("---------- 启动Netty线程池       ---------- ");
		NettyServer server = new NettyServer(childChannelHandler);
		//线程池
		ExecutorService es = Executors.newCachedThreadPool();
		//启动线程池
		es.execute(server);

	}
}

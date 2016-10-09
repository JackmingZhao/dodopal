package com.dodopal.nettyserver.netty;

import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.merdevice.business.constant.MerdeviceConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class NettyServer implements Runnable {
	@Autowired
	private ChildChannelHandler childChannelHandler;
	public NettyServer(ChildChannelHandler childChannelHandler) {
		this.childChannelHandler = childChannelHandler;
	}
	public void run() {
		System.err.println("---------- 服务端开启等待连接 ----------");
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 1024 * 10);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.option(ChannelOption.SO_RCVBUF, 1024 * 10);
			b.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(1024 * 10));
//			b.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
			b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//			b.childOption(ChannelOption.SO_TIMEOUT,6000);
//			b.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS,6000);
//			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			b.childHandler(childChannelHandler);
			//TODO 绑定端口
			int port = Integer.parseInt(DodopalAppVarPropsUtil.getStringProp(MerdeviceConstants.TCPPORT));
			ChannelFuture f = b.bind(port).sync();
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

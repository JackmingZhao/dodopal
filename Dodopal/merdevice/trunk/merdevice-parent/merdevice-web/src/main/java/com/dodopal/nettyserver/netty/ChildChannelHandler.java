package com.dodopal.nettyserver.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

//@Service
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
    @Autowired
    private MerdeviceHanlder merdeviceHanlder;

    public void setMerdeviceHanlder(MerdeviceHanlder merdeviceHanlder) {
        this.merdeviceHanlder = merdeviceHanlder;
    }

    @Override
    protected void initChannel(SocketChannel e) throws Exception {
//		e.pipeline().addLast(new LengthFieldBasedFrameDecoder(4096,0,4));
        e.pipeline().addLast(new MyStringDecoder(Charset.forName("GB2312")));
//		e.pipeline().addLast(new FixedLengthFrameDecoder(3000));
        e.pipeline().addLast(new StringEncoder(Charset.forName("GB2312")));
        e.pipeline().addLast(merdeviceHanlder);
    }
}

/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.nettyserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by lenovo on 2016/3/30.
 */
public class MyStringDecoder extends ByteToMessageDecoder{
    private static Logger log = Logger.getLogger(MyStringDecoder.class);
    private final Charset charset;
    private int maxLength;

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public MyStringDecoder() {
        this(Charset.defaultCharset());
    }

    public MyStringDecoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

//        String test=msg.toString(this.charset);
//        if(Integer.parseInt(test.substring(0,4))!=test.length()){
//            return;
//        }
//        out.add(msg.toString(this.charset));
        if (buf.readableBytes() < 58) {
            return;
        }
        buf.markReaderIndex();
        if(this.getMaxLength()==0){
            byte[] decoded = new byte[58];
            buf.readBytes(decoded);
            String msg = new String(decoded);
            int i =Integer.parseInt(new String(msg.substring(0,4)));
            this.setMaxLength(i);
            buf.resetReaderIndex();
        }
        log.info("---------------------maxLength--------------------" + this.getMaxLength());
        log.info("---------------------buf.readableBytes--------------------" + buf.readableBytes());
        if(buf.readableBytes() == this.getMaxLength()){
            byte[] decoded1 = new byte[this.getMaxLength()];
            buf.readBytes(decoded1);
            String frame = new String(decoded1) ;
            this.setMaxLength(0);
            out.add(frame);
        }else{
            if (buf.readableBytes() < this.getMaxLength()) {
                buf.resetReaderIndex();
            }else if(buf.readableBytes() > this.getMaxLength()){
                ctx.close();//强迫关闭连接
            }
            return;
        }
    }
}

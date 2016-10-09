package com.dodopal.nettyserver.netty;

import com.dodopal.common.md5.V71MD5;
import com.dodopal.merdevice.business.constant.MerdeviceConstants;
import com.dodopal.merdevice.web.controller.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;

//@Service
@ChannelHandler.Sharable
public class MerdeviceHanlder extends ChannelHandlerAdapter {
    private static Logger log = Logger.getLogger(MerdeviceHanlder.class);
    @Autowired
    MessageHandler5003 messageHandler5003;
    @Autowired
    MessageHandler5001 messageHandler5001;
    @Autowired
    MessageHandler5101 messageHandler5101;
    @Autowired
    MessageHandler2011 messageHandler2011;
    @Autowired
    MessageHandler2101 messageHandler2101;
    @Autowired
    MessageHandler2201 messageHandler2201;
    @Autowired
    MessageHandler3011 messageHandler3011;
    @Autowired
    MessageHandler3101 messageHandler3101;
    @Autowired
    MessageHandler3201 messageHandler3201;
    @Autowired
    MessageHandler320101 messageHandler320101;
    @Autowired
    MessageHandler320106 messageHandler320106;
    @Autowired
    MessageHandler3301 messageHandler3301;
    @Autowired
    MessageHandler3401 messageHandler3401;
    @Autowired
    MessageHandler3411 messageHandler3411;
    @Autowired
    MessageHandler3421 messageHandler3421;
    @Autowired
    MessageHandler3501 messageHandler3501;
    @Autowired
    MessageHandler3511 messageHandler3511;

    /*
     * channelAction
     * channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().localAddress().toString()
                + " channelActive");
        String str = "Connect successfully" + " " + ctx.channel().id() + new Date() + " " + ctx.channel().localAddress();
        log.info("========客户端连入============" + str);
//		ctx.writeAndFlush(str);
    }

    /*
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().localAddress().toString()
                + " channelInactive");
    }

    /*
     * channelRead
     * channel 通道 Read 读
     * 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据 但是这个数据在不进行解码时它是ByteBuf类型的
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg != null && !"".equals(msg)) {
//			try{
            String str = "";
            String mes = msg.toString();
            log.info("接收到报文=====：" + mes);
            String returnDescription = mes.substring(4, 26);
            String tocheckSign = mes.substring(58, mes.length());
            String message = mes.substring(62, mes.length());
            String messageType = message.substring(0, 4);
            String reVer = message.substring(4, 6);
            String toCheckVal = mes.substring(26, 58);
            if(log.isInfoEnabled()){
                log.info("报文所带签名值为======"+toCheckVal);
            }
            String sifnCheckval = V71MD5.MD5Encode(tocheckSign + MerdeviceConstants.INVERSE_KEY);
            if(log.isInfoEnabled()){
                log.info("验签值为======"+sifnCheckval);
            }
            if (!toCheckVal.equals(sifnCheckval.toUpperCase())) {
                //发送时间
                String reDate = message.substring(6, 20);
                //城市代码
                String recityCode = message.substring(32, 36);
                log.info("================签名错误！==========");
                str = String.valueOf(Integer.parseInt(messageType) + 1) +
                        //版本号
                        reVer +
                        //发送时间
                        reDate +
                        //特殊域启用该标志
                        "00" +
                        //特殊域长度
                        "0000" +
                        //应答码
                        MerdeviceConstants.CHECK_FAILURE +
                        //城市代码
                        recityCode;
                String newStirng = String.format("%0" + 4 + "d", str.length() + 4);
                str = newStirng + str;
            }
            log.info("-----" + messageType + "收到报文--------" + mes);
            //5001
            if ("5001".equals(messageType)) {
                str = messageHandler5001.message5001(message);
            }
            //5003
            if ("5003".equals(messageType)) {
//			MessageHandler5003 messageHandler5003 = new MessageHandler5003();
                str = messageHandler5003.message5003(message);
            }
            //5101
            if ("5101".equals(messageType)) {
                str = messageHandler5101.message5101(message);
            }
            //3201
            if ("3201".equals(messageType)) {
                if ("01".equals(reVer)) {
                    str = messageHandler320101.message320101(message);
                }else if("06".equals(reVer)){
                    str = messageHandler320106.message320106(message);
                } else {
                    str = messageHandler3201.message3201(message);
                }
            }
            //2011
            if ("2011".equals(messageType)) {
                str = messageHandler2011.message2011(message);
            }
            //2101
            if ("2101".equals(messageType)) {
                str = messageHandler2101.message2101(message);
            }
            //2201
            if ("2201".equals(messageType)) {
                str = messageHandler2201.message2201(message);
            }
            //3011
            if ("3011".equals(messageType)) {
                str = messageHandler3011.message3011(message);
            }
            //3101
            if ("3101".equals(messageType)) {
                str = messageHandler3101.message3101(message);
            }
            //3301
            if ("3301".equals(messageType)) {
                str = messageHandler3301.message3301(message);
            }
            //3401
            if ("3401".equals(messageType)) {
                str = messageHandler3401.message3401(message);
            }
            //3411
            if ("3411".equals(messageType)) {
                str = messageHandler3411.message3411(message);
            }
            //3421
            if ("3421".equals(messageType)) {
                str = messageHandler3421.message3421(message);
            }
            //3501
            if ("3501".equals(messageType)) {
                str = messageHandler3501.message3501(message);
            }
            //3511
            if ("3511".equals(messageType)) {
                str = messageHandler3511.message3511(message);
            }
            //Md5加密
            String ciphertext = V71MD5.MD5Encode(str + MerdeviceConstants.SIGN_KEY);
            String testStr = URLDecoder.decode((returnDescription + ciphertext + str), "gbk");
            String messageLen = String.format("%0" + 4 + "d", testStr.getBytes("gbk").length + 4);
            String returnMsg = messageLen + returnDescription + ciphertext.toUpperCase() + str;
            log.info("--------" + messageType + "返回报文-------------" + returnMsg);
            ctx.writeAndFlush(URLDecoder.decode(returnMsg,"gb2312"));
//            String ttt = "测试测试大中国 1 a";
//            ctx.writeAndFlush(ttt);
//			}catch(Exception ex){
//				log.error("========error==============",ex);
//				ex.printStackTrace();
//			}
        }
    }

    /*
     * channelReadComplete
     * channel 通道 Read 读取 Complete 完成
     * 在通道读取完成后会在这个方法里通知，对应可以做刷新操作 ctx.flush()
     */
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /*
     * exceptionCaught
     * exception 异常 Caught 抓住
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.error("异常信息：\r\n" + cause.getMessage());
        cause.printStackTrace();
//        ctx.close();
        ctx.flush();
    }
}

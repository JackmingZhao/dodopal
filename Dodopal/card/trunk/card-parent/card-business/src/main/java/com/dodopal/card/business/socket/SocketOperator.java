package com.dodopal.card.business.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketOperator {

    private SocketChannel sc;
    private final int MAX_LENGTH = 10240;
    private ByteBuffer r_buff = ByteBuffer.allocate(MAX_LENGTH);
    private ByteBuffer w_buff = ByteBuffer.allocate(MAX_LENGTH);

    static Logger log = LoggerFactory.getLogger(SocketOperator.class);

    public String AsyncClient(String sendx, String ip, int port) throws Exception {
        String re = "";
        String sends = new String(sendx);
        log.info("Sending Message：" + sends);
        w_buff = ByteBuffer.wrap(sends.getBytes());
        try {
            InetSocketAddress addr = new InetSocketAddress(ip, port);
            sc = SocketChannel.open();

            sc.connect(addr);

            // 等待完成连接
            while (!sc.finishConnect());
            // 发送消息
            while (w_buff.hasRemaining())
                // 发送数据
                sc.write(w_buff);
            // 接收容器清空
            w_buff.clear();
            // 接收
            // 清空接收容器
            r_buff.clear();
            // 接收
            sc.read(r_buff);
            //
            r_buff.flip();
            re = decode(r_buff);
            sc.close();
            // r_buff.clear();
            w_buff.clear();
            log.info("Return Message:" + re);
            return re;
        }
        catch (IOException e) {
            log.error("Error in sending Message：" + e.toString());
            e.printStackTrace();
            throw e;
        }
        finally {
            if (sc != null) {
                try {
                    sc.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (r_buff != null) {
                r_buff.clear();

            }
            if (r_buff != null) {
                r_buff.clear();
            }

        }
    }

    public String AsyncClient1(String sendx) {
        // 本地测试使用
        int port = 7030;
        // 本地测试使用
        String host = "219.143.10.82";
        //        String host2 = "192.168.1.240";
        String re = "";
        String sends = new String(sendx);
        //        log.info("要发送报文：send Message" + sends);
        // System.out.println("要发送报文："+sends);
        w_buff = ByteBuffer.wrap(sends.getBytes());
        try {
            InetSocketAddress addr = new InetSocketAddress(host, port);

            sc = SocketChannel.open();
            //          sc.connect(addr);
            sc.socket().connect(addr, 30 * 1000);
            // 等待完成连接
            while (!sc.finishConnect())
                ;
            // 连接完成
            // System.out.println("connection has been established!");
            // 发送消息
            while (w_buff.hasRemaining())
                // 发送数据
                sc.write(w_buff);
            // 接收容器清空
            w_buff.clear();
            // 接收
            // 清空接收容器
            r_buff.clear();
            // 接收
            sc.read(r_buff);
            r_buff.flip();
            re = decode(r_buff);
            //            log.info("first:" + re);
            int num = 0;
            if (re.length() > 25) {
                String textLength = new String(re.substring(21, 25));
                num = Integer.parseInt(textLength);
            }
            //            log.info("baowenchangdu:" + num);
            String re_temp = re;
            Date dt = new Date();
            long startTime = dt.getTime() + 60000;
            long nowTime = 0;
            while (true) {
                if ((num + 41) == re_temp.length()) {
                    break;
                } else {
                    r_buff.rewind();
                    sc.read(r_buff);
                    r_buff.flip();
                    re = decode(r_buff);
                    re_temp += re;
                    //                    log.info("xunhuanneirong:" + re_temp);
                }
                if (startTime > nowTime) {
                    nowTime = dt.getTime();
                } else {
                    break;
                }
            }

            sc.close();
            // r_buff.clear();
            w_buff.clear();
            //            log.info("返回报文：return Message" + re_temp);
            return re_temp;
        }
        catch (IOException e) {
            //            log.error("发送报文出现问题：" + e.toString());
            e.printStackTrace();
            return null;
        }
        finally {
            if (sc != null) {
                try {
                    sc.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (r_buff != null) {
                r_buff.clear();

            }
            if (r_buff != null) {
                r_buff.clear();
            }

        }
    }

    /**
     * ByteBuffer 转化为 String
     * @param buffer
     * @return
     */
    public String decode(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("gb2312");
            decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            return charBuffer.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

}

package com.dodopal.common.util;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MemcachedTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		MemcachedClient mc = new MemcachedClient(new BinaryConnectionFactory(),AddrUtil.getAddresses("192.168.1.250:11211"));
		//mc.set("key", 0, "Value");
		System.out.println(mc.get("STYLE"));
//		int i=0;
//		while(true){
//			try{
//			    i++;
//				System.out.println(mc.get("key"));
//				System.out.println(mc.get("key2"));
//				Thread.sleep(3000);
//				if(i==10) {
//				    mc.flush();
//				}
//			}catch(Exception e){
//				Thread.sleep(3000);
//				e.printStackTrace();
//			}
//		}
    }
	
}

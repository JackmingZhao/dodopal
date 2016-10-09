package com.dodopal.card.web;

import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DDPOracleLog;

public class ThreadTest extends Thread {
    
    private DDPOracleLog<ThreadTest> orclLog = new DDPOracleLog<>(ThreadTest.class);
    
    private int i;

    public ThreadTest() {
    }

    public ThreadTest(int i) {
        this.i = i;
    }

    public void run() {
        System.out.println(i);
//        for (int j = 0; j < 100; j++) {
        SysLog syslog = new SysLog();//日志对象
        syslog.setTradeStart(i);
        syslog.setInParas(i + "");
        syslog.setDescription(i + "");
        syslog.setClassName(i + "");
        syslog.setMethodName(i + "");
        
        orclLog.info(syslog);
//        }
        
        System.err.println("end:" + i);
    }
}

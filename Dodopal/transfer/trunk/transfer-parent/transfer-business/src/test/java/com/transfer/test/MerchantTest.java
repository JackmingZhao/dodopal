package com.transfer.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.service.ProxyInfoTbService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:transfer-business-test-context.xml"})
public class MerchantTest {
	
    @Autowired
    private ProxyInfoTbService proxyInfoTbService;
	
    @Test
    public void testProxyInfotbS() {
    	DodopalResponse<String> l =  proxyInfoTbService.insertDataByProxyId();	// where t.cityno = '100000-1110' and t.proxytypeid = 1
    	System.out.println("........" + ResponseCode.SUCCESS.equals(l.getCode()) + "...........................");
    	System.out.println("........END...........................");
    }
}

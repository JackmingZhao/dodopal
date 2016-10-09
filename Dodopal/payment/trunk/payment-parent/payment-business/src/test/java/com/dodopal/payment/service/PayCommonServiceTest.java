package com.dodopal.payment.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.payment.business.model.PayWay;
import com.dodopal.payment.business.service.PayCommonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayCommonServiceTest {
    
    @Autowired
    private PayCommonService payWayService;
    @Test
    public void testPayWayServce(){
     //  String payWayId = "10000000000000000086";
      // String testResult = payWayService.findPaywayCommonByPayWayId(payWayId);
        
     // System.out.println(testResult);   
    }
}

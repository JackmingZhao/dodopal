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

import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.PayWay;
import com.dodopal.payment.business.service.PayWayService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayWayServiceTest {
    
    @Autowired
    private PayWayService payWayService;
    @Test
    public void testPayWayServce(){
        List<PayWay>testResult = payWayService.findCommonExternal("014381000000169");
        
        if (testResult != null) {
            for (PayWay dp : testResult) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    
    @Test
    public void testPayWayServce1(){
        List<PayWay>testResult = payWayService.findPayWayGeneral("2");
        
        if (testResult != null) {
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
            for (PayWay dp : testResult) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    
    @Test
    public void testPayWayByPayType(){
        List<PayWay>testResult = payWayService.findPayWayByPayType(null, "3");
        
        if (testResult != null) {
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
            for (PayWay dp : testResult) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    
    @Test
    public void vailTest(){
        
    }
}

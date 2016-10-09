package com.dodopal.payment.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.service.PayTraTransactionService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月27日 下午3:47:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayTraTransactionServiceTest {
    @Autowired
    private PayTraTransactionService payTranService;
    
    
    @Test
    public void testFindById(){
        PayTraTransaction payTra = new PayTraTransaction();
        payTra = payTranService.findPayTraTransactionById("10000000000000000001");
        System.out.println("##########################################");
        System.out.println(ReflectionToStringBuilder.toString(payTra, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("##########################################");
    
    }
//    if (testResult != null) {
//        System.out.println("##########################################");
//        System.out.println(ReflectionToStringBuilder.toString(testResult, ToStringStyle.MULTI_LINE_STYLE));
//        System.out.println("##########################################");
//        for (com.dodopal.payment.business.model.Test dp : testResult) {
//            System.out.println("##########################################");
//            System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
//            System.out.println("##########################################");
//        }
//    }
}

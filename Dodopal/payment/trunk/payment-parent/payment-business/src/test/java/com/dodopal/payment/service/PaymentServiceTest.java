package com.dodopal.payment.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.service.PayTraTransactionService;
import com.dodopal.payment.business.service.PaymentService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月27日 下午3:46:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;
    @Test
    public void testFindById(){
        Payment py= paymentService.findPaymentById("1");
        System.out.println("##########################################");
        System.out.println(ReflectionToStringBuilder.toString(py, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("##########################################");

    }
}

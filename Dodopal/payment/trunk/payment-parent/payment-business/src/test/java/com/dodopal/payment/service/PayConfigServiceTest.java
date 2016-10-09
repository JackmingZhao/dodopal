package com.dodopal.payment.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.payment.business.service.PayConfigService;
import com.dodopal.payment.business.service.PaymentService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月3日 下午6:41:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayConfigServiceTest {
    @Autowired
    private PayConfigService payConfigService;
    @Test
    public void batchStop(){
        List list = new ArrayList();
        list.add("10000000000000000015");
        list.add("2");
        payConfigService.batchActivatePayConfig("1", list, "lifeng");
    }
}

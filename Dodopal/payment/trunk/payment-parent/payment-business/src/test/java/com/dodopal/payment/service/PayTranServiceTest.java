package com.dodopal.payment.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;
import com.dodopal.payment.business.service.PayTraTransactionService;
import com.dodopal.payment.business.service.PayTranService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月17日 下午7:34:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:payment-business-test-context.xml"})
public class PayTranServiceTest {
    @Autowired
    private PayTranService payTranService;
    @Autowired
    private PayTraTransactionService  transactionService;
    @Test
    public void vailTest(){
        PayTranDTO payTranDTO = new PayTranDTO();
        payTranDTO.setCusTomerType("0");
        payTranDTO.setCusTomerCode("123");
        System.out.println(payTranService.checkMerOrUserCode(payTranDTO));
    }
    
    @Test
    public void vailTest1(){
        PayTranDTO payTranDTO = new PayTranDTO();
        PayTraTransactionQuery query = new PayTraTransactionQuery();
        DodopalDataPage<PayTraTransaction> page = transactionService.findPayTraTransactionByPage(query);
        System.out.println(page.getRecords().size());
    }
}

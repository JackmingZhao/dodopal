package com.dodopal.oss.business.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.PayTraTransaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class PayTraTransactionMapperTest {


    @Autowired
    private PayTraTransactionMapper payTraTransactionMapper;
    
    @Test
    public void transactionMapperTest(){
        List<PayTraTransaction> tranList = payTraTransactionMapper.findPayTraTransactionByList(100000);
        for(PayTraTransaction tran :tranList){
            System.out.println("tran:"+tran.getTranCode());
        }
    }
}

package com.dodopal.payment.facade;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

public class PayFacadeTest {
    private PayFacade Facade;
    @Before 
    public void setUp(){  
        String hessianUrl ="http://localhost:8085/payment-web/hessian/index.do?id=";
        Facade=RemotingCallUtil.getHessianService(hessianUrl,PayFacade.class);        
   }
    @After
    public void tearDown(){
    }
    @Test
    public void testFind() {
        String merCode = "048241000000174";
        DodopalResponse<List<PayWayDTO>> p = Facade.findPayWay(true, merCode);
        System.out.println(p);
    }
    @Test
    public void freezeAccountAmtTest(){
        PayTranDTO transactionDTO = new PayTranDTO();
        transactionDTO.setAmount(121231.54);
        transactionDTO.setBusinessType("0");
        transactionDTO.setCityCode("123city4");
        transactionDTO.setCusTomerCode("014381000000169");
        transactionDTO.setCusTomerType("1");
        transactionDTO.setExtFlg(false);
        transactionDTO.setGoodsName("测试1");
        transactionDTO.setSource("0");
        transactionDTO.setOrderCode("订单号1");
        transactionDTO.setOrderDate(new Date());
        System.out.println(Facade.freezeAccountAmt(transactionDTO).getMessage());
    }
}

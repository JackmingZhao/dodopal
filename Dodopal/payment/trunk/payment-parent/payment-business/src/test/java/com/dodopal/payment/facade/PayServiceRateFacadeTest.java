package com.dodopal.payment.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.dto.query.PayConfigQueryDTO;
import com.dodopal.api.payment.facade.PayConfigFacade;
import com.dodopal.api.payment.facade.PayServiceRateFacade;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

public class PayServiceRateFacadeTest {
    private PayServiceRateFacade facade;
    @Before 
    public void setUp(){  
        String hessianUrl ="http://localhost:8085/payment-web/hessian/index.do?id=";
        facade=RemotingCallUtil.getHessianService(hessianUrl,PayServiceRateFacade.class);        
   }
    @After
    public void tearDown(){
        
    }
    
    
    @Test
    public void findPayServiceRateTest(){
        DodopalResponse<PayServiceRateDTO> tempResult = facade.findPayServiceRate("10000000000000000091", "99", 301);
        System.out.println(tempResult);
    }
  

}

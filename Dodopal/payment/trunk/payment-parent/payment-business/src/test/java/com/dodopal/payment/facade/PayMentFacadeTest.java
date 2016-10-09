package com.dodopal.payment.facade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PaymentDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.dto.query.PaymentQueryDTO;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.api.payment.facade.PaymentFacade;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月25日 下午2:37:39
 */
public class PayMentFacadeTest {
    private PaymentFacade Facade;
    @Before 
    public void setUp(){  
        String hessianUrl ="http://localhost:8085/payment-web/hessian/index.do?id=";
        Facade=RemotingCallUtil.getHessianService(hessianUrl,PaymentFacade.class);        
   }
    @After
    public void tearDown(){
        
    }
    @Test
    public void testFind() {
        PaymentQueryDTO query = new PaymentQueryDTO();
        query.setPayWayName("支付");;
        PageParameter page = new PageParameter();
        page.setPageNo(1);
        page.setPageSize(10);
        query.setPage(page);
        DodopalResponse<DodopalDataPage<PaymentDTO>> p = Facade.findPayMentByPage(query);
        System.out.println(p);
    }
}

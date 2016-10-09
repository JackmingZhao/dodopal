package com.dodopal.payment.facade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.Dynamic.Parameter;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 下午5:06:24
 */
public class TraTransactionFacadeTest {
  private PayTraTransactionFacade Facade;
  @Before 
  public void setUp(){  
      String hessianUrl ="http://localhost:8085/payment-web/hessian/index.do?id=";
      Facade=RemotingCallUtil.getHessianService(hessianUrl,PayTraTransactionFacade.class);        
 }
  @After
  public void tearDown(){
      
  }
  @Test
  public void testFind() {
      PayTraTransactionQueryDTO query = new PayTraTransactionQueryDTO();
      query.setMerOrUserName("ks");
      PageParameter page = new PageParameter();
      page.setPageNo(1);
      page.setPageSize(10);
      query.setPage(page);
      DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> p = Facade.findPayTraTransactionByPage(query);
      System.out.println(p);
  }
  
  @Test
  public void testFind1() {
      PayTraTransactionQueryDTO query = new PayTraTransactionQueryDTO();
      PageParameter parameter = new PageParameter();
      query.setPage(parameter);
      DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> page = Facade.findPayTraTransactionByPage(query);
      System.out.println(page.getResponseEntity());
  }
}

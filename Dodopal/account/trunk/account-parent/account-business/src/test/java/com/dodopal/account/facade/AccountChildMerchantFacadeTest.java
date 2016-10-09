package com.dodopal.account.facade;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dodopal.api.account.dto.ChildMerchantAccountChangeDTO;
import com.dodopal.api.account.dto.query.ChildMerFundChangeQueryDTO;
import com.dodopal.api.account.facade.AccountChildMerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;


public class AccountChildMerchantFacadeTest {
    
    private AccountChildMerchantFacade facade;
  
    @Before 
    public void setUp(){  
        String hessianUrl ="http://localhost:8087/account-web/hessian/index.do?id=";
        facade=RemotingCallUtil.getHessianService(hessianUrl,AccountChildMerchantFacade.class);        
   }
    @After
    public void tearDown(){
        
    }
    
    
    //账户调帐
    @Test
    public void accountChange(){
       
        DodopalResponse<List<ChildMerchantAccountChangeDTO>> response = null;
        ChildMerFundChangeQueryDTO query = new ChildMerFundChangeQueryDTO();
        query.setMerParentCode("013541000000190");
        try {
            response =facade.findAccountChangeChildMerByList(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
        if(response != null){
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
        }
        
    }
 

}

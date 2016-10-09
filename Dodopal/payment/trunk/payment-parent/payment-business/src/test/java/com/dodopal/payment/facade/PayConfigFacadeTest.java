package com.dodopal.payment.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayConfigQueryDTO;
import com.dodopal.api.payment.facade.PayConfigFacade;
import com.dodopal.api.payment.facade.PaymentFacade;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.payment.business.model.PayConfig;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午9:39:16
 */
public class PayConfigFacadeTest {
    private PayConfigFacade facade;
    @Before 
    public void setUp(){  
        String hessianUrl ="http://localhost:8085/payment-web/hessian/index.do?id=";
        facade=RemotingCallUtil.getHessianService(hessianUrl,PayConfigFacade.class);        
   }
    @After
    public void tearDown(){
        
    }
    
    @Test
    public void savePayConfigTest(){
        PayConfigDTO config = new PayConfigDTO();
     Map map = new HashMap();
     map.put("cityCode", "112");
     map.put("rate", 2.1);
     map.put("cityName", "jfsi");
     List list = new ArrayList();
     list.add(map);
        System.out.println(facade.icdcPayCreate(list));
    }
    @Test 
    public void saveIdcd(){
        List<Map<String,Object>>list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("cityCode", "1231231");
        map.put("cityName", "洪城一卡通");
        map.put("rate", 2.5);
        list.add(map);
       System.out.println(facade.icdcPayCreate(list)); 
    }
    @Test
    public void findPayConfigTest(){
        PayConfigDTO config = new PayConfigDTO();
        PayConfigQueryDTO configQueryDTO = new PayConfigQueryDTO();
        PageParameter page = new PageParameter();
        page.setPageNo(1);
        page.setPageSize(10);
        configQueryDTO.setPage(page);
        DodopalResponse<DodopalDataPage<PayConfigDTO>> tempResult = facade.findPayConfigByPage(configQueryDTO);
        System.out.println(tempResult);
    }
}

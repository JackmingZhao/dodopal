package com.dodopal.users.facadeImpl;


import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dodopal.api.users.dto.PosLogDTO;
import com.dodopal.api.users.dto.PosLogQueryDTO;
import com.dodopal.api.users.facade.PosLogFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

public class PosLogFacadeTest {

    //@Autowired
    private PosLogFacade service;   
    
	
	    @Before 
	    public void setUp(){  
	    	String hessianUrl ="http://localhost:8099/users-web/hessian/index.do?id=";
	    	service=RemotingCallUtil.getHessianService(hessianUrl,PosLogFacade.class);	    
	   }
	    @After
	    public void tearDown(){
	    	
	    }

    /**
     * Pos操作日志查询
     */
    //@Test
    public void testposOperBundLing() {
        try {
        	PosLogQueryDTO findDto = new PosLogQueryDTO();
        	//findDto.setCode("102");
        	
        	PageParameter page = new PageParameter();
        	page.setPageNo(2);
        	page.setPageSize(5);
        	findDto.setPage(page);
        	//findDto.setSource("1");
        	DodopalResponse<DodopalDataPage<PosLogDTO>> response = service.findPosLogList(findDto);
        
        	if(response!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                if(ResponseCode.SUCCESS.equals(response.getCode())){
	                 for (PosLogDTO dp : response.getResponseEntity().getRecords()) {
	                    System.out.println("##########################################");
	                    System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
	                    System.out.println("##########################################");
	                }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}

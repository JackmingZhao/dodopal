package com.dodopal.users.facadeImpl;



import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosLogDTO;
import com.dodopal.api.users.dto.PosLogQueryDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.facade.PosFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

public class PosFacadeTest {

    //@Autowired
    private PosFacade service;   
    
	
	    @Before 
	    public void setUp(){  
	    	String hessianUrl ="http://localhost:8099/users-web/hessian/index.do?id=";
	    	service=RemotingCallUtil.getHessianService(hessianUrl,PosFacade.class);	    
	   }
	    @After
	    public void tearDown(){
	    	
	    }

    /**
     * Pos绑定
     */
   //@Test
    public void testposOperBundLing() {
        try {
        	String merCode = "017701000000021";
        	String[] posCodes={"test"};
        	OperUserDTO operUser = new OperUserDTO();
        	operUser.setId("admin");
        	operUser.setOperName("Admin");
        	DodopalResponse<String> response = service.posOper(PosOperTypeEnum.OPER_BUNDLING, merCode, posCodes, operUser,SourceEnum.OSS,null);
        
        	if(response!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Pos解绑
     */
    //@Test
    public void testposOperUnBundLing() {
        try {
        	String merCode = "017701000000021";
        	String[] posCodes={"test"};
        	OperUserDTO operUser = new OperUserDTO();
        	operUser.setId("admin");
        	operUser.setOperName("Admin");
        	DodopalResponse<String> response = service.posOper(PosOperTypeEnum.OPER_UNBUNDLING, merCode, posCodes, operUser,SourceEnum.OSS,null);
        
        	if(response!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Pos禁用
     */
    //@Test
    public void testposOperDisable() {
        try {
        	String merCode = "017701000000021";
        	String[] posCodes={"test","test2"};
        	OperUserDTO operUser = new OperUserDTO();
        	operUser.setId("admin");
        	operUser.setOperName("Admin");
        	DodopalResponse<String> response = service.posOper(PosOperTypeEnum.OPER_DISABLE, null, posCodes, operUser,SourceEnum.OSS,null);
        
        	if(response!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Pos启用
     */
    //@Test
    public void testposOperEnable() {
        try {
        	String merCode = "017701000000021";
        	String[] posCodes={"test","test2"};
        	OperUserDTO operUser = new OperUserDTO();
        	operUser.setId("admin");
        	operUser.setOperName("Admin");
        	DodopalResponse<String> response = service.posOper(PosOperTypeEnum.OPER_ENABLE, null, posCodes, operUser,SourceEnum.OSS,null);
        
        	if(response!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Pos查询/分页
     */
    //@Test
    public void testFindPosPage() {
        try {
        	PosQueryDTO findDto = new PosQueryDTO();
        	//findDto.setCode("102");
        	//M0009
        	findDto.setMerchantCode("017701000000022");
        	PageParameter page = new PageParameter();
        	page.setPageNo(2);
        	page.setPageSize(1);
        	findDto.setPage(page);
        	DodopalResponse<DodopalDataPage<PosDTO>> response = service.findPosListPage(findDto);
        
        	if(response!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                if(ResponseCode.SUCCESS.equals(response.getCode())){
	                 for (PosDTO dp : response.getResponseEntity().getRecords()) {
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

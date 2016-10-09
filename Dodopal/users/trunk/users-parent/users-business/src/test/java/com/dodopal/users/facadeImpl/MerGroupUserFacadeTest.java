package com.dodopal.users.facadeImpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerGroupUserDTO;
import com.dodopal.api.users.dto.MerGroupUserFindDTO;
import com.dodopal.api.users.dto.query.MerGroupUserQueryDTO;
import com.dodopal.api.users.facade.MerGroupUserFacade;
import com.dodopal.api.users.facade.SendFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;
import com.dodopal.users.business.model.MobileCodeCheck;

public class MerGroupUserFacadeTest {

    //@Autowired
    private MerGroupUserFacade service;   
    
	
	    @Before 
	    public void setUp(){  
	    	String hessianUrl ="http://localhost:8082/users-web/hessian/index.do?id=";
	    	service=RemotingCallUtil.getHessianService(hessianUrl,MerGroupUserFacade.class);	    
	   }
	    @After
	    public void tearDown(){
	    	
	    }

    /**
     * 集团用户查询
     */
    //@Test
    public void testfindMerGpUsers() {
        try {
        	MerGroupUserFindDTO findDto =new MerGroupUserFindDTO();
        	findDto.setMerCode("M0000000009");
        	DodopalResponse<List<MerGroupUserDTO>> response = service.findMerGpUsers(findDto,"0");
        
        	if(response!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                if(ResponseCode.SUCCESS.equals(response.getCode())){
	                 for (MerGroupUserDTO dp : response.getResponseEntity()) {
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
    
    /**
     * 集团用户查询(分页)
     */
    //@Test
    public void testfindMerGpUsersByPage() {
        try {
        	MerGroupUserQueryDTO findDto =new MerGroupUserQueryDTO();
        	PageParameter page = new PageParameter();
        	page.setPageNo(2);
        	page.setPageSize(1);
        	findDto.setPage(page);
        	findDto.setMerCode("018491000000022");
        	DodopalResponse<DodopalDataPage<MerGroupUserDTO>> response = service.findMerGpUsersByPage(findDto,SourceEnum.PORTAL);
        
        	if(response!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                if(ResponseCode.SUCCESS.equals(response.getCode())){
	                 for (MerGroupUserDTO dp : response.getResponseEntity().getRecords()) {
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
    
    /**
     * 集团用户详情
     */
    //@Test
    public void testfindMerGpUserById() {
        try {
        	DodopalResponse<MerGroupUserDTO> response = service.findMerGpUserById("0"); 
        	System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
        	if(ResponseCode.SUCCESS.equals(response.getCode())){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");                
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 添加集团用户(支持批量添加：用于导入)
     */
    //@Test
    public void testSaveMerGpUsers() {
        try {
        	List<MerGroupUserDTO> gpUserDtos = new ArrayList<MerGroupUserDTO>();
        	MerGroupUserDTO user = new MerGroupUserDTO();
        	//user.setDepId("1");
        	user.setDepName("技术部4");
        	user.setMerCode("M0000000009");
        	user.setRechargeAmount(500);
        	user.setCardCode("T-900006");
        	user.setGpUserName("技术部4-张三");
        	user.setCardType("CPU");
        	user.setRechargeWay("固定充钱包");
        	gpUserDtos.add(user);
        	user = new MerGroupUserDTO();
        	user.setDepName("技术部2");
        	user.setMerCode("M0000000009");
        	user.setRechargeAmount(400);
        	user.setCardCode("T-900007");
        	user.setGpUserName("技术部2-李四");
        	user.setCardType("CPU");
        	user.setRechargeWay("固定充钱包");
        	gpUserDtos.add(user);
//        	
//        	user = new MerGroupUserDTO();
//        	user.setDepId("1");
//        	user.setMerCode("M0000000009");
//        	user.setRechargeAmount(0);
//        	user.setCardCode("T-200005");
//        	user.setGpUserName("集团用户5");
//        	user.setCardType("CPU");
//        	user.setRechargeWay("固定充钱包");
//        	gpUserDtos.add(user);
        	
        	
        	DodopalResponse<String> response = service.saveMerGpUsers(gpUserDtos); 
        	
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");                
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testSaveMerDep() {
        try {
            List<MerGroupUserDTO> gpUserDtos = new ArrayList<MerGroupUserDTO>();
            MerGroupUserDTO user = new MerGroupUserDTO();
            //user.setDepId("1");
            user.setDepName("技术部4");
            user.setMerCode("M0000000009");
            user.setRechargeAmount(500);
            user.setCardCode("T-900006");
            user.setGpUserName("技术部4-张三");
            user.setCardType("CPU");
            user.setRechargeWay("固定充钱包");
            gpUserDtos.add(user);
            for(int i =0;i<100;i++){
                user = new MerGroupUserDTO();
                user.setDepName("技术部"+i);
                user.setMerCode("M0000000009");
                user.setRechargeAmount(400);
                user.setCardCode("T-900007"+i);
                user.setGpUserName("编号"+i);
                user.setCardType("CPU");
                user.setRechargeWay("固定充钱包");
                gpUserDtos.add(user);
            }
//          
//          user = new MerGroupUserDTO();
//          user.setDepId("1");
//          user.setMerCode("M0000000009");
//          user.setRechargeAmount(0);
//          user.setCardCode("T-200005");
//          user.setGpUserName("集团用户5");
//          user.setCardType("CPU");
//          user.setRechargeWay("固定充钱包");
//          gpUserDtos.add(user);
            
            
            DodopalResponse<String> response = service.saveMerGpUsers(gpUserDtos); 
            
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");                
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 添加集团用户(单条)
     */
    //@Test
    public void testSaveMerGpUser() {
        try {
        	//List<MerGroupUserDTO> gpUserDtos = new ArrayList<MerGroupUserDTO>();
        	MerGroupUserDTO user = new MerGroupUserDTO();
        	user.setDepId("1");
        	user.setMerCode("M0000000009");
        	user.setRechargeAmount(600);
        	user.setCardCode("T-200006");
        	user.setGpUserName("集团用户6");
        	user.setCardType("CPU");
        	user.setRechargeWay("固定充钱包");
        	
        	DodopalResponse<String> response = service.saveMerGpUser(user); 
        	
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");                
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 修改集团用户
     */
    //@Test
    public void testUpdateMerGpUser() {
        try {
        	//List<MerGroupUserDTO> gpUserDtos = new ArrayList<MerGroupUserDTO>();
        	MerGroupUserDTO user = new MerGroupUserDTO();
        	
        	user.setId("10000000000000000004");
//        	user.setDepId("1");
        	user.setRechargeAmount(700);
//        	user.setCardCode("T-200005-1");
//        	user.setGpUserName("集团用户5-1");
//        	user.setCardType("M1");
        	user.setRemark("测试集团");
        	
        	DodopalResponse<Integer> response = service.updateMerGpUser(user); 
        	
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");                
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 删除集团用户
     */
    //@Test
    public void testdeleteMerGpUser() {
        try {
        	String userID  = "";        	
        	DodopalResponse<Integer> response = service.deleteMerGpUser(userID);
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");                
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}

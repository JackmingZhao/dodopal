package com.dodopal.oss.business.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.MerchantDelegate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class MerchantTest {
	 @Autowired
	 private MerchantService merchantService;
	 @Autowired
	 MerchantDelegate merchantdelegate;
	 @Test
	 public void kfMerchant(){
		 MerchantDTO merchantDto = new MerchantDTO();
	        MerchantUserDTO merchantUserDTO =new MerchantUserDTO();
	        //商户用户信息
	        merchantUserDTO.setMerUserName("ltt");
	        merchantUserDTO.setMerUserNickName("李3甜");
	        merchantUserDTO.setMerUserSex("1");
	        merchantUserDTO.setMerUserTelephone("13215987341");
	        merchantUserDTO.setMerUserIdentityType("0");
	        merchantUserDTO.setMerUserIdentityNumber("341889199708097230");
	        merchantUserDTO.setMerUserEmail("dodotewts@dodopal.com");
	        merchantUserDTO.setMerUserRemark("上海虹梅路323号");
	        merchantUserDTO.setMerUserMobile("13215987341");
	        merchantUserDTO.setActivate("0");//界面选择启用或者停用的时候，两者在开户的都关联
	        merchantUserDTO.setMerUserSource("1");//TODO
	        merchantUserDTO.setMerUserAdds("广东省2是");
	        merchantUserDTO.setCreateUser("admin");
	      //merchantDto.setCreateDate(createDate);TODO 创建时间
	        merchantDto.setMerchantUserDTO(merchantUserDTO);
	        
	       
	        //商户为空字段
	        merchantDto.setMerName("清3远一卡通");
	        merchantDto.setMerType("11");
	        merchantDto.setActivate("0");
	        merchantDto.setMerClassify("0");
	        merchantDto.setMerProperty("1");
	        merchantDto.setMerState("0");
	        merchantDto.setMerLinkUser("陆3千");
	        merchantDto.setMerLinkUserMobile("13215987341");
	        merchantDto.setMerAdds("广东省清远市");
	        merchantDto.setMerPro("广东");
	        merchantDto.setMerCity("清远");
	        merchantDto.setMerBusinessScopeId("0");
	        merchantDto.setMerFax("01");
	        merchantDto.setMerZip("452367");
	        merchantDto.setMerBankName("0");
	        merchantDto.setMerBankAccount("0007898009981");
	        merchantDto.setMerBankUserName("李32甜");
	        merchantDto.setCreateUser("admin");
	        //merchantDto.setCreateDate(createDate);TODO 创建时间
	        //补充信息
//	        merchantDto.setMerDdpLinkIp("192.168.1.898");
//	        merchantDto.setMerDdpLinkUser("王天宝");
//	        merchantDto.setMerDdpLinkUserMobile("13019087341");
	        
	        //业务费率表
	        List<MerBusRateDTO> merBusRateList = new ArrayList<MerBusRateDTO>();
	      
	        	MerBusRateDTO merBusRateDTOTemp = new MerBusRateDTO();
	        	merBusRateDTOTemp.setMerCode("M00000006787");
	        	merBusRateDTOTemp.setRateCode("004");
	        	merBusRateDTOTemp.setActivate("0");
	        	merBusRateDTOTemp.setProviderCode("330000");
	        	merBusRateDTOTemp.setRate(1);
	        	merBusRateDTOTemp.setRateType("1");
	        	merBusRateDTOTemp.setCreateUser("admin");
	        	merBusRateList.add(merBusRateDTOTemp);
	        
	        merchantDto.setMerBusRateList(merBusRateList);
	        
	        //自定义功能商户
	        List<MerFunctionInfoDTO> merFunctionInfoList = new ArrayList<MerFunctionInfoDTO>();
	  
	        	MerFunctionInfoDTO merFunctionInfoDTOTemp =new MerFunctionInfoDTO();
	        	merFunctionInfoDTOTemp.setMerFunCode("10");//客户功能编码
	        	merFunctionInfoDTOTemp.setMerFunName("角色分类");
	        	merFunctionInfoDTOTemp.setCreateUser("admin");
	        	merFunctionInfoList.add(merFunctionInfoDTOTemp);
	       
	        merchantDto.setMerDefineFunList(merFunctionInfoList);
	        System.out.println("保存商户信息");
	        DodopalResponse<String> code=merchantdelegate.saveMerchant(merchantDto);
	        System.out.println(code.getCode());
	 }
	 
	 
	 //测试编辑商户信息
	 @Test
	 public void editMerchant(){
		 DodopalResponse<MerchantDTO>  merchantDtos  = merchantdelegate.findMerchants("162921000000011");
		 System.out.println(merchantDtos.getResponseEntity().getMerParentCode());
		 System.out.println(merchantDtos.getResponseEntity().getMerParentName());
		 
	 }
}

package com.dodopal.users.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.MerKeyTypeFacade;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年4月29日 下午3:28:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerKeyTypeFacadeTest {
    @Autowired
    private MerKeyTypeFacade facade;
    @Autowired
    private MerchantUserFacade userfacade;
 
    
    //@Test
    public void testFindMerMD5PayPWDOrBackPayPWD(){
        MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
        merKeyTypeDTO.setMerCode("1234");
        merKeyTypeDTO =     facade.findMerMD5PayPWDOrBackPayPWD(merKeyTypeDTO, MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD).getResponseEntity();
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(merKeyTypeDTO, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
    }
    
    //@Test
    public void testUpdateMerMD5PayPwd(){
        MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
        merKeyTypeDTO.setMerCode("1234");
        merKeyTypeDTO.setMerMD5BackPayPWD("7654321");
        System.out.println(facade.updateMerMD5PayPwdOrBackPayPWD(merKeyTypeDTO, "1234567",MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD).getResponseEntity());
        
    }

   @Test
    public void testbatchStop(){
        MerchantUserDTO dto = new MerchantUserDTO();
        List<String> idList =  new ArrayList<String>();
        
        idList.add("10000000000000000017");
        
        idList.add("10000000000000000025");
       //System.out.println(userfacade.toStopOrStartUser("1",idList));
        
    }
    
    
}

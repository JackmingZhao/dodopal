package com.dodopal.users.facadeImpl;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerchantUserFacadeTest {
    @Autowired
    private MerchantUserFacade merchantUserFacade;

    @Test
    public void testResetPWDSendMsg() {
        try {
            MerchantUserDTO merUserDTO = new MerchantUserDTO();
            merUserDTO.setId("10000000000000000005");
            merUserDTO.setMerUserMobile("15618382874");

            DodopalResponse<Boolean> response = merchantUserFacade.resetPWDSendMsg(merUserDTO);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("####################");
                System.out.println(response.getResponseEntity());
                System.out.println("####################");
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testInsert() {
        MerchantUserDTO user = new MerchantUserDTO();
        user.setId("10000000000000000005");
        user.setActivate("0");
        user.setMerUserBirthday(new Date());
        user.setCreateDate(new Date());;
        user.setDelFlag("1");
        user.setMerUserEmail("test11@dudupal.com");
        user.setMerUserEmployeeDate(new Date());
        user.setMerUserIdentityNumber("11");
        user.setMerUserIdentityType("11");
        user.setMerUserLoginFaiCount(3);
        user.setMerUserName("wangwu");
        user.setUserCode("11114");
        user.setMerUserNickName("王五");
        user.setMerUserSex("1");
        user.setMerUserTelephone("13023211737");
        user.setMerUserMobile("1267111222");
        user.setMerUserLastLoginIp("123");
        user.setMerUserLockedDate(new Date());
        user.setMerUserRemark("三");
        user.setCityCode("13");
        user.setMerUserLastLoginDate(new Date());
        user.setMerUserType("12");
        user.setMerUserPWD("123122121");
        user.setMerUserSource("1");
        user.setPayInfoFlg("1");
        user.setMerUserFlag("1");
    }
    
    
    @Test
    public void testyyyy() {
        try {
            
           String id = "10000000000000000680";

            DodopalResponse<MerchantUserDTO> response = merchantUserFacade.findUserInfoById(id);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("####################");
                System.out.println(response.getResponseEntity());
                System.out.println("####################");
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

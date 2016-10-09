package com.dodopal.users.facadeImpl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MobileUserDTO;
import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.api.users.facade.UserLoginFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class UserLoginFacadeTest {
    @Autowired
    private UserLoginFacade userLoginFacade;

    @Test
    public void testLogin() {
        try {
            String userName = "lf001";
            String password = "16f5a43e6a36827e57771f41ad72";
            DodopalResponse<PortalUserDTO> response = userLoginFacade.login(userName, password);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
            } else {
                System.out.println("商户信息未找到...");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin2() {
        try {
            String userName = "admin";
            String password = "14e1b600b1fd579f47433b88e8d85291";
            String source = SourceEnum.VC.getCode();
            DodopalResponse<MobileUserDTO> response = userLoginFacade.login(userName, password, source, null, null);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
            } else {
                System.out.println("用户信息未找到..." + response.getCode() + ":" + response.getMessage());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

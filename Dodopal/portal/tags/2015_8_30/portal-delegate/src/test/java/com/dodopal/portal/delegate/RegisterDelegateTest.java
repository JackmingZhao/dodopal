package com.dodopal.portal.delegate;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:portal-delegate-test-context.xml"})
public class RegisterDelegateTest {
    @Autowired
    private RegisterDelegate registerDelegate;

    @Test
    public void testCheckMobileExist() {
        try {
            String mobile = "13012345670";
            DodopalResponse<Boolean> response = registerDelegate.checkMobileExist(mobile);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println(mobile + ":" + response.getResponseEntity());
            } else {
                System.out.println("校验失败，返回码：" + response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckUserNameExist() {
        try {
            String userName = "lf001";
            DodopalResponse<Boolean> response = registerDelegate.checkUserNameExist(userName);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println(userName + ":" + response.getResponseEntity());
            } else {
                System.out.println("校验失败，返回码：" + response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckMerchantNameExist() {
        try {
            String merName = "lifeng002";
            DodopalResponse<Boolean> response = registerDelegate.checkMerchantNameExist(merName);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println(merName + ":" + response.getResponseEntity());
            } else {
                System.out.println("校验失败，返回码：" + response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterUser() {
        try {
            MerchantUserDTO merUserBean = new MerchantUserDTO();
            merUserBean.setMerUserName("lf003");
            merUserBean.setMerUserPWD("16f5a43e6a36827e57771f41ad72");
            merUserBean.setMerUserMobile("13012345670");

            String dypwd = "";
            String serialNumber = "";

            DodopalResponse<MerchantUserDTO> response = registerDelegate.registerUser(merUserBean, dypwd, serialNumber);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
            } else {
                System.out.println("校验失败，返回码：" + response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterMerchant() {
        try {

            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setMerName("lifeng005");
            merchantDTO.setMerLinkUser("李峰");
            merchantDTO.setMerLinkUserMobile("13012345672");
            merchantDTO.setMerPro("3300");
            merchantDTO.setMerCity("1791");
            merchantDTO.setMerAdds("未来路005号");

            MerchantUserDTO merUserDTO = new MerchantUserDTO();
            merUserDTO.setMerUserName("lf005");
            merUserDTO.setMerUserPWD("16f5a43e6a36827e57771f41ad72");
            merchantDTO.setMerchantUserDTO(merUserDTO);

            String dypwd = "";
            String serialNumber = "";

            DodopalResponse<String> response = registerDelegate.registerMerchant(merchantDTO, dypwd, serialNumber);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println(response.getResponseEntity());
            } else {
                System.out.println("校验失败，返回码：" + response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

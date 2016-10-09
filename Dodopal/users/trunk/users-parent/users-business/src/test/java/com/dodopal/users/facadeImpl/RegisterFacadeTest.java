package com.dodopal.users.facadeImpl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.RegisterFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class RegisterFacadeTest {
    @Autowired
    RegisterFacade registerFacade;

    @Test
    public void testRegisterUser() {
        try {
            MerchantUserDTO merUserDTO = new MerchantUserDTO();
            merUserDTO.setMerUserName("lf03");
            merUserDTO.setMerUserPWD("16f5a43e6a36827e57771f41ad72");
            merUserDTO.setMerUserMobile("15618382875");
            String dypwd = "7178";
            String serialNumber = "32";
            DodopalResponse<MerchantUserDTO> response = registerFacade.registerUser(merUserDTO, dypwd, serialNumber);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                System.out.println(ReflectionToStringBuilder.toString(response.getResponseEntity(), ToStringStyle.MULTI_LINE_STYLE));
            } else {
                System.out.println("用户注册失败：" + response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterMerchant() {
        try {
            MerchantDTO merDTO = new MerchantDTO();
            merDTO.setMerName("lifeng003");
            merDTO.setMerLinkUser("李峰");
            merDTO.setMerLinkUserMobile("15618382876");
            merDTO.setMerPro("3300");
            merDTO.setMerCity("1791");
            merDTO.setMerAdds("未来路001号");

            MerchantUserDTO merUserDTO = new MerchantUserDTO();
            merUserDTO.setMerUserName("lifeng003");
            merUserDTO.setMerUserPWD("16f5a43e6a36827e57771f41ad72");
            merDTO.setMerchantUserDTO(merUserDTO);

            String dypwd = "7178";
            String serialNumber = "32";
            DodopalResponse<String> response = registerFacade.registerMerchant(merDTO, dypwd, serialNumber);
            if (ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                System.out.println(response.getResponseEntity());
            } else {
                System.out.println("商户注册失败：" + response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

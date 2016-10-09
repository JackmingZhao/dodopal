package com.dodopal.portal.delegate;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:portal-delegate-test-context.xml"})
public class UserLoginDelegateTest {
    @Autowired
    private UserLoginDelegate userLoginDelegate;

    @Test
    public void testLogin() {
        try {
            String userName = "ss009";
            String password = "6729ba93854ed5614d56608cfd810dc2";
            DodopalResponse<PortalUserDTO> response = userLoginDelegate.login(userName, password);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                PortalUserDTO merchantDTO = response.getResponseEntity();
                if (merchantDTO != null) {
                    /*商户信息*/
                    System.out.println("--------------------商户信息--------------------");
                    System.out.println(ReflectionToStringBuilder.toString(merchantDTO, ToStringStyle.MULTI_LINE_STYLE));
                    /*功能信息(权限)*/
                    System.out.println("--------------------功能信息(权限)--------------------");
                    List<MerFunctionInfoDTO> merFunInfoDTOList = merchantDTO.getMerFunInfoList();
                    if (!CollectionUtils.isEmpty(merFunInfoDTOList)) {
                        for (MerFunctionInfoDTO merFunInfoBeanTemp : merFunInfoDTOList) {
                            System.out.println(ReflectionToStringBuilder.toString(merFunInfoBeanTemp, ToStringStyle.MULTI_LINE_STYLE));
                        }
                    }
                } else {
                    System.out.println("未能获取商户信息");
                }
            } else {
                System.out.println("登录失败：" + response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

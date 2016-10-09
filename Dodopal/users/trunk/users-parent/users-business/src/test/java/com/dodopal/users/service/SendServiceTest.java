package com.dodopal.users.service;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.users.business.service.SendService;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class SendServiceTest {
    @Autowired
    SendService sendService;

    @Test
    public void testSend() {
        String mobileNum = "15618382874";
        try {
            // 1、默认验证码
            //DodopalResponse<Map<String, String>> response = sendService.send(mobileNum, MoblieCodeTypeEnum.USER_RG);
            // 2、指定验证码
            String dypwd = DDPStringUtil.getRandomStr(4);
            System.out.println("yyyy:"+dypwd);
            DodopalResponse<Map<String, String>> response = sendService.send(mobileNum, MoblieCodeTypeEnum.USER_RG, dypwd);

            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                Map<String, String> resultMap = response.getResponseEntity();
                if (!CollectionUtils.isEmpty(resultMap)) {
                    System.out.println("验证码：" + resultMap.get("dypwd"));
                    System.out.println("序号：" + resultMap.get("pwdseq"));
                }
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMoblieCodeCheck() {
        String mobileNum = "15618382874";
        String dypwd = "3k8t";
        String serialNumber = "75";
        try {
            DodopalResponse<String> response = sendService.moblieCodeCheck(mobileNum, dypwd, serialNumber);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("验证通过...");
            } else {
                System.out.println(response.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.dodopal.portal.delegate;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:portal-delegate-test-context.xml"})
public class SendDelegateTest {
    @Autowired
    private SendDelegate sendDelegate;

    @Test
    public void testSend() {
        try {
            String moblieNum = "15618382874";
            DodopalResponse<Map<String, String>> response = sendDelegate.send(moblieNum, MoblieCodeTypeEnum.USER_RG);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                Map<String, String> result = response.getResponseEntity();
                if (result != null) {
                    System.out.println("验证码：" + result.get("dypwd"));
                    System.out.println("序号：" + result.get("pwdseq"));
                }
            } else {

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMoblieCodeCheck() {
        try {
            String moblieNum = "15618382874";
            String dypwd = "hyn8";
            String pwdseq = "90";
            DodopalResponse<String> response = sendDelegate.moblieCodeCheck(moblieNum, dypwd, pwdseq);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                System.out.println("验证通过。。。");
            } else {

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

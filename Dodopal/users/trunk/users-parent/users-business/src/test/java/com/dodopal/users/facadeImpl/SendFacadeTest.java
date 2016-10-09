package com.dodopal.users.facadeImpl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dodopal.api.users.facade.SendFacade;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

public class SendFacadeTest {

    private SendFacade sendFacade;

    @Before
    public void setUp() {
        String hessianUrl = "http://localhost:8082/users-web/hessian/index.do?id=";
        sendFacade = RemotingCallUtil.getHessianService(hessianUrl, SendFacade.class);
    }

    @After
    public void tearDown() {

    }

    /**
     * 测试发短信
     */
    @Test
    public void testSend() {
        try {
            String moNum = "15618382874";
            sendFacade.send(moNum, MoblieCodeTypeEnum.USER_RG);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMoblieCodeCheck() {
        try {
            String moNum = "15618382874";
            DodopalResponse<String> rp = sendFacade.moblieCodeCheck(moNum, "y1r3", "95");
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(rp, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

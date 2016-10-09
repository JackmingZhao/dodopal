package com.dodopal.logservice.delegate;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.logservice.delegate.LogServiceDelegate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:logservice-delegate-test-context.xml"})
public class LogServiceDelegateTest {

    @Autowired
    private LogServiceDelegate logServiceDelegate;

    @Test
    public void testSayHello() {
        DodopalResponse<Map<String, String>> response = logServiceDelegate.sayHello("Dodopal");
        if (ResponseCode.SUCCESS.equals(response.getCode())) {
            System.out.println(response.getResponseEntity());
        }
    }

}

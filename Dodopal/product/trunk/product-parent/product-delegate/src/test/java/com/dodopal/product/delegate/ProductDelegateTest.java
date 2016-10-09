package com.dodopal.product.delegate;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.ProductDelegate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-delegate-test-context.xml"})
public class ProductDelegateTest {

    @Autowired
    private ProductDelegate productDelegate;

    @Test
    public void testSayHello() {
        DodopalResponse<Map<String, String>> response = productDelegate.sayHello("Dodopal");
        if (ResponseCode.SUCCESS.equals(response.getCode())) {
            System.out.println(response.getResponseEntity());
        }
    }

}

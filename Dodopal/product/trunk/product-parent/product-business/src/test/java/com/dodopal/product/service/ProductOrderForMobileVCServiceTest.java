package com.dodopal.product.service;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.product.business.model.QueryProductOrderResult;
import com.dodopal.product.business.model.QueryProductOrderStatesResult;
import com.dodopal.product.business.model.query.QueryProductOrderRequest;
import com.dodopal.product.business.model.query.QueryProductOrderStatesRequest;
import com.dodopal.product.business.service.ProductOrderForMobileVCService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class ProductOrderForMobileVCServiceTest {

    @Autowired
    private ProductOrderForMobileVCService productOrderForMobileVCService;

    @Test
    public void queryProductOrder() {
        try {

            QueryProductOrderRequest query = new QueryProductOrderRequest();
            query.setCusttype("1");
            query.setUserid("10000000000000000600");
            query.setCustcode("059001000000523");
            List<QueryProductOrderResult> response = productOrderForMobileVCService.queryProductOrder(query);

            System.out.println("##########################################");
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    System.out.println("############___list" + i + "____start____#####################");
                    System.out.println(ReflectionToStringBuilder.toString(response.get(i), ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("############___list" + i + "____end____#####################");
                }
            }
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryProductOrderStates() {
        try {

            QueryProductOrderStatesRequest query = new QueryProductOrderStatesRequest();
            query.setCustcode("059001000000523");
            query.setOrdernum("O2015092217341611309");
            QueryProductOrderStatesResult response = productOrderForMobileVCService.queryProductOrderStates(query);

            System.out.println("##########################################");
            if (response != null) {
                System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
            }
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

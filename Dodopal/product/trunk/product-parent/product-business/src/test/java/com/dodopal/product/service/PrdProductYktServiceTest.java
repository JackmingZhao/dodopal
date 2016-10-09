package com.dodopal.product.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.business.service.PrdProductYktService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class PrdProductYktServiceTest {

    @Autowired
    private PrdProductYktService  productYktService;
    @Autowired
    LoadOrderService loadOrderService;
    
    @Test
    public void testCheckExist(){
        try {
            PrdProductYkt productYkt = new PrdProductYkt();
            productYkt.setYktCode("20150001");
            productYkt.setCityId("1566");
            productYkt.setProPrice(1000);
            boolean bool = productYktService.checkExist(productYkt);
            System.out.println(bool);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

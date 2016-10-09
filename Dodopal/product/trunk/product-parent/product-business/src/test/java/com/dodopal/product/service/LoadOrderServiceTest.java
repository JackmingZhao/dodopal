package com.dodopal.product.service;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.api.product.dto.LoadOrderQueryResponseDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.query.LoadOrderQuery;
import com.dodopal.product.business.service.LoadOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class LoadOrderServiceTest {

    @Autowired
    private LoadOrderService loadOrderService;
    
    
    @Test
    public void testValidateLoadOrderForIcdcRecharge() {
        try {
            LoadOrderQuery loadOrder = new LoadOrderQuery();
            loadOrder.setOrderNum("Q20150826150852100010");
            DodopalResponse<LoadOrderDTO> orders = loadOrderService.validateLoadOrderForIcdcRecharge(loadOrder);
            if (orders != null) {
                System.out.println("##########################################");
                System.out.println(orders.getResponseEntity());
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
//    @Test
    public void testFindAvailableLoadOrdersByCardNum() {
        try {
            
            DodopalResponse<List<LoadOrderQueryResponseDTO>> orders = loadOrderService.findAvailableLoadOrdersByCardNum("12345");
            

            if (orders != null) {
                System.out.println("##########################################");
                System.out.println("##########################################");
                for (LoadOrderQueryResponseDTO dp : orders.getResponseEntity()) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


   

}

package com.dodopal.product.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.product.business.dao.MerJointQueryMapper;
import com.dodopal.product.business.model.ChargeOrder;
import com.dodopal.product.business.model.ConsumptionOrderCount;
import com.dodopal.product.business.model.query.ChargeOrderQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class MerJointQueryMapperTest {

    @Autowired
    private MerJointQueryMapper mapper;
    
    @Test
    public void testFindChargeOrderByPage() {
        try {
            ChargeOrderQuery query = new ChargeOrderQuery();
            List<ChargeOrder> orders = mapper.findChargeOrderByPage(query);
            
            System.out.println("##########################################");
            
            if (orders != null) {
                System.out.println("##########################################");
                for (ChargeOrder dp : orders) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
            System.out.println("##########################################");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFindConsumptionOrderCountByPage(){
        try {
            ChargeOrderQuery query = new ChargeOrderQuery();
            List<ConsumptionOrderCount> orders = mapper.findConsumptionOrderCountByPage(query);
            
            System.out.println("##########################################");
            
            if (orders != null) {
                System.out.println("##########################################");
                for (ConsumptionOrderCount dp : orders) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
            System.out.println("##########################################");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}

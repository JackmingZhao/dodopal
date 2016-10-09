package com.dodopal.portal.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.model.query.ChildMerchantQuery;
import com.dodopal.portal.business.service.ChildMerchantService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:portal-business-test-context.xml"})
public class ChildMerchantTest {
    @Autowired
    ChildMerchantService childMerchantService;
    @Test
    public void findChildMerchantPage(){
        ChildMerchantQuery childMerchantQuery =new ChildMerchantQuery();
        childMerchantQuery.setMerCode("013541000000190");
        childMerchantService.findChildMerchantBeanByPage(childMerchantQuery);
        System.out.println(ReflectionToStringBuilder.toString(childMerchantQuery, ToStringStyle.MULTI_LINE_STYLE));
    }
    
    
    @Test
    public void saveChildMerchant(){
        MerchantBean merchantBean =new MerchantBean();
        childMerchantService.saveChildMerchants(merchantBean);
    }
}

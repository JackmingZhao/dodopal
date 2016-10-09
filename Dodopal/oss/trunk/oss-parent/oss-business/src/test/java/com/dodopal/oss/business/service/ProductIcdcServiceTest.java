package com.dodopal.oss.business.service;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.dao.ProductIcdcMapper;
import com.dodopal.oss.business.model.ProductIcdc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class ProductIcdcServiceTest {
    @Autowired
    ProductIcdcMapper productIcdcMapper;
    @Test
    public void testfindProductNames(){
        ProductIcdc productIcdc =new ProductIcdc();
        List<ProductIcdc> proicdcList =productIcdcMapper.findProductIcdcNames(productIcdc);
            for(ProductIcdc pros : proicdcList){
                System.out.println(ReflectionToStringBuilder.toString(pros, ToStringStyle.MULTI_LINE_STYLE));
            }
    }
    
    @Test
    public void testfindProductList(){
        String[] productCode = {"3600","3700","3800"};
        List<ProductIcdc> proList = productIcdcMapper.findProductIcdcbByCode(productCode);
        if(proList!=null){
            for(ProductIcdc productIcdc :proList){
                 System.out.println(ReflectionToStringBuilder.toString(productIcdc, ToStringStyle.MULTI_LINE_STYLE));
            }
        }
    }
}

package com.dodopal.product.dao;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.product.business.dao.PrdProductYktMapper;
import com.dodopal.product.business.dao.ProductYktCityInfoMapper;
import com.dodopal.product.business.model.PrdProductYkt;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:product-business-test-context.xml"})
public class PrdProductYktMapperTest {

    @Autowired
    private PrdProductYktMapper mapper;
    @Autowired
    private ProductYktCityInfoMapper productYktCityInfoMapper;
    
    @Test
    public void testFindPrdProductYktByProCode(){
        String proCode = "1000015";
        try {
            PrdProductYkt result = mapper.findPrdProductYktByProCode(proCode);
            if (result != null) {
                System.out.println(ReflectionToStringBuilder.toString(result, ToStringStyle.MULTI_LINE_STYLE));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    @Test
//    public void testFindAvailableIcdcProductsForMerchant(){
//        try {
//            String cityId="1566";
//            String merchantNum="004971000000428";
//            List<PrdProductYkt> result = mapper.findAvailableIcdcProductsForMerchant(merchantNum, cityId);
//            if (result != null && result.size() > 0) {
//                for (PrdProductYkt prd : result) {
//                    System.out.println(ReflectionToStringBuilder.toString(prd, ToStringStyle.MULTI_LINE_STYLE));
//                }
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    @Test
    public void testFindVersion(){
        try {
            String cityId="1791";
            
            System.out.println(productYktCityInfoMapper.getProversion(cityId,"123"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFindVersionId(){
    	
    	try {
            String cityId="1791";
            
            System.out.println(productYktCityInfoMapper.getProversionByCityCode(cityId));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.dodopal.users.service;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.model.MerKeyType;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.service.MerKeyTypeService;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年4月28日 下午4:19:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerKeyTypeServiceTest {
    @Autowired
    private MerKeyTypeService keyTypeService;
    
    //@Test
    public void testSave(){
        MerKeyType keyType = new MerKeyType();
        keyType.setActivate("1");
        keyType.setInnerBackPayPWD("123");
        keyType.setMerMD5PayPwd("321");
        keyType.setMerMD5BackPayPWD("456");
        keyType.setMerCode("1234");
        keyType.setMerKeyType("MD5");
        keyTypeService.saveKeyType(keyType);
    }
    
   //@Test
    public void testUpdateMerMD5PayPwd(){
        MerKeyType keyType = new MerKeyType();
        keyType.setActivate("1");
        keyType.setInnerBackPayPWD("123");
        keyType.setMerMD5PayPwd("123789");
        keyType.setMerMD5BackPayPWD("456");
        keyType.setMerCode("1234");
        keyType.setMerKeyType("MD5");
        System.out.println(keyTypeService.updateMerMD5PayPwd(keyType, "321")); 
        
    }
    
    //@Test
    public void testUpdateMerMD5BackPayPWD(){
        MerKeyType keyType = new MerKeyType();
        keyType.setActivate("1");
        keyType.setMerMD5BackPayPWD("1234567");
        keyType.setMerCode("1234");
        keyType.setMerKeyType("MD5");
        System.out.println(keyTypeService.updateMerMD5BackPayPWD(keyType, "456")); 
    }
    
    //@Test
    public void testFindMerKeyTypeList(){
        MerKeyType keyType = new MerKeyType();
        keyType.setMerCode("12312312");
        List <MerKeyType> list = keyTypeService.findMerKeyTypeList(keyType);
        if (list != null) {
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
            for (MerKeyType dp : list) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
}

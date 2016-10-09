package com.dodopal.users.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.model.MerKeyType;
import com.dodopal.users.business.service.MerKeyTypeService;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年4月28日 下午2:57:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerkeyTypeMapperTest {
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
    
    @Test
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
    
}

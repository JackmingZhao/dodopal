package com.dodopal.users.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.dao.MobileCodeCheckMapper;
import com.dodopal.users.business.dao.TestMapper;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.MobileCodeCheck;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MobliCodeCheckMapperTest {

    @Autowired
    private MobileCodeCheckMapper mapper;

    @Test
    public void testInsertMobileCode() {
        try {
        	MobileCodeCheck mod = new MobileCodeCheck();
        	//mod.setId("100000000001");
        	mod.setMobilTel("182");
        	mod.setDypwd("aju2yk");
        	mod.setSerialNumber("24");
        	//mod.setExpirationTime(new Date());           
            mapper.insertMobileCode(mod);

            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //@Test
    public void testMobileCode() {
        try {
        	MobileCodeCheck mod = new MobileCodeCheck();
//        	mod.setId("100000000001");
        	mod.setMobilTel("");
        	mod.setDypwd("8tmyzz");
        	mod.setSerialNumber("17");
        	mod.setExpirationTime(new Date());           
        	List<MobileCodeCheck> list = mapper.findMobileCodeCheckes(mod);
            System.out.println(list.size());
            if(list!=null){
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");

                 for (MobileCodeCheck dp : list) {
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

    //@Test
    public void testMobileCodeCount() {
        try {
        	MobileCodeCheck mod = new MobileCodeCheck();
//        	mod.setId("100000000001");
        	mod.setMobilTel("");
        	mod.setDypwd("8tmyzz");
        	mod.setSerialNumber("17");
        	mod.setExpirationTime(new Date());           
        	Integer count = mapper.findMobileCodeCount(mod);
            System.out.println(count);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}

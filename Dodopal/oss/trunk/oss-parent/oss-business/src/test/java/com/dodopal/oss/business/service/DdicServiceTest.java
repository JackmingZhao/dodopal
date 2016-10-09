package com.dodopal.oss.business.service;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.Ddic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class DdicServiceTest {

    
    @Autowired
    private DdicService ddicService;
    
    @Test
    public void testInserDdic() {
        try {
            Ddic ddic = new Ddic();
            ddic.setCreateDate(new Date());
            ddic.setCreateUser("1");
            ddic.setUpdateDate(new Date());
            ddic.setUpdateUser("1");
            
            ddic.setCode("123");
            ddic.setName("Junit");
            ddic.setDescription("Junit 测试");
            ddic.setOrderList(1);
            
            ddicService.saveOrUpdateDdic(ddic);
            
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    @Test
    public void testFindDdics() {
        try {
            Ddic ddic = new Ddic();
            ddic.setCode("123");
            ddicService.findDdics(ddic);
            if(ddic != null) {
                System.out.println(ReflectionToStringBuilder.toString(ddic, ToStringStyle.MULTI_LINE_STYLE));
            }
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    @Test
    public void testUpdateDdic() {
        try {
            Ddic ddic = new Ddic();
            ddic.setId("1231");
            ddic.setCode("update");
            ddic.setName("Junit update");
            ddic.setDescription("Junit 测试update");
            ddic.setOrderList(1);
            
            ddicService.saveOrUpdateDdic(ddic);
            
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    @Test
    public void testDeleteDdic() {
        try {
            ddicService.deleteDdic("015c948166424937bd53969348d85bcf");
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}

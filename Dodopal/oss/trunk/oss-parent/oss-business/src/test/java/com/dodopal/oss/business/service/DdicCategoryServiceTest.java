package com.dodopal.oss.business.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.DdicCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class DdicCategoryServiceTest {

    @Autowired
    private DdicCategoryService ddicCategoryService;

    //    @Test
    public void testInserDdicCategory() {
        try {
            DdicCategory ddicCategory = new DdicCategory();
            ddicCategory.setCreateDate(new Date());
            ddicCategory.setCreateUser("1");
            ddicCategory.setUpdateDate(new Date());
            ddicCategory.setUpdateUser("1");

            ddicCategory.setCategoryCode("123");
            ddicCategory.setCategoryName("name");
            ddicCategory.setDescription("Junit 测试");
            ddicCategory.setActivate("0");

            ddicCategoryService.saveOrUpdateDdicCategory(ddicCategory);

            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void testFindDdicCategorys() {
        try {
            DdicCategory ddicCategory = new DdicCategory();
            ddicCategory.setCategoryCode("123");
            List<DdicCategory> ddicCategorys = ddicCategoryService.findDdicCategorys(ddicCategory);
            if (ddicCategory != null) {
                for (DdicCategory rs : ddicCategorys) {
                    System.out.println(ReflectionToStringBuilder.toString(rs, ToStringStyle.MULTI_LINE_STYLE));
                }
            }
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void testUpdateDdicCategory() {
        try {
            DdicCategory ddicCategory = new DdicCategory();
            ddicCategory.setId("123");
            ddicCategory.setCategoryCode("update");
            ddicCategory.setCategoryName("Junit update");
            ddicCategory.setDescription("Junit 测试update");

            ddicCategoryService.saveOrUpdateDdicCategory(ddicCategory);

            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteDdicCategory() {
        try {
 //           ddicCategoryService.deleteDdicCategory("431a6c15649e48569600a61d2f223cc1");
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

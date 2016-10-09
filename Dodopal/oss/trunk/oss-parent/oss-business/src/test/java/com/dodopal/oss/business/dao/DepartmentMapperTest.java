package com.dodopal.oss.business.dao;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class DepartmentMapperTest {

    @Autowired
    private DepartmentMapper departmentMapper;


      @Test
    public void testFindDepartmentById() {
        try {
            Department departResult = departmentMapper.findDepartmentById("62E88C94D0E042839993E60995B15A6A");

            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(departResult, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

      @Test
    public void testInsertDepartment() {
        try {
           
//            String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
//            depart.setId("123");
//            depart.setName("test4");
//            depart.setCharger("testCharger");
//            depart.setPhone("021-2134234234");
            for(int i=20;i<40;i++){
                Department depart = new Department();
                depart.setDepCode("kf"+i);
                depart.setDepName("开发"+i);
                depart.setActivate("0");
                depart.setCreateDate(new Date());
                depart.setUpdateDate(new Date());
                depart.setCreateUser("admin");
                depart.setUpdateUser("admin");
                departmentMapper.insertDepartment(depart);
            }
           

            System.out.println("##########################################");
//            System.out.println("id= [" + id + "]");
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void testDeleteDepartment() {
        try {
            String id = "6635C33E7E694A6BB073B1778418B5F5";
         //  departmentMapper.deleteDepartment(id);

            System.out.println("##########################################");
            System.out.println("id= [" + id + "]");
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    //  @Test
    public void testUpdateDepartment() {
        try {
//            String id = "C13B5A6B080446B4A9AB85EBE28AEFA7";
            Department depart = new Department();
            depart.setId("123");
//            depart.setName("updateName");
//            depart.setCharger("updateCharger");
//            depart.setPhone("987654321");
            depart.setUpdateDate(new Date());
            depart.setUpdateUser("1");
            departmentMapper.updateDepartment(depart);

            System.out.println("##########################################");
//            System.out.println("id= [" + id + "]");
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}

package com.dodopal.oss.business.service;

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
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testFindDepartmentById() {
        try {
            Department departResult = departmentService.findDepartmentById("62E88C94D0E042839993E60995B15A6A");

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
            Department depart = new Department();
//            String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            depart.setId("1231");
//            depart.setName("test4");
//            depart.setCharger("testCharger");
//            depart.setPhone("021-2134234234");
            depart.setCreateDate(new Date());
            depart.setUpdateDate(new Date());
            depart.setCreateUser("1");
            depart.setUpdateUser("1");
            //departmentService.insertDepartment(depart);

            System.out.println("##########################################");
//            System.out.println("id= [" + id + "]");
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //	@Test
    public void testInsertDepartmentWithException() {
        try {
            Department depart = new Department();
//            String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            depart.setId("1231");
//            depart.setName("ErrorUser");
//            depart.setCharger("testCharger");
//            depart.setPhone("021-2134234234");
            depart.setCreateDate(new Date());
            depart.setUpdateDate(new Date());
            depart.setCreateUser("1");
            depart.setUpdateUser("1");
         //   departmentService.insertDepartmentWithException(depart);

            System.out.println("##########################################");
//            System.out.println("id= [" + id + "]");
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeleteDepartment() {
        try {
            String id = "6635C33E7E694A6BB073B1778418B5F5";
            //departmentService.deleteDepartment(id);

            System.out.println("##########################################");
            System.out.println("id= [" + id + "]");
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUpdateDepartment() {
        try {
//            String id = "C13B5A6B080446B4A9AB85EBE28AEFA7";
            Department depart = new Department();
            depart.setId("12312");
//            depart.setName("updateName");
//            depart.setCharger("updateCharger");
//            depart.setPhone("987654321");
            depart.setUpdateDate(new Date());
            depart.setUpdateUser("1");
           // departmentService.updateDepartment(depart);

            System.out.println("##########################################");
//            System.out.println("id= [" + id + "]");
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}

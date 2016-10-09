package com.dodopal.users.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.model.MerGroupDepartment;
import com.dodopal.users.business.service.MerGroupDepartmentService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月5日 下午4:54:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerGroupDepartmentServiceTest {
    @Autowired
    private MerGroupDepartmentService service;
    
   @Test
    public void deleteTest(){
        List<String> list = new ArrayList<String>();
        list.add("10000000000000000121");
        int rows = service.deleteMerGroupDepartment(list);
        System.out.println(rows);
    }
   //@Test
    public void findTest(){
        MerGroupDepartment department = new  MerGroupDepartment();
        department.setDepName("1");
        department.setMerCode("1");
        service.findMerGroupDepartmentList(department);
        
    }
    //@Test
    public void findByMerCodeTest(){
        MerGroupDepartment department = new  MerGroupDepartment();
        department.setDepName("测试部门");
        department.setMerCode("M1234567890");
        List<MerGroupDepartment> list =  service.findMerGpDepByMerCodeAndDeptName(department);
        for(MerGroupDepartment d :list){
        System.out.println("##########################################");
        System.out.println(ReflectionToStringBuilder.toString(d, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("##########################################");
        }
    }
    
    @Test
    public void testStart(){
        List list = new ArrayList();
        list.add("1");
        System.out.println(service.startOrStopMerGroupDepartment("1",list));
    }
    
    @Test
    public void saveTest(){
        MerGroupDepartment department = new  MerGroupDepartment();
        department.setDepName("测试部门");
        department.setMerCode("M1234567890");
        service.saveMerGroupDepartment(department);
        System.out.println(department.getId());
    }
    @Test
    public void updateTest(){
        MerGroupDepartment department = new  MerGroupDepartment();
        department.setId("1");
        department.setDepName("人力部");
        System.out.println(service.updateMerGroupDepartment(department));
        
    }
    
    
    
}

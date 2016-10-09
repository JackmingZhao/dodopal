package com.dodopal.users.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.dao.MerGroupDepartmentMapper;
import com.dodopal.users.business.model.MerGroupDepartment;
import com.dodopal.users.business.model.MerchantUser;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月5日 下午12:00:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerGroupDepartmentMapperTest {
    @Autowired
    private MerGroupDepartmentMapper departmentMapper;
    //@Test
    public void findTest(){
        MerGroupDepartment department = new MerGroupDepartment();
        List<MerGroupDepartment> list = departmentMapper.findMerGroupDepartmentList(department);
        if(list!=null){
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");

             for (MerGroupDepartment dp : list) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
 
    @Test
    public void saveTest(){
        MerGroupDepartment department = new MerGroupDepartment();
        department.setCreateDate(new Date());
        department.setDepName("技术部3");
        department.setActivate("0");
        department.setMerCode("M1234567890");
        departmentMapper.saveMerGroupDepartment(department);
        System.out.println(department.getId());
    }
   // @Test
    public void updateTest(){
        MerGroupDepartment department = new MerGroupDepartment();
        department.setId("1");
        department.setDepName("修改机构");
        System.out.println(departmentMapper.updateMerGroupDepartment(department));
    }
    @Test
    public void deleteTest(){
        MerGroupDepartment department = new MerGroupDepartment();
        List<String> list = new ArrayList<String>();
        list.add("1");
        System.out.println(departmentMapper.deleteMerGroupDepartment(list));
    }
}

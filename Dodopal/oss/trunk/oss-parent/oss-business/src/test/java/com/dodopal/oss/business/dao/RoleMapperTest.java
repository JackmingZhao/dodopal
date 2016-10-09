package com.dodopal.oss.business.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.Operation;
import com.dodopal.oss.business.model.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AppFunctionMapper appFunctionMapper;

    //    @Test
    public void testInserRole() {
        try {
            Role role = new Role();
            role.setId("1123");
            role.setName("Junit");
            role.setDescription("Junit test");
            role.setOperationIds("123333333333333");
            role.setCreateDate(new Date());
            role.setUpdateDate(new Date());
            List<Operation> operations = appFunctionMapper.findAllMenus();
            role.setOperations(operations);
            ;

            roleMapper.insertRole(role);
            /*for(Operation operation : operations) {
                RoleFunctionLink link = new RoleFunctionLink();
                link.setRole(role);
                link.setOperation(operation);
            //                roleMapper.insertRoleFunctionLink(link);
            }*/

            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void testFindRole() {
        try {
            Role role = new Role();
            role.setId("12312");
            role.setName("Junit");
            role.setDescription("Junit test");
            role.setOperationIds("123333333333333");
            role.setCreateDate(new Date());
            role.setUpdateDate(new Date());
            roleMapper.insertRole(role);
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadRoles() {
        try {
            String roleIds = "220a90be14de4f7aad4bb8f14904b8d0,5405304cf7264e1b818c69656d4f7514";
            List<Role> roles = roleMapper.loadRoles(roleIds.split(","));
            if (roles != null) {
                for (Role role : roles) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(role, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void testCountRole() {
        try {
            int count = roleMapper.countRole("haha");
            System.out.println("################" + count + "##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

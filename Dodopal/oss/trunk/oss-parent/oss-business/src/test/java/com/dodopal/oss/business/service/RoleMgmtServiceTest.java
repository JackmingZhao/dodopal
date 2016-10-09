package com.dodopal.oss.business.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class RoleMgmtServiceTest {

    
    @Autowired
    private RoleMgmtService roleMgmtService;
    
    @Test
    public void testSaveOrUpdateRole() {
            String operationIds = "7a9be58f1602415b9d77a01524d04ed9,c81f1eae97774688ba2cad0f3288ec9f";
            Role role = new Role();
            role.setName("Junit");
            role.setDescription("Junit test");
            role.setOperationIds(operationIds);
            role.setCreateDate(new Date());
            role.setUpdateDate(new Date());
            roleMgmtService.saveOrUpdateRole(role);
            
           
            System.out.println("################done##########################");
//            throw new RuntimeException();
    }



}

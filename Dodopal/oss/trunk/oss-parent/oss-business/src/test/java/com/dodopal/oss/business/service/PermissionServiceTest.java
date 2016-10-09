package com.dodopal.oss.business.service;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.Operation;
import com.dodopal.oss.business.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class PermissionServiceTest {

    @Autowired
    private PermissionService permissionService;

    @Test
    public void testFindDepartment() {
        try {
            List<Operation> operations = permissionService.findMenus(new User());
            if (operations != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(operations, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (Operation operation : operations) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(operation, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.dodopal.oss.business.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.md5.MD5;
import com.dodopal.oss.business.model.Role;
import com.dodopal.oss.business.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Test
    public void testInserUser() {
        try {
            for(int i=0;i<100;i++){
                User user = new User();
                user.setId("123123"+i);
                user.setName("Junit"+i);
                user.setLoginName("Junit"+i);
                user.setCreateDate(new Date());
                user.setActivate(0);
                user.setPassword(MD5.MD5Encode("1"));
                user.setUpdateDate(new Date());
                List<Role> roles = roleMapper.findRoles(null);
                user.setRoles(roles);
                userMapper.insertUser(user);
            }
            System.out.println("################done##########################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

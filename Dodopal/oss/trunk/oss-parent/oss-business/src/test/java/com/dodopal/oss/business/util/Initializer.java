package com.dodopal.oss.business.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.md5.MD5;
import com.dodopal.oss.business.dao.AppFunctionMapper;
import com.dodopal.oss.business.dao.RoleMapper;
import com.dodopal.oss.business.dao.UserMapper;
import com.dodopal.oss.business.model.Operation;
import com.dodopal.oss.business.model.Role;
import com.dodopal.oss.business.model.User;

@Service
public class Initializer {

    @Autowired
    private AppFunctionMapper appFunctionMapper;
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Transactional
    public void initialize() throws Exception {
        System.out.println(".....Starting init data......");
        System.out.println(".....addDefaultAppFunctions......");
        addDefaultAppFunctions();
        addAdminRole();
        addAdminUser();
        System.out.println(".....the End......");
    }

    private void addAdminRole() {
        Role admin = new Role();
        admin.setName("Admin");
        admin.setCreateDate(new Date());
        admin.setUpdateDate(new Date());
        admin.setCreateUser("1");
        admin.setUpdateUser("1");
        admin.setDescription("都都宝超级管理员权限,系统无法修改.");
        
        List<Operation>  all = appFunctionMapper.findAllOperations();
        StringBuffer buffer = new StringBuffer();
        if(all != null) {
            for(int i=0 ; i< all.size(); i++) {
                if(all.get(i) != null) {
                    buffer.append(all.get(i).getId());
                }
                if(i != all.size()-1) {
                    buffer.append(",");
                }
            }
        }
        admin.setOperationIds(buffer.toString());
        roleMapper.insertRole(admin);
    }
    
    private void addAdminUser() {
        User user = new User();
        user.setLoginName("admin");
        user.setPassword(MD5.MD5Encode("123"));
        user.setName("Admin");
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setCreateUser("1");
        user.setUpdateUser("1");
        user.setActivate(0);
        Role role = roleMapper.findRoleByName("Admin");
        if(role != null) {
            user.setRoleIds(role.getId()+"");
        }
       userMapper.insertUser(user);
    }
    
    private void addDefaultAppFunctions() {
        try {
            int order = 1;
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("appFunctions.properties"), "utf-8"));
            String line = null;

            List<Operation> currentFunctions = new ArrayList<Operation>();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                System.out.print("Current path : ");
                for (Operation function : currentFunctions) {
                    System.out.print(function.getCode() + " ");
                }
                System.out.println();
                int level = 0;
                while (line.startsWith("-")) {
                    level++;
                    line = line.substring(1);
                }
                String[] splits = line.split("\\s+");
                Operation function = new Operation();
                function.setCreateDate(new Date());
                function.setUpdateDate(new Date());
                function.setCreateUser("1");
                function.setUpdateUser("1");
                function.setAppName("OSS");
                function.setLevels(level);
                function.setCode(splits[0]);
                function.setName(splits[1]);
                if (splits.length > 2) {
                    function.setDescription(splits[2]);
                }
                order +=5;
                function.setPosition(order);
                if (splits.length > 3) {
                    function.setVisibility(Integer.parseInt(splits[3]));
                } else {
                    function.setVisibility(1);
                }
                
                if (splits.length > 4) {
                    function.setType(splits[4]);
                }
                System.out.println(function.getCode() + " : " + level);
                // reset node path, clear nodes with depth greater than current node level
                for (int i = currentFunctions.size() - 1; i >= level; i--) {
                    currentFunctions.remove(level);
                }
                if (level > 0) {
                    Operation parentFun = currentFunctions.get(level - 1);
                    function.setParentId(appFunctionMapper.findOperationIdByCode(parentFun.getCode()));
                }
                
                appFunctionMapper.insertFunction(function);
                currentFunctions.add(function);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        AbstractXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"oss-business-test-context.xml"});
        context.getBean(Initializer.class).initialize();
    }

}

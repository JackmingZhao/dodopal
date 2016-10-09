package com.dodopal.users.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.service.MerchantUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerchantUserServiceTest {
    @Autowired
    private MerchantUserService userService;

    @Test
    public void testFindUser() {
        try {
            MerchantUser user = new MerchantUser();
            user.setMerUserName("li");
            List<MerchantUser> UserResult = userService.findMerchantUser(user);

            if (UserResult != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(UserResult, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (MerchantUser dp : UserResult) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    @Test
    public void testFindUserByUserName() {
        try {
            MerchantUser user = new MerchantUser();
            user.setMerUserName("li");
            MerchantUser UserResult = userService.findMerchantUserByUserName("kidrock_hj");

            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(UserResult, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //030641000000001
    @Test
    public void testFindUserByUserCode() {
        try {
            MerchantUser user = new MerchantUser();
            user.setMerUserName("li");
            MerchantUser UserResult = userService.findMerchantUserByUserCode("17762100000000221");

            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(UserResult, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
   @Test
    public void testUpdateUser(){
        MerchantUser user = new MerchantUser();
        user.setId("10000000000000000101");
        user.setMerUserIdentityType("0");
        user.setMerUserIdentityNumber("900900900900");
        user.setMerUserRemark("junit_test");
        userService.updateMerchantUser(user);
    }
    
    @Test
    public void testModifyPWD(){
        MerchantUser user = new MerchantUser();
        userService.resetPWDByMobile("15618382874", "123131231");
    }
    @Test
    public void testBachStartUser(){
        List idList =  new ArrayList();
        idList.add("10000000000000000001");
        userService.batchStopUser("1",idList,"admin1");
    }
    
    @Test
    public void testInsert(){
        MerchantUser user = new MerchantUser();
        user.setActivate("0");
        user.setMerUserBirthday(new Date());
        user.setCreateDate(new Date());;
        user.setDelFlag("1");
        user.setMerUserEmail("test11@dudupal.com");
        user.setMerUserEmployeeDate(new Date());
        user.setMerUserIdentityNumber("11");
        user.setMerUserIdentityType("11");
        user.setMerUserLoginFaiCount(3);
        user.setMerUserName("wangwu");
        user.setUserCode("11114");
        user.setMerUserNickName("王五");
        user.setMerUserSex("1");
        user.setMerCode("030641000000001");
        user.setMerUserTelephone("13023211737");
        user.setMerUserMobile("1267111222");
        user.setMerUserLastLoginIp("123");
        user.setMerUserLockedDate(new Date());
        user.setMerUserRemark("三");
        user.setCityCode("13");
        user.setMerUserLastLoginDate(new Date());
        user.setMerUserType(MerTypeEnum.GROUP.getCode());
        user.setMerUserPWD("123122121");
        user.setMerUserSource("1");
        user.setPayInfoFlg("1");
        user.setMerUserFlag("1");
        List<String>depList = new ArrayList<String>();
        depList.add("123131231");
        depList.add("1231322221231");
        user.setMerGroupDeptList(depList);
        System.out.println(userService.insertMerchantUser(user,UserClassifyEnum.MERCHANT));
        System.out.println(user.getId());
    }
    
    //@Test
    public void testUserPwd(){
        MerchantUser user = new MerchantUser();
        user.setMerUserName("zhangsan");
        user.setMerUserPWD("1111");
        
        System.out.println(userService.modifyPWD(user, "222222"));
    }
    
    //@Test
    public void testGenerateMerUserCode(){
        System.out.println(userService.generateMerUserCode(UserClassifyEnum.MERCHANT));
    }
    
    //@Test
    public void testCheckExist(){
        MerchantUser user = new MerchantUser();
        user.setMerUserName("test01");
        System.out.println(userService.checkExist(user));
    }
}

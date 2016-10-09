package com.dodopal.users.dao;




import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.dao.MerchantUserMapper;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerchantUserQuery;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerchantUserMapperTest {
    @Autowired
    private MerchantUserMapper userMapper;
    
    @Test
    public void testfindMerchantUserAndMerNameByPage() {
        MerchantUserQuery user = new MerchantUserQuery();
        user.setActivate("0");
        user.setMerUserType("0");
        user.setMerUserNickName("");
        List<MerchantUser> users = userMapper.findMerchantUserAndMerNameByPage(user);
        if(users!=null){
            System.out.println("##########################################");

             for (MerchantUser dp : users) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    //@Test
    public void userInsertTest() {
        try {
            MerchantUser user = new MerchantUser();
            user.setActivate("0");
            user.setMerUserBirthday(new Date());
            user.setCreateDate(new Date());;
            user.setDelFlag("1");
            user.setMerUserEmail("test3@dudupal.com");
            user.setMerUserEmployeeDate(new Date());
            user.setMerUserIdentityNumber("11");
            user.setMerUserIdentityType("11");
            user.setMerUserLoginFaiCount(3);
            user.setMerUserName("fly");
            user.setUserCode("1123");
            user.setMerUserNickName("fly");
            user.setMerUserSex("1");
            user.setMerUserTelephone("13023439727");
            user.setMerUserMobile("12672222");
            user.setMerUserLastLoginIp("123");
            user.setMerUserLockedDate(new Date());
            user.setMerUserRemark("三");
            user.setMerCode("1");
            user.setCityCode("13");
            user.setMerUserLastLoginDate(new Date());
            user.setMerUserType("16");
            user.setMerUserPWD("123124121");
            user.setMerUserSource("1");
            user.setPayInfoFlg("1");
            user.setMerUserFlag("1");
            userMapper.insertMerchantUser(user);
            System.out.println(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
//    @Test
    public void findUserByMobileTest(){
        String id = userMapper.findMerchantUserByMobile("15618382874");
        System.out.println(id);
    }
    
//   @Test
    public void findUserTest() {
        MerchantUser user = new MerchantUser();
        user.setCreateDateStart(new Date());
        try {
        List<MerchantUser> list = userMapper.findMerchantUser(user);
           System.out.println(list.size());
           if(list!=null){
               System.out.println("##########################################");
               System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
               System.out.println("##########################################");

                for (MerchantUser dp : list) {
                   System.out.println("##########################################");
                   System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                   System.out.println("##########################################");
               }
           }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
//   @Test
   public void findDepTest() {
       MerchantUser user = new MerchantUser();
       user.setCreateDateStart(new Date());
       try {
       List<String> list = userMapper.findMerGroupDeptNameByUserName("lifeng01");
          System.out.println(list.size());
          if(list!=null){
              System.out.println("##########################################");
              System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
              System.out.println("##########################################");

               for (String dp : list) {
                  System.out.println("##########################################");
                  System.out.println(dp);
                  System.out.println("##########################################");
              }
          }
       }catch(Exception e){
           e.printStackTrace();
       }
   }
    //@Test
    public void updateUserTest(){
        //10000000000000000002
        MerchantUser user = new MerchantUser();
        user.setId("10000000000000000030");
       
        int a =   userMapper.updateMerchantUser(user); 
        System.out.println(a);
    }
    
    //@Test
    public void updateBathTest(){
        List idList =  new ArrayList();
        idList.add("10000000000000000039");
        idList.add("10000000000000000001");
        Map map  = new HashMap();
        map.put("activate", 7);
        map.put("list", idList);
        System.out.println(userMapper.startOrStopUser(map));
    }
    //@Test
    public void deletUserTest(){
       
        int a = userMapper.deleteMerchantUser("10000000000000000012");
        System.out.println(a);
    }
    
   // @Test
    public void testFindUsernamePwd(){
        MerchantUser user = new MerchantUser();
        user.setMerUserPWD("123124121");
        user.setMerUserName("zhangsan");
        List<MerchantUser> list = userMapper.findByUsernameAndPWd(user);
        if(list!=null){
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");

             for (MerchantUser dp : list) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
   // @Test
    public void testModifyPwd(){
        MerchantUser user = new MerchantUser();
        user.setMerUserPWD("1111111");
        user.setMerUserName("zhangsan");
        userMapper.modifyPWD(user);
        
    }
    //@Test
    public void resetPWD(){
        MerchantUser user = new MerchantUser();
        user.setMerUserPWD("1121");
        user.setMerUserName("zhangsan");
        user.setId("10000000000000000021");
        System.out.println(userMapper.resetPWD(user));
    }
    
   // @Test
    public void testModifyPayInfoFlg(){
        MerchantUser user = new MerchantUser();
        user.setPayInfoFlg("9");
        user.setId("10000000000000000021");
        System.out.println(userMapper.modifyPayInfoFlg(user));
        
    }
//     @Test
    public void testSaveDeptUser(){
         Map map = new HashMap();
         map.put("merUserName",1 );
         map.put("merGrpDepId", 2);
        System.out.println(userMapper.saveMerDeptUser("211","411"));
        
        //System.out.println(com.dodopal.common.util.MD5.MD5Encode("123"));
    }
     
//     @Test
     public void testDelDeptUser(){
         List<String> list = new ArrayList<String>();
         list.add("4");
         //delMerDeptUserById
         System.out.println(userMapper.deleteMerDeptUserByDeptId(list));
     }
     
     
     
}

package com.dodopal.oss.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.UserQuery;

public interface UserMapper {

    public void insertUser(User user);
    
    public void updateUser(User user);
    
    public void deleteUser(String id);
    
    public List<User> findUsers(UserQuery user);
    
    public List<User> findUserByPage(UserQuery user);

    public User findUser(String loginName);
    
    public User findUserById(String id);
    
    public int countUser(String loginName);
    
    public void upPwdUser(User user);
    
    public void startUser(String[] userIds);
    
    public void disableUser(String[] userIds);
    
    public void updateUserLoginIPAndDate(@Param("loginName") String loginName,@Param("ip") String ip);
    public List<User> getUserManagerExportList(UserQuery userQuery);
}
    

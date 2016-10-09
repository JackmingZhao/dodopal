package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.TreeNode;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.UserQuery;

public interface UserMgmtService {

    /**
     * 保存或修改用户信息
     * @param user
     * @return
     */
    String saveOrUpdateUser(User user);

    /**
     * 查找用户
     * @param user
     * @return
     */
    List<User> findUsers(UserQuery user);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(String userId);
    
    /**
     * 加载用户角色
     */
    List<TreeNode> loadRoles(User user);
    /**
     * 禁用用户
     * @param user
     */
    void disableUser(String[] userId); 
    /**
     * 启用用户
     * @param user
     */
    void startUser(String[] userId); 
    /**
     * 修改用户密码
     * @param user
     * @return
     */
    String upPwdUser(User user);
    
    /** 
     * @Title: resetPWD 
     * @Description: 重置用户密码（邮箱）
     * @throws 
     */
    String resetPWD(User user);
    /**
     * 查看用户
     * @Title: findUser 
     * @Description: TODO
     * @param id
     * @return    设定文件 
     * User    返回类型 
     * @throws
     */
    User findUser(String id);
    
    DodopalDataPage<User> findUserByPage(UserQuery user);
    
    
    User findUserByLoginName(String loginName);
    /**
     * Excel导出 用户管理
     * @param userQuery
     * @return
     */
    List<User> getUserManagerExportList(UserQuery userQuery);

}

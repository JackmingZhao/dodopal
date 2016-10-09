package com.dodopal.oss.business.service;

import java.util.Collection;
import java.util.List;

import com.dodopal.oss.business.model.Operation;
import com.dodopal.oss.business.model.Role;
import com.dodopal.oss.business.model.TreeNode;
import com.dodopal.oss.business.model.User;

public interface PermissionService {

    /**
     * 得到第一级目录
     * @param user
     * @return
     */
    public List<Operation> findMenus(User user);

    /**
     * 得到第一级目录下的子目录
     * @param user
     * @param menuId
     * @return
     */
    public List<TreeNode> findAccordionMenus(User user, String menuId);

    /**
     * 得到角色所有的权限构建的权限树
     * @param user
     * @return
     */
    List<TreeNode> loadPermissionTree(Role role);

    /**
     * 查询某个指定用户的所有权限
     */
    public Collection<String> findAllPermissons(User user);

    /**
     * 查找用户
     */
    public User findUser(String loginName);
    
    /**
     * 更新登录用户的Ip和登录时间
     */
    public void updateUserLoginIPAndDate(String loginName,String ip);

}

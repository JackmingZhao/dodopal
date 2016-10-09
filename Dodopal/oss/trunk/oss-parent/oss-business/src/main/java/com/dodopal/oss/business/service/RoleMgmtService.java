package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.Role;
import com.dodopal.oss.business.model.dto.RoleQuery;

public interface RoleMgmtService {

    /**
     * 保存或修改角色信息
     * @param role
     * @return
     */
    String saveOrUpdateRole(Role role);

    /**
     * 查找角色
     * @param role
     * @return
     */
    List<Role> findRoles(Role role);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteRole(String [] roleId);
    
    Role findRoleById(String id);
    
    DodopalDataPage<Role> findRoleByPage(RoleQuery user);
}

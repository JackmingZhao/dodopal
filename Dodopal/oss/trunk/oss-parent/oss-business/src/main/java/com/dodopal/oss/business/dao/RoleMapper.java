package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.Role;
import com.dodopal.oss.business.model.dto.RoleQuery;

public interface RoleMapper {

    public void insertRole(Role role);
    
    public void updateRole(Role role);
    
    public void deleteRole(String []id);
    
    public List<Role> findRoles(Role role);
    
    public List<Role> findRoleByPage(RoleQuery role);
    
    public Role findRoleByName(String name);
    
    public Role findRoleById(String id);
    
    public int countRole(String name);
    
    public List<Role> loadRoles(String[] rolesId);

}
    

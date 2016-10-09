package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.dao.RoleMapper;
import com.dodopal.oss.business.model.Role;
import com.dodopal.oss.business.model.dto.RoleQuery;
import com.dodopal.oss.business.service.RoleMgmtService;

@Service
public class RoleMgmtServiceImpl implements RoleMgmtService {

    @Autowired
    private RoleMapper roleMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveOrUpdateRole(Role role) {
        validateRole(role);
        if (DDPStringUtil.isNotPopulated(role.getId())) {
            role.setCreateDate(new Date());
            role.setUpdateDate(new Date());
            roleMapper.insertRole(role);
        } else {
            role.setUpdateDate(new Date());
            roleMapper.updateRole(role);
        }
       
        return CommonConstants.SUCCESS;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> findRoles(Role role) {
        return roleMapper.findRoles(role);
    }

    @Transactional
    @Override
    public void deleteRole(String [] roleId) {
//        if (StringUtils.isNotEmpty(roleId)) {
//            Role role = roleMapper.findRoleById(roleId);
//            if (role != null) {
//                if (OSSConstants.ADMIN_ROLE.equalsIgnoreCase(role.getName())) {
//                    throw new DDPException("validate.error:\n", "admin 为超级管理员角色,不允许操作。");
//                }
                roleMapper.deleteRole(roleId);
//            }
//        }
    }

    private void validateRole(Role role) {
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.existingWithLength(role.getName(), 200)) {
            msg.add("角色名称不能为空或长度不能超过200个字符");
        }
        int count = roleMapper.countRole(role.getName());
        
        if ((DDPStringUtil.isNotPopulated(role.getId()) && count >= 1) || (DDPStringUtil.isPopulated(role.getId()) && count >= 1)) {
            if(DDPStringUtil.isPopulated(role.getId()) && count >= 1 ){
             // 判断当前修改的记录是不是原角色记录，如果不是，则提示重复
                //之前条件〉1考虑不全，新建重名没有考虑到
                Role nowRole = roleMapper.findRoleById(role.getId());
                if( null != nowRole && !nowRole.getName().equals(role.getName())){
                    msg.add("角色名称不能重复");
                }
            }else{
                msg.add("角色名称不能重复");
            }
        } 
        if (OSSConstants.ADMIN_ROLE.equalsIgnoreCase(role.getName())) {
            msg.add("admin 为超级管理员用户角色,不允许操作。");
        }
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<Role> findRoleByPage(RoleQuery role) {
        List<Role> result = roleMapper.findRoleByPage(role);
        DodopalDataPage<Role> pages = new DodopalDataPage<Role>(role.getPage(), result);
        return pages;
    }

    @Override
    public Role findRoleById(String id) {
        return roleMapper.findRoleById(id);
    }

}

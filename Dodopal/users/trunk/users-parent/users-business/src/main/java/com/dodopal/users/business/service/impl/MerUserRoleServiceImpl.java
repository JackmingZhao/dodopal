package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.users.business.dao.MerUserRoleMapper;
import com.dodopal.users.business.model.MerUserRole;
import com.dodopal.users.business.service.MerUserRoleService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class MerUserRoleServiceImpl implements MerUserRoleService {
    @Autowired
    MerUserRoleMapper mapper;

    @Override
    public List<MerUserRole> findMerUserRoleByUserCode(String userCode) {
        return mapper.findMerUserRoleByUserCode(userCode);
    }

    @Override
    public int batchAddMerUserRole(List<MerUserRole> merUserRoleList) {
        return mapper.batchAddMerUserRole(merUserRoleList);
    }

    @Override
    public int delMerUserRoleByUserCode(String userCode) {
        return mapper.delMerUserRoleByUserCode(userCode);
    }

    @Override
    public int delMerUserRoleByUserRoleCode(String userCode, String merRoleCode) {
        return mapper.delMerUserRoleByUserRoleCode(userCode, merRoleCode);
    }

}

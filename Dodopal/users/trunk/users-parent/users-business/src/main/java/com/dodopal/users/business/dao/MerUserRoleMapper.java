package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.MerUserRole;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerUserRoleMapper {
    public List<MerUserRole> findMerUserRoleByUserCode(String userCode);

    public int batchAddMerUserRole(List<MerUserRole> merUserRoleList);

    public int delMerUserRoleByUserCode(String userCode);

    public int delMerUserRoleByUserRoleCode(String userCode, String merRoleCode);
}

package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerRoleFun;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerRoleFunMapper {
    public List<MerRoleFun> findMerRoleFun(MerRoleFun merRoleFun);

    public MerRoleFun findMerRoleFunById(String id);

    public List<MerRoleFun> findMerRoleFunByMerRoleCode(String merRoleCode);

    public int delMerRoleFunByMerRoleCode(String merRoleCode);

    public int batchAddMerRoleFun(List<MerRoleFun> list);

    public int updateActivateById(@Param("id") String id, @Param("activate") String activate);

    public int updateActivateByMerRoleCode(@Param("merRoleCode") String merRoleCode, @Param("activate") String activate, @Param("list") List<MerRoleFun> list);
}

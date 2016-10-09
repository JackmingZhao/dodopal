package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerRole;
import com.dodopal.users.business.model.query.MerRoleQuery;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerRoleMapper {
    public List<MerRole> findMerRole(MerRole merRole);

    public List<MerRole> findMerRoleByPage(MerRoleQuery merRoleQuery);

    public MerRole findMerRoleById(String id);

    public MerRole findMerRoleByMerRoleCode(@Param("merCode") String merCode, @Param("merRoleCode") String merRoleCode);

    public List<MerRole> findMerRoleByUserCode(String userCode);

    public String getMerRoleCodeSeq();

    public int addMerRole(MerRole merRole);

    public int delMerRoleByMerRoleCode(@Param("merCode") String merCode, @Param("merRoleCode") String merRoleCode);

    public int batchDelMerRoleByCodes(@Param("merCode") String merCode, @Param("merRoleCodes") List<String> merRoleCodes);

    public int updateMerRoleByMerRoleCode(MerRole merRole);
}

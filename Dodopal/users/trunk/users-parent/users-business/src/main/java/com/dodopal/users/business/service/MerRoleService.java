package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.model.MerRole;
import com.dodopal.users.business.model.query.MerRoleQuery;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerRoleService {

    public List<MerRole> findMerRole(MerRole merRole);

    /**
     * 根据查询条件查询商户角色【分页】
     * @param merRoleQuery
     * @return
     */
    public DodopalDataPage<MerRole> findMerRoleByPage(MerRoleQuery merRoleQuery);

    public MerRole findMerRoleById(String id);

    /**
     * 根据商户号和角色编码查询角色及权限信息
     * @param merCode
     * @param roleCode
     * @return
     */
    public MerRole findMerRoleByMerRoleCode(String merCode, String merRoleCode);

    public List<MerRole> findMerRoleByUserCode(String userCode);

    public int addMerRole(MerRole merRole);

    public int updateMerRole(MerRole merRole);

    /**
     * 根据商户号和角色编码删除角色及权限信息
     * @param merCode
     * @param roleCode
     */
    public int delMerRoleByMerRoleCode(String merCode, String merRoleCode);
 
    /**
     * 根据商户号、角色号批量删除角色信息
     * @param merCode
     * @param merRoleCodes
     * @return
     */
    public int batchDelMerRoleByCodes(String merCode, List<String> merRoleCodes);

    /**
     * 角色是否存在
     * @param merCode 不能为空
     * @param merRoleName 不能为空
     * @param id 可以为空
     * @return
     */
    public boolean checkMerRoleNameExist(String merCode, String merRoleName, String id);
}

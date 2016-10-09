package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.query.MerRoleQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：商户角色信息操作
 * @author lifeng
 */

public interface MerRoleFacade {
    /**
     * 查询商户角色列表
     * @param merRoleDTO
     * @return
     */
    public DodopalResponse<List<MerRoleDTO>> findMerRole(MerRoleDTO merRoleDTO);

    /**
     * 查询商户角色列表【分页】
     * @param merRoleQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerRoleDTO>> findMerRoleByPage(MerRoleQueryDTO merRoleQueryDTO);

    /**
     * 根据角色编码查询角色信息，由于前端页面树的需要，这里权限只取叶子节点
     * @param merCode
     * @param merRoleCode
     * @return
     */
    public DodopalResponse<MerRoleDTO> findMerRoleByMerRoleCode(String merCode, String merRoleCode);

    /**
     * 添加商户角色信息
     * @param merRoleDTO
     * @return
     */
    public DodopalResponse<Integer> addMerRole(MerRoleDTO merRoleDTO);

    /**
     * 修改商户角色信息
     * @param merRoleDTO
     * @return
     */
    public DodopalResponse<Integer> updateMerRole(MerRoleDTO merRoleDTO);

    /**
     * 根据角色编码删除角色信息
     * @param merCode
     * @param merRoleCode
     * @return
     */
    public DodopalResponse<Integer> delMerRoleByMerRoleCode(String merCode, String merRoleCode);

    /**
     * 根据商户号、角色号批量删除角色信息
     * @param merCode
     * @param merRoleCodes
     * @return
     */
    public DodopalResponse<Integer> batchDelMerRoleByCodes(String merCode, List<String> merRoleCodes);

    /**
     * 根据用户编号获取角色权限列表
     * @param merUserFlg
     * @param merProperty
     * @return
     */
    public DodopalResponse<List<MerFunctionInfoDTO>> findMerFuncInfoByUserCode(String userCode);

    /**
     * 根据商户号和角色名称判断角色是否已存在
     * @param merCode
     * @param merRoleName
     * @return
     */
    public DodopalResponse<Boolean> checkMerRoleNameExist(String merCode, String merRoleName, String id);
}

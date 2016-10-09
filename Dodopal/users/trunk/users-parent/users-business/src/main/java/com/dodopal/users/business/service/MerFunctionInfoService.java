package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerFunctionInfo;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerFunctionInfoService {
    /**
     * 标准商户管理员和个人功能查询
     * @param merType
     * @return
     */
    public List<MerFunctionInfo> findMerFunctionInfoByMerType(String merType);

    /**
     * 自定义商户管理员功能查询
     * @param merCode
     * @return
     */
    public List<MerFunctionInfo> findMerFunctionInfoByMerCode(String merCode);

    /**
     * 标准商户操作员功能查询
     * @param userCode
     * @return
     */
    public List<MerFunctionInfo> findStandardOperatorFuns(String userCode, String merType);

    /**
     * 自定义商户操作员功能查询
     * @param userCode
     * @param merCode
     * @return
     */
    public List<MerFunctionInfo> findCustomOperatorFuns(String userCode, String merCode);

    /**
     * 查询所有启用的功能信息
     * @return
     */
    public List<MerFunctionInfo> findMerFunctionInfo(MerFunctionInfo merFunctionInfo);

    /**
     * 查询所有功能（层级0和1）
     * @return
     */
    public List<MerFunctionInfo> findAllFuncInfoForOSS(String merType);

    /**
     * 根据角色号查询功能信息
     * @param merRoleCode
     * @return
     */
    public List<MerFunctionInfo> findMerFunInfoByMerRoleCode(String merRoleCode);

    /**
     * 根据功能编码查询功能列表
     * @param merFunCodes
     * @return
     */
    public List<MerFunctionInfo> batchFindMerFunInfoByFunCode(List<String> merFunCodes);

    /**
     * 查询下级功能列表
     * @param merFunCodes
     * @return
     */
    public List<MerFunctionInfo> batchFindChildMerFunInfo(List<String> merFunCodes);

    /**
     * 按类型查询禁用功能列表
     * @param merType
     * @return
     */
    public List<MerFunctionInfo> findDisableFunByMerType(String merType);
}

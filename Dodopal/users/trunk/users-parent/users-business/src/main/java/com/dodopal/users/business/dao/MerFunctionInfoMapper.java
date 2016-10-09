package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerFunctionInfo;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerFunctionInfoMapper {

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
     * @param merType
     * @return
     */
    public List<MerFunctionInfo> findStandardOperatorFuns(@Param("userCode") String userCode, @Param("merType")String merType);

    /**
     * 自定义商户操作员功能查询
     * @param userCode
     * @param merCode
     * @return
     */
    public List<MerFunctionInfo> findCustomOperatorFuns(@Param("userCode") String userCode, @Param("merCode")String merCode);

    /**
     * 查询功能列表
     * @return
     */
    public List<MerFunctionInfo> findMerFunctionInfo(MerFunctionInfo merFunctionInfo);

    /**
     * 查询所有功能（层级0和1）
     * @return
     */
    public List<MerFunctionInfo> findAllFuncInfoForOSS(@Param("merType") String merType);

    /**
     * 根据角色编码查询功能列表
     * @param merRoleCode
     * @return
     */
    public List<MerFunctionInfo> findMerFunInfoByMerRoleCode(String merRoleCode);

    /**
     * 根据功能编码查询功能列表
     * @param merFunCodes
     * @return
     */
    public List<MerFunctionInfo> batchFindMerFunInfoByFunCode(@Param("merFunCodes")List<String> merFunCodes);

    /**
     * 查询下级功能列表
     * @param merFunCodes
     * @return
     */
    public List<MerFunctionInfo> batchFindChildMerFunInfo(@Param("merFunCodes")List<String> merFunCodes);

    /**
     * 按类型查询禁用功能列表
     * @param merType
     * @return
     */
    public List<MerFunctionInfo> findDisableFunByMerType(String merType);
}

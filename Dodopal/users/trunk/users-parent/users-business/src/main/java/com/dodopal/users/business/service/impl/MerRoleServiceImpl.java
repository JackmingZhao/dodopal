package com.dodopal.users.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.dao.MerRoleMapper;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.model.MerRole;
import com.dodopal.users.business.model.MerRoleFun;
import com.dodopal.users.business.model.query.MerRoleQuery;
import com.dodopal.users.business.service.MerFunctionInfoService;
import com.dodopal.users.business.service.MerRoleFunService;
import com.dodopal.users.business.service.MerRoleService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class MerRoleServiceImpl implements MerRoleService {
    @Autowired
    private MerRoleMapper merRoleMapper;
    @Autowired
    private MerRoleFunService merRoleFunService;
    @Autowired
    private MerFunctionInfoService merFunctionInfoService;

    @Override
    @Transactional(readOnly = true)
    public List<MerRole> findMerRole(MerRole merRole) {
        return merRoleMapper.findMerRole(merRole);
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<MerRole> findMerRoleByPage(MerRoleQuery merRoleQuery) {
        if (StringUtils.isBlank(merRoleQuery.getMerCode())) {
            return null;
        }
        List<MerRole> result = merRoleMapper.findMerRoleByPage(merRoleQuery);
        DodopalDataPage<MerRole> pages = new DodopalDataPage<MerRole>(merRoleQuery.getPage(), result);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
    public MerRole findMerRoleById(String id) {
        return merRoleMapper.findMerRoleById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MerRole findMerRoleByMerRoleCode(String merCode, String merRoleCode) {
        if (StringUtils.isBlank(merCode) || StringUtils.isBlank(merRoleCode)) {
            return null;
        }
        List<MerFunctionInfo> merRoleFunList = merFunctionInfoService.findMerFunInfoByMerRoleCode(merRoleCode);
        MerRole merRole = merRoleMapper.findMerRoleByMerRoleCode(merCode, merRoleCode);
        if (merRole != null) {
            merRole.setMerRoleFunList(merRoleFunList);
        }
        return merRole;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerRole> findMerRoleByUserCode(String userCode) {
        return merRoleMapper.findMerRoleByUserCode(userCode);
    }

    @Override
    @Transactional
    public int addMerRole(MerRole merRole) {
        String merRoleCode = merRoleMapper.getMerRoleCodeSeq();
        //----------保存角色信息
        merRole.setMerRoleCode(merRoleCode);
        int num = merRoleMapper.addMerRole(merRole);
        //----------保存权限信息
        List<MerFunctionInfo> merRoleFunList = merRole.getMerRoleFunList();
        if (!CollectionUtils.isEmpty(merRoleFunList)) {
            List<MerRoleFun> addMerRoleFunList = convertToMerRoleFunList(merRoleCode, merRoleFunList);
            merRoleFunService.batchAddMerRoleFun(addMerRoleFunList);
        }
        return num;
    }

    @Override
    @Transactional
    public int updateMerRole(MerRole merRole) {
        String merRoleCode = merRole.getMerRoleCode();
        List<MerFunctionInfo> merRoleFunList = merRole.getMerRoleFunList();
        // 先删除原有权限信息  TODO:逻辑待优化
        merRoleFunService.delMerRoleFunByMerRoleCode(merRoleCode);
        // 添加信息的权限信息
        List<MerRoleFun> addMerRoleFunList = convertToMerRoleFunList(merRoleCode, merRoleFunList);
        merRoleFunService.batchAddMerRoleFun(addMerRoleFunList);
        return merRoleMapper.updateMerRoleByMerRoleCode(merRole);
    }

    @Override
    @Transactional
    public int delMerRoleByMerRoleCode(String merCode, String merRoleCode) {
        //删除角色权限信息
        merRoleFunService.delMerRoleFunByMerRoleCode(merRoleCode);
        //TODO：是否删除用户角色信息？
        return merRoleMapper.delMerRoleByMerRoleCode(merCode, merRoleCode);
    }

    @Override
    @Transactional
    public int batchDelMerRoleByCodes(String merCode, List<String> merRoleCodes) {
        //删除角色权限信息
        if (!CollectionUtils.isEmpty(merRoleCodes)) {
            for (String merRoleCodeTemp : merRoleCodes) {
                merRoleFunService.delMerRoleFunByMerRoleCode(merRoleCodeTemp);
            }
        }
        return merRoleMapper.batchDelMerRoleByCodes(merCode, merRoleCodes);
    }

    @Transactional(readOnly = true)
    private List<MerRoleFun> convertToMerRoleFunList(String merRoleCode, List<MerFunctionInfo> merRoleFunList) {
        List<MerRoleFun> addMerRoleFunList = new ArrayList<MerRoleFun>(merRoleFunList.size());
        for (MerFunctionInfo merFunInfoTemp : merRoleFunList) {
            MerRoleFun merRoleFunTemp = new MerRoleFun();
            merRoleFunTemp.setActivate(ActivateEnum.ENABLE.getCode());
            merRoleFunTemp.setMerRoleCode(merRoleCode);
            merRoleFunTemp.setMerFunCode(merFunInfoTemp.getMerFunCode());
            merRoleFunTemp.setMerFunName(merFunInfoTemp.getMerFunName());
            addMerRoleFunList.add(merRoleFunTemp);
        }
        return addMerRoleFunList;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkMerRoleNameExist(String merCode, String merRoleName, String id) {
        MerRole merRole = new MerRole();
        merRole.setMerCode(merCode);
        merRole.setMerRoleName(merRoleName);
        List<MerRole> result = merRoleMapper.findMerRole(merRole);
        if(CollectionUtils.isEmpty(result)) {
            return false;
        } else if(StringUtils.isNotBlank(id) && result.get(0).getId().equals(id)) {
            return false;
        }
        return true;
    }

}

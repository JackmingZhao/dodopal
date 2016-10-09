package com.dodopal.users.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerFunctionInfoMapper;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.service.MerFunctionInfoService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class MerFunctionInfoServiceImpl implements MerFunctionInfoService {
    @Autowired
    private MerFunctionInfoMapper merFunctionInfoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> findMerFunctionInfoByMerType(String merType) {
        return merFunctionInfoMapper.findMerFunctionInfoByMerType(merType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> findMerFunctionInfoByMerCode(String merCode) {
        List<MerFunctionInfo> merFunList = merFunctionInfoMapper.findMerFunctionInfoByMerCode(merCode);
        // 增加父节点
        addAllParentNode(merFunList);
        return merFunList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> findStandardOperatorFuns(String userCode, String merType) {
        List<MerFunctionInfo> merFunList = merFunctionInfoMapper.findStandardOperatorFuns(userCode, merType);
        // 增加父节点
        addAllParentNode(merFunList);
        return merFunList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> findCustomOperatorFuns(String userCode, String merCode) {
        List<MerFunctionInfo> merFunList = merFunctionInfoMapper.findCustomOperatorFuns(userCode, merCode);
        // 增加父节点
        addAllParentNode(merFunList);
        return merFunList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> findMerFunctionInfo(MerFunctionInfo merFunctionInfo) {
        return merFunctionInfoMapper.findMerFunctionInfo(merFunctionInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> findAllFuncInfoForOSS(String merType) {
        return merFunctionInfoMapper.findAllFuncInfoForOSS(merType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> findMerFunInfoByMerRoleCode(String merRoleCode) {
        return merFunctionInfoMapper.findMerFunInfoByMerRoleCode(merRoleCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> batchFindMerFunInfoByFunCode(List<String> merFunCodes) {
        return merFunctionInfoMapper.batchFindMerFunInfoByFunCode(merFunCodes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerFunctionInfo> batchFindChildMerFunInfo(List<String> merFunCodes) {
        return merFunctionInfoMapper.batchFindChildMerFunInfo(merFunCodes);
    }

    @Transactional(readOnly = true)
    private void addAllParentNode(List<MerFunctionInfo> merFunList) {
        // OSS在保存树的时候，如果叶子节点没有被完全勾选，则父节点不会保存
        // 但在门户构建树的时候又需要一个完整的树结构，所以在此人为将父节点提取出来
        if (CollectionUtils.isNotEmpty(merFunList)) {
            Map<String, String> curMerFunMap = new HashMap<String, String>();
            for (MerFunctionInfo temp : merFunList) {
                curMerFunMap.put(temp.getMerFunCode(), null);
            }

            List<MerFunctionInfo> allFunInfo = merFunctionInfoMapper.findMerFunctionInfo(new MerFunctionInfo());
            Map<String, MerFunctionInfo> allFunInfoMap = new HashMap<String, MerFunctionInfo>();
            for (MerFunctionInfo temp : allFunInfo) {
                allFunInfoMap.put(temp.getMerFunCode(), temp);
            }

            List<MerFunctionInfo> addList = new ArrayList<MerFunctionInfo>();
            for (MerFunctionInfo temp : merFunList) {
                addParentNode(temp, curMerFunMap, allFunInfoMap, addList);
            }
            if (addList.size() > 0) {
                merFunList.addAll(addList);
            }
        }
    }

    @Transactional(readOnly = true)
    private void addParentNode(MerFunctionInfo temp, Map<String, String> curMerFunMap, Map<String, MerFunctionInfo> allFunInfoMap, List<MerFunctionInfo> addList) {
        String parentCode = temp.getParentCode();
        if (StringUtils.isNotBlank(parentCode)) {
            if (!curMerFunMap.containsKey(parentCode)) {
                if (allFunInfoMap.containsKey(parentCode)) {
                    curMerFunMap.put(parentCode, null);
                    MerFunctionInfo parentMerFun = allFunInfoMap.get(parentCode);
                    addList.add(parentMerFun);
                    addParentNode(parentMerFun, curMerFunMap, allFunInfoMap, addList);
                }
            }
        }
    }

	@Override
	@Transactional(readOnly = true)
	public List<MerFunctionInfo> findDisableFunByMerType(String merType) {
		return merFunctionInfoMapper.findDisableFunByMerType(merType);
	}
}

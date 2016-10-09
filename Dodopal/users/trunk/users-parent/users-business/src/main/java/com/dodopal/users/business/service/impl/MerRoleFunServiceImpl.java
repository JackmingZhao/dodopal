package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerRoleFunMapper;
import com.dodopal.users.business.model.MerRoleFun;
import com.dodopal.users.business.service.MerRoleFunService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class MerRoleFunServiceImpl implements MerRoleFunService {
    @Autowired
    private MerRoleFunMapper merRoleFunMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MerRoleFun> findMerRoleFun(MerRoleFun merRoleFun) {
        return merRoleFunMapper.findMerRoleFun(merRoleFun);
    }

    @Override
    @Transactional(readOnly = true)
    public MerRoleFun findMerRoleFunById(String id) {
        return merRoleFunMapper.findMerRoleFunById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerRoleFun> findMerRoleFunByMerRoleCode(String merRoleCode) {
        return merRoleFunMapper.findMerRoleFunByMerRoleCode(merRoleCode);
    }

    @Override
    @Transactional
    public int delMerRoleFunByMerRoleCode(String merRoleCode) {
        return merRoleFunMapper.delMerRoleFunByMerRoleCode(merRoleCode);
    }

    @Override
    @Transactional
    public int batchAddMerRoleFun(List<MerRoleFun> list) {
        return merRoleFunMapper.batchAddMerRoleFun(list);
    }

    @Override
    @Transactional
    public int updateActivateById(String id, String activate) {
        return merRoleFunMapper.updateActivateById(id, activate);
    }

    @Override
    @Transactional
    public int updateActivateByMerRoleCode(String merRoleCode, String activate, List<MerRoleFun> list) {
        return merRoleFunMapper.updateActivateByMerRoleCode(merRoleCode, activate, list);
    }

}

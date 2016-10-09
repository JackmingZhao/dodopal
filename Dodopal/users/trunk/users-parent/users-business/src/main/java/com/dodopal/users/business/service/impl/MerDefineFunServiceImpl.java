package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerDefineFunMapper;
import com.dodopal.users.business.model.MerDefineFun;
import com.dodopal.users.business.service.MerDefineFunService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class MerDefineFunServiceImpl implements MerDefineFunService {
    @Autowired
    private MerDefineFunMapper merDefineFunMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MerDefineFun> findMerDefineFunByMerCode(String merCode) {
        return merDefineFunMapper.findMerDefineFunByMerCode(merCode);
    }

    @Override
    @Transactional
    public int batchAddMerDefineFunList(List<MerDefineFun> merDefineFunList) {
        return merDefineFunMapper.batchAddMerDefineFunList(merDefineFunList);
    }

    @Override
    @Transactional
    public int deleteMerDefineFunById(String id) {
        return merDefineFunMapper.deleteMerDefineFunById(id);
    }

    @Override
    @Transactional
    public int deleteMerDefineFunByMerCode(String merCode) {
        return merDefineFunMapper.deleteMerDefineFunByMerCode(merCode);
    }

    @Override
    @Transactional
    public int batchDelDefineFunByMerCodes(List<String> merCodes) {
        return merDefineFunMapper.batchDelDefineFunByMerCodes(merCodes);
    }

}

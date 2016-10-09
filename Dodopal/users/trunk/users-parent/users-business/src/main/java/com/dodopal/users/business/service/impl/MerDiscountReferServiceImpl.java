package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerDiscountReferMapper;
import com.dodopal.users.business.model.MerDiscountRefer;
import com.dodopal.users.business.model.query.MerDiscountQuery;
import com.dodopal.users.business.service.MerDiscountReferService;
@Service
public class MerDiscountReferServiceImpl implements MerDiscountReferService {
    
    @Autowired
    MerDiscountReferMapper  merDiscountReferMapper;

    @Transactional(readOnly = true)
    public List<MerDiscountRefer> findMerDiscountRefer(MerDiscountQuery merDiscountQuery) {
        return merDiscountReferMapper.findMerDiscountRefer(merDiscountQuery);
    }

    @Transactional(readOnly = true)
    public List<MerDiscountRefer> findMerDiscountReferByList(String merDiscountId) {
        return merDiscountReferMapper.findMerDiscountReferByList(merDiscountId);
    }

    @Transactional
    public int insertMerDiscountRefer(MerDiscountRefer merDiscountRefer) {
        return merDiscountReferMapper.insertMerDiscountRefer(merDiscountRefer);
    }

    @Transactional
    public int deleteMerDiscountRefer(String merDiscountId) {
        return merDiscountReferMapper.deleteMerDiscountRefer(merDiscountId);
    }

}

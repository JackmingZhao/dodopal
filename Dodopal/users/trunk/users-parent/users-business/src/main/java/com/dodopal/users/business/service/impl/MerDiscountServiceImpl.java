package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.dao.MerDiscountMapper;
import com.dodopal.users.business.model.MerDiscount;
import com.dodopal.users.business.model.query.MerDiscountQuery;
import com.dodopal.users.business.service.MerDiscountService;

@Service
public class MerDiscountServiceImpl implements MerDiscountService {
    
    @Autowired
    MerDiscountMapper merDiscountMapper;

    //查询（分页）
    @Transactional(readOnly = true)
    public DodopalDataPage<MerDiscount> findMerDiscountByPage(MerDiscountQuery merDiscountQuery) {
        List<MerDiscount> result = merDiscountMapper.findMerDiscountByPage(merDiscountQuery);
        DodopalDataPage<MerDiscount> pages = new DodopalDataPage<MerDiscount>(merDiscountQuery.getPage(), result);
        return pages;
    }

    //批量启用or停用 商户折扣
    @Transactional
    public int startOrStopMerDiscount(String activate, List<String> ids) {
        return merDiscountMapper.startOrStopMerDiscount(activate, ids);
    }

    //修改
    @Transactional
    public int updateMerDiscount(MerDiscount merDiscount) {
        return merDiscountMapper.updateMerDiscount(merDiscount);
    }

    //新增
    @Transactional
    public int saveMerDiscount(MerDiscount merDiscount) {
        return merDiscountMapper.saveMerDiscount(merDiscount);
    }

    @Transactional(readOnly = true)
    public MerDiscount findMerDiscountById(String id) {
        // TODO Auto-generated method stub
        return merDiscountMapper.findMerDiscountById(id);
    }

    
    @Transactional(readOnly = true)
    public int findMerDiscountNum(String merCode, String discount) {
        return merDiscountMapper.findMerDiscountNum(merCode,discount);
    }

    @Override
    public MerDiscount findMerDiscountByCode(String merCode, String discount) {
        return merDiscountMapper.findMerDiscountByCode(merCode,discount);
    }

    @Override
    public List<MerDiscount> findMerDiscountByList(MerDiscountQuery merDiscountQuery) {
        // TODO Auto-generated method stub
        return merDiscountMapper.findMerDiscountByList(merDiscountQuery);
    }

}

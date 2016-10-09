package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.dao.MerCountMapper;
import com.dodopal.users.business.model.MerCount;
import com.dodopal.users.business.model.query.MerCountQuery;
import com.dodopal.users.business.service.ManagementForSupplierService;

@Service("managementForSupplierService")
public class ManagementForSupplierServiceImpl implements ManagementForSupplierService {
    
    @Autowired
    private MerCountMapper merCountMapper;

    //查询（分页）
    @Transactional(readOnly = true )
    public DodopalDataPage<MerCount> findMerCountByPage(MerCountQuery merCountQuery) {
       List<MerCount> result =  merCountMapper.findMerCountByPage(merCountQuery);
       DodopalDataPage<MerCount> pages = new DodopalDataPage<MerCount>(merCountQuery.getPage(), result);
       return pages;
    }
    
    
    
    
}

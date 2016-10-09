package com.dodopal.account.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.account.business.dao.AccountChangeChildMerMapper;
import com.dodopal.account.business.dao.AccountChildMerchantMapper;
import com.dodopal.account.business.model.AccountChildMerchant;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.model.ChildMerchantAccountChange;
import com.dodopal.account.business.model.query.AccountChildMerchantQuery;
import com.dodopal.account.business.model.query.ChildMerFundChangeQuery;
import com.dodopal.account.business.service.AccountChildMerchantService;
import com.dodopal.common.model.DodopalDataPage;

@Service
public class AccountChildMerchantServiceImpl implements AccountChildMerchantService {

    @Autowired
    AccountChildMerchantMapper accountChildMerchantMapper;
    
    @Autowired
    AccountChangeChildMerMapper mapper;
    
     //根据上级商户 查询其子商户的总余额 及账户信息 （分页） 
    @Transactional(readOnly = true)
    public DodopalDataPage<AccountChildMerchant> findAccountChildMerByPage(AccountChildMerchantQuery query) {
        
        List<AccountChildMerchant> result =  accountChildMerchantMapper.findAccountChildMerByPage(query);
        DodopalDataPage<AccountChildMerchant> pages = new DodopalDataPage<AccountChildMerchant>(query.getPage(), result);
        return pages;
    }

    //根据上级商户 查询其子商户的总余额 及账户信息
    @Transactional(readOnly = true)
    public List<AccountChildMerchant> findAccountChildMerByList(AccountChildMerchantQuery query) {
        List<AccountChildMerchant> accountChildMerchantList = accountChildMerchantMapper.findAccountChildMerByList(query);
        return  accountChildMerchantList;
    }

    //根据上级商户编号 查询 子商户资金变更记录（分页）
    @Transactional(readOnly = true)
    public DodopalDataPage<ChildMerchantAccountChange> findAccountChangeChildMerByPage(ChildMerFundChangeQuery query) {
        List<ChildMerchantAccountChange>  AccountFundList = mapper.findAccountChangeChildMerByPage(query);
        DodopalDataPage<ChildMerchantAccountChange> pages = new DodopalDataPage<ChildMerchantAccountChange>(query.getPage(), AccountFundList);
        return pages;
    }

   //根据上级商户编号 查询 子商户资金变更记录（导出）
    @Transactional(readOnly = true)
    public List<ChildMerchantAccountChange> findAccountChangeChildMerByList(ChildMerFundChangeQuery query) {
        List<ChildMerchantAccountChange>  AccountFundList = mapper.findAccountChangeChildMerByList(query);
        return AccountFundList;
    }

}

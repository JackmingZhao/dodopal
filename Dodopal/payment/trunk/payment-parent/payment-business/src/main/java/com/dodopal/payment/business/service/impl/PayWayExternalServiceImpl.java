package com.dodopal.payment.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.dao.PayConfigMapper;
import com.dodopal.payment.business.dao.PayWayExternalMapper;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayWayExternal;
import com.dodopal.payment.business.model.query.PayWayQuery;
import com.dodopal.payment.business.service.PayWayExternalService;
/**
 * 外接支付方式
 * @author dodopal
 *
 */
@Service
public class PayWayExternalServiceImpl implements PayWayExternalService {

    @Autowired
    PayWayExternalMapper payWayExternalMapper;
    @Autowired
    PayConfigMapper  payConfigMapper;
    
    //查询
    @Transactional(readOnly = true)
    public DodopalDataPage<PayWayExternal> findPayWayExternalByPage(PayWayQuery payWayQuery) {
        List<PayWayExternal> result = payWayExternalMapper.findPayWayExternalByPage(payWayQuery);
        DodopalDataPage<PayWayExternal> pages = new DodopalDataPage<PayWayExternal>(payWayQuery.getPage(), result);
        return pages;
    }

    //添加
    @Transactional
    public int savePayWayExternal(PayWayExternal payExternal) { 
        return payWayExternalMapper.savePayWayExternal(payExternal);
    }

    //修改
    @Transactional
    public int updatePayWay(PayWayExternal payExternal) {
        return  payWayExternalMapper.updatePayWayExternal(payExternal);
    }

    //批量启用停用
    @Transactional
    public int batchActivateOperator(String activate,String updateUser, List<String> ids) {
        return payWayExternalMapper.batchActivateOperator(activate, updateUser, ids);
    }

    //批量删除
    @Transactional
    public int batchDelPayExternal(List<String> ids) {
        return payWayExternalMapper.batchDelPayWayExternalByIds(ids);
    }

    
    //根据id查询
    @Transactional(readOnly = true)
    public PayWayExternal findPayExternalById(String id) {
        return payWayExternalMapper.findPayExternalById(id);
    }

 
    @Transactional(readOnly = true)
     public List<PayConfig>  findPayWayNameByPayType(String payType){
         return payConfigMapper.findPayConfigPayType(payType);
    }
    
    @Transactional(readOnly = true)
    public int countBymerCodeAndPayConfigId(String merCode,String payConfigId){
        return payWayExternalMapper.countBymerCodeAndPayConfigId(merCode, payConfigId);
        
    }

    /**
     * 根据支付方式id或者支付配置信息id
     * @param payWayId
     * @return
     */
    @Override
    public PayWayExternal queryPayConfigId(String payWayId) {
        return payWayExternalMapper.queryPayConfigId(payWayId);
    }
}

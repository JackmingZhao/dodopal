package com.dodopal.product.business.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.product.business.dao.MerJointQueryMapper;
import com.dodopal.product.business.model.ChargeOrder;
import com.dodopal.product.business.model.ConsumptionOrderCount;
import com.dodopal.product.business.model.ConsumptionOrder;
import com.dodopal.product.business.model.query.ChargeOrderQuery;
import com.dodopal.product.business.model.query.ConsumptionOrderQuery;
import com.dodopal.product.business.service.MerJointQueryService;
@Service
public class MerJointQueryServiceImpl implements MerJointQueryService{
    private final static Logger log = LoggerFactory.getLogger(MerJointQueryServiceImpl.class);

    
    @Autowired
    MerJointQueryMapper mapper;
    
    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<ChargeOrder> findChargeOrderByPage(ChargeOrderQuery query) {
        List<ChargeOrder> result = null;
        changeMerCode(query);
        if(StringUtils.isNotBlank(query.getMchnitid()))
            result = mapper.findChargeOrderByPage(query);
        DodopalDataPage<ChargeOrder> pages = new DodopalDataPage<ChargeOrder>(query.getPage(), result);
        return pages;
    }



    private void changeMerCode(ChargeOrderQuery query) {
        if(StringUtils.isNotBlank(query.getMchnitid())){
           query.setMchnitid(mapper.findMchnitidByMerCode(query.getMchnitid()));
        }
        log.info("根据新商户号查询到老平台商户号为："+query.getMchnitid());
    }

    private void changeMerCode(ConsumptionOrderQuery query) {
        if(StringUtils.isNotBlank(query.getMchnitid())){
           query.setMchnitid(mapper.findMchnitidByMerCode(query.getMchnitid()));
        }
        log.info("根据新商户号查询到老平台商户号为："+query.getMchnitid());
    }
    
    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<ConsumptionOrder> findConsumptionOrderByPage(ConsumptionOrderQuery query) {
        List<ConsumptionOrder> result = null;
        changeMerCode(query);
        if(StringUtils.isNotBlank(query.getMchnitid()))
            result = mapper.findConsumptionOrderByPage(query);
        DodopalDataPage<ConsumptionOrder> pages = new DodopalDataPage<ConsumptionOrder>(query.getPage(), result);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<ConsumptionOrderCount> findConsumptionOrderCountByPage(ChargeOrderQuery query) {
        changeMerCode(query);
        List<ConsumptionOrderCount> result = null;
        if(StringUtils.isNotBlank(query.getMchnitid()))
            result = mapper.findConsumptionOrderCountByPage(query);
        DodopalDataPage<ConsumptionOrderCount> pages = new DodopalDataPage<ConsumptionOrderCount>(query.getPage(), result);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
    public ConsumptionOrderCount findConsumptionOrderCount(ChargeOrderQuery query) {
        changeMerCode(query);
        ConsumptionOrderCount result=null;
        if(StringUtils.isNotBlank(query.getMchnitid()))
         result = mapper.findConsumptionOrderCount(query);
        return result;
    }



    @Override
    @Transactional(readOnly = true)
    public List<ChargeOrder> exportChargeOrder(ChargeOrderQuery query) {
        changeMerCode(query);
        List<ChargeOrder> result=null;
        if(StringUtils.isNotBlank(query.getMchnitid()))
            result = mapper.exportChargeOrder(query);
        return result;
    }



    @Override
    @Transactional(readOnly = true)
    public List<ConsumptionOrder> exportConsumptionOrder(ConsumptionOrderQuery query) {
        changeMerCode(query);
        List<ConsumptionOrder> result=null;
        if(StringUtils.isNotBlank(query.getMchnitid()))
            result = mapper.exportConsumptionOrder(query);
        return result;
    }



    @Override
    @Transactional(readOnly = true)
    public List<ConsumptionOrderCount> exportConsumptionOrderCount(ChargeOrderQuery query) {
        changeMerCode(query);
        List<ConsumptionOrderCount> result=null;
        if(StringUtils.isNotBlank(query.getMchnitid()))
            result = mapper.exportConsumptionOrderCount(query);
        return result;
    }

}

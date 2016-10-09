package com.dodopal.card.business.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.card.business.dao.FrontFailReportMapper;
import com.dodopal.card.business.service.FrontFailPeportService;

@Service("frontFailReportService")
public class FrontFailReportServiceImpl implements FrontFailPeportService {
    @Autowired
    FrontFailReportMapper frontFailReportMapper;
    
    @Transactional(readOnly=true)
    @Override
    public Integer queryCrdOrderCountByPrdOrderId(String prdOrderNo) {
        return frontFailReportMapper.queryCrdOrderCountByPrdOrderId(prdOrderNo);
    }

    @Transactional
    @Override
    public int updateCardSysOrderByPrdOrderNum(Map<String, Object> value) {
        return frontFailReportMapper.updateCardSysOrderByPrdOrderNum(value);
    }
    
    @Transactional(readOnly=true)
    @Override
    public String queryCardSysOrderStatus(String prdOrderNo) {
        return frontFailReportMapper.queryCardSysOrderStatus(prdOrderNo);
    }
    
    @Transactional(readOnly=true)
    @Override
    public String queryCardSysOrderNumByPrdOrderNum(String prdOrderNo) {
        return frontFailReportMapper.queryCardSysOrderNumByPrdOrderNum(prdOrderNo);
    }

}

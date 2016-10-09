package com.dodopal.card.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.card.business.dao.CrdSysOrderMapper;
import com.dodopal.card.business.dao.CrdSysSupplementMapper;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;
import com.dodopal.card.business.service.QueryCrdSysOrderService;
import com.dodopal.common.model.DodopalDataPage;

@Service
public class QueryCrdSysOrderServiceImpl implements QueryCrdSysOrderService {
    
    @Autowired
    CrdSysOrderMapper crdSysOrderMapper;
    
    @Autowired 
    CrdSysSupplementMapper crdSysSupplementMapper;
    
    private final static Logger log = LoggerFactory.getLogger(QueryCrdSysOrderServiceImpl.class);
    /**
     * @Title findCrdSysOrderByPage
     * @Description OSS系统查询卡服务订单
     * @param crdDTO
     * @author 乔
     * @return
     */
    @Override
    public DodopalDataPage<CrdSysOrder> findCrdSysOrderByPage(CrdSysOrderQuery crdSysOrderQuery) {
        //日志写入OSS系统传入过来的卡服务订单的参数 
        log.info("Oss Query cardOrder input crdDTO"+crdSysOrderQuery); 
        List<CrdSysOrder> crdSysOrderList = crdSysOrderMapper.findCrdSysOrderByPage(crdSysOrderQuery);
        DodopalDataPage<CrdSysOrder> pages = new DodopalDataPage<CrdSysOrder>(crdSysOrderQuery.getPage(), crdSysOrderList);
        return pages;
        
    }
    /**
     * @Title findCrdSysOrderByCode
     * @Description 根据卡服务订单号查询详情
     * @param crdOrderNum
     * @return
     */
    @Override
    public CrdSysOrder findCrdSysOrderByCode(String crdOrderNum){
        //日志写入OSS系统传入过来的卡服务订单的参数 
        log.info("Oss Query cardOrder input crdOrderNum"+crdOrderNum); 
        CrdSysOrder crdSysOrder = crdSysOrderMapper.findCrdSysOrderByCode(crdOrderNum);
        return crdSysOrder;
    }
    /**
     * @Title findCrdSysOrderAll
     * @Description 导出卡服务订单内容
     * @return
     */
    @Override
    public List<CrdSysOrder> findCrdSysOrderAll(CrdSysOrderQuery crdSysOrderQuery){
        return crdSysOrderMapper.findCrdSysOrderAll(crdSysOrderQuery);
    }
    
    /**
     * @Title findCrdSysSupplementByCode
     * @Description 根据卡服务订单号查询卡服务补充信息详情
     * @param crdOrderNum
     * @return
     */
    @Override
    public CrdSysSupplement findCrdSysSupplementByCode(String crdOrderNum) {
        //日志写入OSS系统传入过来的卡服务订单的参数 
        log.info("Oss Query CrdSysSupplement input crdOrderNum"+crdOrderNum); 
        CrdSysSupplement crdSysSupplement = crdSysSupplementMapper.findCrdSysSupplementByCode(crdOrderNum);
        return crdSysSupplement;
    }

}

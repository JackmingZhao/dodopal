package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.ProxyCardAddRequestBean;
import com.dodopal.product.business.bean.ProxyCardAddResultBean;
import com.dodopal.product.business.dao.ProxyCardAddQueryMapper;
import com.dodopal.product.business.model.ProxyCardAdd;
import com.dodopal.product.business.model.ProxyCountAllByID;
import com.dodopal.product.business.model.ProxyOffLineOrderTb;
import com.dodopal.product.business.model.query.ProxyCardAddQuery;
import com.dodopal.product.business.model.query.ProxyOffLineOrderTbQuery;
import com.dodopal.product.business.service.ProxyCardAddQueryService;

/**
 * 4.2.1 城市一卡通充值记录查询
 * @author lenovo
 *
 */
@Service
public class ProxyCardAddQueryServiceImpl implements ProxyCardAddQueryService {
    private final static Logger log = LoggerFactory.getLogger(ProxyCardAddQueryServiceImpl.class);
    
    @Autowired
    ProxyCardAddQueryMapper proxyCardAddQueryMapper;
    
    @Override
    public DodopalResponse<List<ProxyCardAddResultBean>> queryCardTradeList(ProxyCardAddRequestBean requestDto) {
        
        DodopalResponse<List<ProxyCardAddResultBean>> response = new DodopalResponse<List<ProxyCardAddResultBean>>();
        try {
            ProxyCardAddQuery proxyCardAddQuery = new ProxyCardAddQuery();
            PropertyUtils.copyProperties(proxyCardAddQuery, requestDto);
            
            List<ProxyCardAddResultBean> proxyCardAddResultBeanList = new ArrayList<ProxyCardAddResultBean>();
            
            List<ProxyCardAdd> proxyCardAddList = proxyCardAddQueryMapper.queryCardTradeList(proxyCardAddQuery);
            if(CollectionUtils.isNotEmpty(proxyCardAddList)){
                for(ProxyCardAdd proxyCardAdd :proxyCardAddList){
                    ProxyCardAddResultBean proxyCardAddResultBean = new ProxyCardAddResultBean();
                    PropertyUtils.copyProperties(proxyCardAddResultBean, proxyCardAdd);
                    proxyCardAddResultBeanList.add(proxyCardAddResultBean);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(proxyCardAddResultBeanList);
            
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("4.2.1 城市一卡通充值记录查询   ProxyCardAddQueryServiceImpl queryCardTradeList call Exception e:", e);
        }
        return response;
    }

    //查询城市一卡通充值记录 （供门户调用）
    @Transactional(readOnly= true)
    public DodopalDataPage<ProxyCardAdd> cardTradeList(ProxyCardAddQuery proxyCardAddQuery) {
        List<ProxyCardAdd> result =  proxyCardAddQueryMapper.cardTradeListByPage(proxyCardAddQuery);
        DodopalDataPage<ProxyCardAdd> pages = new DodopalDataPage<ProxyCardAdd>(proxyCardAddQuery.getPage(), result);
        return pages;
    }

    //查询城市一卡通充值记录 统计 （供门户调用）
    @Transactional(readOnly= true)
    public ProxyCountAllByID findCardTradeListCount(ProxyCardAddQuery proxyCardAddQuery) {
        return proxyCardAddQueryMapper.findCardTradeListCount(proxyCardAddQuery);
    }

    //查询城市一卡通充值记录 （供门户调用）
    @Transactional(readOnly= true)
    public DodopalDataPage<ProxyOffLineOrderTb> offLineTradeList(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery) {
        List<ProxyOffLineOrderTb> result =  proxyCardAddQueryMapper.offLineTradeListByPage(proxyOffLineOrderTbQuery);
        DodopalDataPage<ProxyOffLineOrderTb> pages = new DodopalDataPage<ProxyOffLineOrderTb>(proxyOffLineOrderTbQuery.getPage(), result);
        return pages;
    }

    //城市一卡通充值记录  统计（供门户调用）
    @Transactional(readOnly= true)
    public ProxyCountAllByID findoffLineTradeListCount(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery) {
        return proxyCardAddQueryMapper.findoffLineTradeListCount(proxyOffLineOrderTbQuery);
    }

}

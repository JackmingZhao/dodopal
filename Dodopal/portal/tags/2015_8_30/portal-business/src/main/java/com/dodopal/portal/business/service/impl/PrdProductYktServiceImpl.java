package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PrdProductYktBean;
import com.dodopal.portal.business.service.PrdProductYktService;
import com.dodopal.portal.delegate.MerUserDelegate;
import com.dodopal.portal.delegate.PrdProductYktDelegate;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月20日 下午2:04:56
 */
@Service("prdProductYktServiceImpl")
public class PrdProductYktServiceImpl implements PrdProductYktService{
    @Autowired
    PrdProductYktDelegate prdProductYktDelegate;
    
    private final static Logger log = LoggerFactory.getLogger(PrdProductYktServiceImpl.class);

    
    @Override
    public DodopalResponse<List<PrdProductYktBean>> findAvailableIcdcProductsInCity(String cityId) {
        DodopalResponse<List<PrdProductYktBean>> dodopalResponse = new DodopalResponse<List<PrdProductYktBean>>();
        if(log.isDebugEnabled()){
            log.debug("基于城市查询公交卡充值产品,参数cityId:{}",cityId);
        }
        DodopalResponse<List<PrdProductYktDTO>> resultResponse = prdProductYktDelegate.findAvailableIcdcProductsInCity(cityId);
        try {
            List<PrdProductYktBean> beanList = new ArrayList<PrdProductYktBean>();
            for(PrdProductYktDTO yktDTO : resultResponse.getResponseEntity()){
                PrdProductYktBean tempBean = new PrdProductYktBean();
                PropertyUtils.copyProperties(tempBean, yktDTO);
                beanList.add(tempBean);
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(beanList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PrdProductYktServiceImpl call a error:",e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        if(log.isDebugEnabled()){
            log.debug("基于城市查询公交卡充值产品结束，返回码Code:{}",dodopalResponse.getCode());
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<List<PrdProductYktBean>> findAvailableIcdcProductsForMerchant(String merchantNum, String cityId) {
        DodopalResponse<List<PrdProductYktBean>> dodopalResponse = new DodopalResponse<List<PrdProductYktBean>>();
        if(log.isDebugEnabled()){
            log.debug("查询商户签约城市公交卡充值产品,参数cityId:{}",cityId);
        }
        DodopalResponse<List<PrdProductYktDTO>> resultResponse =  prdProductYktDelegate.findAvailableIcdcProductsForMerchant(merchantNum, cityId);
        try {
            List<PrdProductYktBean> beanList = new ArrayList<PrdProductYktBean>();
            for(PrdProductYktDTO yktDTO : resultResponse.getResponseEntity()){
                PrdProductYktBean tempBean = new PrdProductYktBean();
                PropertyUtils.copyProperties(tempBean, yktDTO);
                beanList.add(tempBean);
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(beanList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PrdProductYktServiceImpl call a error:",e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        if(log.isDebugEnabled()){
            log.debug("查询商户签约城市公交卡充值产品，返回码Code:{}",dodopalResponse.getCode());
        }
        return dodopalResponse;
    }

}

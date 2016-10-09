package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PrdProductYktBean;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月20日 下午2:01:28
 */
public interface PrdProductYktService {
    /**
     * 基于城市查询公交卡充值产品
     * @param cityId
     * @return
     */
    public DodopalResponse<List<PrdProductYktBean>> findAvailableIcdcProductsInCity(String cityId);
    
    /**
     * 查询商户签约城市公交卡充值产品
     * @param merchantNum
     * @param cityId
     * @return
     */
    public DodopalResponse<List<PrdProductYktBean>> findAvailableIcdcProductsForMerchant(String merchantNum, String cityId);

}

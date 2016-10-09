package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月20日 上午11:42:56
 */
public interface PrdProductYktDelegate {
    /**
     * 基于城市查询公交卡充值产品
     * @param cityId
     * @return
     */
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsInCity(String cityId);
    
    /**
     * 查询商户签约城市公交卡充值产品
     * @param merchantNum
     * @param cityId
     * @return
     */
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsForMerchant(String merchantNum, String cityId);

}

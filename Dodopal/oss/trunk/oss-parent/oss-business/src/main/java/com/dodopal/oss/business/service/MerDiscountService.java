package com.dodopal.oss.business.service;

import java.util.List;
import java.util.Map;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.MerchantDiscount;
import com.dodopal.oss.business.model.dto.MerchantDiscountQuery;

public interface MerDiscountService {

	 /**
     * 根据商户号查询 商户折扣
     * @param merCode
     * @return
     */

    public DodopalResponse<DodopalDataPage<MerchantDiscount>> findMerDiscountByPage(MerchantDiscountQuery query);

    /**
     * 启用 or 停用
     * @param activate
     * @param ids
     * @return
     */
    public DodopalResponse<Integer> startOrStopMerDiscount(String activate, List<String> ids);
    
    /**
     * 解除折扣
     * @param discountThresholds
     */
    public DodopalResponse<Integer> unbind(Map<String,Object> map);
    
    /**
     * 绑定折扣 
     * @param discount
     * @return
     */
    public DodopalResponse<Integer> bind(String merId, List<String> ids);
    
    /**
     * 查询交易折扣
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerchantDiscount>> findAllTranDiscountByPage(MerchantDiscountQuery query);

}

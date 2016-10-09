package com.dodopal.payment.delegate;

import java.util.Map;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月17日 下午6:49:28
 */
public interface MerchantDelegate {
    /**
     * 支付交易交易用户或商户合法性
     * @param userType
     * @param code
     * @return
     */
    public DodopalResponse<Map<String,Object>> validateMerchantForPayment(MerUserTypeEnum userType, String code);

    /**
     * @descripton 根据userId 查询userCode
     * @param userID
     * @return
     */
    public DodopalResponse<MerchantUserDTO> findMerUser(String userID);

    public DodopalResponse<Map<String,Object>> validateMerchantUserForPayment(String userCode);

}

package com.dodopal.api.payment.facade;

import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.common.model.DodopalResponse;

public interface PayServiceRateFacade {
    /**
     * 获取支付服务费和费率类型
     * @param payWayId (通用)支付方式id
     * @param busType 业务类型
     * @param amout 金额  单位 （分）
     * @return  DodopalResponse<PayServiceRateDTO>
     */
    DodopalResponse<PayServiceRateDTO> findPayServiceRate(String payWayId,String busType,long amout);

}

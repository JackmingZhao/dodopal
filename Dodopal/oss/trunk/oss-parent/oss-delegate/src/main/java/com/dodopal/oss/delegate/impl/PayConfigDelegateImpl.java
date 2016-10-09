package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayConfigQueryDTO;
import com.dodopal.api.payment.facade.PayConfigFacade;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PayConfigDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月30日 下午5:00:00
 */
@Service("payConfigDelegate")
public class PayConfigDelegateImpl  extends BaseDelegate  implements PayConfigDelegate{

    @Override
    public DodopalResponse<DodopalDataPage<PayConfigDTO>> findPayConfigByPage(PayConfigQueryDTO queryDTO) {
        PayConfigFacade facade =getFacade(PayConfigFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<DodopalDataPage<PayConfigDTO>> response = facade.findPayConfigByPage(queryDTO);
        return response;
    }

    @Override
    public DodopalResponse<PayConfigDTO> findPayConfigById(String id) {
        PayConfigFacade facade =getFacade(PayConfigFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<PayConfigDTO> response = facade.findPayConfigById(id);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> batchActivatePayConfig(String flag, List<String> ids, String updateUser) {
        PayConfigFacade facade =getFacade(PayConfigFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<Boolean> response = facade.batchActivatePayConfig(flag, ids, updateUser);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> updatePayConfig(PayConfigDTO payConfigDTO) {
        PayConfigFacade facade =getFacade(PayConfigFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<Boolean> response = facade.updatePayConfig(payConfigDTO);
        return response;
    }

    
    public DodopalResponse<Boolean> updatePayConfigBankGateway(List<String> ids,String updateUser,String payConfigId,BankGatewayTypeEnum toBankGateWayType){
        PayConfigFacade facade =getFacade(PayConfigFacade.class,DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<Boolean> response = facade.changeGatewayNumber(ids, updateUser, payConfigId,toBankGateWayType);
        return response;
    }

}

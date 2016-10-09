package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayTransferDTO;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.api.users.dto.DirectMerChantDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.TransferDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;
@Service("transferDelegate")
public class TransferDelegateImpl extends BaseDelegate  implements TransferDelegate {

    //根据上级商户查询直营网点
    public DodopalResponse<List<DirectMerChantDTO>> findMerchantByParentCode(String merParentCode, String merName) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerchantByParentCode(merParentCode, merName);
    }
    //根据上级商户查询直营网点
    public DodopalResponse<List<DirectMerChantDTO>> findMerchantByParentCode(String merParentCode, String merName,String businessType) {
    	MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
    	return facade.findMerchantByParentCode(merParentCode, merName,businessType);
    }
    //根据上级商户查询直营网点
    public DodopalResponse<List<DirectMerChantDTO>> findDirectTransferFilter(String merParentCode, String merName,String businessType) {
    	MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
    	return facade.findDirectTransferFilter(merParentCode, merName,businessType);
    }

    //转账
    public DodopalResponse<Boolean> transferAccount(List<PayTransferDTO> payTransferDTOList) {
        PayTraTransactionFacade facade = getFacade(PayTraTransactionFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.transfercreateTran(payTransferDTOList);
    }

}

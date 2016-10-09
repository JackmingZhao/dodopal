package com.dodopal.product.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.IcdcConsumeCardFacade;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.IcdcPurchaseDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service("IcdcPurchaseDelegate")
public class IcdcPurchaseDelegateImpl extends BaseDelegate implements IcdcPurchaseDelegate{

    /**
     * 检验商户合法性
     */
    @Override
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcPurchase(String merchantNum, String userId, String posId, String providerCode,String source) {
        MerchantFacade facade = getFacade(MerchantFacade.class,DelegateConstant.FACADE_USERS_URL);
        return facade.validateMerchantForIcdcPurchase(merchantNum, userId, posId, providerCode,source);
    }
    
    /**
     * 产品库调用卡服务提供的申请扣款初始化指令接口
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> queryCheckCardConsFun(CrdSysOrderDTO crdDTO){
        IcdcConsumeCardFacade facade = getFacade(IcdcConsumeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.queryCheckCardConsFun(crdDTO);
    }
    
    /**
     * 产品库调用卡服务提供的申请扣款指令接口
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> getDeductOrderConsFun(CrdSysOrderDTO crdDTO){
        IcdcConsumeCardFacade facade = getFacade(IcdcConsumeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.getDeductOrderConsFun(crdDTO);
    }
    
    
    /**
     * 产品库调用卡服务提供的上传收单结果接口
     */
    public DodopalResponse<CrdSysOrderDTO> uploadResultConsFun(CrdSysOrderDTO crdDTO){
        IcdcConsumeCardFacade facade = getFacade(IcdcConsumeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.uploadResultConsFun(crdDTO);
    }
}

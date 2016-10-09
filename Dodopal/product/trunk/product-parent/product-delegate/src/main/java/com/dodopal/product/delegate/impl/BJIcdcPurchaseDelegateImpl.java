package com.dodopal.product.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.api.card.facade.BJIcdcConsumeCardFacade;
import com.dodopal.api.card.facade.BJIcdcConsumeCardV71Facade;
import com.dodopal.api.card.facade.SignInSignOutCardFacade;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BJIcdcPurchaseDelegate;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service("BJIcdcPurchaseDelegate")
public class BJIcdcPurchaseDelegateImpl extends BaseDelegate implements BJIcdcPurchaseDelegate{

    /**
     * 根据POS编号查询其绑定商户信息
     * @param posId
     * @return 
     *      返回Map-->key：
     *          merCode(商户编号[String])，
     *          merName(商户名称[String])，
     *          merCode(商户编号[String])，
     */
    @Override
    public DodopalResponse<Map<String, String>> getMerchantByPosCode(String posId) {
        MerchantFacade facade = getFacade(MerchantFacade.class,DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Map<String, String>> returnResponse = facade.getMerchantByPosCode(posId);
        return returnResponse;
    }
    
    /**
     * 检验商户合法性
     */
    @Override
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcPurchase(String merchantNum, String userId, String posId, String providerCode,String source) {
        MerchantFacade facade = getFacade(MerchantFacade.class,DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Map<String, String>> returnResponse = facade.validateMerchantForIcdcPurchase(merchantNum, userId, posId, providerCode,source);
        return returnResponse;
    }

    /**
     * 卡服务：消费验卡生单接口（V61、联机、脱机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardConsByV61(BJCrdSysOrderDTO crdDTO) {
        BJIcdcConsumeCardFacade facade = getFacade(BJIcdcConsumeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> returnResponse = facade.queryCheckCardCons(crdDTO);
        return returnResponse;
    }
    
    /**
     * 卡服务：消费验卡生单接口（V71联机、脱机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardConsByV71(BJCrdSysOrderDTO crdDTO) {
        BJIcdcConsumeCardV71Facade facade = getFacade(BJIcdcConsumeCardV71Facade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> returnResponse = facade.queryCheckCardCons(crdDTO);
        return returnResponse;
    }

    /**
     * 卡服务：消费申请密钥接口（V61联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> getDeductOrderCons(BJCrdSysOrderDTO crdDTO) {
        BJIcdcConsumeCardFacade facade = getFacade(BJIcdcConsumeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> returnResponse = facade.getDeductOrderCons(crdDTO);
        return returnResponse;
    }

    /**
     * 卡服务：消费结果上传接口（V61联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultConsByOnlineV61(BJCrdSysOrderDTO crdDTO) {
        BJIcdcConsumeCardFacade facade = getFacade(BJIcdcConsumeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> returnResponse = facade.uploadResultCons(crdDTO);
        return returnResponse;
    }

    /**
     * 卡服务：消费结果上传接口（V61脱机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultConsByOfflineV61(BJCrdSysOrderDTO crdDTO) {
        BJIcdcConsumeCardFacade facade = getFacade(BJIcdcConsumeCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> returnResponse = facade.uploadResultConsOffLine(crdDTO);
        return returnResponse;
    }

    /**
     * 卡服务：消费申请获得扣款指令接口（V71联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> getDeductOrderConsByOnlineV71(BJCrdSysOrderDTO crdDTO) {
        BJIcdcConsumeCardV71Facade facade = getFacade(BJIcdcConsumeCardV71Facade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> returnResponse = facade.getDeductOrderCons(crdDTO);
        return returnResponse;
    }

    /**
     * 卡服务：消费结果上传接口（V71联机消费）
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultConsByOnlineV71(BJCrdSysOrderDTO crdDTO) {
        BJIcdcConsumeCardV71Facade facade = getFacade(BJIcdcConsumeCardV71Facade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<BJCrdSysOrderDTO> returnResponse = facade.uploadResultCons(crdDTO);
        return returnResponse;
    }

    /**
     * 卡服务：V71脱机消费批量上传
     */
    @Override
    public DodopalResponse<ReslutDataParameter> batchUploadResultByOfflineV71(ReslutDataParameter parameter) {
        SignInSignOutCardFacade facade = getFacade(SignInSignOutCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        DodopalResponse<ReslutDataParameter> returnResponse = facade.batchUploadResult(parameter);
        return returnResponse;
    }

}

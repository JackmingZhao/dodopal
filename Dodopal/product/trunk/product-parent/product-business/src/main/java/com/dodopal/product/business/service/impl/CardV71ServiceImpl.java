/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.product.business.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.ParameterList;
import com.dodopal.api.card.dto.SignParameter;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.OpenSignEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.service.CardV71Service;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.delegate.BJIcdcPurchaseDelegate;
import com.dodopal.product.delegate.CardV71Delegate;

/**
 * Created by lenovo on 2016/3/21.
 */
@Service
public class CardV71ServiceImpl implements CardV71Service {
    
    @Autowired
    private CardV71Delegate cardV71Delegate;
    
    @Autowired
    private BJIcdcPurchaseDelegate bjIcdcPurchaseDelegate;
    
    @Autowired
    private ProductYKTService productYKTService;
    
    /**
     * @description   V71 签到
     * @param signParameter
     * @return
     */
    @Override
    public DodopalResponse<SignParameter> signin(SignParameter signParameter) {
        
        DodopalResponse<SignParameter> SignResponse = new DodopalResponse<SignParameter>();
        
        // 验证城市（通卡公司）是否停启用   2016-05-09 add by shenXiang
        if (StringUtils.isNotBlank(signParameter.getCitycode())) {
            ProductYKT productYkt = productYKTService.getYktInfoByBusinessCityCode(signParameter.getCitycode());
            
            // 1，检验通卡公司是否处于启用状态,相关充值业务是否处于开通状态
            if (productYkt == null || !ActivateEnum.ENABLE.getCode().equals(productYkt.getActivate()) 
                || (!OpenSignEnum.OPENED.getCode().equals(productYkt.getYktIsRecharge()) 
                    && !OpenSignEnum.OPENED.getCode().equals(productYkt.getYktIsPay()))) {

                // 城市尚未启用，请联系客服人员
                SignResponse.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);
                SignResponse.setResponseEntity(signParameter);
                return SignResponse;
            }
        }
        
        // 签到追加POS注册及绑定商户关系验证   2016-04-22 add by shenXiang 
        DodopalResponse<Map<String, String>> merInfoMapResponse = null;
        try {
            merInfoMapResponse = bjIcdcPurchaseDelegate.getMerchantByPosCode(signParameter.getPosid());
        }
        catch (HessianRuntimeException e) {
            SignResponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
            SignResponse.setResponseEntity(signParameter);
            return SignResponse;
        }
        if (!ResponseCode.SUCCESS.equals(merInfoMapResponse.getCode())) {
            SignResponse.setCode(merInfoMapResponse.getCode());
            SignResponse.setResponseEntity(signParameter);
            return SignResponse;
        }
        
        // 调用卡服务签到接口
        try {
            SignResponse = cardV71Delegate.signin(signParameter);
        }
        catch (HessianRuntimeException e) {
            SignResponse.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            SignResponse.setResponseEntity(signParameter);
            return SignResponse;
        }
        
        return  SignResponse;
    }
    /**
     * @description   V71 签退
     * @param signParameter
     * @return
     */
    @Override
    public DodopalResponse<SignParameter> signout(SignParameter signParameter) {
        return  cardV71Delegate.signout(signParameter);
    }

    /**
     * @description   V71 参数下载
     * @param parameterList
     * @return
     */
    @Override
    public DodopalResponse<ParameterList> paraDownload(ParameterList parameterList) {
        return cardV71Delegate.paraDownload(parameterList);
    }
    
    /**
     * 查询脱机消费优惠信息
     * @param bjDiscountDTO
     * @return
     */
    @Override
    public DodopalResponse<BJDiscountDTO> queryDiscountAmt(BJDiscountDTO bjDiscountDTO) {

        DodopalResponse<BJDiscountDTO> response = new DodopalResponse<BJDiscountDTO>();
        
        // 城市开启（通卡公司停启用，消费业务是否开启）
        String yktCode = "";
        if (StringUtils.isNotBlank(bjDiscountDTO.getCitycode())) {
            ProductYKT productYkt = productYKTService.getYktInfoByBusinessCityCode(bjDiscountDTO.getCitycode());
            
            if (productYkt == null) {
                
                // 城市尚未启用，请联系客服人员
                response.setCode(ResponseCode.PRODUCT_CHECK_YKT_STATUS_DISABLE);
                response.setResponseEntity(bjDiscountDTO);
                return response;
            }
            yktCode = productYkt.getYktCode();
            DodopalResponse<ProductYKT> yktResponse = productYKTService.validateYktServiceNormalForIcdcConsume(yktCode);
            
            if (!ResponseCode.SUCCESS.equals(yktResponse.getCode())) {
                response.setCode(yktResponse.getCode());
                response.setResponseEntity(bjDiscountDTO);
                return response;
            }
            
        }
        
        // 进行pos、商户合法性的验证
        DodopalResponse<Map<String, String>> merResponse = bjIcdcPurchaseDelegate.validateMerchantForIcdcPurchase(bjDiscountDTO.getMercode(), "", bjDiscountDTO.getPosid(), yktCode, "");
        
        if (!ResponseCode.SUCCESS.equals(merResponse.getCode())) {
            response.setCode(merResponse.getCode());
            response.setResponseEntity(bjDiscountDTO);
            return response;
        }
        
        Map<String, String> merInfoMap = merResponse.getResponseEntity();
        String merCode = merInfoMap.containsKey("merCode") ? merInfoMap.get("merCode") : "";
        String merType = merInfoMap.containsKey("merType") ? merInfoMap.get("merType") : "";
        bjDiscountDTO.setMercode(merCode);
        bjDiscountDTO.setMertype(merType);
        
        // 调用卡服务签到接口
        try {
            response = cardV71Delegate.queryDiscountAmt(bjDiscountDTO);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setResponseEntity(bjDiscountDTO);
            return response;
        }
        
        return response;
    }
}

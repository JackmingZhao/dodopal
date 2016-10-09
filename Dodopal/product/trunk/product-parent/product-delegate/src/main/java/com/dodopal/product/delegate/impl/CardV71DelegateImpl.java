/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.product.delegate.impl;

import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.ParameterList;
import com.dodopal.api.card.facade.SignInSignOutCardFacade;
import com.dodopal.api.card.dto.SignParameter;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.CardV71Delegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2016/3/21.
 */
@Service
public class CardV71DelegateImpl extends BaseDelegate implements CardV71Delegate {
    /**
     * @description 签到
     * @param signParameter
     * @return
     */
    @Override
    public DodopalResponse<SignParameter> signin(SignParameter signParameter) {
        SignInSignOutCardFacade facade = getFacade(SignInSignOutCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.SignIn(signParameter);
    }
    /**
     * @description 签退
     * @param signParameter
     * @return
     */
    @Override
    public DodopalResponse<SignParameter> signout(SignParameter signParameter) {
        SignInSignOutCardFacade facade = getFacade(SignInSignOutCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.SignOut(signParameter);
    }

    /**
     *@description 参数下载接口，提供tcpservice调用
     * @param parameterList
     * @return
     */
    @Override
    public DodopalResponse<ParameterList> paraDownload(ParameterList parameterList) {
        SignInSignOutCardFacade facade = getFacade(SignInSignOutCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.downloadParameter(parameterList);
    }
    
    /**
     * 查询脱机消费优惠信息
     * @param bjDiscountDTO
     * @return
     */
    @Override
    public DodopalResponse<BJDiscountDTO> queryDiscountAmt(BJDiscountDTO bjDiscountDTO) {
        SignInSignOutCardFacade facade = getFacade(SignInSignOutCardFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.queryDiscountAmt(bjDiscountDTO);
    }
    
}

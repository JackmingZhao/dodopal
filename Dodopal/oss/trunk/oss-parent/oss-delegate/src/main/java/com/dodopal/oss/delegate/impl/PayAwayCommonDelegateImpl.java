package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.api.payment.dto.query.PayCommonQueryDTO;
import com.dodopal.api.payment.facade.PayCommonFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PayAwayCommonDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;
/**
 * 用户常用支付方式 PayAwayCommonDelegateImpl
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月11日
 *
 */
@Service("PayAwayCommonDelegate")
public class PayAwayCommonDelegateImpl extends BaseDelegate implements PayAwayCommonDelegate {

    @Override
    public DodopalResponse<DodopalDataPage<PayWayCommonDTO>> findPayAwayCommonListByPage(PayCommonQueryDTO queryDTO) {
        PayCommonFacade facade  = getFacade(PayCommonFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.findPayCommonByPage(queryDTO);
    }

    @Override
    public DodopalResponse<Integer> deletePayAwayCommon(List<String> ids) {
        PayCommonFacade facade  = getFacade(PayCommonFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.deletePayCommonByIds(ids);
    }

}

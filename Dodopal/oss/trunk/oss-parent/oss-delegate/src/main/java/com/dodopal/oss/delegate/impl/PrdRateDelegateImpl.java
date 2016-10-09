package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.PrdRateDTO;
import com.dodopal.api.product.facade.PrdRateFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PrdRateDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;
@Service("prdRateDelegate")
public class PrdRateDelegateImpl extends BaseDelegate implements PrdRateDelegate {

    @Override
    public DodopalResponse<List<PrdRateDTO>> findPrdRate() {
        PrdRateFacade facade = getFacade(PrdRateFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findPrdRate();
    }

}

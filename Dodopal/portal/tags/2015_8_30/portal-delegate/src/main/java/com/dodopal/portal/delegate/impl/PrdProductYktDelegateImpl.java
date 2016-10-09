package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.api.product.facade.PrdProductYktFacade;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.query.MerRoleQueryDTO;
import com.dodopal.api.users.facade.MerRoleFacade;
import com.dodopal.api.users.facade.RegisterFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.PosDelegate;
import com.dodopal.portal.delegate.PrdProductYktDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月20日 上午11:46:51
 */
@Service("prdProductYktDelegate")
public class PrdProductYktDelegateImpl extends BaseDelegate implements PrdProductYktDelegate  {

    @Override
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsInCity(String cityId) {
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findAvailableIcdcProductsInCity(cityId);
    }

    @Override
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsForMerchant(String merchantNum, String cityId) {
        PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findAvailableIcdcProductsForMerchant(merchantNum, cityId);
    }
}

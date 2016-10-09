package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.MerDiscountReferDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.api.users.facade.MerDiscountFacade;
import com.dodopal.api.users.facade.MerDiscountReferFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.MerDiscountDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service("merDiscountDelegate")
public class MerDiscountDelegateImpl extends BaseDelegate implements MerDiscountDelegate {

	@Override
	public DodopalResponse<List<MerDiscountDTO>> findMerDiscountByList(MerDiscountQueryDTO merDiscountQueryDTO) {
		MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
	    return facade.findMerDiscountByList(merDiscountQueryDTO);
	}

	@Override
	public DodopalResponse<List<MerDiscountReferDTO>> findMerDiscountRefer(MerDiscountQueryDTO merDiscountQueryDTO) {
		MerDiscountReferFacade facade = getFacade(MerDiscountReferFacade.class, DelegateConstant.FACADE_USERS_URL);
	    return facade.findMerDiscountRefer(merDiscountQueryDTO);
	}

}

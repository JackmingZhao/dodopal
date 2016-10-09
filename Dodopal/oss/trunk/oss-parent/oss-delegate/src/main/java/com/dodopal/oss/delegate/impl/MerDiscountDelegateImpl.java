package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.api.users.facade.MerDiscountFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.MerDiscountDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("merDiscountDelegate")
public class MerDiscountDelegateImpl extends BaseDelegate implements MerDiscountDelegate{

	
	//查询商户的折扣（分页）
    public DodopalResponse<DodopalDataPage<MerDiscountDTO>> findMerDiscountByPage(MerDiscountQueryDTO merDiscountQueryDTO) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerDiscountByPage(merDiscountQueryDTO);
    }

    //批量or启用商户折扣
    public DodopalResponse<Integer> startOrStopMerDiscount(String activate, List<String> ids) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.startOrStopMerDiscount(activate, ids);
    }
}

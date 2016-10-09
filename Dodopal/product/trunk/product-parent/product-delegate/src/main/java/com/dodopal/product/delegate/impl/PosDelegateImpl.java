package com.dodopal.product.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.facade.PosFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.PosDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/** 
 * @author lifeng@dodopal.com
 */
@Service("posDelegate")
public class PosDelegateImpl extends BaseDelegate implements PosDelegate {

	@Override
	public DodopalResponse<DodopalDataPage<PosDTO>> findPosListPage(PosQueryDTO findDto) {
		PosFacade facade = getFacade(PosFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<DodopalDataPage<PosDTO>> response = facade.findPosListPage(findDto);
		return response;
	}

}

package com.dodopal.product.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.service.PosService;
import com.dodopal.product.delegate.PosDelegate;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class PosServiceImpl implements PosService {
	private final static Logger logger = LoggerFactory.getLogger(PosServiceImpl.class);

	@Autowired
	private PosDelegate posDelegate;

	@Override
	public DodopalResponse<DodopalDataPage<PosDTO>> findPosListPage(PosQueryDTO findDto) {
		DodopalResponse<DodopalDataPage<PosDTO>> dodopal = new DodopalResponse<DodopalDataPage<PosDTO>>();
		try {
			dodopal = posDelegate.findPosListPage(findDto);
		} catch (HessianRuntimeException e) {
			logger.error("PosServiceImpl's findPosListPage call an error", e);
			dodopal.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return dodopal;
	}

}

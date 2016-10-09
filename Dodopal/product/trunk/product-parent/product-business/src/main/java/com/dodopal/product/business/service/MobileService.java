package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.api.users.dto.MobileTypeWhiteListDTO;
import com.dodopal.common.model.DodopalResponse;

/** 
 * @author lifeng@dodopal.com
 */

public interface MobileService {
	public DodopalResponse<List<MobileTypeWhiteListDTO>> findAllWhiteList();
}

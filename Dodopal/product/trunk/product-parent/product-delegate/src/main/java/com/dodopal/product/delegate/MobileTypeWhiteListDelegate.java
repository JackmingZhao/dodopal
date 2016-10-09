package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MobileTypeWhiteListDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author lifeng@dodopal.com
 */

public interface MobileTypeWhiteListDelegate {
	public DodopalResponse<List<MobileTypeWhiteListDTO>> findAllWhiteList();
}

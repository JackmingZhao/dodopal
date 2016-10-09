package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.MobileTypeWhiteListDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author lifeng@dodopal.com
 */

public interface MobileTypeWhiteListFacade {
	public DodopalResponse<List<MobileTypeWhiteListDTO>> findAllWhiteList();
}

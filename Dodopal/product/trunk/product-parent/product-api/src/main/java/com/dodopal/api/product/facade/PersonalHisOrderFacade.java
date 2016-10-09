package com.dodopal.api.product.facade;

import com.dodopal.api.product.dto.PersonalHisOrderDTO;
import com.dodopal.api.product.dto.query.PersonalHisOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/** 
 * @author lifeng@dodopal.com
 */

public interface PersonalHisOrderFacade {
	public DodopalResponse<DodopalDataPage<PersonalHisOrderDTO>> findRechargeOrderByPage(PersonalHisOrderQueryDTO personalHisOrderQueryDTO);
}

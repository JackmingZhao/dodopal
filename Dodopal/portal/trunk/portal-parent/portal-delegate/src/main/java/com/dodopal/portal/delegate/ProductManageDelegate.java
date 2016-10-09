package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.api.product.dto.query.PrdProductYktQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ProductManageDelegate {

	 /**
     * 基于城市查询公交卡充值产品(分页)
     * @param cityId
     * @return
     */
    public  DodopalResponse<DodopalDataPage<PrdProductYktDTO>> findAvailableIcdcProductsByPage(PrdProductYktQueryDTO query,String userType);
	
	 /**
     * 基于城市查询公交卡充值产品
     * @param cityId
     * @return
     */
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsInCity(PrdProductYktQueryDTO query,String userType);
}

package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface MerDiscountDelegate {

	 /**
     * 根据商户号查询 商户折扣
     * @param merCode
     * @return
     */
 
    public DodopalResponse<DodopalDataPage<MerDiscountDTO>> findMerDiscountByPage(MerDiscountQueryDTO merDiscountQueryDTO);

    /**
     * 启用 or 停用
     * @param activate
     * @param ids
     * @return
     */
    public DodopalResponse<Integer> startOrStopMerDiscount(String activate, List<String> ids);  
}

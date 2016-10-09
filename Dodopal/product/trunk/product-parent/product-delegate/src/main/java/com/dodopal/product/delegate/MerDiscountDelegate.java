package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.MerDiscountReferDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.model.DodopalResponse;

public interface MerDiscountDelegate {
	 public DodopalResponse<List<MerDiscountDTO>> findMerDiscountByList(MerDiscountQueryDTO merDiscountQueryDTO);
	 /**
     * 连锁商户直营网点  根据其 商户号和启用标识 查询其对应的折扣
     * @param merCode
     * @return
     */
	 public DodopalResponse<List<MerDiscountReferDTO>> findMerDiscountRefer(MerDiscountQueryDTO merDiscountQueryDTO);
}

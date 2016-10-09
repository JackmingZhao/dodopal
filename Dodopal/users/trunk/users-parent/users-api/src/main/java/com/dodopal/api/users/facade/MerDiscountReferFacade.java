package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.MerDiscountReferDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.model.DodopalResponse;

public interface MerDiscountReferFacade {
    /**
     * 根据 商户折扣 id 查询对应的直营网点（用于反选）
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerDiscountReferDTO>> findMerDiscountReferByList(String merDiscountId);
    
    /**
     * 商户时直营网点  根据其 商户号和启用标识 查询其对应的折扣
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerDiscountReferDTO>> findMerDiscountRefer(MerDiscountQueryDTO merDiscountQueryDTO);
    
    /**
     * 新增商户折扣中间表记录（直营网点）
     * @param merDiscount
     * @return
     */
    DodopalResponse<Integer> insertMerDiscountRefer(MerDiscountReferDTO merDiscountReferDTO);
    
    /**
     * 根据商户折扣id 删除对应折扣的所有直营网点
     * @param merDiscount
     * @return
     */
    DodopalResponse<Integer> deleteMerDiscountRefer(String merDiscountId);
}

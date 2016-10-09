package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface MerDiscountFacade {
    
    /**
     * 根据商户号查询 商户折扣
     * @param merCode
     * @return
     */

    public DodopalResponse<DodopalDataPage<MerDiscountDTO>> findMerDiscountByPage(MerDiscountQueryDTO merDiscountQueryDTO);
    
    
    /**
     * 根据商户号查询 商户折扣
     * @param merCode
     * @return
     */

    public DodopalResponse<List<MerDiscountDTO>> findMerDiscountByList(MerDiscountQueryDTO merDiscountQueryDTO);

    /**
     * 启用 or 停用
     * @param activate
     * @param ids
     * @return
     */
    public DodopalResponse<Integer> startOrStopMerDiscount(String activate, List<String> ids);

    /**
     * 修改商户折扣
     * @param merDiscountDTO
     * @return
     */
    public DodopalResponse<Integer> updateMerDiscount(MerDiscountDTO merDiscountDTO);

    /**
     * 新增商户折扣
     * @param merDiscountDTO
     * @return
     */
    public DodopalResponse<Integer> saveMerDiscount(MerDiscountDTO merDiscountDTO);

    /**
     * 根据id 查询商户折扣详情
     * @param id
     * @return
     */
    public DodopalResponse<MerDiscountDTO> findMerDiscountById(String id);
    
    /**
     * 查询商户折扣是否存在
     * @param merCode
     * @param discount
     * @return
     */
    public DodopalResponse<Integer> findMerDiscountNum(String merCode, String discount);
    
    /**
     * 根据商户号和折扣 查询商户折扣详细信息
     * @param merCode
     * @param discount
     * @return
     */
    public DodopalResponse<MerDiscountDTO> findMerDiscountByCode(String merCode, String discount);
}

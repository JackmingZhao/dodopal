package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.MerDiscountReferDTO;
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

    /**
     * 查询商户折扣是否存在
     * @param merCode
     * @param discount
     * @return
     */
    public DodopalResponse<Integer>   findMerDiscountNum(String merCode, String discount);
    
    
    
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
     * 查询详情
     * @param id
     * @return
     */
    public DodopalResponse<MerDiscountDTO> findMerDiscountById(String id);
    
    
    /**
     * 新增商户折扣中间表记录（直营网点）
     * @param merDiscount
     * @return
     */
    DodopalResponse<Integer> insertMerDiscountRefer(MerDiscountReferDTO merDiscountReferDTO);
    
    /**
     * 根据 商户折扣 id 查询对应的直营网点
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerDiscountReferDTO>> findMerDiscountReferByList(String merDiscountId);
    
    
    /**
     * 根据商户号和折扣  查询商户折扣详细信息
     * @param merCode
     * @param discount
     * @return
     */
    public DodopalResponse<MerDiscountDTO> findMerDiscountByCode(String merCode, String discount);
    
    
    /**
     * 根据商户折扣id 删除对应折扣的所有直营网点
     * @param merDiscount
     * @return
     */
    DodopalResponse<Integer> deleteMerDiscountRefer(String merDiscountId);

}

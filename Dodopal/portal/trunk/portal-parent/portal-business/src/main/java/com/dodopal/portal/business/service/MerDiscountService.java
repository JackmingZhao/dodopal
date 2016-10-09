package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerDiscountBean;
import com.dodopal.portal.business.model.MerDiscountAdd;
import com.dodopal.portal.business.model.MerDiscountEdit;

public interface MerDiscountService {
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
     * 修改商户折扣
     * @param merDiscountDTO
     * @return
     */
    public DodopalResponse<Integer> updateMerDiscount(MerDiscountDTO merDiscountDTO);

    /**
     * 添加商户折扣
     * @param merDiscountDTO
     * @return
     */
    public DodopalResponse<Integer> saveMerDiscount(MerDiscountDTO merDiscountDTO);
    
  
    
    /**
     * 查询详情
     * @param id
     * @return
     */
    
    public DodopalResponse<MerDiscountBean> findMerDiscountById(String id,String merParentCode,String merName);
    
    /**
     * 新增商户折扣
     * @param merDiscountAdd
     * @return
     */
    public  DodopalResponse<Boolean> addMerDiscount(MerDiscountAdd merDiscountAdd);
    
    /**
     * 编辑商户折扣
     * @param merDiscountAdd
     * @return
     */
    public  DodopalResponse<Boolean> editMerDiscount(MerDiscountEdit merDiscountEdit);
    
    

}

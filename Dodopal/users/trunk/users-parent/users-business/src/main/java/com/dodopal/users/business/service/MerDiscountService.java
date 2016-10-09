package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.MerDiscount;
import com.dodopal.users.business.model.query.MerDiscountQuery;

public interface MerDiscountService {

    
    /**
     * 根据商户号查询 商户折扣
     * @param merCode
     * @return
     */
    public DodopalDataPage<MerDiscount> findMerDiscountByPage(MerDiscountQuery merDiscountQuery);
    
    
    /**
     * 根据商户号查询 商户折扣
     * @param merCode
     * @return
     */
    public List<MerDiscount> findMerDiscountByList(MerDiscountQuery merDiscountQuery);
    
    
    /**
     * 启用 or 停用
     * @param activate
     * @param ids
     * @return
     */
    public int startOrStopMerDiscount(String activate, List<String> ids); 
    
    
 
    /**
     * 修改
     * @param merDiscount
     * @return
     */
    public int updateMerDiscount(MerDiscount merDiscount);
    
    /**
     * 新增商户折扣
     * @param merDiscount
     * @return
     */
    public int saveMerDiscount(MerDiscount merDiscount);
    
    /**
     * 根据id查询商户折扣
     * @param id
     * @return
     */
    public MerDiscount findMerDiscountById(String id);
    
    /**
     * 查询折扣是否存在
     * @param merCode
     * @param discount
     * @return
     */
    public int findMerDiscountNum(String merCode, String discount);
    
    /**
     * 根据商户号和折扣 查询 商户折扣详细信息
     * @param merCode
     * @param discount
     * @return
     */
    public MerDiscount  findMerDiscountByCode(String merCode, String discount);
    
}

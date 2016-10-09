package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerDiscountRefer;
import com.dodopal.users.business.model.query.MerDiscountQuery;

public interface MerDiscountReferMapper {
    /**
     * 查询 商户折扣中间表 （分页，商户为直营网点）
     * @param merCode
     * @return
     */
    public List<MerDiscountRefer> findMerDiscountRefer(MerDiscountQuery merDiscountQuery);
    
    /**
     * 根据 商户折扣 id 查询对应的直营网点（用于反选）
     * @param merCode
     * @return
     */
    public List<MerDiscountRefer> findMerDiscountReferByList(@Param("merDiscountId")String merDiscountId);
    
    /**
     * 新增商户折扣中间表记录（直营网点）
     * @param merDiscount
     * @return
     */
    public int insertMerDiscountRefer(MerDiscountRefer merDiscountRefer);
    
    /**
     * 根据商户折扣id 删除对应折扣的所有直营网点
     * @param merDiscount
     * @return
     */
    public int deleteMerDiscountRefer(String merDiscountId);
    
}

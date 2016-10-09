package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerDiscount;
import com.dodopal.users.business.model.query.MerDiscountQuery;

/**
 * 商户折扣
 * @author lenovo
 */
public interface MerDiscountMapper {

    /**
     * 根据商户号查询 商户折扣
     * @param merCode
     * @return
     */
    public List<MerDiscount> findMerDiscountByPage(MerDiscountQuery merDiscountQuery);
    
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
    public int startOrStopMerDiscount(@Param("activate") String activate, @Param("ids") List<String> ids);

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
    public MerDiscount findMerDiscountById(@Param("id")String id);
    
    /**
     * 查询商户折扣是否存在
     * @param merCode
     * @param discount
     * @return
     */
    public int findMerDiscountNum(@Param("merCode")String merCode, @Param("discount")String discount);
    /**
     * 根据商户号和商户折扣 查询商户折扣的详细信息
     * @param merCode
     * @param discount
     * @return
     */
    public MerDiscount findMerDiscountByCode(@Param("merCode")String merCode, @Param("discount")String discount);

}

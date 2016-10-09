package com.dodopal.oss.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.DiscountMerchantInfo;
import com.dodopal.oss.business.model.MerchantDiscount;
import com.dodopal.oss.business.model.dto.MerchantDiscountQuery;
import com.dodopal.oss.business.model.dto.MerchantQuery;

public interface MerchantDiscountMapper {

	public List<MerchantDiscount> findMerchantDiscountsByUserDiscountByPage(MerchantDiscountQuery mdq);
	
	public List<DiscountMerchantInfo> findMerchantsByDiscountIdByPage(MerchantQuery merchantQuery);
	
	public void insertMerDiscount(MerchantDiscount merDiscount);
	
	public String getMerDiscountId();
	
	public void deleteMerDiscountByIds(@Param("delSql")String delSql);
	
	public void updateMerDiscount(MerchantDiscount merDiscount);
	
	public void insertMerTranDiscount(MerchantDiscount merDiscount);
	
	public void delMerTranDiscountByDiscountId(@Param("id")String id);
	
	public MerchantDiscount findDiscountById(String discountId);
	
	public List<DiscountMerchantInfo> findMerArrByDiscountId(String discountId);
	
	public List<DiscountMerchantInfo> findMerInfoByIdArr(@Param("merCodeArr")String[] merCodeArr);
	
	public List<MerchantDiscount> findMerchantDiscountsPageByUserDiscount(MerchantDiscountQuery mdq);
	
	public List<String> getMerCodeListByDiscountId(@Param("discountId")String discountId);
	
	/*************************************** sql param in java ********************************************/
	
	public List<String> findMerDiscountsByMerCode(@Param("merCode")String merCode);
	
	public void insertSql(@Param("insSql")String insSql);
	
	public String getConflictInfoList(@Param("paramSql")String paramSql);
	
//	public List<MerchantDiscount> findMerchantDiscountsByMerIdByPage(@Param("sql")String sql);
								
	public MerchantDiscount getMerchantDiscountById(@Param("sql")String sql);
	
	/*************************************** sql param in java ********************************************/
	
	public List<MerchantDiscount> findMerchantDiscountsByMerIdByPage(MerchantDiscountQuery mdq);
	
	 /**
     * 根据商户号查询折扣
     * @param merCode
     * @return
     */
    public List<MerchantDiscount> findTranDiscountByPage(MerchantDiscountQuery query);
    
    /**
     * 接除折扣
     * @param discountThresholds
     */
    public int unbind(Map<String,Object> map);
    
    /**
     * 绑定折扣 
     * @param discount
     * @return
     */
    public int bind(MerchantDiscount discount);
	
	 /**
     * 根据商户号查询折扣
     * @param merCode
     * @return
     */
//    public List<MerchantDiscount> findTranDiscountByPage(MerchantDiscountQuery query);
    
    /**
     * 接除折扣
     * @param discountThresholds
     */
//    public int unbind(Map<String,Object> map);
    
    /**
     * 绑定折扣 
     * @param discount
     * @return
     */
//    public int bind(MerchantDiscount discount);
	
}

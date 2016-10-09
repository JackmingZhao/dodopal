package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.DiscountMerchantInfo;
import com.dodopal.oss.business.model.MerchantDiscount;
import com.dodopal.oss.business.model.dto.MerchantDiscountQuery;
import com.dodopal.oss.business.model.dto.MerchantQuery;

/**
 * @author Mikaelyan
 */
public interface MerchantDiscountService {

	/**
	 * @author Mikaelyna
	 * 根据折扣值查询折扣信息列表
	 * @param mdq
	 * @return
	 */
	public DodopalDataPage<MerchantDiscount> findMerchantDiscountsByUserDiscountByPage(MerchantDiscountQuery mdq);

	/**
	 * @author Mikaelyan
	 * 根据折扣信息Id 得到以绑定的商户和未绑定的商户列表
	 * @param merchantQuery
	 * @return
	 */
	public DodopalDataPage<DiscountMerchantInfo> findMerchantsByDiscountIdByPage(MerchantQuery merchantQuery);
	
	
	public String saveOrUpdateMerDiscount(MerchantDiscount merDiscount);
	
	public MerchantDiscount findDiscountById(String discountId);
	
	public List<DiscountMerchantInfo> findMerArrByDiscountId(String discountId);
	
	public List<DiscountMerchantInfo> findMerInfoByIdArr(String[] merCodeArr);
	
	public String deleteMerDiscountByIds(String[] ids);
}

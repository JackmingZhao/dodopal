package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.MerDiscountBean;
import com.dodopal.product.business.bean.MerDiscountReferBean;

public interface MerDiscountService {
	 public DodopalResponse<List<MerDiscountBean>> findMerDiscountByList(MerDiscountQueryDTO merDiscountQueryDTO);
	 /**
     * 连锁商户直营网点  根据其 商户号和启用标识 查询其对应的折扣
     * @param merCode
     * @return
     */
	 public DodopalResponse<List<MerDiscountReferBean>> findMerDiscountRefer(MerDiscountQueryDTO merDiscountQueryDTO);

	 
	 public DodopalResponse<List<MerDiscountBean>> findMerDiscountByList(String mercode,String merType);

	 
	 /** 
	  * @author  Dingkuiyuan@dodopal.com 
	  * @date 创建时间：2016年4月16日 下午4:04:27 
	  * @version 1.0 
	  * @parameter  
	  * @描述 查找今天此时起作用的商户折扣
	  * @return  
	  */
	public List<MerDiscountBean> findMerDiscountToday(String mercode);
	
	/** 
	  * @author  Dingkuiyuan@dodopal.com 
	  * @date 创建时间：2016年4月16日 下午4:37:02 
	  * @version 1.0 
	  * @parameter  
	  * @描述 根据城市号商户号商户类型来获取折扣信息
	  * @return  
	  */
	public List<MerDiscountBean> findMerDiscountList(String mercode,String merType,String cityCode);

}

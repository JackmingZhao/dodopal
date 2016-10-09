package com.dodopal.transfer.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Tlcouponinfo;

/**
 * 优惠券使用信息
 * @author lenovo
 *
 */
public interface TlcouponinfoMapper {

	/**
	 * 查询优惠券使用信息
	 * @param userId
	 * @return
	 */
	public List<Tlcouponinfo> findTlcouponinfoById(@Param("userid")String userid);
	 
	 /**
		 * 查询优惠券使用信息统计
		 * @param userId
		 * @return
		 */
	 public  Tlcouponinfo findTlcouponinfoCountById(@Param("userid")String userid);
	 
}

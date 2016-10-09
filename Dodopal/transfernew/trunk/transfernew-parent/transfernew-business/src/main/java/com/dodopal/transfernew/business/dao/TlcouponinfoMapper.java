package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.transfer.Tlcouponinfo;


/**
 * 优惠券使用信息
 * 
 * @author lenovo
 * 
 */
public interface TlcouponinfoMapper {

	/**
	 * 查询优惠券使用信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Tlcouponinfo> findTlcouponinfoById(@Param("userid") String userid);

	/**
	 * 查询优惠券使用信息统计
	 * 
	 * @param userId
	 * @return
	 */
	public Tlcouponinfo findTlcouponinfoCountById(@Param("userid") String userid);

	/**
	 * 批量添加优惠券信息
	 * 
	 * @param list
	 * @return
	 */
	public int batchAddTlcouponinfo(List<Tlcouponinfo> list);
}

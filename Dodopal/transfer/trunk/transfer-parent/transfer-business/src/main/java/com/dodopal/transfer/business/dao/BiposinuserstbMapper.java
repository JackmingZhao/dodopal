package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Biposinuserstb;

public interface BiposinuserstbMapper {

	/**
	 * 查询pos用户关系
	 * @param userid
	 * @return
	 */
	public Biposinuserstb findBiposinuserstb(@Param("userid")String userid);
	/**
	 * 第二部查询posuser表里面的有多少用户
	 * @param posid
	 * @return
	 */
	public Biposinuserstb findBiposinuserstbByPosId(@Param("posid")String posid);
}

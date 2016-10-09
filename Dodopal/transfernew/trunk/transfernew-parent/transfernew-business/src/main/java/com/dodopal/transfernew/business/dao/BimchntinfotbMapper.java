package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Bimchntinfotb;

public interface BimchntinfotbMapper {
	/**
	 * 测试使用
	 * 
	 * @param mchnitid
	 * @return
	 */
	public Bimchntinfotb findBimchntinfotb(@Param("mchnitid") String mchnitid);

	/**
	 * 对指定商户进行迁移
	 * 
	 * @return
	 */
	public List<Bimchntinfotb> findAllBimchntinfotb();

	/**
	 * 根据userid查询出上级商户信息
	 * 
	 * @param userid
	 * @return
	 */
	public Bimchntinfotb findBimchntinfotbChanJoin(@Param("mchnitid") String mchnitid);

	// 更新老系统迁移标志
	public int updateTransFlag(String mchnitid);

	/**
	 * 迁移成功的商户，向老系统商户扩展表增加一条记录
	 * 
	 * @param mchnitid
	 * @param trans_flag
	 * @return
	 */
	public int addBimchntinfotbExtend(@Param("mchnitid") String mchnitid, @Param("transFlag") String transFlag);

	// ----------------------------------------根据省份和城市迁移数据----------------------------------------------//

	/***
	 * 根据城市查出需要迁移的商户信息
	 * 
	 * @param parentcode
	 * @param citycode
	 * @return
	 */
	public List<Bimchntinfotb> findBimchntinfotbs(@Param("citycode") String citycode);
}

package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Tlpospoint;
import com.dodopal.transfer.business.model.target.Tlpospoints;

/**
 * pos积分表信息
 * @author lenovo
 *
 */
public interface TlposPointsMapper {

	/**
	 * 查询pos积分表信息(老)
	 * @param posId
	 * @return
	 */
	public  Tlpospoint findTlpospointById(@Param("posId")long posId);
	
	/**
	 * 增加pos积分表信息(新)
	 * @param tlpospoints
	 */
	public void addTlpospoints(Tlpospoints tlpospoints);
	
}

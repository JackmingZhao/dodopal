package com.dodopal.transfer.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Tlpospointstnx;
import com.dodopal.transfer.business.model.target.TlpospointstnxTarget;

/**
 * 用户的积分流水
 * @author lenovo
 *
 */
public interface TlposPointsTnxMapper {

	/**
	 * 查询用户的积分流水(老)
	 * @param posId
	 * @return
	 */
	public List<Tlpospointstnx> findTlpospointstnxById(@Param("posId")long posId);
	
	/**
	 * 增加用户的积分流水
	 * @param tlpospointstnxTarget
	 */
	public void addTlpospointstnxTarget(TlpospointstnxTarget tlpospointstnxTarget);
}

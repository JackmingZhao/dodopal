package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.PosLog;
import com.dodopal.users.business.model.PosLogQuery;

public interface PosLogMapper {
	
	
	/**
	 * 插入日志
	 * @param posLog 
	 */
	public void insertPosLog(PosLog posLog);
	
	/**
	 * 查询Pos操作日志
	 * @param posLog 
	 */
	public List<PosLog> findPosLogListByPage(PosLogQuery posLog);
	
	/**
	 * 查询Pos操作日志   导出
	 * @param posLog
	 * @return
	 */
    public List<PosLog> findPosLogByList(PosLogQuery posLog);

}

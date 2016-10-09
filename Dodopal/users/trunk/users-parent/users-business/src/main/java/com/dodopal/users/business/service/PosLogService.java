package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.PosLog;
import com.dodopal.users.business.model.PosLogQuery;

public interface PosLogService {
	
	/**
	 * Pos操作日志查询
	 * @param findBean 查询条件
	 * @return
	 */
	List<PosLog> findPosLogList(PosLogQuery findBean);
	
	   /**
     * Pos操作日志  导出
     * @param findBean 查询条件
     * @return
     */
    List<PosLog> findPosLogByList(PosLogQuery findBean);

}

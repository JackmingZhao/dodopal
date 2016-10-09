package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.DdicResMsg;
import com.dodopal.oss.business.model.dto.DdicResMsgQuery;

public interface DdicResMsgService {

	/**
	 * 查找按页
	 * @param ddicResMsgQuery
	 * @return
	 */
	public DodopalDataPage<DdicResMsg> findDdicResMsgsByPage(DdicResMsgQuery ddicResMsgQuery);
	
    /**
     * 保存或修改数据字典信息
     * @param ddicResMsg
     * @return
     */
	String saveOrUpdateDdicResMsg(DdicResMsg ddicResMsg);
    
    /**
     * 查看数据字典
     * @param id
     * @return
     */
    public DdicResMsg findDdicResMsgById(String id);

    /**
     * 批量删除数据字典(逻辑删除)
     * @param updateUser
     * @param ids
     */
    public void batchDelDdicResMsg(List<String> ids);
    
    /**
     * 查询
     * @param ddicResMsgQuery
     * @return
     */
    public List<DdicResMsg> findDdicResMsgs(DdicResMsgQuery ddicResMsgQuery);

}

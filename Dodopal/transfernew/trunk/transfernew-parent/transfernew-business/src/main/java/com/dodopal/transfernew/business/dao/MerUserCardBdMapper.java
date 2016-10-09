package com.dodopal.transfernew.business.dao;

import java.util.List;

import com.dodopal.transfernew.business.model.transfer.MerUserCardBd;

/**
 * 用户绑定卡片信息
 * 
 * @author lenovo
 * 
 */
public interface MerUserCardBdMapper {

	/**
	 * 增加用户绑定卡片信息
	 * 
	 * @param merUserCardBd
	 */
	public void addMerUserCardBd(MerUserCardBd merUserCardBd);

	/**
	 * 批量添加用户绑定卡片信息
	 * 
	 * @param list
	 */
	public int batchAddMerUserCardBd(List<MerUserCardBd> list);
}

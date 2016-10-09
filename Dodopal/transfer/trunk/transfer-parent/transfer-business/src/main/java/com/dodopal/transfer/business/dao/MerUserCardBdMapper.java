package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.MerUserCardBd;

/**
 * 用户绑定卡片信息
 * @author lenovo
 *
 */
public interface MerUserCardBdMapper {

	/**
	 * 增加用户绑定卡片信息
	 * @param merUserCardBd
	 */
	public void addMerUserCardBd(MerUserCardBd merUserCardBd);
}

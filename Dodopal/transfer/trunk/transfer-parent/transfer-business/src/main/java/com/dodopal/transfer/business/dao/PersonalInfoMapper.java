package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.old.Sysuserstb;


public interface PersonalInfoMapper {

	public void insertSysUserstb(Sysuserstb sysuserstb);
	
	/**
	 * 查询用户的基础信息
	 * @param userType
	 * @param cityId
	 * @param YktCityId
	 * @return
	 */
	public Sysuserstb findSysUserstb(String userType,String cityId,String YktCityId);
}

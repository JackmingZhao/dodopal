package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Sysuserstb;

public interface SysuserstbMapper {
    //Test  单条连锁加盟网店数据
    public Sysuserstb findSysuserstb(@Param("userid")String userid);
    //连锁加盟商户网点
    public List<Sysuserstb> findAllSysuserstb(@Param("mchnitid")String mchnitid);
    
    public void insertSysUserstb(Sysuserstb sysuserstb);
   /**
    * 连锁商户根据商户id查询对应的管理员信息
    * @param mchnitid
    * @return
    */
    public Sysuserstb findSysUserstbByMchnitid(@Param("mchnitid")String mchnitid);
	
	/**
	 * 
	 * 第四布查询用户信息
	 * 查询用户的基础信息
	 * @param userType
	 * @param cityId
	 * @param YktCityId
	 * @return
	 */
	public Sysuserstb findSysUserstb(@Param("userid")String userid);
	
	/**
	 * 测试查询全部然后迁移
	 * @return
	 */
	//public List<Sysuserstb> findSysUserstbAllByPage(QueryBase queryBase);
	
	/**
	 * 迁移完毕后更新数据迁移标志位
	 * @param userid
	 * @return
	 */
	 public int updateUserTransFlag(@Param("userid")String userid);
	 
	
}

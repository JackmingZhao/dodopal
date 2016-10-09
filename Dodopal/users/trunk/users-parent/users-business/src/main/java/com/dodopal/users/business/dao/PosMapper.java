package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.Pos;
import com.dodopal.users.business.model.PosMerTb;
import com.dodopal.users.business.model.PosQuery;

public interface PosMapper {
	
	
//	public int updateActivateByMerRoleCode(@Param("merRoleCode") String merRoleCode, @Param("activate") String activate, @Param("list") List<MerRoleFun> list);
	/**
	 * POS 绑定
	 * @param posCode 
	 */
	public void updatePosBundling(@Param("pos")Pos pos,@Param("list")List<String> posList);
	
	/**
	 * POS 解绑
	 * @param posCode 
	 */
	public void updatePosUnBundling(@Param("updateUserId") String updateUserId,@Param("list")List<Pos> posList);
	
    /**
     * 检查当前POS是否存在
     * @param code
     * @return
     */
    int getPosCount(@Param("code")String code);
	
    /**
     * 检查已与商户绑定的POS是否存在
     * @param posCode
     * @return
     */
    int getPosBindedCountByCodes(@Param("array")String[] posCode);
    
    int getPosBindedCountByCodesAndMerCode(@Param("array")String[] posCode,@Param("merCode")String merCode);
	/**
	 * POS 查询
	 * @param posCode 
	 */
	public List<Pos> findPosList(@Param("bind")String bind,@Param("array")String[] posCode);
	
	/**
	 * 门户POS查询
	 * @param findBean
	 * @return
	 */
	public List<Pos> findPosListPage(PosQuery findBean);
	
	/**
	 * POS 禁用
	 * @param posCode 
	 */
	public void updatePosDisable(@Param("updateUserId") String updateUserId,@Param("array")String[] posCode);
	
	/**
	 * POS 启用
	 * @param posCode 
	 */
	public void updatePosEnable(@Param("updateUserId") String updateUserId,@Param("array")String[] posCode);

	/**
	 * POS绑定中间表插入
	 * @param posMerTb 
	 */
	public void insertPosMerTb(PosMerTb posMerTb);
	
	/**
	 * POS绑定中间表解绑删除
	 * @param posMerTb 
	 */
	public void deletePosMerTb(PosMerTb posMerTb);
    /**
     * POS信息删除
     * @param posMerTb 
     */
    public void deletePos(String[] posId);
	
	/** 
	 * @author dingkuiyuan@dodopal.com
	 * @Title: findChildrenMerPosListPage 
	 * @Description: 子商户pos查询
	 * @param findBean
	 * @return    设定文件 
	 * List<Pos>    返回类型 
	 * @throws 
	 */
	public List<Pos> findChildrenMerPosListPage(PosQuery findBean);

	/**
	 * 根据POS编码查询POS信息
	 * @param code
	 * @return
	 */
	public Pos findPosByCode(String code);
	
	/**
     * 根据POS编码查询商户编号
     * @param code
     * @return
     */
    public Pos findMerchantCodeByPosCode(String posCode);
    
	/**
	 * 供应商 查询 商户 城市的pos信息
	 * @param findBean 
	 * @return
	 */
    public List<Pos> findPosinfoByPage(PosQuery findBean);
    
    /**
     * 查询商户pos数
     * @param merCode
     * @return
     */
    public int posCount(@Param("merCode")String merCode);
    
    /**
     * 查询POS信息
     * @param posCode
     * @param merCode
     * @return
     */
    public Pos findPosInfoByCode(@Param("posCode")String posCode, @Param("merCode")String merCode);
    
    /**
     * 更新POS的COMMENTS的备注信息
     * @param sql
     */
    public void updatePosInfo(@Param("sql")String sql);
}

package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerAutoAmt;

/**
 * 连锁商户 直营网点 自动转账额度及阀值
 * 
 * @author xiongzhijing@dodopal.com
 * 
 */
public interface MerAutoAmtMapper {

	/**
	 * 增加直营网点自动分配额度信息
	 * 
	 * @param merAutoAmt
	 * @return
	 */
	public int addMerAutoAmt(MerAutoAmt merAutoAmt);

	/**
	 * 修改直营网点自动分配额度信息
	 * 
	 * @param merAutoAmt
	 * @return
	 */
	public int updateMerAutoAmt(MerAutoAmt merAutoAmt);

	/**
	 * 查询 连锁直营网点自动分配额度
	 * 
	 * @param merCode
	 * @return
	 */
	public MerAutoAmt findMerAutoAmtByMerCode(@Param("merCode") String merCode);

	/**
	 * 删除直营网点自动分配额度信息
	 * 
	 * @param merAutoAmt
	 * @return
	 */
	public int deleteMerAutoAmtByMerCode(@Param("merCode") String merCode);

	/**
	 * 批量删除
	 * 
	 * @param merCodes
	 * @return
	 */
	public int batchDelMerAutoAmtByMerCodes(@Param("merCodes") List<String> merCodes);
}

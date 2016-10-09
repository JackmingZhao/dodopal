package com.dodopal.transfernew.business.dao;

import java.util.List;

import com.dodopal.transfernew.business.model.transfer.Pos;

public interface PosMapper {

	/**
	 * 增加pos信息表记录
	 * 
	 * @param pos
	 */
	public void addPos(Pos pos);

	/**
	 * 批量添加Pos信息
	 * 
	 * @param posList
	 * @return
	 */
	public int batchAddPos(List<Pos> list);
}

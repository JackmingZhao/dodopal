package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.ScoreuserCard;

/**
 * 用户绑定卡片信息
 * @author lenovo
 *
 */
public interface ScoreuserCardMapper {

	public ScoreuserCard findScoreuserCardById(@Param("userId")String userId);
}

package com.dodopal.oss.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.DdicResMsg;
import com.dodopal.oss.business.model.dto.DdicResMsgQuery;

public interface DdicResMsgMapper {

	public List<DdicResMsg> findDdicResMsgsByPage(DdicResMsgQuery ddicResMsgQuery);
	
    public void insertDdicResMsg(DdicResMsg ddicResMsg);

    public void updateDdicResMsg(DdicResMsg ddicResMsg);
    
    public DdicResMsg findDdicResMsgById(String id);
    
    public int batchDelDdicResMsg(@Param("ids") List<String> ids);
    
}

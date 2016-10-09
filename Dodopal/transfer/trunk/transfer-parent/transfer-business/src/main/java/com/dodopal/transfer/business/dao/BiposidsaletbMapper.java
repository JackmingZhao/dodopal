package com.dodopal.transfer.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Biposidsaletb;

public interface BiposidsaletbMapper {
public List<Biposidsaletb> findByPosId(@Param("posid") String posid);
}

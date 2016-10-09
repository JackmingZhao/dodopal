package com.dodopal.transfer.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Biposinfotb;

public interface BiposinfotbMapper {
    
    Biposinfotb findBiposinfotbByPosId(@Param("posid")String posid);
    //1.1根据连锁商户查询多少对应的pos
    List<Biposinfotb> findBiposinfotbAll(@Param("mchnitid")String mchnitid);
}

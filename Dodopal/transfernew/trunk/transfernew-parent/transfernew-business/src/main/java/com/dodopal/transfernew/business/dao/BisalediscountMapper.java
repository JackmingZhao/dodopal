package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Bisalediscount;

public interface BisalediscountMapper {
    List<Bisalediscount> findBisalediscountById(@Param("mchnitid")String mchnitid);
    Bisalediscount findBisalediscountBySaleId(@Param("saleid")String saleid);
}

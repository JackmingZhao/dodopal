package com.dodopal.transfernew.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Mchntposinfo;

public interface MchntposinfoMapper {
    Mchntposinfo findMchntposinfoByMchId(@Param("userid")String userid);
    Mchntposinfo findMchntposinfoByPosId(@Param("posid")String posid);
}

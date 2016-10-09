package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Mchntposinfo;

public interface MchntposinfoMapper {
    Mchntposinfo findMchntposinfoByMchId(@Param("userid")String userid);
    Mchntposinfo findMchntposinfoByPosId(@Param("posid")String posid);
}

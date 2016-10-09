package com.dodopal.transfernew.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Mchnitlimitinfotb;

public interface MchnitlimitinfotbMapper {
    //根据老商户号查询资金账户表记录
    Mchnitlimitinfotb findMchlimitByMchId(@Param("mchnitid")String mchnitid);
}

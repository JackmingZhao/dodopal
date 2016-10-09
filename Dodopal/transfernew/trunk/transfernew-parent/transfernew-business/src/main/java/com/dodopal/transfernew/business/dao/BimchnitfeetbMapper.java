package com.dodopal.transfernew.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Bimchnitfeetb;

public interface BimchnitfeetbMapper {
  //根据老商户号查询资金账户表记录
    Bimchnitfeetb findBimchnitfeetbByMchId(@Param("mchnitid")String mchnitid);
}

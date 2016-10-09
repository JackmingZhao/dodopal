package com.dodopal.transfernew.business.service;

import org.apache.ibatis.annotations.Param;

import com.dodopal.common.model.DodopalResponse;

public interface BimchntinfotbService {
    public DodopalResponse<String> findBimchntinfotbs(@Param("procode")String procode,@Param("citycode")String citycode);
}

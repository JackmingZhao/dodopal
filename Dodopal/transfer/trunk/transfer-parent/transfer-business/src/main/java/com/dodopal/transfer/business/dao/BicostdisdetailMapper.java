package com.dodopal.transfer.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Bicostdisdetail;

public interface BicostdisdetailMapper {
public List<Bicostdisdetail> findBicostdisdetailByPosId(@Param("posid")String posid) ;
}

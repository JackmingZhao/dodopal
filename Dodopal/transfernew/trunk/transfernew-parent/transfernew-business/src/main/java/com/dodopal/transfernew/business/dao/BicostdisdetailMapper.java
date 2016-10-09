package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Bicostdisdetail;

public interface BicostdisdetailMapper {
public List<Bicostdisdetail> findBicostdisdetailByPosId(@Param("posid")String posid) ;
}

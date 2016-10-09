package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Groupinproxytb;

public interface GroupinproxytbMapper {

   /**
    * 根据集团查询其下的网点信息
    * @param string
    * @return
    */
   public  List<Groupinproxytb> findGroupinproxytb(@Param("groupid") String groupid);

}

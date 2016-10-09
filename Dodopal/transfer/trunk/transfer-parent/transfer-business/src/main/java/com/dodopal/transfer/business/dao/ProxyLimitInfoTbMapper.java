package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Proxylimitinfotb;

/**
 * 网点充值额度信息表
 * @author lenovo
 *
 */
public interface ProxyLimitInfoTbMapper {
    
    Proxylimitinfotb findProxyLimitInfoTbById(@Param("proxyid")String proxyid);
    
   
}

package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Proxyuserinfotb;

/**
 * 网点用户信息表
 * @author lenovo
 *
 */
public interface ProxyuserinfotbMapper {
    
    
    Proxyuserinfotb  findProxyuserinfotbByID(@Param("proxyid") String proxyid);
}

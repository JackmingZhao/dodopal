package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Proxyuserinfotb;

/**
 * 网点用户信息表
 * @author lenovo
 *
 */
public interface ProxyuserinfotbMapper {
    
    
    Proxyuserinfotb  findProxyuserinfotbByID(@Param("proxyid") String proxyid);
    
    List<Proxyuserinfotb>  findProxyuserinfotbByProxyid(@Param("proxyid") String proxyid);
}

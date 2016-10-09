package com.dodopal.transfernew.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Proxyautoaddeduoperinfotb;

public interface ProxyautoaddeduoperinfotbMapper {

    /**
     * 
     * @param proxyid
     * @return
     */
    Proxyautoaddeduoperinfotb findProxyautoaddeduoperinfotb(@Param("proxyid")String proxyid);
}

package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Proxyautoaddeduoperinfotb;

public interface ProxyautoaddeduoperinfotbMapper {

    /**
     * 
     * @param proxyid
     * @return
     */
    Proxyautoaddeduoperinfotb findProxyautoaddeduoperinfotb(@Param("proxyid")String proxyid);
}

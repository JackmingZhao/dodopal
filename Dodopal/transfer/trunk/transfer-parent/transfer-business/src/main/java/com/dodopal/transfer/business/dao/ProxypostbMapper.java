package com.dodopal.transfer.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Proxypostb;

/**
 * 代理网点绑定pos信息表
 * @author lenovo
 *
 */
public interface ProxypostbMapper {
    
    Proxypostb findProxypostbById(@Param("proxyid") String proxyid);
    
    /**
     * @author Mikaelyan
     * @param proxyid
     * @return
     */
    List<Proxypostb> findProxypostbListById(@Param("proxyid") String proxyid);

}

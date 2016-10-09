package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Proxypostb;

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

    /**
     * 根据posid 查询pos信息
     * @param posid
     * @return
     */
    Proxypostb findProxypostbByPosid(@Param("posid")String posid);

}

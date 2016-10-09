package com.dodopal.transfer.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Proxyinfotb;
/**
 * 网点信息表
 * @author lenovo
 *
 */
public interface ProxyinfotbMapper {
    
    /**
     * 查询网点信息
     * @param proxyid
     * @return
     */
    public  Proxyinfotb findProxyinfotbById(@Param("proxyid") String proxyid);

    /**
     * @author Mikaelyan
     * @param cityno
     * @param proxytypeid
     * @return
     */
    public List<Proxyinfotb> findProxyInfoTbByCitynoAndProxytypeid(@Param("cityno")String cityno, @Param("proxytypeid")int proxytypeid);

    /**
     * 更新老系统迁移标识
     * @param mchnitid
     * @return
     */
    public int updateTransFlag(String proxyid);
}

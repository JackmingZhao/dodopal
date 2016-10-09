package com.dodopal.transfer.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.old.Bimchntinfotb;

public interface BimchntinfotbMapper {
    /**
     * 测试使用
     * @param mchnitid
     * @return
     */
    public Bimchntinfotb findBimchntinfotb(@Param("mchnitid") String mchnitid);
    //1.读取老系统连锁商户Bimchntinfotb表中的商户
    public List<Bimchntinfotb> findAllBimchntinfotb();
    /**
     * 根据userid查询出上级商户信息
     * @param userid
     * @return
     */
    public Bimchntinfotb findBimchntinfotbChanJoin(@Param("mchnitid") String mchnitid);

    // 更新老系统迁移标志
    public int updateTransFlag(String mchnitid);

    /**
     * 迁移成功的商户，向老系统商户扩展表增加一条记录
     * @param mchnitid
     * @param trans_flag
     * @return
     */
    public int addBimchntinfotbExtend(@Param("mchnitid")String mchnitid, @Param("transFlag")String transFlag);
}

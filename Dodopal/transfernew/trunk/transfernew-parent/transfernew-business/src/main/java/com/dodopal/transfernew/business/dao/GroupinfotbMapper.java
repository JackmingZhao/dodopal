package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.old.Groupinfotb;
import com.dodopal.transfernew.business.model.old.Groupposinfotb;
import com.dodopal.transfernew.business.model.old.Groupuserinfotb;

public interface GroupinfotbMapper {

    /**
     * 查询城市为北京 的所有集团(未迁移)
     * @return
     */
    public List<Groupinfotb> findGroupinfotb(@Param("cityno")String cityno);

    /**
     * 查询城市下的所有集团
     * @return
     */
    public List<Groupinfotb> findGroupinfotbALL(@Param("cityno")String cityno);

    /**
     * 查询集团基本信息
     * @param cityno
     * @return
     */
    public Groupinfotb findGroupinfoByCityNO(@Param("groupid") String groupid);

    /**
     * 查询迁移集团的用户信息数据
     * @param groupid
     * @return
     */
    public Groupuserinfotb findGroupuserinfoByGroupId(@Param("groupid") String groupid);

    /**
     * 查询迁移集团的用户信息数据
     * @param groupid
     * @return
     */
    public List<Groupuserinfotb> findGroupuserinfoByList(@Param("groupid") String groupid);
    //查询出其集团的关联网点信息
    //public List<Groupinproxytb> findGroupinproxyByGroupId(@Param("groupid") String groupid);

    //查询迁移集团绑定的pos信息
    public List<Groupposinfotb> findGroupposinfoByGroupId(@Param("groupid") String groupid);

    // 更新老系统迁移标志
    public int updateTransFlag(String groupid);
}

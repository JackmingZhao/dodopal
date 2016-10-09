package com.dodopal.product.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.product.business.model.YktPsam;
import com.dodopal.product.business.model.query.YktPsamQuery;

public interface YktPsamMapper {
    
    /**
     * 查询PSAM卡信息
     * @param yktPsamQuery
     * @return
     */
    public List<YktPsam> findYktPsamByPage(YktPsamQuery yktPsamQuery);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int batchDeleteYktPsamByIds(@Param("ids")List<String> ids);

    /**
     * 批量启用商户
     * @param ids
     * @return
     */
    public int batchActivateMerByIds(@Param("ids")List<String> ids);

    /**
     * 批量修改重新下载参数
     * @param ids
     * @return
     */
    public int batchNeedDownLoadPsamByIds(@Param("ids")List<String> ids);
    
    //新增
    public void addYktPsam(YktPsam yktPsam);
    //编辑
    public void updateYktPsam(YktPsam yktPsam);

    //查询psam卡信息
    public YktPsam findPsamById(@Param("id")String id);

    //增加签到控制表记录
    public void addSamSigninOfftb(YktPsam yktPsam);

    //修改签到控制表
    public void updateSamSigninOfftb(YktPsam yktPsam);
    
    //新增pos
    public void addPos(YktPsam yktPsam);
    
    //新增pos商户绑定信息
    public void addPosMertb(YktPsam yktPsam);

    //修改pos
    public void updatePos(YktPsam yktPsam);

    //修改pos商户中间表
    public void updatePosMertb(YktPsam yktPsam);
    
    
    //删除pos
    public void deletePos(YktPsam yktPsam);
    
    
    //删除pos商户绑定记录
    public void deletePosMerTb(YktPsam yktPsam);
    

    //删除签到控制表记录
    public void deleteSamSigninOfftb(YktPsam yktPsam);

    //根据pos编号更新pos记录
    public void updatePosByCode(YktPsam yktPsam);
    
    //根据id更新pos记录
    public void updatePosByid(YktPsam yktPsam);

    //根据psam卡号查询psam卡信息
    public YktPsam findYktPsamByPsam(@Param("samNo")String samNo);
    

}

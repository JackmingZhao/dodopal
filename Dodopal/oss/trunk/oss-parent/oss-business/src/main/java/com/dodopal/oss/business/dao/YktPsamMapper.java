package com.dodopal.oss.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.MerChant;
import com.dodopal.oss.business.model.Samsigninoff;
import com.dodopal.oss.business.model.YktPsam;


public interface YktPsamMapper {
    
    // 查询商户信息
    public MerChant getMerchantByMerCode(String merCode);
    
    // 查询POS与商户绑定关系
    public boolean checkPosMerBind(@Param("merCode")String merCode, @Param("posCode")String posCode);
    
    //查询psam卡信息
    public YktPsam getPsamBySamNo(@Param("samNo")String samNo);
    
    //批量新增
    public void insertPsamBatch(List<YktPsam> list);

    //批量增加签到控制表记录
    public void insertSamSigninOffBatch(List<Samsigninoff> list);

}

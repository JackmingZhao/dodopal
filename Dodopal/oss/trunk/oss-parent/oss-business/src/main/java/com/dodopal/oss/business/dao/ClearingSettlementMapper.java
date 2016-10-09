package com.dodopal.oss.business.dao;

import com.dodopal.oss.business.bean.ProfitCollectBean;
import com.dodopal.oss.business.model.ProfitCollect;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface ClearingSettlementMapper {
    /**
     * 获取服务器系统时间
     * @return
     */
    public String getSysdate();

    /**
     * 插入支付网关分类清算表
     */
    public void insertBank(String date);

    /**
     * 插入支付网关-业务类型分类清算表
     */
    public void insertBankTxn(String date);

    /**
     * 清算支付网关
     */
    public void updateBank(String date);

    /**
     * 通卡公司分类清算表
     */
    public void insertYkt(String date);

    /**
     * 通卡公司-城市分类清算表
     */
    public void insertYktCity(String date);

    /**
     * 清算通卡公司
     */
    public void updateYkt(String date);

    /**
     * 商户分类清算表
     */
    public void insertMer(String date);

    /**
     * 商户-业务类型分类清算表
     */
    public void insertMerTxn(String date);

    /**
     * 清算商户
     */
    public void updateMer(String date);

    /**
     * 查询出分润汇总的数据信息
     */
    public List<ProfitCollect> queryProfitCollect();

    /**
     * @description 将信息插入分润汇总表中
     * @param profitCollect
     */
    public void addProfitCollect(ProfitCollect profitCollect) throws SQLException;

    /**
     * @description 根据客户号查询客户类型
     * @param merCode
     * @return
     */
    public String  queryMerchantName(@Param("merCode")String merCode);

    /**
     * @description 删除当天生成的分润汇总数据
     * @param dateStr
     */
    public void deleteProfitCollect(@Param("dateStr")String dateStr);

}

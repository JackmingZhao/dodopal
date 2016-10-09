/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.running.business.dao;

import com.dodopal.running.business.model.*;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lenovo on 2015/10/15.
 */
public interface ClearingTaskMapper {
    /**
     * @description 获取数据库的前一天 格式为(2015-10-21)
     * @return
     */
    public String getYestoday();
    /**
     * @description 一卡通充值查询需要清分的数据
     * @return
     */
    public List<ProductOrder> queryYKTRchargeClearingData();

    /**
     * @description 账户充值查询需要清分的数据
     * @return
     */
    public List<PayTransaction> queryAccountRechargeClearingData();
    /**
     * @description 一卡通消费需要清分的数据
     * @return
     */
    public List<Purchase> queryYKTPurchaseClearingData();
    /**
     * @description 清分数据显增到清分基础表
     * @return
     */
    public int addClearingData(ClearingDataModel clearingDataModel) throws SQLException;

    /**
     * @description 清分过后的数据  订单表清分状态回填
     * @param orderNo
     * @param clearingFlag
     * @return
     * @throws SQLException
     */
    public int updateProductOrderClearingFlag(@Param("orderNo") String orderNo, @Param("clearingFlag") String clearingFlag) throws SQLException;
    /**
     * @description 清分过后的数据  交易流水表清分状态回填
     * @param orderNo
     * @param clearingFlag
     * @return
     * @throws SQLException
     */
    public int updateTransactionClearingFlag(@Param("orderNo") String orderNo, @Param("clearingFlag") String clearingFlag) throws SQLException;
    /**
     * @description 清分过后的数据  一卡通消费流水表清分状态回填
     * @param orderNo
     * @param clearingFlag
     * @return
     * @throws SQLException
     */
    public int updatePurchaseOrderClearingFlag(@Param("orderNo") String orderNo, @Param("clearingFlag") String clearingFlag) throws SQLException;
    /**
     * @description 根据paywayid查询支付网关(关联通用表)
     * @param payWayId
     * @return
     */
    public PayWay getGateWayByGeneral(@Param("payWayId") String payWayId) ;

    /**
     * @description 根据paywayid查询支付网关(关联外接表)
     * @param payWayId
     * @return
     */
    public PayWay getGateWayByExternal(@Param("payWayId") String payWayId) ;

    /**
     * @description 根据一卡通code查询一卡通名称
     * @param supplierCode
     * @return
     */
    public String getSupplierName(@Param("supplierCode") String supplierCode) ;

    /**
     * @description 根据客户号(如果是商户，传商户号，如果是个人，传入个人用户号) 订单号和交易类型查询费率
     * @param userCode
     * @param orderNo
     * @param tranType
     * @return
     */
    public String getPayRate(@Param("userCode") String userCode, @Param("orderNo") String orderNo, @Param("tranType") String tranType);

    /**
     * @description 查询需要进行分润的数据
     * @return
     */
    public List<ClearingDataModel> queryProfitData();

    /**
     * @description 根据用户号查询所有的上级商户
     * @return
     */
    public List<MerChant> queryMerchant(@Param("merCode") String merCode, @Param("bussinessType") String bussinessType, @Param("YKTCode") String YKTCode);

    /**
     * @description 根据用户号查询用户信息
     * @param merCode
     * @return
     */
    public MerchantUserInfo queryMerchantUserInfo(@Param("merCode") String merCode, @Param("bussinessType") String bussinessType, @Param("YKTCode") String YKTCode);

    /**
     * @description 根据用户号查询下级商户
     * @param merCode
     * @return
     */
    public List<MerchantUserInfo> querySubMerchantUserInfo(@Param("merCode") String merCode);

    /**
     * @descripton 插入数据到分润明细表
     * @param pm
     * @return
     */
    public int addProfitData(ProfitModel pm);

    /**
     * @description 上级商户分润计算状态更新为0
     * @param orderNo
     * @param clearingFlag
     * @return
     */
    public int updateClearingFlage(@Param("orderNo") String orderNo, @Param("clearingFlag") String clearingFlag);

    /**
     * @description 如果是连锁直营商户，通过customerCode 查询parentCode
     * @param customerCode
     * @return
     */
    public String queryParentCode(@Param("customerCode") String customerCode);

    /**
     * @description 查询商户信息，连锁直营网点时处理使用
     * @param merCode
     * @return
     */
    /*public MerChant serarchMerchant(@Param("merCode") String merCode);*/
}
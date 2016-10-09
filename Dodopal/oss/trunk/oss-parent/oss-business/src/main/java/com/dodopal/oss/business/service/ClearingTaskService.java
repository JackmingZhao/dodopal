/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.business.service;

import com.dodopal.oss.business.model.PayTransaction;
import com.dodopal.oss.business.model.ProductOrder;
import com.dodopal.oss.business.model.Purchase;

import java.sql.SQLException;
import java.util.List;
/**
 * Created by lenovo on 2015/10/14.
 */
public interface ClearingTaskService {
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
     * @description 一卡通消费查询需要清分的数据
     * @return
     */
    public List<Purchase> queryYKTPurchaseClearingData();

    /**
     * @description 一卡通充值清分数据新增
     * @param productOrder
     * @return
     * @throws SQLException
     */
    public void addYKTRechargeClearingData(ProductOrder productOrder) throws SQLException;

    /**
     * @description 账户充值清分数据新增
     * @param payTransaction
     * @return
     * @throws SQLException
     */
    public void addAccountRechargeClearingData(PayTransaction payTransaction) throws SQLException;
    /**
     * @description 一卡通消费清分数据新增
     * @param purchase
     * @return
     * @throws SQLException
     */
    public void addYKTPurchaseClearingData(Purchase purchase) throws SQLException;
}
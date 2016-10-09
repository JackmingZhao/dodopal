package com.dodopal.product.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.api.product.dto.ProductOrderForExport;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.product.business.model.ProductConsumeOrder;
import com.dodopal.product.business.model.ProductConsumeOrderDetail;
import com.dodopal.product.business.model.ProductOrder;

public interface ProductOrderMapper {

    /**
     * 根据圈存订单号查询充值订单号add by shenXiang  2016-05-03
     * 
     * @param loadOrderNum
     * @return
     */
    List<ProductOrder> getOrderListByLoadOrderNum(@Param("loadOrderNum")String loadOrderNum);
    
    /**
     * OSS异常充值订单查询分页  add by shenXiang  2015-11-05
     * @param prdOrderQuery
     * @return
     */
    List<ProductOrder> findExceptionProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    
    /**
     * 第一次进入申请充值密钥环节将“重发标志位”：置为1（产品库接收到DLL请求时，以此判断是否为重发请求） add by shenXiang 2015-11-10
     * @param proOrderNum
     * @return
     */
    public int updateReSignByProOrderNum(String proOrderNum);
    
    /**
     * 产品库公交卡充值订单编号生成规则O + 20150428222211 +五位数据库cycle sequence（循环使用）
     * @return
     */
    public String getPrdOrderCodeSeq();

    /**
     * 创建公交卡充值产品库订单
     * @param order
     * @return
     */
    public int addProductOrder(ProductOrder order);

    /**
     * 通过订单号，验证查询产品库订单是否存在
     * @param proOrderNum
     * @return
     */
    public ProductOrder getProductOrderByProOrderNum(String proOrderNum);

    /**
     * 通过外部订单号、商户编号，查询产品库订单是否存在,验证外接商户是否重复下单
     * @param externalOrderNum
     * @param merchantNum
     * @return
     */
    public boolean checkProductOrderExistByExternalOrderNum(@Param("externalOrderNum") String externalOrderNum, @Param("merchantNum") String merchantNum);

    /**
     * 通过外部订单号、商户编号，查询产品库订单不存在时,更新订单中的外部订单号
     * @param externalOrderNum
     * @param icdcOrderNum
     * @return
     */
    public int updateProductOrderExternalOrderNum(@Param("externalOrderNum") String externalOrderNum, @Param("proOrderNum") String proOrderNum);

    /**
     * 更新产品库订单状态（主状态，内部状态，前内部状态）
     * @param order
     * @return
     */
    public int updateProductOrderStates(ProductOrder order);

    /**
     * 明确充值失败，更新产品库订单状态（主状态，内部状态，前内部状态，充值后金额==充值前金额）
     * @param order
     * @return
     */
    public int updateProductOrderFailedStates(ProductOrder order);
    
    /**
     * 充值结果上传， 更新产品库订单状态（主状态，内部状态，前内部状态）和充值后金额
     * @param order
     * @return
     */
    public int updateOrderStatusForResultUpload(ProductOrder order);

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.1 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    List<ProductOrder> findProductOrdersByPage(ProductOrderQueryDTO prdOrderQuery);
    //查询一卡通充值订单（管理员能够查看操作员）
    List<ProductOrder> findAdminProductOrdersByPage(ProductOrderQueryDTO prdOrderQuery);

    int getCountProductOrdersForExport(ProductOrderQueryDTO prdOrderQuery);
    
    List<ProductOrderForExport> getListProductOrdersForExport(ProductOrderQueryDTO prdOrderQuery);

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.2 订单查看 用户选择一条公交卡充值订单，点击“查看”按钮，页面向用户展示详情。
     * 订单编号、商户名称、产品编号
     * 、产品名称、充值金额、城市、实付金额、成本价（商户进货价）、订单时间、商户利润（这个字段对个人用户不要显示）、卡号、充值前金额
     * 、充值后金额、充值前账户可用余额、充值后账户可用余额、订单状态、外部订单号（仅限于外部商户）、操作员名称、POS编号、POS备注。
     */
    ProductOrder findProductOrderDetails(@Param("proOrderNum") String proOrderNum);

    /**
     * 5.4 OSS中公交卡充值订单 --5.4.1 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    //List<ProductOrder> findProductOrdersForOssByPage(ProductOrderOssQueryDTO prdOrderQuery);

    /**
     * 5.4 OSS中公交卡充值订单--5.4.2 订单查看 用户选择一条公交卡充值订单，点击“查看”按钮，页面向用户展示详情。
     */
    ProductOrder findProductOrderForOssDetails(@Param("proOrderNum") String proOrderNum);

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.1 订单查询,TODO 界面导出功能 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    List<ProductOrder> findProductOrdersExcel(ProductOrderQueryDTO prdOrderQuery);

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.1 订单查询 产品库提供标准的子商户订单查询页面给终端商户（各种网点）和个人。
     */
    List<ProductOrder> findChildMerProductOrdersByPage(ProductOrderQueryDTO prdOrderQuery);

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.1 订单查询,TODO 界面导出功能 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    List<ProductOrder> findChildProductOrdersExcel(ProductOrderQueryDTO prdOrderQuery);

    
    
    /**
     * 一卡通消费 收单查询   add by xiong
     * @param prdOrderQuery
     * @return
     */
    List<ProductConsumeOrder> findProductConsumeOrdersByPage(ProductConsumeOrderQueryDTO prdOrderQuery);
    /**
     * 一卡通消费 收单查询  (导出) add by xiong
     * @param prdOrderQuery
     * @return
     */
    List<ProductConsumeOrder> findProductConsumeOrdersByList(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * OSS一卡通消费 收单查询  (导出)
     * @param prdOrderQuery
     * @return
     */
    int getCountForConsumerOrderExportExcel(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * OSS一卡通消费 收单查询  (导出)
     * @param prdOrderQuery
     * @return
     */
    List<ProductConsumerOrderForExport> getListForConsumerOrderExportExcel(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 一卡通消费 订单详情
     * @param orderNum 订单号
     * @return
     */
    public  ProductConsumeOrderDetail findProductConsumeOrderDetails(String orderNum);
    
    
    /**
     * 一卡通消费 异常查询   add by liutoa
     * @param prdOrderQuery
     * @return
     */
    List<ProductConsumeOrder> findProductConsumeOrdersExptionByPage(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 导出异常消费记录
     * @param prdOrderQuery
     * @return
     */
    List<ProductConsumeOrder> excelExceptionProductOrderExport(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 充值异常订单导出
     * @param prdOrderQuery
     * @return
     */
    List<ProductOrder> findProductExceptionOrdersForExport(ProductOrderQueryDTO prdOrderQuery);
    
    /**
     * 根据商户和pos号查询一卡通充值记录
     * @param prdOrderQuery
     * @return
     */
    List<ProductOrder> findRechargeProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    
}

package com.dodopal.product.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.product.dto.ProductConsumeOrderDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDetailDTO;
import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.ProductOrderForExport;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.ProductOrder;

/**
 * 产品库公交卡充值订单Service
 * @author dodopal
 *
 */
public interface ProductOrderService {
    

    /**
     * OSS异常充值订单查询分页  add by shenXiang  2015-11-05
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findExceptionProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    
    /**
     * 根据订单号查询该订单
     * 
     * @param proOrderNum 产品库订单号
     * @return
     */
    public DodopalResponse<ProductOrder> getProductOrderByProOrderNum(String proOrderNum);
    
    /**
     * 第一次进入申请充值密钥环节将“重发标志位”：置为1（产品库接收到DLL请求时，以此判断是否为重发请求） add by shenXiang 2015-11-10
     * 
     * @param proOrderNum 产品库订单号
     * @return
     */
    public DodopalResponse<Integer> updateReSignByProOrderNum(String proOrderNum);
    
    /**
     * 生单接口调用方法：创建公交卡充值产品库订单
     * 
     * @param order 数据库表PRD_ORDER 对应实体对象
     * @return
     */
    public DodopalResponse<Integer> bookIcdcOrder(ProductOrder order);    
    
    /**
     * 获取圈存初始化指令接口调用方法：充值验卡环节中验证产品库订单
     * 
     * @param cardNum           卡号(必须)
     * @param productNum        一卡通充值产品的编号(必须)
     * @param externalOrderNum  外部订单号
     * @param merchantNum       外接商户号
     * @return  DodopalResponse<ProductOrder>    返回类型
     */
    public DodopalResponse<ProductOrder> validIcdcOrderWhenRetrievingApdu(String cardNum, String icdcOrderNum, String externalOrderNum, String merchantNum);
    
    /**
     * 产品库公交卡充值接口调用方法：更新公交卡充值订单充值密钥状态
     * 
     * @param icdcOrderNum  一卡通充值订单编号(必须)
     * @param getRechargeKeyResult  获取充值密钥("000000":成功;非"000000":失败)(必须)
     * @return  
     */
    public DodopalResponse<Integer> updateOrderStatusWhenRetrieveRechargeKey(String icdcOrderNum, String getRechargeKeyResult);
    
    /**
     * 产品库充值结果接口调用方法：更新公交卡充值订单结果状态
     * 
     * @param icdcOrderNum  一卡通充值订单编号
     * @param result        上传充值结果("0":充值成功；"1":充值失败；"2":充值可疑)
     * 
     * @return  
     */
    public DodopalResponse<Integer> updateOrderStatusForResultUpload(String icdcOrderNum, String result, String blackAmt);

    /**
     * 前端充值失败接口调用方法：更新公交卡充值订单前端判断失败结果状态
     * 
     * @param icdcOrderNum  一卡通充值订单编号(必须)
     * @return  
     */
    public DodopalResponse<Integer> frontFailOrderStatus(String icdcOrderNum);
    
    /**
     * 更新公交卡充值订单资金变动状态(资金冻结状态)
     * 
     * @param icdcOrderNum  一卡通充值订单编号（必须）
     * @param blockResult   资金冻结结果("000000":成功;非"000000":失败)(必须)
     * @return  
     */
    public DodopalResponse<Integer> updateOrderStatusWhenBlockFund(String icdcOrderNum, String blockResult);
    
    /**
     * 更新公交卡充值订单资金变动状态(资金冻解状态)
     * 
     * @param icdcOrderNum  一卡通充值订单编号(必须)
     * @param unblockResult 资金解冻结果("000000":成功;非"000000":失败)(必须)
     * @return  
     */
    public DodopalResponse<Integer> updateOrderStatusWhenUnbolckFund(String icdcOrderNum, String unblockResult);
    
    /**
     * 更新公交卡充值订单资金变动状态(资金扣款状态)
     * 
     * @param icdcOrderNum  一卡通充值订单编号(必须)
     * @param deductResult  资金扣款结果("000000":成功;非"000000":失败)(必须)
     * @return  
     */
    public DodopalResponse<Integer> updateOrderStatusWhendeduckFund(String icdcOrderNum, String deductResult);
    
    
    /**
     * 网银支付失败，更新公交卡充值订单资金变动状态(一卡通充值选择非账户支付方式网银支付失败后)
     * 
     * @param icdcOrderNum  一卡通充值订单编号(必须)
     * @return
     */
    public DodopalResponse<ProductOrder> updateOrderStatusWhenOnlinePayment(String icdcOrderNum);
    
    /**
     * 账户充值结果，更新公交卡充值订单资金变动状态(一卡通充值选择非账户支付方式网银支付成功后)
     * 
     * @param icdcOrderNum  一卡通充值订单编号(必须)
     * @param accountRechargeResult  账户充值结果返回值("000000":成功;非"000000":失败)(必须)
     * @return
     */
    public DodopalResponse<ProductOrder> updateOrderStatusWhenAccountRecharge(String icdcOrderNum, String accountRechargeResult);
    
    
    //******************************************************************************************************************************************//
    //*****************************************************     华丽分割线           *****************************************************************//
    //******************************************************************************************************************************************//
    
    
    /**
     * 5.2  产品库中公交卡充值订单 --5.2.1 订单查询
     * 
     * 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     * 
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);

    public DodopalResponse<List<ProductOrderForExport>> findProductOrdersForExport(ProductOrderQueryDTO prdOrderQuery);
    
    /**
     * 异常订单导出
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductOrderDTO>> findProductExceptionOrdersForExport(ProductOrderQueryDTO prdOrderQuery);

    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findAdminProductOrdersByPage(ProductOrderQueryDTO prdOrderQuery);

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.2 订单查看 用户选择一条公交卡充值订单，点击“查看”按钮，页面向用户展示详情。
     * 订单编号、商户名称、产品编号
     * 、产品名称、充值金额、城市、实付金额、成本价（商户进货价）、订单时间、商户利润（这个字段对个人用户不要显示）、卡号、充值前金额
     * 、充值后金额、充值前账户可用余额、充值后账户可用余额、订单状态、外部订单号（仅限于外部商户）、操作员名称、POS编号、POS备注。
     */
    public DodopalResponse<ProductOrderDetailDTO> findProductOrderDetails(String proOrderNum);
    
    /**
     * 一卡通公交卡充值订单导出按钮 处理
     * 
     * @param response HttpServletResponse
     * @return 导出结果
     */
    public DodopalResponse<String> excelProDuctExport(HttpServletResponse response,ProductOrderQueryDTO prdOrderQuery);
    /**
     * 根据商户号查询子商户的业务订单
     * @param prdOrderQuery
     * @param merCodes
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findChildMerProductOrdersByPage(ProductOrderQueryDTO prdOrderQuery);
    /**
     * 导出子商户业务订单
     * @param prdOrderQuery
     * @return
     */
    public List<ProductOrderDTO> findChildMerProductOrderExcel(ProductOrderQueryDTO prdOrderQuery);
    
    
    public DodopalResponse<String> findMerchantByMerCode(String merCode);
    
    
    /**
     * 查询 一卡通消费 收单记录(分页)
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderByPage(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 导出查询 一卡通消费 收单记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductConsumerOrderForExport>> getConsumerOrderListForExportExcel(ProductConsumeOrderQueryDTO prdOrderQuery);

    /**
     * 一卡通消费 收单记录（导出）
     * @param response HttpServletResponse
     * @return 导出结果
     */
    public DodopalResponse<String> excelConsumeOrderExport(HttpServletResponse response, ProductConsumeOrderQueryDTO prdConsumeOrderQuery);

    /**
     * 根据消费订单号 orderNum 查询一卡通消费订单详情
     * @param orderNum 订单号
     * @return
     */
    public DodopalResponse<ProductConsumeOrderDetailDTO> findProductConsumeOrderDetails(String orderNum);
    
    /**
     * 一卡通消费 异常查询   add by liutoa
     * @param prdOrderQuery
     * @return
     */
    DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrdersExptionByPage(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 导出消费异常记录
     * @param prdOrderQuery
     * @return
     */
    DodopalResponse<List<ProductConsumeOrderDTO>> excelExceptionProductOrderExport(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    
   /**
    * 根据商户和pos查询一卡通充值
    * @param prdOrderQuery
    * @return
    */
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findRechargeProductOrderByPage(ProductOrderQueryDTO prdOrderQuery);
    
    
}

package com.dodopal.product.business.service;

import java.util.Map;

import com.dodopal.api.product.dto.IcdcPurchaseDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.ConsumeOrder;
import com.dodopal.product.business.model.ProductYKT;

/**
 * 产品库公交卡消费订单Service
 * @author dodopal
 *
 */
public interface IcdcPurchaseOrderService {
    
    
    /**
     * 第一次进入申请扣款指令环节将“重发标志位”：置为1（产品库接收到DLL请求时，以此判断是否为重发请求） add by shenXiang 2015-11-10
     * 
     * @param purchaseOrderNum
     */
    public DodopalResponse<Integer> updateResendSignByOrderNum(String purchaseOrderNum);   
    
    /**
     * 6.3.1.2  创建产品库公交卡收单记录
     * 
     * @param order
     * @return 消费主订单编号
     */
    public DodopalResponse<String> bookIcdcPurchaseOrder(IcdcPurchaseDTO purchaseDto, Map<String, String> merInfoMap, ProductYKT productYKT);    
    
    /**
     *  北京v61创建产品库公交卡收单记录
     * 
     * @param order
     * @return 消费主订单编号
     */
    public DodopalResponse<String> bookBJPurchaseOrderForV61(IcdcPurchaseDTO purchaseDto, Map<String, String> merInfoMap, ProductYKT productYKT, String userdiscount, String settldiscount);
    
    /**
     *  北京v71创建产品库公交卡收单记录
     * 
     * @param order
     * @return 消费主订单编号
     */
    public DodopalResponse<String> bookBJPurchaseOrderForV71(IcdcPurchaseDTO purchaseDto, Map<String, String> merInfoMap, ProductYKT productYKT);  
    
    /**
     * 6.3.2.1  申请扣款初始化指令过程中验证收单记录
     * 
     * @param 产品库收单记录的主订单号
     * @return 
     */
    public DodopalResponse<ConsumeOrder> validateIcdcPurchaseOrderWhenCheckCard(String purchaseOrderNum);    
    
    /**
     * 6.3.2.2  申请扣款初始化指令过程更新产品库订单数据
     * @param purchaseOrderNum
     * @param cardNum
     * @param cardFaceno
     * @param posId
     * @param balanceBeforePurchase
     * @param cardCheckResult
     * @return
     */
    public DodopalResponse<Integer> updateIcdcPurchaseOrderWhenCheckCard(String purchaseOrderNum, String cardNum,String cardFaceno, String posId, int balanceBeforePurchase, String cardCheckResult );    
    
    
    /**
     * 6.3.3.1  申请扣款指令过程中验证收单记录
     * 
     * @param 产品库收单记录的主订单号
     * @return 
     */
    public DodopalResponse<ConsumeOrder> validateIcdcPurchaseOrderWhenDeductCard(String purchaseOrderNum);    
    

    /**
     * 6.3.3.2  申请扣款指令过程更新产品库订单数据
     * 
     * @param purchaseOrderNum
     * @param cardCheckResult (如果是000000，则将订单状态设置为：待支付—申请消费密钥成功；如果不是000000，则将订单状态设置为：支付失败—申请消费密钥失败。)
     * @return
     */
    public DodopalResponse<Integer> updateIcdcPurchaseOrderWhenDeductCard(String purchaseOrderNum, String cardCheckResult );  
    
    
    /**
     * 6.3.4.1  上传收单结果过程中验证收单记录并更新结果
     * 
     * @param purchaseOrderNum
     * @param purchaseResult (0:成功;1:失败2:未知)
     * @param blackAmt (收单后卡内金额)
     * @return
     */
    public DodopalResponse<ConsumeOrder> validateIcdcPurchaseOrderWhenUploadResult(String purchaseOrderNum, String purchaseResult, String blackAmt);
    
    /**
     * 北京V61脱机消费：上传收单结果过程中验证收单记录并更新结果
     * 
     * @param purchaseOrderNum
     * @param purchaseResult (0:成功;1:失败2:未知)
     * @param blackAmt (收单后卡内金额)
     * @return
     */
    public DodopalResponse<ConsumeOrder> validateBJOrderWhenUploadResultByV61(String purchaseOrderNum, String purchaseResult, String blackAmt); 
    
    
    /**
     * 北京V71消费：上传收单结果过程中验证收单记录并更新结果
     * 
     * @param purchaseOrderNum
     * @param purchaseResult (0:成功;1:失败2:未知)
     * @param blackAmt (收单后卡内金额)
     * @param amtreceivable 应收金额
     * @param userdiscount 用户折扣
     * @param settldiscount 结算金额
     * @return
     */
    public DodopalResponse<ConsumeOrder> validateBJOrderWhenUploadResultByV71(String purchaseOrderNum, String purchaseResult, String blackAmt, String amtreceivable, String userdiscount, String settldiscount);
    
    
}

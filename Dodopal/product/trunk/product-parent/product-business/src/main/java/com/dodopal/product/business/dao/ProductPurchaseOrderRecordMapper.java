package com.dodopal.product.business.dao;

import com.dodopal.product.business.model.ProductPurchaseOrderRecord;

public interface ProductPurchaseOrderRecordMapper {
    
    
    /**
     * 第一次进入申请扣款指令环节将“重发标志位”：置为1（产品库接收到DLL请求时，以此判断是否为重发请求） add by shenXiang 2015-11-10
     * @param purchaseOrderNum 主订单编号
     * @return
     */
    public int updateResendSignByOrderNum(String purchaseOrderNum);
    
    /**
     * 创建公交卡收单记录
     * @param orderRecord
     * @return
     */
    public int addProductPurchaseOrderRecord(ProductPurchaseOrderRecord orderRecord);
    
    /**
     * 根据主订单编号获取收单记录信息
     * @param purchaseOrderNum 主订单编号
     * @return
     */
    public ProductPurchaseOrderRecord getPurchaseOrderRecordByOrderNum(String purchaseOrderNum);
    
    /**
     * 申请扣款初始化指令过程更新产品库收单数据
     * @param orderRecord 收单记录信息
     * @return
     */
    public int updatePurchaseOrderRecordWhenCheckCard(ProductPurchaseOrderRecord orderRecord);
    
    /**
     * 申请扣款指令过程更新产品库收单数据
     * @param orderRecord 收单记录信息
     * @return
     */
    public int updatePurchaseOrderRecordWhenDeductCard(ProductPurchaseOrderRecord orderRecord);
    
    /**
     * 上传收单结果更新产品库收单数据
     * @param orderRecord 收单记录信息
     * @return
     */
    public int updatePurchaseOrderRecordWhenUploadResult(ProductPurchaseOrderRecord orderRecord);
}

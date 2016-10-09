package com.dodopal.transfer.business.service;


import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.model.old.Bimchntinfotb;
import com.dodopal.transfer.business.model.old.Biposinfotb;
import com.dodopal.transfer.business.model.old.Proxyinfotb;

public interface MerchantService {
    /**
     * 根据商户类型、商户分类生成商户号 
     * 生成规则：1位(是否为测试账户)+ 4位随机数 + 10位数据库sequence
     * @param merType
     * @param merClassify
     * @return
     */
    public String generateMerCode(String merClassify);
    /**
     * 迁移数据连锁商户
     * @param merCode
     * @return
     */
    public DodopalResponse<String> insertMerchant(Bimchntinfotb bim,String batchId);
    /**
     * 迁移数据连锁加盟网店商户
     * @param sysuserstb
     * @return
     */
    public DodopalResponse<String> insertMerchantChinaJoin(Biposinfotb biposinfotb,String batchId,Bimchntinfotb bim,String merParentCode);
    
    /**
     * @author Mikaelyan
     * @return
     */
    public DodopalResponse<String> insertMerchantFromProxy(Proxyinfotb proxyinfotb,String batchId);
}

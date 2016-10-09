package com.dodopal.transfer.business.service;

import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 连锁直营网点和加盟网点
 * @author lenovo
 *
 */
public interface ChilrenMerchantService {
    
    
    /**
     * 迁移网点数据
     * @param proxyid 网点id
     * @return
     */
    public DodopalResponse<String> childrenMerchantTransfer(String proxyid,String groupid, String batchId);

    /**
     * 生成商户号
     * @param merClassify 商户分类
     * @return
     */
    public String generateMerCode(String merClassify);

    /**
     * 生成商户用户号
     * @param userClassify
     * @return
     */
    public String generateMerUserCode(UserClassifyEnum userClassify);

 



}

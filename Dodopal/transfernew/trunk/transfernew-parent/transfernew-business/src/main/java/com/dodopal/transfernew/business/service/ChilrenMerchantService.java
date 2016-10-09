package com.dodopal.transfernew.business.service;

import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 连锁直营网点和加盟网点
 * @author lenovo
 */
public interface ChilrenMerchantService {

    /**
     * 迁移网点，如果网点下有pos，则将pos挂在网点下
     * 
     * @param proxyid 网点id
     * @param groupid 集团id
     * @param mertype 要迁移成对应的商户类型
     * @param citycode 城市编号
     * @param batchId 批次号
     * @return
     */
    public DodopalResponse<String> childrenMerchantTransfer(String proxyid, String groupid, String mertype, String citycode, String batchId);

    /**
     * 将网点下的pos迁移成对应的商户
     * 
     * @param proxyid 网点id
     * @param groupid 集团id
     * @param posid pos编号
     * @param mertype 商户类型
     * @param citycode 城市编号
     * @param batchId 批次号
     * @return
     */
    public DodopalResponse<String> childrenMerchantTransferByPos(String proxyid, String groupid, String posid, String mertype, String citycode, String batchId);

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

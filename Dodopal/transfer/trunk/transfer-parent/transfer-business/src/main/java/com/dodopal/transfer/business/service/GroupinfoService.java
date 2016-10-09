package com.dodopal.transfer.business.service;


import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 集团基本信息
 * @author tao
 *
 */
public interface GroupinfoService {
	/**
	 * 迁移集团相关信息
	 * @param groupid
	 * @return
	 */
    public DodopalResponse<String> groupInfoTranfer(String groupid);
	
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

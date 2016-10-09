package com.dodopal.transfer.business.service;

import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.model.DodopalResponse;

public interface PersonalInfoService {

	public DodopalResponse<String> insertSysUserstb(String userid,String batchId);
	
	 /**
     * 生成商户用户号
     * @param userClassify
     * @return
     */
    public String generateMerUserCode(UserClassifyEnum userClassify);
}

package com.dodopal.transfernew.business.service;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.model.transfer.Merchant;

public interface MerchantService {
	/**
	 * 根据商户类型、商户分类生成商户号 生成规则：1位(是否为测试账户)+ 4位随机数 + 10位数据库sequence
	 * 
	 * @param merType
	 * @param merClassify
	 * @return
	 */
	public String generateMerCode(String merClassify);

	/**
	 * 添加商户相关信息
	 * 
	 * @param merchant
	 * @return
	 */
	public DodopalResponse<String> addMerchantInfo(Merchant merchant);
}

package com.dodopal.portal.business.service;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.account.dto.query.AccountChildMerchantQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.AccountChildMerchantBean;
import com.dodopal.portal.business.bean.query.AccountChildMerchantQuery;

/**
 * 账户余额
 * @author hxc
 *
 */
public interface ChildMerchantActAmtService {

	/**
     * 根据上级商户 查询其子商户的总余额 及账户信息 （分页） 
     * @param merParentCode 上级商户编号
     * @param merName  商户 名称
     * @return DodopalDataPage<AccountChildMerchantDTO>
     */
	public DodopalResponse<DodopalDataPage<AccountChildMerchantBean>> findAccountChildMerByPage(AccountChildMerchantQuery query);
	
	 /**
     * 根据上级商户 查询其子商户的总余额 及账户信息
     * @param query 
     * @param response
     * @return String
     */
	public DodopalResponse<String> excelExport(AccountChildMerchantQueryDTO queryDTO,HttpServletResponse response);
}

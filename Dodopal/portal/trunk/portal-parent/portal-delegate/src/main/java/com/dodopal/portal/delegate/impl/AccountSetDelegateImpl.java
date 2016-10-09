package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.payment.facade.PayCommonFacade;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.AccountSetDelegate;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("accountSetDelegate")
public class AccountSetDelegateImpl extends BaseDelegate implements  AccountSetDelegate{

	/**
	 * 根据id查询
	 */
	public DodopalResponse<MerchantUserDTO> findUserInfoById(String id) {
		MerchantUserFacade facade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<MerchantUserDTO> response = facade.findUserInfoById(id);
	    return response;
	}
	
	/**
	 * 根据merCode查询业务城市
	 */
	public DodopalResponse<List<Area>> findMerCitys(String merCode){
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<List<Area>> merCitys = facade.findMerCitys(merCode);
		return merCitys;
	}
	
	/**
	 * 根据custType查询业务城市(个人和商户)
	 * @param merCode
	 * @param custType 用户类型
	 */
	public DodopalResponse<List<Area>> findYktCitys(MerUserTypeEnum custType, String custCode){
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<List<Area>> merCitys = facade.findYktCitys(custType, custCode);
		return merCitys;
	}
	
	
	/**
	 * 修改
	 */
	public DodopalResponse<Boolean> updateAccountSetInfo(MerchantUserDTO dto) {
		MerchantUserFacade facade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<Boolean> response = facade.updateMerchantUser(dto);
		return response;
		
	}
	

	/**
	 * 常用支付方式
	 */
	public DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext,
			String userCode) {
		PayFacade facade = getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		return facade.findCommonPayWay(ext, userCode);
	}

	/**
	 * 更多支付方式
	 */
	public DodopalResponse<List<PayWayDTO>> findMorePayWay(boolean ext,
			String merCode) {
		PayFacade facade = getFacade(PayFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		return facade.findPayWay(ext, merCode,"");
	}

	/**
	 * 新增
	 */
	public DodopalResponse<List<Integer>> insertPayWayCommon(
			List<PayWayCommonDTO> commons) {
		PayCommonFacade facade = getFacade(PayCommonFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		return facade.insertPayWayCommon(commons);
	}

	/**
	 * 删除
	 */
	public DodopalResponse<Integer> deletePaywayCommon(String userCode) {
		PayCommonFacade facade= getFacade(PayCommonFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
		return facade.deletePaywayCommon(userCode);
	}

	/**
	 * 修改业务城市
	 */
	public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode,
			String updateUser) {
		MerchantUserFacade facade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<Boolean> response = facade.updateMerUserBusCity(cityCode, updateUser);
		return response;
	}

}

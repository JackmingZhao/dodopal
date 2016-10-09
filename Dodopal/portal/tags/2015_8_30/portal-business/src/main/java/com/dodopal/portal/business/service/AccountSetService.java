package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.bean.PayWayCommonBean;

public interface AccountSetService {
	/**
	 * 根据id查询联系人信息
	 * @param id
	 * @return DodopalResponse<MerchantUserBean>
	 */
	DodopalResponse<MerchantUserBean> findUserInfoById(String id);
	
	/**
	 * 根据merCode查询业务城市
	 * @param merCode
	 * @return  DodopalResponse<List<Area>>
	 */
	DodopalResponse<List<Area>> findMerCitys(String merCode);
	/**
	 * 修改联系人信息
	 * @param dto
	 * @return DodopalResponse<Boolean>
	 */ 
	DodopalResponse<Boolean> updateAccountSetInfo(MerchantUserBean dto);
	
	/**
	 * 常用支付方式
	 * @param ext 是否为外接商户（true 是|false 否）
	 * @param userCode 商户编号  如果是外接商户，商户编号不能为空
	 * @return DodopalResponse<List<PayWayBean>>
	 */
	DodopalResponse<List<PayWayBean>> findCommonPayWay(boolean ext,String userCode);
	/**
	 * 查询更多支付方式
	 * @param ext 是否为外接商户（true 是|false 否）
	 * @param merCode 商户编号  如果是外接商户，商户编号不能为空
	 * @return  DodopalResponse<List<PayWayBean>>
	 */
	DodopalResponse<List<PayWayBean>> findMorePayWay(boolean ext,String merCode);
	
	/**
	 * 新增
	 * @param commons
	 * @return DodopalResponse<List<Integer>>
	 */
	DodopalResponse<List<Integer>> insertPayWayCommon(List<PayWayCommonBean> commons);
	
	/**
	 * 根据paywayIds删除
	 * @param paywayIds
	 * @return DodopalResponse<Integer>
	 */
	DodopalResponse<Integer> deletePaywayCommon();
	
	/**
	 * 修改业务城市
	 * @param cityCode
	 * @param updateUser
	 * @return DodopalResponse<Boolean>
	 */
	DodopalResponse<Boolean> updateMerUserBusCity(String cityCode,String updateUser);
}

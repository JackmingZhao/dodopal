package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;

public interface AccountSetDelegate {

	/**
	 * 根据id查询
	 * @param id
	 * @return DodopalResponse<MerchantUserDTO>
	 */
	public DodopalResponse<MerchantUserDTO> findUserInfoById(String id);
	
	/**
	 * 根据merCode查询业务城市
	 * @param merCode
	 * @return
	 */
	public DodopalResponse<List<Area>> findMerCitys(String merCode);
	/**
	 * 根据custType查询业务城市(个人和商户)
	 * @param merCode
	 * @param custType 用户类型
	 * @return
	 */
	public DodopalResponse<List<Area>> findYktCitys(MerUserTypeEnum custType, String custCode);
	/**
	 * 修改
	 * @param dto
	 * @return DodopalResponse<Boolean>
	 */
	public DodopalResponse<Boolean> updateAccountSetInfo(MerchantUserDTO dto);
	
	/**
	 * 常用支付
	 * @param ext 是否为外接商户（true 是|false 否）
	 * @param userCode 商户编号  如果是外接商户，商户编号不能为空
	 * @return DodopalResponse<List<PayWayDTO>>
	 */
	public DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext,
			String userCode);
	/**
	 * 更多支付
	 * @param ext 是否为外接商户（true 是|false 否）
	 * @param merCode 商户编号  如果是外接商户，商户编号不能为空
	 * @return DodopalResponse<List<PayWayDTO>>
	 */
	public DodopalResponse<List<PayWayDTO>> findMorePayWay(boolean ext,String merCode);
	
	/**
	 * 新增
	 * @param commons
	 * @return  DodopalResponse<Integer>
	 */
	public DodopalResponse<List<Integer>> insertPayWayCommon(List<PayWayCommonDTO> commons);
	
	/**
	 * 根据userCode删除
	 * @param userCode
	 * @return DodopalResponse<Integer>
	 */
	public DodopalResponse<Integer> deletePaywayCommon(String userCode);
	
	/**
	 * 修改业务城市
	 * @param cityCode
	 * @param updateUser
	 * @return DodopalResponse<Boolean>
	 */
	public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode, String updateUser);
}

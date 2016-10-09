package com.dodopal.product.business.service;

import java.util.List;
import java.util.Map;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.MerBusRateBean;
import com.dodopal.product.business.bean.MerRateSupplementBean;
import com.dodopal.product.business.bean.MerchantBean;

public interface MerchantService {
	public DodopalResponse<List<Area>> findMerCitys(String merCode); 
	
	public DodopalResponse<List<MerBusRateBean>> findMerBusRateByMerCode(String merCode);
	
	/**
	 * @param merCode
	 * @return 根据商户号查找商户信息
	 */
	public DodopalResponse<MerchantBean> findMerchantByCode(String merCode);

	public DodopalResponse<MerchantDTO> findMerchantInfoByMerCode(String merCode);

	/** 
	  * @author  Dingkuiyuan@dodopal.com 
	  * @date 创建时间：2015年10月29日 下午3:15:22 
	  * @version 1.0 
	  * @parameter  根据商户号查询商户开通一卡通消费的城市
	  * @since  
	  * @return  
	  */
	public DodopalResponse<List<Area>> findMerCitysPayment(String merCode);
	
	public DodopalResponse<MerRateSupplementBean> findMerRateSupplementByMerCode(String merCode,String bussinessType);
	
	/**
     * 公交卡充值检验商户合法性
     * @param merchantNum
     * @param userId
     * @param posId
     * @param providerCode
     * @return 
     * 		返回Map-->key：
     * 			merName(商户名称[String])，
     * 			merUserNickName(用户真实姓名[String])，
     * 			rateType(费率类型[String])，
     * 			rate(费率值[Double])
     * 			merType(商户类型[String]，个人为99)
     * 			userCode(用户编码[String])
     * 			pageCallbackUrl(页面回调通知外接商户[String])
     * 			serviceNoticeUrl(服务器回调通知外接商户[String])
     */
	public DodopalResponse<Map<String, String>> validateMerchantForIcdcRecharge(String merchantNum, String userId, String posId,
			String providerCode, String source);
}

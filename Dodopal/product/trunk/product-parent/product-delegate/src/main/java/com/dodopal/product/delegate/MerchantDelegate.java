package com.dodopal.product.delegate;

import java.util.List;
import java.util.Map;

import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;

public interface MerchantDelegate {
    /**
     * 根据mercode查找商户详情
     * @param merCode
     * @return
     */
    public DodopalResponse<MerchantDTO> findMerchantByCode(String merCode);
    
    
    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通充值)
     * @param merCode
     * @return
     */
    public DodopalResponse<List<Area>> findMerCitys(String merCode); 
    
    /**
     * 根据商户号查询费率信息
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerBusRateDTO>> findMerBusRateByMerCode(String merCode);

    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2015年10月29日 下午3:12:41 
      * @version 1.0 
      * @parameter  查询商户消费所开通的城市
      * @since  
      * @return  
      */
    public DodopalResponse<List<Area>> findMerCitysPayment(String merCode);
    
    /**
     * 根据商户号获取商户业务信息列表
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerRateSupplementDTO>> findMerRateSupplementsByMerCode(String merCode);
    
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
    public DodopalResponse<Map<String,String>> validateMerchantForIcdcRecharge(String merchantNum, String userId, String posId, String providerCode, String source);
    
    /**
     * 公交卡充值圈存检验商户合法性
     * @param merCode
     * @param providerCode
     * @return 
     *      返回Map-->key：
     *          rateType(费率类型[String])，
     *          rate(费率值[Double])
     */
    public DodopalResponse<Map<String,String>> validateMerchantForIcdcLoad(MerUserTypeEnum userType, String merCode, String providerCode);

    /**************************************************** 保存供应商信息开始 *****************************************************/
    public DodopalResponse<String> addProviderRegister(MerchantDTO merchantDTO);
    public DodopalResponse<String> upProviderRegister(MerchantDTO merchantDTO);
    /**************************************************** 保存供应商信息结束 *****************************************************/
    
    /**
     * 根据POS编号查询其绑定商户信息
     * @param posId
     * @return 
     *      返回Map-->key：
     *          merCode(商户编号[String])，
     */
    public DodopalResponse<Map<String,String>> getMerchantByPosCode(String posId);
}

package com.dodopal.api.users.facade;

import java.util.List;
import java.util.Map;

import com.dodopal.api.users.dto.DirectMerChantDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantExtendDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 :
 * @author lifeng
 */

public interface MerchantFacade {
    /**
     * 查询商户信息
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<List<MerchantDTO>> findMerchant(MerchantDTO merchantDTO);

    /**
     * 查询商户信息（分页）
     * @param merQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerchantDTO>> findMerchantByPage(MerchantQueryDTO merQueryDTO);

    /**
     * EXCEL导出使用
     * @param merQueryDTO
     * @return
     */
    public DodopalResponse<Integer> findMerchantByPageCount(MerchantQueryDTO merQueryDTO);

    /**
     * EXCEL导出使用
     * @param merQueryDTO
     * @return
     */
    public DodopalResponse<List<MerchantDTO>> findMerchantByPageList(MerchantQueryDTO merQueryDTO);

    /**
     * 根据ID查询商户
     * @param id
     * @return
     */
    public DodopalResponse<MerchantDTO> findMerchantById(String id);

    /**
     * 根据商户号查询商户信息
     * @param merCode
     * @return
     */
    public DodopalResponse<MerchantDTO> findMerchantByMerCode(String merCode);
    
    /**
     * 根据商户号查询商户（只查商户表）
     * @param merCode
     * @return
     */
    public DodopalResponse<MerchantDTO> findMerchantInfoByMerCode(String merCode);

    /**
     * 根据商户号查询商户上下级关系列表
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerchantDTO>> findMerchantRelationByMerCode(String merCode);

    /**
     * 批量停启用商户
     * @param activate
     * @param merCodes
     * @return
     */
    public DodopalResponse<Integer> updateMerchantActivate(String activate, List<String> merCodes, String updateUser);

    /**
     * 商户注册
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<String> merchantRegister(MerchantDTO merchantDTO);

    /**
     * 为供应商注册商户，2016.1.8
     * @param merchantDTO
     * 			merName 商户名称，必填，使用通卡公司名称
     * 			merLinkUser 联系人，必填，使用联系人姓名，如果为空，使用通卡公司名称
     * 			merLinkUserMobile 手机号码，必填，使用联系人手机号，如果为空，使用一卡通代码
     * 			merPro 省份，必填
     * 			merCity 城市，必填
     * 			merAdds 地址，必填
     * 			createUser 创建人，必填
     * 			MerchantUserDTO.merUserName 用户名，必填，使用一卡通代码
     * @return 商户号
     */
    public DodopalResponse<String> providerRegister(MerchantDTO merchantDTO);

    /**
     * 供应商商户、管理员信息更新
     * @param merchantDTO
     * 			merCode 商户号，必填
     * 			merName 商户名称，必填，使用通卡公司名称
     * 			merLinkUser 联系人，必填，使用联系人姓名，如果为空，使用通卡公司名称
     * 			merLinkUserMobile 手机号码，必填，使用联系人手机号，如果为空，使用一卡通代码
     * 			merPro 省份，必填
     * 			merCity 城市，必填
     * 			merAdds 地址，必填
     * 			updateUser 更新人，必填
     * @return
     */
    public DodopalResponse<String> providerUpdate(MerchantDTO merchantDTO);

    /**
     * 商户审核
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<String> merchantAudit(MerchantDTO merchantDTO);

    /**
     * 批量删除审核不通过商户信息
     * @param merCodes
     * @return
     */
    public DodopalResponse<Integer> batchDelRejectMerchantByMerCodes(List<String> merCodes, String updateUser);

    /**
     * 查询商户操作员列表
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<List<MerchantUserDTO>> findMerOperators(MerchantUserDTO merchantUserDTO);

    /**
     * 查询商户操作员信息（分页）
     * @param merQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findMerOperatorByPage(MerchantUserQueryDTO userQueryDTO);

    /**
     * 根据用户编号查询商户操作员
     * @param merCode
     * @param userCode
     * @return
     */
    public DodopalResponse<MerchantUserDTO> findMerOperatorByUserCode(String merCode, String userCode);

    /**
     * 增加商户操作员
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<Integer> addMerOperator(MerchantUserDTO merchantUserDTO);

    /**
     * 更新商户操作员
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<Integer> updateMerOperator(MerchantUserDTO merchantUserDTO);

    /**
     * 批量停启用操作员
     * @param activate
     * @param updateUser
     * @param userCodes
     * @return
     */
    public DodopalResponse<Integer> batchActivateMerOperator(String merCode, String updateUser, ActivateEnum activate, List<String> userCodes);

    /**
     * 给商户操作员分配角色
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<Integer> configMerOperatorRole(MerchantUserDTO merchantUserDTO);

    /**
     * 重置操作员密码
     * @param merCode
     * @param userCode
     * @param password
     * @return
     */
    public DodopalResponse<Boolean> resetOperatorPwd(String merCode, String id, String password, String updateUser);

    /**
     * 门户更新商户信息
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<String> updateMerchantForPortal(MerchantDTO merchantDTO);

    /**
     * 根据商户号查询费率信息
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerBusRateDTO>> findMerBusRateByMerCode(String merCode);

    /**
     * OSS修改商户通卡公司或费率类型时，判断是否与上级费率类型不一致，或者少于下级通卡公司数量，或者与下级费率类型不一致
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<Boolean> checkRelationMerchantProviderAndRateType(MerchantDTO merchantDTO);

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
     * 			payWayExtId(外接商户账户支付方式ID[String])
     */
    public DodopalResponse<Map<String,String>> validateMerchantForIcdcRecharge(String merchantNum, String userId, String posId, String providerCode, String source);

    /**
     * NFC手机个人用户充值：检验个人用户相关合法性接口
     * @param userId
     * @return
     */
    public DodopalResponse<Map<String,String>> validatePersonalUserForNfcRecharge(String userId);
    
    /**
     * 支付交易交易用户或商户合法性
     * @param userType
     * @param code
     * @return
     */
    public DodopalResponse<Map<String,Object>> validateMerchantForPayment(MerUserTypeEnum userType, String code);

    /**
     * 根据用户编码校验用户合法性，如果是商户用户则校验商户合法性
     * @param userCode
     * @return
     */
    public DodopalResponse<Map<String,Object>> validateMerchantUserForPayment(String userCode);

    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通充值)
     * @param merCode
     * @return
     */
    public DodopalResponse<List<Area>> findMerCitys(String merCode);

    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通消费)
     * @param merCode
     * @return
     */
    public DodopalResponse<List<Area>> findMerCitysPayment(String merCode);

    /**
     * 查询商户或个人用户开通的通卡对应的城市列表(必须开通一卡通充值)
     * @param custType 客户类型(枚举)：0个人1商户
     * @param custNo 客户号：用户号或商户号
     * @return
     */
    public DodopalResponse<List<Area>> findYktCitys(MerUserTypeEnum custType, String custCode);

    /**
     * 根据直营网点的 上级编码 和直营网点的名称 查询直营网点
     * @param merParentCode 上级编码
     * @param merName 商户名称
     * @return DodopalResponse<List<MerchantDTO>>
     */
    public DodopalResponse<List<DirectMerChantDTO>> findMerchantByParentCode(String merParentCode, String merName);
    
    public DodopalResponse<List<DirectMerChantDTO>> findMerchantByParentCode(String merParentCode, String merName,String businessType);
    public DodopalResponse<List<DirectMerChantDTO>> findDirectTransferFilter(String merParentCode, String merName,String businessType);

    /**
     * 根据商户号获取商户业务信息列表
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerRateSupplementDTO>> findMerRateSupplementsByMerCode(String merCode);

    /**
     * 收单过程中验证商户合法性
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
     * 			merType(商户类型[String])
     * 			userCode(用户编码[String])
     * 			pageCallbackUrl(页面回调通知外接商户[String])
     * 			serviceNoticeUrl(服务器回调通知外接商户[String])
     */
    public DodopalResponse<Map<String,String>> validateMerchantForIcdcPurchase(String merchantNum, String userId, String posId, String providerCode, String source);
    

    /**
     * 自动转账验证商户合法性
     * @param merCode (自动转账的商户编号)
     * @return 
     *      返回Map-->key：
     *          parentMerCode(上级商户编号[String])，
     *          parentMerUserId(上级商户管理员ID[String])，
     *          merUserId(当前商户管理员ID[String])，
     *          thresholdAmt(额度阀值[Double])
     *          transferAmt(转账金额[String])
     */
    public DodopalResponse<Map<String,String>> checkMerInfo(String merCode);

    /**
     * 生成圈存订单时校验商户或个人合法性
     * @param userType 用户类型
     * @param code 商户号或用户编号（个人）
     * @param providerCode 供应商
     * @return 
     * 		返回Map-->key：
     * 			rateType(费率类型[String])，
     * 			rate(费率值[Double])
     * 			custName(客户名称--商户：商户名称，个人：真实姓名)
     *          userId(管理员ID)
     *          userCode(管理员CODE)
     *          payWayExtId(外接商户账户支付方式ID)
     */
    public DodopalResponse<Map<String,String>> validateMerchantForIcdcLoad(MerUserTypeEnum userType, String code, String providerCode);

    /**
     * 根据商户号查询商户扩展表信息
     * @param merCode
     * @return
     */
    public DodopalResponse<MerchantExtendDTO> findMerchantExtend(String merCode);
    
    /**
     * 根据POS编号查询其绑定商户信息
     * @param posId
     * @return 
     *      返回Map-->key：
     *          merCode(商户编号[String])，
     */
    public DodopalResponse<Map<String,String>> getMerchantByPosCode(String posId);

    /**
     * @description 获取是否自动转账标识
     * @return
     */
    public DodopalResponse<String> getIsAuto(String merCode);

    /**
     * @description 获取共享额度的上级商户的code
     * @return
     */
    public DodopalResponse<String> getParentid(String merCode);

}


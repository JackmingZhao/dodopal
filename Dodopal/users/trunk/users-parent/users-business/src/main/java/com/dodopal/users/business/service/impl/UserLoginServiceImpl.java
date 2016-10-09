package com.dodopal.users.business.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerTypeOldEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.users.business.model.MerDdpInfo;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantExtend;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.MerchantUserExtend;
import com.dodopal.users.business.service.MerBusRateService;
import com.dodopal.users.business.service.MerDdpInfoService;
import com.dodopal.users.business.service.MerFunctionInfoService;
import com.dodopal.users.business.service.MerchantExtendService;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.MerchantUserExtendService;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.business.service.UserLoginService;
import com.dodopal.users.delegate.AccountQueryDelegate;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private MerchantUserService merchantUserService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerFunctionInfoService merFunctionInfoService;
    @Autowired
    private AreaService areaService;
    @Autowired
	private MerBusRateService merBusRateService;
    @Autowired
    private MerDdpInfoService merDdpInfoService;
    @Autowired
    private MerchantUserExtendService merchantUserExtendService;
    @Autowired
    private MerchantExtendService merchantExtendService;
    @Autowired
    private AccountQueryDelegate accountQueryDelegate;

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<Merchant> login(String userName, String password) {
        DodopalResponse<Merchant> response = new DodopalResponse<Merchant>();
        // 判断第一个字母是否为数字，是则为手机号码登录
        char firstChar = userName.charAt(0);
        MerchantUser merUserParam = new MerchantUser();
        if (Character.isDigit(firstChar)) {
			boolean isMobile = DDPStringUtil.isMobile(userName);
			// 非正常手机号
			if (!isMobile) {
				// 用户名不存在
	            response.setCode(ResponseCode.USERS_USER_NAME_NO_EXIST);
	            return response;
			}
            merUserParam.setMerUserMobile(userName);
        } else {
            merUserParam.setMerUserName(userName);
        }

        // 【用户信息】
        MerchantUser merUser = merchantUserService.findMerchantUserExact(merUserParam);
        if (merUser == null) {
            // 用户名不存在
            response.setCode(ResponseCode.USERS_USER_NAME_NO_EXIST);
            return response;
        }
        String merUserPwd = merUser.getMerUserPWD();
        if (!password.equals(merUserPwd)) {
            // 密码错误
            response.setCode(ResponseCode.USERS_PASSWORD_ERR);
            return response;
        }
        String merUserState = merUser.getMerUserState();
        if(!MerStateEnum.THROUGH.getCode().equals(merUserState)) {
        	// 非审核通过状态
        	response.setCode(ResponseCode.USERS_USER_STATE_NO_THROUGH);
            return response;
        }

        String activate = merUser.getActivate();
        if (activate.equals(ActivateEnum.DISABLE.getCode())) {
            // 用户已停用
            response.setCode(ResponseCode.USERS_USER_DISABLE);
            return response;
        }

        // 【商户信息】
        Merchant mer = null;
        // 【商户功能】
        List<MerFunctionInfo> merFunInfoList = null;
        // 判断用户类型，获取权限信息
        if (MerUserTypeEnum.PERSONAL.getCode().equals(merUser.getMerUserType())) { //个人
            mer = new Merchant();
            mer.setMerType(MerTypeEnum.PERSONAL.getCode());
            merFunInfoList = merFunctionInfoService.findMerFunctionInfoByMerType(MerTypeEnum.PERSONAL.getCode());
            // 根据usercode查用户扩展表，判断是否为迁移用户，【是】则增加门户历史记录菜单权限
            if(CollectionUtils.isNotEmpty(merFunInfoList)) {
            	String userCode = merUser.getUserCode();
				MerchantUserExtend merUserExtend = merchantUserExtendService.findByUserCode(userCode);
				if (merUserExtend != null && StringUtils.isNotBlank(merUserExtend.getOldUserId()) && MerTypeOldEnum.PERSONAL.getCode().equals(merUserExtend.getOldUserType())) {
					MerFunctionInfo hisOrderFun = new MerFunctionInfo();
					hisOrderFun.setMerFunCode(MerTypeOldEnum.getAuthorityCodeByType(merUserExtend.getOldUserType()));
					merFunInfoList.add(hisOrderFun);
				}
            }
        } else if (MerUserTypeEnum.MERCHANT.getCode().equals(merUser.getMerUserType())) { //企业
            String merCode = merUser.getMerCode();
            String merUserFlg = merUser.getMerUserFlag();
            String userCode = merUser.getUserCode();

            if (StringUtils.isBlank(merCode)) {
            	// 用户对应的商户号为空
                response.setCode(ResponseCode.USERS_MER_CODE_NULL);
                return response;
            }

            mer = merchantService.findMerchantInfoByMerCode(merCode);
            if (mer == null) {
            	// 未找到用户对应的商户信息
                response.setCode(ResponseCode.USERS_MER_NOT_FOUND_DISABLE);
                return response;
            }
            
            if (mer.getActivate().equals(ActivateEnum.DISABLE.getCode())) {
                // 商户已停用
                response.setCode(ResponseCode.USERS_MERCHANT_DISABLE);
                return response;
            }
            if (!MerStateEnum.THROUGH.getCode().equals(mer.getMerState())) {
                // 非审核通过状态
                response.setCode(ResponseCode.USERS_MER_STATE_NO_THROUGH);
                return response;
            }
            // 商户属性:0.标准商户,1.自定义商户
            String merProperty = mer.getMerProperty();
            String merType = mer.getMerType();
            if (MerUserFlgEnum.ADMIN.getCode().equals(merUserFlg)) { //管理员
                if (MerPropertyEnum.STANDARD.getCode().equals(merProperty)) { // 标准商户(管理员)
                    merFunInfoList = merFunctionInfoService.findMerFunctionInfoByMerType(merType);
                } else if (MerPropertyEnum.CUSTOM.getCode().equals(merProperty)) { //自定义商户(管理员)
                    merFunInfoList = merFunctionInfoService.findMerFunctionInfoByMerCode(merCode);
                }
            } else if (MerUserFlgEnum.COMMON.getCode().equals(merUserFlg)) { // 操作员
                if (MerPropertyEnum.STANDARD.getCode().equals(merProperty)) { // 标准商户(操作员)
                    merFunInfoList = merFunctionInfoService.findStandardOperatorFuns(userCode, merType);
                } else if (MerPropertyEnum.CUSTOM.getCode().equals(merProperty)) { //自定义商户(操作员)
                    merFunInfoList = merFunctionInfoService.findCustomOperatorFuns(userCode, merCode);
                }
            }

            // 根据mercode查商户扩展表，判断是否为迁移商户，【是】则增加门户历史记录菜单权限
			if (CollectionUtils.isNotEmpty(merFunInfoList)) {
				MerchantExtend merchantExtend = merchantExtendService.findByMerCode(merCode);
				if (merchantExtend != null && StringUtils.isNotBlank(merchantExtend.getOldMerchantId()) && MerTypeOldEnum.contains(merchantExtend.getOldMerchantType())) {
					String merFunCode = MerTypeOldEnum.getAuthorityCodeByType(merchantExtend.getOldMerchantType());
					if (StringUtils.isNotBlank(merFunCode)) {
						MerFunctionInfo hisOrderFun = new MerFunctionInfo();
						hisOrderFun.setMerFunCode(merFunCode);
						merFunInfoList.add(hisOrderFun);
					}
				}
			}

            // 获取商户补充信息
			MerDdpInfo merDdpInfo = merDdpInfoService.findMerDdpInfoByMerCode(merCode);
			mer.setMerDdpInfo(merDdpInfo);

			// 供应商获取一卡通编码
			if (MerTypeEnum.PROVIDER.getCode().equals(merType)) {
				String yktCode = merchantService.getYktCodeByMerCode(merCode);
				if(StringUtils.isNotBlank(yktCode)) {
					mer.setYktCode(yktCode);
				} else {
					// 如果未查到，一卡通编码暂时写死，方便测试 2016.1.12
					mer.setYktCode("330000");
				}
			}
        }

        // 没有权限信息
        if(CollectionUtils.isEmpty(merFunInfoList)) {
            response.setCode(ResponseCode.USERS_USER_AUTHORITY_NULL);
            return response;
        }

        // 获取用户业务城市信息
		String cityCode = merUser.getCityCode();
		if (StringUtils.isBlank(cityCode)) {
			if (MerUserTypeEnum.PERSONAL.getCode().equals(merUser.getMerUserType())) {
				// 个人如果没有设置业务城市，则取默认值
				//cityCode = CommonConstants.DEFAULT_CITY_CODE;
				// 个人如果没有设置业务城市，则取已经开通一卡通充值业务城市第一个，按城市字母排序 2016.1.4
				List<String> cityList = merBusRateService.findUserCitys();
				if (CollectionUtils.isNotEmpty(cityList)) {
					cityCode = cityList.get(0);
				}
			} else {
				// 商户取其业务城市,多个则取第一个
				List<String> cityList = merBusRateService.findMerCitys(mer.getMerCode());
				if (CollectionUtils.isEmpty(cityList)) {
					// 商户用户没有设置业务城市，则取注册城市
					cityCode = mer.getMerCity();
				} else {
					cityCode = cityList.get(0);
				}
			}
			// 设置城市编码
			merUser.setCityCode(cityCode);
		}
		// 设置城市名称
		Area area = areaService.findCityByCityCode(cityCode);
		if (area != null) {
			merUser.setCityName(area.getCityName());
		}

		// 账户可用余额，VC端使用
		try {
			String aType = merUser.getMerUserType();
			String custNum = null;
			if (MerUserTypeEnum.PERSONAL.getCode().equals(aType)) {
				custNum = merUser.getUserCode();
			} else if (MerUserTypeEnum.MERCHANT.getCode().equals(aType)) {
				custNum = mer.getMerCode();
			}
			DodopalResponse<AccountFundDTO> accountRes = accountQueryDelegate.findAccountBalance(aType, custNum);
			if (ResponseCode.SUCCESS.equals(accountRes.getCode())) {
				// 金额格式化 保留两位小数
				DecimalFormat df = new DecimalFormat("0.00");
				mer.setAvailableBalance(df.format(accountRes.getResponseEntity().getAvailableBalance()));
			} else {
				mer.setAvailableBalance("0.00");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mer.setAvailableBalance("0.00");
		}

        mer.setMerchantUser(merUser);
        mer.setMerFunInfoList(merFunInfoList);
        response.setResponseEntity(mer);
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

}

package com.dodopal.users.business.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.MD5;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.users.business.constant.UsersConstants;
import com.dodopal.users.business.dao.DirectMerChantMapper;
import com.dodopal.users.business.dao.MerchantMapper;
import com.dodopal.users.business.model.DirectMerChant;
import com.dodopal.users.business.model.MerAutoAmt;
import com.dodopal.users.business.model.MerBusRate;
import com.dodopal.users.business.model.MerDdpInfo;
import com.dodopal.users.business.model.MerDefineFun;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.model.MerKeyType;
import com.dodopal.users.business.model.MerRateSupplement;
import com.dodopal.users.business.model.MerRole;
import com.dodopal.users.business.model.MerUserRole;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantExtend;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.Pos;
import com.dodopal.users.business.model.query.MerchantQuery;
import com.dodopal.users.business.service.MerAutoAmtService;
import com.dodopal.users.business.service.MerBusRateService;
import com.dodopal.users.business.service.MerDdpInfoService;
import com.dodopal.users.business.service.MerDefineFunService;
import com.dodopal.users.business.service.MerFunctionInfoService;
import com.dodopal.users.business.service.MerKeyTypeService;
import com.dodopal.users.business.service.MerRateSupplementService;
import com.dodopal.users.business.service.MerTypeDisableRelationService;
import com.dodopal.users.business.service.MerUserRoleService;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.business.service.PosService;
import com.dodopal.users.delegate.AccountManagementDelegate;
import com.dodopal.users.delegate.AccountQueryDelegate;

/**
 * 类说明 :
 * @author lifeng
 */
@Service
public class MerchantServiceImpl implements MerchantService {
    private final static Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
    @Autowired
    private MerchantMapper mapper;
    @Autowired
    private MerchantUserService merchantUserService;
    @Autowired
    private MerDdpInfoService merDdpInfoService;
    @Autowired
    private MerBusRateService merBusRateService;
    @Autowired
    private MerDefineFunService merDefineFunService;
    @Autowired
    private MerFunctionInfoService merFunctionInfoService;
    @Autowired
    private MerUserRoleService merUserRoleService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private MerKeyTypeService merKeyTypeService;
    @Autowired
    private MerTypeDisableRelationService merTypeDisableRelationService;
    @Autowired
    private AccountManagementDelegate accountManagementDelegate;
    @Autowired
    private AccountQueryDelegate accountQueryDelegate;
    @Autowired
    private MerRateSupplementService merRateSupplementService;
    @Autowired
    private DirectMerChantMapper directMerChantMapper;
    @Autowired
	private MerAutoAmtService merAutoAmtService;
    @Autowired
    private PosService posService;

    // 未审核排序
    private static final String PAGE_NO_AUDIT_ORDER = "update_date desc nulls last,id desc";
    // 其他排序
    private static final String PAGE_OTHER_ORDER = "mer_state_date desc,id desc";

    @Override
    @Transactional(readOnly = true)
    public List<Merchant> findMerchant(Merchant merchant) {
        return mapper.findMerchant(merchant);
    }

    @Override
    @Transactional(readOnly = true)
    public Merchant findMerchantById(String id) {
        return mapper.findMerchantById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Merchant findMerchantInfoByMerCode(String merCode) {
        return mapper.findMerchantByMerCode(merCode);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Merchant findMerchantExact(Merchant merchant) {
        return mapper.findMerchantExact(merchant);
    }

    @Override
    @Transactional(readOnly = true)
    public Merchant findMerchantByMerCode(String merCode) {
        // 获取商户信息
        Merchant merchant = mapper.findMerchantByMerCode(merCode);
        if (merchant == null) {
            return null;
        }
        
        String merType = merchant.getMerType();
        String merParentCode = merchant.getMerParentCode();

        // 获取商户补充信息
        MerDdpInfo merDdpInfo = merDdpInfoService.findMerDdpInfoByMerCode(merCode);
        if (merDdpInfo != null) {
            merchant.setMerDdpInfo(merDdpInfo);
        }

        // 根据商户号获取管理员用户信息
        MerchantUser paramMerUser = new MerchantUser();
        paramMerUser.setMerCode(merCode);
        paramMerUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
        List<MerchantUser> merchantUserList = merchantUserService.findMerchantUser(paramMerUser);
        if (merchantUserList != null && merchantUserList.size() > 0) {
            MerchantUser merchantUser = merchantUserList.get(0);
            merchantUser.setMerUserPWD(null); //清除密码
            merchant.setMerchantUser(merchantUser);
        }

        // 获取【费率信息】和【商户业务信息】
        if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            if (StringUtils.isNotBlank(merParentCode)) {
                Merchant parentMer = mapper.findMerchantByMerCode(merParentCode);
                if (parentMer != null) {
                	// 如果是连锁直营网点，直接取父级费率
                    List<MerBusRate> merParentBusRateList = merBusRateService.findMerBusRateByMerCode(merParentCode);
                    merchant.setMerBusRateList(merParentBusRateList);

                    // 如果是连锁直营网点，直接取父级业务信息
                	List<MerRateSupplement> merRateSpmtList = merRateSupplementService.findMerRateSpmtsByMerCode(merParentCode);
                    merchant.setMerRateSpmtList(merRateSpmtList);
                }
            }
        } else {
            List<MerBusRate> merBusRateList = merBusRateService.findMerBusRateByMerCode(merCode);
            merchant.setMerBusRateList(merBusRateList);

            List<MerRateSupplement> merRateSpmtList = merRateSupplementService.findMerRateSpmtsByMerCode(merCode);
            merchant.setMerRateSpmtList(merRateSpmtList);
        }

        // 如果是自定义商户，获取自定义功能信息
        String merProperty = merchant.getMerProperty();
        if (MerPropertyEnum.CUSTOM.getCode().equals(merProperty)) {
            List<MerFunctionInfo> merDefineFunList = merFunctionInfoService.findMerFunctionInfoByMerCode(merCode);
            merchant.setMerDefineFunList(merDefineFunList);
        }

        // 获取秘钥信息(不再确认是否外接商户 modify by lifeng 2016.4.16)
        MerKeyType merKeyType = merKeyTypeService.findMerKeyTypeByMerCode(merCode);
        merchant.setMerKeyType(merKeyType);

        // 自动分配额度
		if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType)) {
			MerAutoAmt merAutoAmt = merAutoAmtService.findMerAutoAmtByMerCode(merCode);
			merchant.setMerAutoAmt(merAutoAmt);
		}

        // ==============================================================获取扩展属性
        if (StringUtils.isNotBlank(merParentCode)) {
            // 获取上级商户名称、类型
            Merchant merParent = mapper.findMerchantByMerCode(merParentCode);
            if (merParent != null) {
                merchant.setMerParentName(merParent.getMerName());
                merchant.setMerParentType(merParent.getMerType());
            }
        }

        // 地区、省份、城市
        String merArea = merchant.getMerArea();
        String merPro = merchant.getMerPro();
        String merCity = merchant.getMerCity();
        if (StringUtils.isNotBlank(merArea)) {
            Area area = areaService.findCityByCityCode(merArea);
            if (area != null) {
                merchant.setMerAreaName(area.getCityName());
            }
        }
        if (StringUtils.isNotBlank(merPro)) {
            Area pro = areaService.findCityByCityCode(merPro);
            if (pro != null) {
                merchant.setMerProName(pro.getCityName());
            }
        }
        if (StringUtils.isNotBlank(merCity)) {
            Area city = areaService.findCityByCityCode(merCity);
            if (city != null) {
                merchant.setMerCityName(city.getCityName());
            }
        }

        // 获取账户资金类型
        DodopalResponse<AccountFundDTO> accountResponse = accountQueryDelegate.findAccountBalance(MerUserTypeEnum.MERCHANT.getCode(), merCode);
        if(ResponseCode.SUCCESS.equals(accountResponse.getCode()) && accountResponse.getResponseEntity() != null) {
        	merchant.setFundType(accountResponse.getResponseEntity().getFundType());
        } else {
        	// TODO:考虑以前创建的商户都没有账户,所以这里设置一个默认返回值
        	merchant.setFundType(FundTypeEnum.FUND.getCode());
        }

        return merchant;
    }

    @Override
    @Transactional
    public List<Merchant> findMerchantRelationByMerCode(String merCode) {
        return mapper.findMerchantRelationByMerCode(merCode);
    }

    @Override
    @Transactional
    public int batchUpdateMerchant(Merchant merchant, List<String> merCodes) {
        return mapper.batchUpdateMerchant(merchant, merCodes);
    }

    @Override
    @Transactional
    public int batchUpdateMerchantActivate(String activate, List<String> merCodes, List<String> msg, String updateUser) {
        Merchant merchant = new Merchant();
        merchant.setActivate(activate);
        merchant.setUpdateUser(updateUser);
        int num = 0;
        List<Merchant> merList = mapper.batchFindMerchantByMerCode(merCodes);
        if (!CollectionUtils.isEmpty(merList)) {
        	// 停用相关类型的商户
            if (ActivateEnum.DISABLE.getCode().equals(activate)) {
                // 取出所有子商户(含当前商户)
                List<Merchant> childMerList = mapper.findChildMerchantByMerCodes(merCodes);
                // 取出相关停用类型
                Map<String, Map<String, String>> merTypeMap = merTypeDisableRelationService.findDisableRelationTypeMap();
                // 找出有下级商户的商户号
                Map<String, List<Merchant>> childMerListMap = new HashMap<String, List<Merchant>>();
                for (Merchant childMerTemp : childMerList) {
                    String merParentCodeTemp = childMerTemp.getMerParentCode();
                    if (StringUtils.isNotBlank(merParentCodeTemp)) {
                        if (!childMerListMap.containsKey(merParentCodeTemp)) {
                            childMerListMap.put(merParentCodeTemp, new ArrayList<Merchant>());
                        }
                        List<Merchant> childList = childMerListMap.get(merParentCodeTemp);
                        childList.add(childMerTemp);
                    }
                }
                // 递归查找需要停用的商户
                List<String> resultList = new ArrayList<String>();
                for (Merchant curMer : merList) {
                    findDisableMerCodes(curMer, merTypeMap, childMerListMap, resultList);
                }
                if (resultList.size() > 0) {
                    num = mapper.batchUpdateMerchant(merchant, resultList);
                }
            } else if(ActivateEnum.ENABLE.getCode().equals(activate)) {
                // 启用特定商户类型时判断相关的上级是否启用
                Map<String, String> merTypeMap = merTypeDisableRelationService.findEnableRelationType();
                // 可以启用的商户code
                for(Merchant mer : merList) {
                    if(merTypeMap.containsKey(mer.getMerType())) {
                        String merParentCode = mer.getMerParentCode();
                        Merchant parentMerchant = mapper.findMerchantByMerCode(merParentCode);
                        // 如果上级是停用的，下级不能被启用
                        if(ActivateEnum.DISABLE.getCode().equals(parentMerchant.getActivate())) {
                            msg.add("【"+mer.getMerName()+"】");
                            continue;
                        }
                    }
                }
                if (msg.size() == 0) {
                    num = mapper.batchUpdateMerchant(merchant, merCodes);
                }
            }
        }
        return num;
    }

    @Transactional(readOnly = true)
    private void findDisableMerCodes(Merchant curMer, Map<String, Map<String, String>> merTypeMap, Map<String, List<Merchant>> childMerListMap, List<String> resultList) {
        String curMerCode = curMer.getMerCode();
        String curMerType = curMer.getMerType();
        resultList.add(curMerCode);
        if (merTypeMap.containsKey(curMerType)) {
            if (childMerListMap.containsKey(curMerCode)) {
                Map<String, String> disableMap = merTypeMap.get(curMerType);
                List<Merchant> childMerList = childMerListMap.get(curMerCode);
                if (!CollectionUtils.isEmpty(childMerList)) {
                    for (Merchant childMer : childMerList) {
                        String disableType = childMer.getMerType();
                        if (disableMap.containsKey(disableType)) {
                            findDisableMerCodes(childMer, merTypeMap, childMerListMap, resultList);
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public DodopalResponse<String> register(Merchant merchant, AtomicReference<String> randomPWD) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        String merClassify = merchant.getMerClassify();
        String merCode = generateMerCode(merClassify);
        String merType = merchant.getMerType();
        String createUser = merchant.getCreateUser();
        String merLinkUser = merchant.getMerLinkUser();
        // 审核状态
        String merState = null;

        // 3、保存用户信息
        MerchantUser merUser = merchant.getMerchantUser();
        if (merUser != null) {
        	// 注册来源
            String merUserSource = merUser.getMerUserSource();

            // 如果是OSS开户，直接审核通过
            if (SourceEnum.OSS.getCode().equals(merUserSource)) {
            	merState = MerStateEnum.THROUGH.getCode();
            } else if (SourceEnum.PORTAL.getCode().equals(merUserSource) && MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
                // 门户注册的连锁直营网点直接审核通过
            	merState = MerStateEnum.THROUGH.getCode();
            } else {
            	merState = MerStateEnum.NO_AUDIT.getCode();
            }

            merUser.setMerCode(merCode);
            merUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
            merUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());
            // 用户手机号同商户手机号
            merUser.setMerUserMobile(merchant.getMerLinkUserMobile());
            merUser.setCreateUser(createUser);
            merUser.setMerUserNickName(merLinkUser);
            // 管理员只能为启用
            merUser.setActivate(ActivateEnum.ENABLE.getCode());
            // 用户审核状态
            merUser.setMerUserState(merState);
            // 注册来源为OSS时设置随机密码
            if (SourceEnum.OSS.getCode().equals(merUser.getMerUserSource())) {
                String randomPwd = DDPStringUtil.getRandomStr(UsersConstants.MER_USER_PWD_LENGTH);
                // merUser.setMerUserRemark("初始随机密码：" + randomPwd);
                // MD5加密两次
                merUser.setMerUserPWD(MD5.MD5Encode(MD5.MD5Encode(randomPwd)));
                // 密码以短信形式发送给用户
                randomPWD.set(randomPwd);
            }
            merchantUserService.insertMerchantUser(merUser, MerClassifyEnum.OFFICIAL.getCode().equals(merClassify) ? UserClassifyEnum.MERCHANT : UserClassifyEnum.MERCHANT_TEST);
            // 如果为空，则是门户注册过来的
            if(StringUtils.isBlank(createUser)) {
                createUser = merUser.getId();
            }
        } else {
        	throw new DDPException(ResponseCode.USERS_MER_USER_NULL);
        }

        // 1、保存商户信息
        merchant.setMerCode(merCode);
        merchant.setCreateUser(createUser);
        merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());
        // 审核状态
        merchant.setMerState(merState);
        
        mapper.addMerchant(merchant);

        // 2、保存商户补充信息
        if (merchant.getMerDdpInfo() != null) {
        	merchant.getMerDdpInfo().setMerCode(merCode);
        	merchant.getMerDdpInfo().setCreateUser(createUser);
            merDdpInfoService.addMerDdpInfo(merchant.getMerDdpInfo());
        }

        // 4、保存费率信息
        // 连锁直营网点不保存费率，使用时直接取上级费率
        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            List<MerBusRate> merBusRateList = merchant.getMerBusRateList();
            if (!CollectionUtils.isEmpty(merBusRateList)) {
                for (MerBusRate merBusRateTemp : merBusRateList) {
                    merBusRateTemp.setMerCode(merCode);
                    merBusRateTemp.setCreateUser(createUser);
                }
                merBusRateService.addMerBusRateBatch(merBusRateList);
            }
        }

        // 5、保存自定义功能信息
        String merProperty = merchant.getMerProperty();
        if (MerPropertyEnum.CUSTOM.getCode().equals(merProperty)) {
            List<MerFunctionInfo> merFunctionInfoList = merchant.getMerDefineFunList();
            saveCustomFunInfo(merFunctionInfoList, merCode, merType, createUser);
        }

        // 6、保存秘钥信息、保存业务信息
		MerKeyType merKeyType = merchant.getMerKeyType();
		if (merKeyType != null) {
			merKeyType.setMerCode(merCode);
			merKeyType.setCreateUser(createUser);
			// 秘钥类型
			merKeyType.setMerKeyType(UsersConstants.MER_KEY_TYPE_MD5);
			merKeyTypeService.saveKeyType(merKeyType);
		}

        // 商户业务信息
        // 连锁直营网点不保存业务信息，使用时直接取上级业务信息
        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
        	List<MerRateSupplement> spmtList = merchant.getMerRateSpmtList();
            if(!CollectionUtils.isEmpty(spmtList)) {
            	for(MerRateSupplement temp :spmtList) {
            		temp.setMerCode(merCode);
            	}
            	merRateSupplementService.batchAddMerRateSpmts(spmtList, null);
            }
        }

        // 自动分配额度
        if(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType)) {
        	MerAutoAmt merAutoAmt = merchant.getMerAutoAmt();
			if (merAutoAmt != null) {
				merAutoAmt.setMerCode(merCode);
				merAutoAmtService.addMerAutoAmt(merAutoAmt);
			}
        }

		// VC端注册POS add by lifeng 2016-5-12
		Pos pos = merchant.getPos();
		if (pos != null) {
			OperUserDTO operUser = new OperUserDTO();
			operUser.setId(createUser);
			posService.updatePosBundling(merchant, PosOperTypeEnum.OPER_BUNDLING, new String[] { pos.getCode() }, operUser, SourceEnum.getSourceByCode(merUser.getMerUserSource()), "");
		}

        // 7、最后生成账户，如果失败可以回滚事物
        if (SourceEnum.OSS.getCode().equals(merUser.getMerUserSource())) {
            // 调用账户接口创建账户
    		DodopalResponse<String> accountManagementResponse = accountManagementDelegate.createAccount(MerUserTypeEnum.MERCHANT.getCode(), merCode, merchant.getFundType(), merchant.getMerType(), createUser);
    		if(!ResponseCode.SUCCESS.equals(accountManagementResponse.getCode())) {
    			if(logger.isInfoEnabled()) {
    				logger.info("register---->调用账户系统创建账户接口失败,错误码:" + accountManagementResponse.getCode());
    			}
    			throw new DDPException(ResponseCode.USERS_CREATE_ACCOUNT_FAIL);
    		}
        } else if (SourceEnum.PORTAL.getCode().equals(merUser.getMerUserSource()) && MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            // 调用账户接口创建账户(需要先查询上级商户的账户资金类型)
			DodopalResponse<AccountFundDTO> accountQueryResponse = accountQueryDelegate.findAccountBalance(MerUserTypeEnum.MERCHANT.getCode(), merchant.getMerParentCode());
			if (!ResponseCode.SUCCESS.equals(accountQueryResponse.getCode())) {
				if(logger.isInfoEnabled()) {
					logger.info("register---->调用账户系统查询接口失败,错误码:" + accountQueryResponse.getCode());
    			}
				throw new DDPException(accountQueryResponse.getCode());
			}
			AccountFundDTO account = accountQueryResponse.getResponseEntity();
			String fundType = account.getFundType();
			if (StringUtils.isBlank(fundType)) {
				if(logger.isInfoEnabled()) {
					logger.info("register---->上级商户账户资金类别为空");
    			}
				throw new DDPException(ResponseCode.ACC_BALANCE_TYPE_ERROR);
			}
			DodopalResponse<String> accountManagementResponse = accountManagementDelegate.createAccount(MerUserTypeEnum.MERCHANT.getCode(), merCode, fundType, merchant.getMerType(), createUser);
			if (!ResponseCode.SUCCESS.equals(accountManagementResponse.getCode())) {
				if(logger.isInfoEnabled()) {
					logger.info("register---->调用账户系统创建账户接口失败,错误码:" + accountManagementResponse.getCode());
    			}
				throw new DDPException(ResponseCode.USERS_CREATE_ACCOUNT_FAIL);
			}
        }

        response.setResponseEntity(merCode);
        return response;
    }

    @Transactional
	private void saveCustomFunInfo(List<MerFunctionInfo> merFunctionInfoList, String merCode, String merType, String createUser) {
		if (!CollectionUtils.isEmpty(merFunctionInfoList)) {
			List<String> merFunCodes = new ArrayList<String>();
			List<String> hasChildCodes = new ArrayList<String>();
			for (MerFunctionInfo merFunctionInfoTemp : merFunctionInfoList) {
				String merFunCodeTemp = merFunctionInfoTemp.getMerFunCode();
				if (StringUtils.isNotBlank(merFunCodeTemp)) {
					merFunCodes.add(merFunCodeTemp);
				}
			}

			// 查出对应的功能
			List<MerFunctionInfo> findMerFunInfoList = merFunctionInfoService.batchFindMerFunInfoByFunCode(merFunCodes);
			if (!CollectionUtils.isEmpty(findMerFunInfoList)) {
				// 需要保存的功能合集
				List<MerFunctionInfo> resultFunList = new ArrayList<MerFunctionInfo>();
				for (MerFunctionInfo merFunctionInfoTemp : findMerFunInfoList) {
					String merFunCodeTemp = merFunctionInfoTemp.getMerFunCode();
					int level = merFunctionInfoTemp.getLevels();
					if (StringUtils.isNotBlank(merFunCodeTemp)) {
						if (level == 0) {
							resultFunList.add(merFunctionInfoTemp);
						} else if (level == 1) { // 如果是二级菜单，要取出子集权限
							hasChildCodes.add(merFunCodeTemp);
						}
					}
				}

				// 取出二级菜单的子集
				if (!CollectionUtils.isEmpty(hasChildCodes)) {
					resultFunList.addAll(merFunctionInfoService.batchFindChildMerFunInfo(hasChildCodes));
				}
				// 去除禁用功能
				List<MerFunctionInfo> disableFunList = merFunctionInfoService.findDisableFunByMerType(merType);
				if (disableFunList.size() > 0) {
					Map<String, Object> disableFunMap = new HashMap<String, Object>();
					for (MerFunctionInfo temp : disableFunList) {
						disableFunMap.put(temp.getMerFunCode(), null);
					}
					if (!CollectionUtils.isEmpty(resultFunList)) {
						List<MerFunctionInfo> resultTempFunList = new ArrayList<MerFunctionInfo>();
						resultTempFunList.addAll(resultFunList);
						resultFunList.clear();

						for (MerFunctionInfo temp : resultTempFunList) {
							if (!disableFunMap.containsKey(temp.getMerFunCode())) {
								resultFunList.add(temp);
							}
						}
					}
				}

				if (!CollectionUtils.isEmpty(resultFunList)) {
					List<MerDefineFun> merDefineFunList = new ArrayList<MerDefineFun>(findMerFunInfoList.size());
					for (MerFunctionInfo merFunctionInfoTemp : resultFunList) {
						MerDefineFun merDefineFunTemp = new MerDefineFun();
						merDefineFunTemp.setMerCode(merCode);
						merDefineFunTemp.setCreateUser(createUser);
						merDefineFunTemp.setMerFunCode(merFunctionInfoTemp.getMerFunCode());
						merDefineFunTemp.setMerFunName(merFunctionInfoTemp.getMerFunName());
						merDefineFunList.add(merDefineFunTemp);
					}
					merDefineFunService.batchAddMerDefineFunList(merDefineFunList);
				}
			}
		}
	}

    @Override
    @Transactional
    public String updateMerchant(Merchant merchant) {
        String merCode = merchant.getMerCode();
        String updateUser = merchant.getUpdateUser();
        String merType = merchant.getMerType();
        String merLinkUser = merchant.getMerLinkUser();
        Merchant oldMer = mapper.findMerchantByMerCode(merCode);

        // 1、更新商户信息
        mapper.updateMerchant(merchant);

        // 2、更新商户补充信息
		MerDdpInfo merDdpInfo = merchant.getMerDdpInfo();
		merDdpInfoService.deleteMerDdpInfo(merCode);
		if (merDdpInfo != null) {
			merDdpInfo.setMerCode(merCode);
			merDdpInfoService.addMerDdpInfo(merDdpInfo);
		}

        // 3、更新用户信息
        MerchantUser merUser = merchant.getMerchantUser();
        merUser.setMerCode(merCode);
        merUser.setUpdateUser(updateUser);
        merUser.setMerUserNickName(merLinkUser);
        // 管理员只能为启用
        merUser.setActivate(ActivateEnum.ENABLE.getCode());
        int num = merchantUserService.updateMerchantUser(merUser);
		if (num != 1) {
			throw new DDPException(ResponseCode.ID_NULL);
		}

        // 4、保存费率信息：开通的业务不能超过上级的业务范围，设定的费率不能超过上级的费率
        // 连锁直营网点不保存费率，使用时直接取上级费率
        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            List<MerBusRate> merBusRateList = merchant.getMerBusRateList();
            if (!CollectionUtils.isEmpty(merBusRateList)) {
                for(MerBusRate merBusRateTemp : merBusRateList) {
                    merBusRateTemp.setMerCode(merCode);
                    merBusRateTemp.setCreateUser(updateUser);
                }
            }
            merBusRateService.batchUpdateMerBusRate(merBusRateList, merCode);
        }

        // 5、更新自定义功能信息
        String merProperty = merchant.getMerProperty();
        if (MerPropertyEnum.CUSTOM.getCode().equals(merProperty)) {
            // 先删除库中的记录
            merDefineFunService.deleteMerDefineFunByMerCode(merCode);
            List<MerFunctionInfo> merFunctionInfoList = merchant.getMerDefineFunList();
            saveCustomFunInfo(merFunctionInfoList, merCode, merType, updateUser);
        }

        // 6、更新秘钥信息
		MerKeyType merKeyType = merchant.getMerKeyType();
		MerKeyType merKeyTypeOld = merKeyTypeService.findMerKeyTypeByMerCode(merCode);
		if (merKeyType == null) {
			if (merKeyTypeOld != null) {
				merKeyTypeOld.setUpdateUser(updateUser);
				merKeyTypeOld.setActivate(ActivateEnum.DISABLE.getCode());
				merKeyTypeService.updateMerKeyType(merKeyTypeOld);
			}
		} else {
			merKeyType.setMerCode(merCode);
			merKeyType.setMerKeyType(UsersConstants.MER_KEY_TYPE_MD5);
			if (merKeyTypeOld == null) {
				merKeyTypeService.saveKeyType(merKeyType);
			} else {
				merKeyTypeOld.setMerMD5PayPwd(merKeyType.getMerMD5PayPwd());
				merKeyTypeOld.setMerMD5BackPayPWD(merKeyType.getMerMD5BackPayPWD());
				merKeyTypeOld.setUpdateUser(updateUser);
				merKeyTypeService.updateMerKeyType(merKeyTypeOld);
			}
		}

        // 7、商户业务信息
        // 连锁直营网点不保存业务信息，使用时直接取上级业务信息
        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
        	List<MerRateSupplement> spmtList = merchant.getMerRateSpmtList();
            if(!CollectionUtils.isEmpty(spmtList)) {
            	for(MerRateSupplement temp :spmtList) {
            		temp.setMerCode(merCode);
            	}
            }
            merRateSupplementService.batchAddMerRateSpmts(spmtList, merCode);
        }

        // 自动分配额度
		if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType)) {
			merAutoAmtService.deleteMerAutoAmtByMerCode(merCode);
			MerAutoAmt merAutoAmt = merchant.getMerAutoAmt();
			if (merAutoAmt != null) {
				merAutoAmt.setMerCode(merCode);
				merAutoAmtService.addMerAutoAmt(merAutoAmt);
			}
		}

        // 8、最后生成账户，如果失败可以回滚事物
        if(!MerStateEnum.THROUGH.getCode().equals(oldMer.getMerState()) && MerStateEnum.THROUGH.getCode().equals(merchant.getMerState())) {
        	// 调用账户接口创建账户
    		DodopalResponse<String> accountResponse = accountManagementDelegate.createAccount(MerUserTypeEnum.MERCHANT.getCode(), merCode, merchant.getFundType(), merchant.getMerType(), updateUser);
    		if(!ResponseCode.SUCCESS.equals(accountResponse.getCode())) {
    			if(logger.isInfoEnabled()) {
					logger.info("updateMerchant---->调用账户系统创建账户接口失败,错误码:" + accountResponse.getCode());
    			}
				throw new DDPException(ResponseCode.USERS_CREATE_ACCOUNT_FAIL);
    		}
        }

        return ResponseCode.SUCCESS;
    }

    @Override
    @Transactional
    public String updateRejectMerchant(Merchant merchant) {
        String merCode = merchant.getMerCode();
        String updateUser = merchant.getUpdateUser();
        String merType = merchant.getMerType();
        // 1、更新商户信息
        mapper.updateMerchant(merchant);

        // 3、更新用户信息
        MerchantUser merUser = merchant.getMerchantUser();
        merUser.setMerCode(merCode);
        merUser.setUpdateUser(updateUser);
        merchantUserService.updateRejectMerchantUserByUserCode(merUser);

        // 4、保存费率信息：开通的业务不能超过上级的业务范围，设定的费率不能超过上级的费率
        // 连锁直营网点不保存费率，使用时直接取上级费率
        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            List<MerBusRate> merBusRateList = merchant.getMerBusRateList();
            if (!CollectionUtils.isEmpty(merBusRateList)) {
                for(MerBusRate merBusRateTemp : merBusRateList) {
                    merBusRateTemp.setMerCode(merCode);
                    merBusRateTemp.setCreateUser(updateUser);
                }
            }
            merBusRateService.batchUpdateMerBusRate(merBusRateList, merCode);
        }

        // 7、商户业务信息
        // 连锁直营网点不保存业务信息，使用时直接取上级业务信息
        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            List<MerRateSupplement> spmtList = merchant.getMerRateSpmtList();
            if(!CollectionUtils.isEmpty(spmtList)) {
                for(MerRateSupplement temp :spmtList) {
                    temp.setMerCode(merCode);
                }
            }
            merRateSupplementService.batchAddMerRateSpmts(spmtList, merCode);
        }
        
        //补充信息表
        MerDdpInfo merDdpInfo = merDdpInfoService.findMerDdpInfoByMerCode(merCode);
        MerDdpInfo merDdpInfo1 = merchant.getMerDdpInfo(); 
        if(merDdpInfo!=null){
            if(merDdpInfo1!=null){
                merDdpInfo.setIsAutoDistribute(merDdpInfo1.getIsAutoDistribute());
                merDdpInfo.setLimitSource(merDdpInfo1.getLimitSource());
                merDdpInfoService.updateMerDdpInfo(merDdpInfo);
            }
          
        }else{
            if(merDdpInfo1!=null){
                merDdpInfoService.updateMerDdpInfo(merDdpInfo1);
            }
            
        }
        return ResponseCode.SUCCESS;
    }

    @Override
    @Transactional(readOnly = true)
    public String generateMerCode(String merClassify) {
        StringBuffer sb = new StringBuffer();
        // 商户分类(1位):0.正式商户,1.测试商户
        sb.append(merClassify);
        // 4位随机数
        int number = new Random().nextInt(9999) + 1;
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(4);
        formatter.setGroupingUsed(false);
        String randomNum = formatter.format(number);
        sb.append(randomNum);
        // 数据库10位sequence
        String seq = mapper.getMerCodeSeq();
        sb.append(seq);
        return sb.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantUser> findMerOperators(MerchantUser merUser) {
        if(StringUtils.isBlank(merUser.getMerCode())) {
            return null;
        }
        merUser.setMerUserFlag(MerUserFlgEnum.COMMON.getCode());
        return merchantUserService.findMerchantUser(merUser);
    }

    @Override
    @Transactional(readOnly = true)
    public MerchantUser findMerOperatorByUserCode(String merCode, String userCode) {
        if (StringUtils.isBlank(merCode)) {
            return null;
        }
        if (StringUtils.isBlank(userCode)) {
            return null;
        }
        return merchantUserService.findMerchantUserByUserCode(userCode);
    }

    @Override
    @Transactional
    public int configMerOperatorRole(MerchantUser merUser) {
        String merCode = merUser.getMerCode();
        String userCode = merUser.getUserCode();
        if (StringUtils.isBlank(merCode)) {
            return 0;
        }
        if (StringUtils.isBlank(userCode)) {
            return 0;
        }

        int num = 0;
        num = merUserRoleService.delMerUserRoleByUserCode(userCode);
        List<MerRole> merUserRoleList = merUser.getMerUserRoleList();
        if (!CollectionUtils.isEmpty(merUserRoleList)) {
            List<MerUserRole> merUserRoleAddList = new ArrayList<MerUserRole>();
            for (MerRole merRoleTemp : merUserRoleList) {
                MerUserRole merUserRoleTemp = new MerUserRole();
                merUserRoleTemp.setCreateUser(merUser.getUpdateUser());
                merUserRoleTemp.setUserCode(userCode);
                merUserRoleTemp.setMerRoleCode(merRoleTemp.getMerRoleCode());
                merUserRoleTemp.setActivate(ActivateEnum.ENABLE.getCode());
                merUserRoleAddList.add(merUserRoleTemp);
            }
            num = merUserRoleService.batchAddMerUserRole(merUserRoleAddList);
        }

        return num;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExist(Merchant merchant) {
        return mapper.checkExist(merchant);
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<Merchant> findMerchantByPage(MerchantQuery merchantQuery) {
        String merParentName = merchantQuery.getMerParentName();
        String merState = merchantQuery.getMerState();
        if (StringUtils.isNotBlank(merParentName)) {
            Merchant findParent = new Merchant();
            findParent.setMerParentName(merParentName);
            List<Merchant> parentResultList = mapper.findMerchant(findParent);
            if (CollectionUtils.isEmpty(parentResultList)) {
                return null;
            }
            merchantQuery.setMerParentCode(parentResultList.get(0).getMerCode());
        }
        // 动态构建排序
        if(MerStateEnum.NO_AUDIT.getCode().equals(merState)) {
            merchantQuery.setOrderBy(PAGE_NO_AUDIT_ORDER);
        } else {
            merchantQuery.setOrderBy(PAGE_OTHER_ORDER);
        }
        
        List<Merchant> result = null;
        //业务类型  01 一卡通充值;   03 一卡通消费
        if(StringUtils.isNotBlank(merchantQuery.getBussQuery())){
            result = mapper.findMerchantBusByPage(merchantQuery);;
        }else{
         //不选业务类型的时候 
            result = mapper.findMerchantByPage(merchantQuery);
        }
        
        
        if (!CollectionUtils.isEmpty(result)) {
            List<String> merParentCodeList = new ArrayList<String>();

            // 省份、城市名称
            for (Merchant merTemp : result) {
                String merArea = merTemp.getMerArea();
                String merPro = merTemp.getMerPro();
                String merCity = merTemp.getMerCity();
                if (StringUtils.isNotBlank(merArea)) {
                    Area area = areaService.findCityByCityCode(merArea);
                    if (area != null) {
                        merTemp.setMerAreaName(area.getCityName());
                    }
                }
                if (StringUtils.isNotBlank(merPro)) {
                    Area provice = areaService.findCityByCityCode(merPro);
                    if (provice != null) {
                        merTemp.setMerProName(provice.getCityName());
                    }
                }
                if (StringUtils.isNotBlank(merCity)) {
                    Area city = areaService.findCityByCityCode(merCity);
                    if (city != null) {
                        merTemp.setMerCityName(city.getCityName());
                    }
                }

                String merParentCodeTemp = merTemp.getMerParentCode();
                if(StringUtils.isNotBlank(merParentCodeTemp)) {
                    merParentCodeList.add(merParentCodeTemp);
                }
            }

            // 上级商户名称
            if (!CollectionUtils.isEmpty(merParentCodeList)) {
                List<Merchant> parentMerchant = mapper.batchFindMerchantByMerCode(merParentCodeList);
                Map<String, Merchant> parentMerMap = new HashMap<String, Merchant>();
                if (!CollectionUtils.isEmpty(parentMerchant)) {
                    for (Merchant merParent : parentMerchant) {
                        parentMerMap.put(merParent.getMerCode(), merParent);
                    }

                    for (Merchant merTemp : result) {
                        String merParentCodeTemp = merTemp.getMerParentCode();
                        if (StringUtils.isNotBlank(merParentCodeTemp)) {
                            if (parentMerMap.containsKey(merParentCodeTemp)) {
                                merTemp.setMerParentName(parentMerMap.get(merParentCodeTemp).getMerName());
                            }
                        }
                    }
                }
            }
            
        }
        DodopalDataPage<Merchant> pages = new DodopalDataPage<Merchant>(merchantQuery.getPage(), result);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
	public int findMerchantByPageCount(MerchantQuery merchantQuery) {
		return mapper.findMerchantByPageCount(merchantQuery);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Merchant> findMerchantByPageList(MerchantQuery merchantQuery) {
		String merParentName = merchantQuery.getMerParentName();
        String merState = merchantQuery.getMerState();
        if (StringUtils.isNotBlank(merParentName)) {
            Merchant findParent = new Merchant();
            findParent.setMerParentName(merParentName);
            List<Merchant> parentResultList = mapper.findMerchant(findParent);
            if (CollectionUtils.isEmpty(parentResultList)) {
                return null;
            }
            merchantQuery.setMerParentCode(parentResultList.get(0).getMerCode());
        }
        // 动态构建排序
        if(MerStateEnum.NO_AUDIT.getCode().equals(merState)) {
            merchantQuery.setOrderBy(PAGE_NO_AUDIT_ORDER);
        } else {
            merchantQuery.setOrderBy(PAGE_OTHER_ORDER);
        }

        List<Merchant> result = null;
        //业务类型  01 一卡通充值;   03 一卡通消费
        if(StringUtils.isNotBlank(merchantQuery.getBussQuery())){
            result = mapper.findMerchantBusByPageList(merchantQuery);
        }else{
         //不选业务类型的时候 
            result = mapper.findMerchantByPageList(merchantQuery);
        }

        if (!CollectionUtils.isEmpty(result)) {
            List<String> merParentCodeList = new ArrayList<String>();
            List<String> merCodeList = new ArrayList<String>();

            // 省份、城市名称
            for (Merchant merTemp : result) {
                String merArea = merTemp.getMerArea();
                String merPro = merTemp.getMerPro();
                String merCity = merTemp.getMerCity();
                if (StringUtils.isNotBlank(merArea)) {
                    Area area = areaService.findCityByCityCode(merArea);
                    if (area != null) {
                        merTemp.setMerAreaName(area.getCityName());
                    }
                }
                if (StringUtils.isNotBlank(merPro)) {
                    Area provice = areaService.findCityByCityCode(merPro);
                    if (provice != null) {
                        merTemp.setMerProName(provice.getCityName());
                    }
                }
                if (StringUtils.isNotBlank(merCity)) {
                    Area city = areaService.findCityByCityCode(merCity);
                    if (city != null) {
                        merTemp.setMerCityName(city.getCityName());
                    }
                }

                String merParentCodeTemp = merTemp.getMerParentCode();
                if(StringUtils.isNotBlank(merParentCodeTemp)) {
                    merParentCodeList.add(merParentCodeTemp);
                }

                merCodeList.add(merTemp.getMerCode());
            }

            // 上级商户名称
            if (!CollectionUtils.isEmpty(merParentCodeList)) {
                List<Merchant> parentMerchant = mapper.batchFindMerchantByMerCode(merParentCodeList);
                if (!CollectionUtils.isEmpty(parentMerchant)) {
                	Map<String, Merchant> parentMerMap = new HashMap<String, Merchant>();
                    for (Merchant merParent : parentMerchant) {
                        parentMerMap.put(merParent.getMerCode(), merParent);
                    }

                    for (Merchant merTemp : result) {
                        String merParentCodeTemp = merTemp.getMerParentCode();
                        if (StringUtils.isNotBlank(merParentCodeTemp)) {
                            if (parentMerMap.containsKey(merParentCodeTemp)) {
                                merTemp.setMerParentName(parentMerMap.get(merParentCodeTemp).getMerName());
                            }
                        }
                    }
                }
            }

            // 补充信息
			if (!CollectionUtils.isEmpty(merCodeList)) {
				List<MerDdpInfo> ddpInfoList = merDdpInfoService.findMerDdpInfoByMerCodes(merCodeList);
				if (!CollectionUtils.isEmpty(ddpInfoList)) {
					Map<String, MerDdpInfo> ddpInfoMap = new HashMap<String, MerDdpInfo>();
					for (MerDdpInfo ddpInfo : ddpInfoList) {
						ddpInfoMap.put(ddpInfo.getMerCode(), ddpInfo);
					}

					for (Merchant merTemp : result) {
						String merCodeTemp = merTemp.getMerCode();
						if (StringUtils.isNotBlank(merCodeTemp)) {
							if (ddpInfoMap.containsKey(merCodeTemp)) {
								merTemp.setMerDdpInfo(ddpInfoMap.get(merCodeTemp));
							}
						}
					}
				}
			}

        }
        return result;
	}

    @Override
    @Transactional
    public int batchDelMerchantByMerCodes(List<String> merCodes) {
        if (CollectionUtils.isEmpty(merCodes)) {
            return 0;
        }

        // 删除商户补充信息
        merDdpInfoService.batchDelMerDdpInfoByMerCodes(merCodes);

        // 删除商户业务费率信息
        merBusRateService.batchDelMerBusRateByMerCodes(merCodes);

        // 删除商户自定义功能
        merDefineFunService.batchDelDefineFunByMerCodes(merCodes);

        // 删除商户秘钥信息
        merKeyTypeService.batchDelMerKeyTypeByMerCodes(merCodes);

        // 删除商户用户
        merchantUserService.batchDelMerUserByMerCodes(merCodes);

        // 删除商户业务信息
        merRateSupplementService.batchDelMerRateSpmtsByMerCodes(merCodes);

        // 删除自动分配额度信息
        merAutoAmtService.batchDelMerAutoAmtByMerCodes(merCodes);

        // 删除商户信息
        return mapper.batchDelMerchantByMerCodes(merCodes);
    }

    @Override
    @Transactional
    public int updateMerchantForPortal(Merchant merchant) {
        int num = 0;
        String merCode = merchant.getMerCode();
        String updateUser = merchant.getUpdateUser();
        // 更新商户信息
        num = mapper.updateMerchantForPortal(merchant);
        // 更新商户管理员信息
        MerchantUser merUser = merchant.getMerchantUser();
        merUser.setMerCode(merCode);
        merUser.setUpdateUser(updateUser);
        merchantUserService.updateMerchantUser(merUser);
        return num;
    }

    @Override
    @Transactional
    public String updateThroughMerchant(Merchant merchant) {
        String merCode = merchant.getMerCode();
        String updateUser = merchant.getUpdateUser();
        // 1、更新商户信息
        mapper.updateThroughMerchant(merchant);

        // 2、更新用户信息
        MerchantUser merUser = merchant.getMerchantUser();
        merUser.setMerCode(merCode);
        merUser.setUpdateUser(updateUser);
        merUser.setMerUserMobile(null);
        merUser.setActivate(null);
        merUser.setMerUserNickName(null);
        merchantUserService.updateMerchantUser(merUser);

        //补充信息表
        MerDdpInfo merDdpInfo = merDdpInfoService.findMerDdpInfoByMerCode(merCode);
        MerDdpInfo merDdpInfo1 = merchant.getMerDdpInfo(); 
        if(merDdpInfo!=null){
            if(merDdpInfo1!=null){
                merDdpInfo.setIsAutoDistribute(merDdpInfo1.getIsAutoDistribute());
                merDdpInfo.setLimitSource(merDdpInfo1.getLimitSource());
                merDdpInfoService.updateMerDdpInfo(merDdpInfo);
            }
          
        }else{
            if(merDdpInfo1!=null){
                merDdpInfoService.updateMerDdpInfo(merDdpInfo1);
            }
            
        }
        
        // 自动分配额度
		merAutoAmtService.deleteMerAutoAmtByMerCode(merCode);
		MerAutoAmt merAutoAmt = merchant.getMerAutoAmt();
		if (merAutoAmt != null) {
			merAutoAmt.setMerCode(merCode);
			merAutoAmtService.addMerAutoAmt(merAutoAmt);
		}

        return ResponseCode.SUCCESS;
    }

    @Override
    @Transactional
    public String rejectMerchantReg(Merchant merchant) {
    	String merCode = merchant.getMerCode();
        // 1、更新商户信息
        mapper.rejectMerchantReg(merchant);
        // 2、更新商户用户信息
        merchantUserService.updateRejectMerchantUserByUserName(merchant.getMerchantUser());
		// 自动分配额度
		merAutoAmtService.deleteMerAutoAmtByMerCode(merCode);
		MerAutoAmt merAutoAmt = merchant.getMerAutoAmt();
		if (merAutoAmt != null) {
			merAutoAmt.setMerCode(merCode);
			merAutoAmtService.addMerAutoAmt(merAutoAmt);
		}

        return ResponseCode.SUCCESS;
    }

    @Override
    @Transactional(readOnly = true)
	public List<MerBusRate> findMerBusRateByMerCode(String merCode) {
		String findMerCode = merCode;
		Merchant mer = mapper.findMerchantByMerCode(merCode);
		// 连锁直营取上级费率
		if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(mer.getMerType())) {
			findMerCode = mer.getMerParentCode();
		}
		return merBusRateService.findMerBusRateByMerCode(findMerCode);
	}

    @Transactional(readOnly = true)
    public List<DirectMerChant> findMerchantByParentCode(String merParentCode, String merName) {
        return directMerChantMapper.findMerchantByParentCode(merParentCode, merName);
    }

    @Transactional(readOnly = true)
    public List<DirectMerChant> findMerchantByParentCode(String merParentCode, String merName,String businessType) {
        return directMerChantMapper.findMerchantByParentCodeType(merParentCode, merName,businessType);
    }

	@Override
	@Transactional(readOnly = true)
	public String findPayWayExtId(String merCode) {
		return mapper.findPayWayExtId(merCode);
	}
	@Override
    @Transactional(readOnly = true)
    public MerAutoAmt findMerAutoAmtInfo(String merCode) {
        return mapper.findMerAutoAmtInfo(merCode);
    }

    @Override
    @Transactional(readOnly = true)
    public String findManagerIdByMerCode(String merCode) {
        return mapper.findManagerIdByMerCode(merCode);
    }

	@Override
	public List<DirectMerChant> findMerchantByParentCodeType(String merParentCode, String merName,
			String businessType) {
		return directMerChantMapper.findMerchantByParentCodeType(merParentCode, merName, businessType);
	}
	@Override
	public List<DirectMerChant> findDirectTransferFilter(String merParentCode, String merName,
			String businessType) {
		return directMerChantMapper.findDirectTransferFilter(merParentCode, merName, businessType);
	}

	@Override
	@Transactional(readOnly = true)
	public String getYktCodeByMerCode(String merCode) {
		return mapper.getYktCodeByMerCode(merCode);
	}

	@Override
	@Transactional
	public int updateMerchantProvider(Merchant merchant) {
		merchantUserService.updateProviderAdminUser(merchant.getMerchantUser());
		return mapper.updateMerchantProvider(merchant);
	}

    @Override
    @Transactional(readOnly = true)
    public MerchantExtend findMerchantExtend(String merCode) {
        return mapper.findMerchantExtend(merCode);
    }

}

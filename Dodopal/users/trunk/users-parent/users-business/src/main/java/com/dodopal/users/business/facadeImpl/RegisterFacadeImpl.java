package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.CommonUserDTO;
import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.facade.MerUserCardBDFacade;
import com.dodopal.api.users.facade.RegisterFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.enums.PosStatusEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.MD5;
import com.dodopal.users.business.model.MerUserCardBD;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.Pos;
import com.dodopal.users.business.service.MerUserCardBDService;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.business.service.PosService;
import com.dodopal.users.business.service.RegisterService;
import com.dodopal.users.business.service.SendService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service("registerFacade")
public class RegisterFacadeImpl implements RegisterFacade {
    private final static Logger logger = LoggerFactory.getLogger(RegisterFacadeImpl.class);
    @Autowired
    private RegisterService registerService;
    @Autowired
    private MerchantUserService merchantUserService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SendService sendService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private PosService posService;
    @Autowired
    private MerUserCardBDFacade merUserCardBDFacade;
    @Autowired
    private MerUserCardBDService bdService;
    


    @Override
    public DodopalResponse<Boolean> checkMobileExist(String mobile) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            if (StringUtils.isBlank(mobile)) {
                response.setCode(ResponseCode.USERS_MOB_TEL_NULL);
                return response;
            }
            if (!DDPStringUtil.isMobile(mobile)) {
                response.setCode(ResponseCode.USERS_MOB_TEL_ERR);
                return response;
            }

            boolean bool = registerService.checkMobileExist(mobile, null);
            response.setResponseEntity(bool);
            if (bool) {
                response.setCode(ResponseCode.USERS_MOB_EXIST);
            } else {
            	response.setCode(ResponseCode.SUCCESS);
            }
        } catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
        	// 如果发生异常，提示手机号已经注册
            response.setCode(ResponseCode.USERS_MOB_EXIST);
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkMobileExist(String mobile, String merCode) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            if (StringUtils.isBlank(mobile)) {
                response.setCode(ResponseCode.USERS_MOB_TEL_NULL);
                return response;
            }
            if (!DDPStringUtil.isMobile(mobile)) {
                response.setCode(ResponseCode.USERS_MOB_TEL_ERR);
                return response;
            }
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                return response;
            }

            boolean bool = registerService.checkMobileExist(mobile, merCode);
            response.setResponseEntity(bool);
            if (bool) {
                response.setCode(ResponseCode.USERS_MOB_EXIST);
            } else {
            	response.setCode(ResponseCode.SUCCESS);
            }
        } catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
            response.setCode(ResponseCode.USERS_MOB_EXIST);
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkUserNameExist(String userName) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            if (StringUtils.isBlank(userName)) {
                response.setCode(ResponseCode.USERS_FIND_USER_NAME_NULL);
                return response;
            }

            boolean bool = registerService.checkUserNameExist(userName, null);
            response.setResponseEntity(bool);
            if (bool) {
                response.setCode(ResponseCode.USERS_MER_USER_NAME_EXIST);
            } else {
            	response.setCode(ResponseCode.SUCCESS);
            }
        } catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkUserNameExist(String userName, String merCode) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            if (StringUtils.isBlank(userName)) {
                response.setCode(ResponseCode.USERS_FIND_USER_NAME_NULL);
                return response;
            }
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                return response;
            }

            boolean bool = registerService.checkUserNameExist(userName, merCode);
            response.setResponseEntity(bool);
            if (bool) {
                response.setCode(ResponseCode.USERS_MER_USER_NAME_EXIST);
            } else {
            	response.setCode(ResponseCode.SUCCESS);
            }
        } catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            if (StringUtils.isBlank(merName)) {
                response.setCode(ResponseCode.USERS_MER_NAME_NULL);
                return response;
            }

            boolean bool = registerService.checkMerchantNameExist(merName, null);
            response.setResponseEntity(bool);
            if (bool) {
                response.setCode(ResponseCode.USERS_MER_NAME_EXIST);
            } else {
            	response.setCode(ResponseCode.SUCCESS);
            }
        } catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName, String merCode) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            if (StringUtils.isBlank(merName)) {
                response.setCode(ResponseCode.USERS_MER_NAME_NULL);
                return response;
            }
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                return response;
            }

            boolean bool = registerService.checkMerchantNameExist(merName, merCode);
            response.setResponseEntity(bool);
            if (bool) {
                response.setCode(ResponseCode.USERS_MER_NAME_EXIST);
            } else {
            	response.setCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
	public DodopalResponse<MerchantUserDTO> registerUser(MerchantUserDTO merUserDTO, String dypwd, String serialNumber) {
		DodopalResponse<MerchantUserDTO> response = new DodopalResponse<MerchantUserDTO>();
		try {
			String resCode = checkRegisterUser(merUserDTO, dypwd, serialNumber);
			if (ResponseCode.SUCCESS.equals(resCode)) {
				MerchantUser merUser = new MerchantUser();
				BeanUtils.copyProperties(merUserDTO, merUser);
				boolean bool = merchantUserService.registertUser(merUser);
				if (bool) {
					MerchantUser muMerName = new MerchantUser();
					muMerName.setMerUserName(merUserDTO.getMerUserName());
					MerchantUser muResult = merchantUserService.findMerchantUserExact(muMerName);
					if (muResult != null) {
						MerchantUserDTO muDTOResult = new MerchantUserDTO();
						BeanUtils.copyProperties(muResult, muDTOResult);
						response.setResponseEntity(muDTOResult);
					} else {
						response.setCode(ResponseCode.USERS_FIND_USER_ERR);
					}
				}
				response.setCode(ResponseCode.SUCCESS);
			} else {
				response.setCode(resCode);
			}
		} catch (HessianRuntimeException e) {
			logger.error(e.getMessage(), e);
			response.setCode(ResponseCode.HESSIAN_ERROR);
		} catch (DDPException e) {
			logger.error("registerUser---->门户注册异常code:" + e.getCode());
			response.setCode(e.getCode());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

    @Override
	public DodopalResponse<Boolean> registerUserForMobile(String mobile, String userName, String password, String source, String cityCode) {
    	DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		try {
			// 校验参数
			MerchantUser merUser = registerUserForMobileCheck(mobile, userName, password, source, cityCode);
			merchantUserService.registertUser(merUser);
			response.setCode(ResponseCode.SUCCESS);
		} catch (HessianRuntimeException e) {
			logger.error(e.getMessage(), e);
			response.setCode(ResponseCode.HESSIAN_ERROR);
		} catch (DDPException e) {
			logger.error("registerUser---->手机端注册异常code:" + e.getCode());
			response.setCode(e.getCode());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

    @Override
    public DodopalResponse<String> registerMerchant(MerchantDTO merchant, String dypwd, String serialNumber) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String resCode = checkRegisterMerchant(merchant, dypwd, serialNumber);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                Merchant mer = new Merchant();
                BeanUtils.copyProperties(merchant, mer);

                MerchantUser merUser = new MerchantUser();
                BeanUtils.copyProperties(merchant.getMerchantUserDTO(), merUser);
                mer.setMerchantUser(merUser);

                AtomicReference<String> randomPWD = new AtomicReference<String>();
                DodopalResponse<String> res = merchantService.register(mer, randomPWD);
                if (ResponseCode.SUCCESS.equals(res.getCode())) {
                    response.setResponseEntity(res.getResponseEntity());
                } else {
                    response.setCode(res.getCode());
                }
            } else {
                response.setCode(resCode);
            }
        } catch (HessianRuntimeException e) {
        	if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.HESSIAN_ERROR);
		} catch (DDPException e) {
			response.setCode(e.getCode());
		} catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
	public DodopalResponse<String> registerMerchantForVC(MerchantDTO merchant) {
    	DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String resCode = checkRegisterMerchantForVC(merchant);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                Merchant mer = new Merchant();
                BeanUtils.copyProperties(merchant, mer);

                MerchantUser merUser = new MerchantUser();
                BeanUtils.copyProperties(merchant.getMerchantUserDTO(), merUser);
                mer.setMerchantUser(merUser);

                Pos pos = new Pos();
                BeanUtils.copyProperties(merchant.getPosDTO(), pos);
                mer.setPos(pos);

                AtomicReference<String> randomPWD = new AtomicReference<String>();
                DodopalResponse<String> res = merchantService.register(mer, randomPWD);
                response.setCode(res.getCode());
                if (ResponseCode.SUCCESS.equals(res.getCode())) {
                    response.setResponseEntity(res.getResponseEntity());
                }
            } else {
                response.setCode(resCode);
            }
        } catch (HessianRuntimeException e) {
        	if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.HESSIAN_ERROR);
		} catch (DDPException e) {
			response.setCode(e.getCode());
		} catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
	}
    @Override
	public DodopalResponse<Map<String,String>> registerComUser(CommonUserDTO userDTO){
    	DodopalResponse<Map<String,String>>  response = new DodopalResponse<Map<String,String>> ();
		try {
			String merUserPWD = DDPStringUtil.getRandomStr(8);
            String md5MerUserPWD = MD5.MD5Encode(MD5.MD5Encode(merUserPWD));
            userDTO.setMerUserPWD(md5MerUserPWD);
			//校验参数
			MerchantUser merUser = registerUserCommonCheck(userDTO.getMerUserMobile(), userDTO.getMerUserMobile()
					, userDTO.getMerUserPWD(), userDTO.getMerUserSource(),userDTO.getCityCode());
			merUser.setWechatId(userDTO.getWechatId());
			merUser.setParamKey(userDTO.getParamKey());
			///组装绑卡信息
			MerUserCardBDDTO cardBDDTO =new MerUserCardBDDTO();
			cardBDDTO.setCardCode(userDTO.getCardNo());
			cardBDDTO.setCardCityName(merUser.getCityName());;
			cardBDDTO.setUserCode(merUser.getUserCode());
			cardBDDTO.setSource(userDTO.getMerUserSource());
			cardBDDTO.setMerUserName(userDTO.getMerUserMobile());
			
			//卡片校验---------------------------开始-----------------------------------------	
	        String cardCode = userDTO.getCardNo();
	        if (null == cardCode) {
	            response.setCode(ResponseCode.USERS_CARD_CODE_NOT_EMPTY);
	            return response;
	        }
            List<MerUserCardBD> merUserCardBDList = new ArrayList<MerUserCardBD>();
            try {
                //查询表中该卡片的绑定记录 List
                merUserCardBDList = bdService.findMerUserCardByCardCode(userDTO.getCardNo());
                
                //表中之前有绑定该卡的记录
                if (merUserCardBDList!=null && merUserCardBDList.size()>0) {
                    //如果卡片之前和多个用户有过绑定，现在处于解绑状态，和新用户绑定，表里产生新的用户绑卡记录
                    for (MerUserCardBD merUserCardBD : merUserCardBDList) {
                        //卡已绑定
                        if (merUserCardBD.getBundLingType().equals(BindEnum.ENABLE.getCode())) {
                            response.setCode(ResponseCode.USERS_CARD_CODE_BIND_ENABLE);
                            return response;
                        }
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
                return response;
            }
            //卡片校验---------------------------结束-----------------------------------------	
            
			//注册用户
			merchantUserService.registertUser(merUser);
			//发送短信
			//sendService.send(userDTO.getMerUserMobile(), MoblieCodeTypeEnum.USER_PWD, merUserPWD);
			
			//取得用户
			MerchantUser user = merchantUserService.findMerchantUserByUserCode(merUser.getUserCode());
			Map<String,String> map = new HashMap<String, String>();
			map.put("userid", user.getId());
			response.setResponseEntity(map);
			
			//绑卡操作
			DodopalResponse<MerUserCardBDDTO> bindCrdRep = merUserCardBDFacade.saveMerUserCardBD(cardBDDTO);
			if(!ResponseCode.SUCCESS.equals(bindCrdRep.getCode())){
				response.setCode(bindCrdRep.getCode());
				return response;
			}
						
			response.setCode(ResponseCode.SUCCESS);
			
		} catch (HessianRuntimeException e) {
			logger.error(e.getMessage(), e);
			response.setCode(ResponseCode.HESSIAN_ERROR);
		} catch (DDPException e) {
			logger.error("registerUser---->注册异常code:" + e.getCode());
			response.setCode(e.getCode());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

    private String checkRegisterUser(MerchantUserDTO merUserDTO, String dypwd, String serialNumber) {
        // 用户手机号
        String merUserMobile = merUserDTO.getMerUserMobile();
        if (StringUtils.isBlank(merUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_NULL;
        }
        if (registerService.checkMobileExist(merUserMobile, null)) {
            return ResponseCode.USERS_MOB_EXIST;
        }
        // 校验验证码 
        if (StringUtils.isBlank(dypwd)) {
            return ResponseCode.USERS_MOB_CODE_ERR;
        }
        if (StringUtils.isBlank(serialNumber)) {
            return ResponseCode.USERS_MOB_CODE_ERR;
        }
        DodopalResponse<String> moblieCodeCheckResult = sendService.moblieCodeCheck(merUserMobile, dypwd, serialNumber);
        if (!ResponseCode.SUCCESS.equals(moblieCodeCheckResult.getCode())) {
            return moblieCodeCheckResult.getCode();
        }
        // 用户名
        String merUserName = merUserDTO.getMerUserName();
        if (StringUtils.isBlank(merUserName)) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }
        if (registerService.checkUserNameExist(merUserName, null)) {
            return ResponseCode.USERS_MER_USER_NAME_EXIST;
        }
        // 密码
        if (StringUtils.isBlank(merUserDTO.getMerUserPWD())) {
            return ResponseCode.USERS_PWD_NULL;
        }
        // 用户标志
        merUserDTO.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
        // 用户类型
        merUserDTO.setMerUserType(MerUserTypeEnum.PERSONAL.getCode());
        // 启用标志
        merUserDTO.setActivate(ActivateEnum.ENABLE.getCode());
        // 用户注册来源
        merUserDTO.setMerUserSource(SourceEnum.PORTAL.getCode());
        // 用户真实姓名(默认为用户名)
        merUserDTO.setMerUserNickName(merUserName);

        return ResponseCode.SUCCESS;
    }

    private MerchantUser registerUserForMobileCheck(String mobile, String userName, String password, String source, String cityCode) {
    	MerchantUser merUser = new MerchantUser();
    	// 校验手机号
    	if (StringUtils.isBlank(mobile)) {
    		throw new DDPException(ResponseCode.USERS_MOB_TEL_NULL);
        }
        if (registerService.checkMobileExist(mobile, null)) {
            throw new DDPException(ResponseCode.USERS_MOB_EXIST);
        }
        merUser.setMerUserMobile(mobile);
        // 校验用户名
        if (StringUtils.isBlank(userName)) {
            throw new DDPException(ResponseCode.USERS_FIND_USER_NAME_NULL);
        }
        if (registerService.checkUserNameExist(userName, null)) {
            throw new DDPException(ResponseCode.USERS_MER_USER_NAME_EXIST);
        }
        merUser.setMerUserName(userName);
        // 密码
        if (StringUtils.isBlank(password)) {
            throw new DDPException(ResponseCode.USERS_PWD_NULL);
        }
        // 校验来源
        if(!SourceEnum.isPhone(source)) {
        	throw new DDPException(ResponseCode.USERS_MER_USER_SOURCE_ERROR);
        }

		Area areaCity = areaService.findCityByCityCode(cityCode);
		if (areaCity == null) {
			throw new DDPException(ResponseCode.USERS_CITY_INFO_NOT_FOUND);
		}
		merUser.setMerUserPro(areaCity.getParentCode());
		merUser.setMerUserCity(areaCity.getCityCode());
		
        merUser.setMerUserPWD(password);
        // 用户标志
        merUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
        // 用户类型
        merUser.setMerUserType(MerUserTypeEnum.PERSONAL.getCode());
        // 启用标志
        merUser.setActivate(ActivateEnum.ENABLE.getCode());
        // 用户注册来源
        merUser.setMerUserSource(source);
        // 用户真实姓名(默认为用户名)
        merUser.setMerUserNickName(userName);
       
        return merUser;
    }

    private MerchantUser registerUserCommonCheck(String mobile, String userName, String password, String source,String city) {
    	MerchantUser merUser = new MerchantUser();
    	// 校验手机号
    	if (StringUtils.isBlank(mobile)) {
    		throw new DDPException(ResponseCode.USERS_MOB_TEL_NULL);
        }
        if (registerService.checkMobileExist(mobile, null)) {
            throw new DDPException(ResponseCode.USERS_MOB_EXIST);
        }
        merUser.setMerUserMobile(mobile);
        // 校验用户名
        if (StringUtils.isBlank(userName)) {
            throw new DDPException(ResponseCode.USERS_FIND_USER_NAME_NULL);
        }
        if (registerService.checkUserNameExist(userName, null)) {
            throw new DDPException(ResponseCode.USERS_MER_USER_NAME_EXIST);
        }
        merUser.setMerUserName(userName);
        // 密码
        if (StringUtils.isBlank(password)) {
            throw new DDPException(ResponseCode.USERS_PWD_NULL);
        }
        // 校验来源
        if(!SourceEnum.checkCodeExist(source)) {
        	throw new DDPException(ResponseCode.USERS_MER_USER_SOURCE_ERROR);
        }

        Area areaCity = areaService.findCityByCityCode(city);
		if (areaCity == null) {
			throw new DDPException(ResponseCode.USERS_CITY_INFO_NOT_FOUND);
		}
		merUser.setCityCode(city);
		merUser.setCityName(areaCity.getCityName());
		merUser.setMerUserPro(areaCity.getParentCode());
		merUser.setMerUserCityName(areaCity.getCityName());

        merUser.setMerUserPWD(password);
        // 用户标志
        merUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
        // 用户类型
        merUser.setMerUserType(MerUserTypeEnum.PERSONAL.getCode());
        // 启用标志
        merUser.setActivate(ActivateEnum.ENABLE.getCode());
        // 用户注册来源
        merUser.setMerUserSource(source);
        // 用户真实姓名(默认为用户名)
        merUser.setMerUserNickName(userName);

        return merUser;
    }

    private String checkRegisterMerchant(MerchantDTO merDTO, String dypwd, String serialNumber) {
        // 商户名称
        String merName = merDTO.getMerName();
        if (StringUtils.isBlank(merName)) {
            return ResponseCode.USERS_MER_NAME_NULL;
        }
        if (registerService.checkMerchantNameExist(merName, null)) {
            return ResponseCode.USERS_MER_NAME_EXIST;
        }
        // 联系人
        String merLinkUser = merDTO.getMerLinkUser();
        if (StringUtils.isBlank(merLinkUser)) {
            return ResponseCode.USERS_MER_LINK_USER_NULL;
        }
        // 手机号码
        String merLinkUserMobile = merDTO.getMerLinkUserMobile();
        if (StringUtils.isBlank(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_NULL;
        }
        if (!DDPStringUtil.isMobile(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_ERR;
        }
        if (registerService.checkMobileExist(merLinkUserMobile, null)) {
            return ResponseCode.USERS_MOB_EXIST;
        }
        // 校验验证码 
        if (StringUtils.isBlank(dypwd)) {
            return ResponseCode.USERS_MOB_CODE_ERR;
        }
        if (StringUtils.isBlank(serialNumber)) {
            return ResponseCode.USERS_MOB_CODE_ERR;
        }
        DodopalResponse<String> moblieCodeCheckResult = sendService.moblieCodeCheck(merLinkUserMobile, dypwd, serialNumber);
        if (!ResponseCode.SUCCESS.equals(moblieCodeCheckResult.getCode())) {
            return moblieCodeCheckResult.getCode();
        }
        // 省份
        String merPro = merDTO.getMerPro();
        if (StringUtils.isBlank(merPro)) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        // 城市
        String merCity = merDTO.getMerCity();
        if (StringUtils.isBlank(merCity)) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        // 地址
        String merAdds = merDTO.getMerAdds();
        if (StringUtils.isBlank(merAdds)) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        // 用户名
        MerchantUserDTO merUserDTO = merDTO.getMerchantUserDTO();
        if (merUserDTO == null) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }
        String merUserName = merUserDTO.getMerUserName();
        if (StringUtils.isBlank(merUserName)) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }
        if (registerService.checkUserNameExist(merUserName, null)) {
            return ResponseCode.USERS_MER_USER_NAME_EXIST;
        }
        // 密码
        String merUserPwd = merUserDTO.getMerUserPWD();
        if (StringUtils.isBlank(merUserPwd)) {
            return ResponseCode.USERS_FIND_USER_PWD_NULL;
        }
        //---------------------------------------
        // 商户类型为空
        // 商户分类
        merDTO.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());
        // 商户属性
        merDTO.setMerProperty(MerPropertyEnum.STANDARD.getCode());
        // 商户启用标志
        merDTO.setActivate(ActivateEnum.ENABLE.getCode());
        // 删除标志
        merDTO.setDelFlg(DelFlgEnum.NORMAL.getCode());
        // 商户注册来源
        merDTO.setSource(SourceEnum.PORTAL.getCode());
        // 用户注册来源
        merUserDTO.setMerUserSource(SourceEnum.PORTAL.getCode());
        // 用户启用标志
        merUserDTO.setActivate(ActivateEnum.ENABLE.getCode());
        // 用户手机号
        merUserDTO.setMerUserMobile(merLinkUserMobile);
        // 真实姓名
        merUserDTO.setMerUserNickName(merLinkUser);

        return ResponseCode.SUCCESS;
    }

    private String checkRegisterMerchantForVC(MerchantDTO merDTO) {
    	// 验证POS
		PosDTO posDTO = merDTO.getPosDTO();
		if (posDTO != null) {
			String code = posDTO.getCode();
			if(StringUtils.isBlank(code)) {
				return ResponseCode.USERS_POS_CODE_NULL;
			}

			// 判断POS是否注册，未注册，则提示未注册
			Pos findPos = posService.findPosByCode(code);
			if (findPos == null) {
				return ResponseCode.USERS_POS_NO_USE;
			}

			// POS已注册，但是停用状态，提示POS已停用
			String status = findPos.getStatus();
			if (PosStatusEnum.STOP.getCode().equals(status) || PosStatusEnum.INVALID.getCode().equals(status)) {
				return ResponseCode.USERS_POS_NO_USE;
			}

			// POS已注册，判断是否已经绑定其他商户，是则提示已经绑定其他商户
			String binding = findPos.getBind();
			if(BindEnum.ENABLE.getCode().equals(binding)) {
				return ResponseCode.USERS_POS_BUND_MER_CODE_EXIST;
			}

		}

    	// 商户名称
        String merName = merDTO.getMerName();
        if (StringUtils.isBlank(merName)) {
            return ResponseCode.USERS_MER_NAME_NULL;
        }
        if (registerService.checkMerchantNameExist(merName, null)) {
            return ResponseCode.USERS_MER_NAME_EXIST;
        }
        // 联系人
        String merLinkUser = merDTO.getMerLinkUser();
        if (StringUtils.isBlank(merLinkUser)) {
            return ResponseCode.USERS_MER_LINK_USER_NULL;
        }
        // 手机号码
        String merLinkUserMobile = merDTO.getMerLinkUserMobile();
        if (StringUtils.isBlank(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_NULL;
        }
        if (!DDPStringUtil.isMobile(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_ERR;
        }
        if (registerService.checkMobileExist(merLinkUserMobile, null)) {
            return ResponseCode.USERS_MOB_EXIST;
        }
        // 省份
        String merPro = merDTO.getMerPro();
        if (StringUtils.isBlank(merPro)) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        // 城市
        String merCity = merDTO.getMerCity();
        if (StringUtils.isBlank(merCity)) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        // 地址
        String merAdds = merDTO.getMerAdds();
        if (StringUtils.isBlank(merAdds)) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        // 用户名
        MerchantUserDTO merUserDTO = merDTO.getMerchantUserDTO();
        if (merUserDTO == null) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }
        String merUserName = merUserDTO.getMerUserName();
        if (StringUtils.isBlank(merUserName)) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }
        if (registerService.checkUserNameExist(merUserName, null)) {
            return ResponseCode.USERS_MER_USER_NAME_EXIST;
        }
        // 密码
        String merUserPwd = merUserDTO.getMerUserPWD();
        if (StringUtils.isBlank(merUserPwd)) {
            return ResponseCode.USERS_FIND_USER_PWD_NULL;
        }
        //---------------------------------------
        // 商户类型为空
        // 商户分类
        merDTO.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());
        // 商户属性
        merDTO.setMerProperty(MerPropertyEnum.STANDARD.getCode());
        // 商户启用标志
        merDTO.setActivate(ActivateEnum.ENABLE.getCode());
        // 删除标志
        merDTO.setDelFlg(DelFlgEnum.NORMAL.getCode());
        // 商户注册来源
        merDTO.setSource(SourceEnum.VC.getCode());
        // 用户注册来源
        merUserDTO.setMerUserSource(SourceEnum.VC.getCode());
        // 用户启用标志
        merUserDTO.setActivate(ActivateEnum.ENABLE.getCode());
        // 用户手机号
        merUserDTO.setMerUserMobile(merLinkUserMobile);
        // 真实姓名
        merUserDTO.setMerUserNickName(merLinkUser);

    	return ResponseCode.SUCCESS;
    }
}

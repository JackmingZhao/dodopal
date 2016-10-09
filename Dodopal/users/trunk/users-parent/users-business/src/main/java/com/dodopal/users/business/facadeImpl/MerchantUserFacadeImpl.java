package com.dodopal.users.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.md5.MD5;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.constant.UsersConstants;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerchantUserQuery;
import com.dodopal.users.business.service.MerBusRateService;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.business.service.SendService;

@Service("merchantUserFacade")
public class MerchantUserFacadeImpl implements MerchantUserFacade{
    
    private final static Logger log = LoggerFactory.getLogger(MerchantUserFacadeImpl.class);
    @Autowired
    private MerchantUserService userService;
    @Autowired
    private SendService sendService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerBusRateService merBusRateService;
    @Autowired
    private AreaService areaService;
    @Override
    public DodopalResponse<List<MerchantUserDTO>> findUserInfoList(MerchantUserDTO userDTO) {
        DodopalResponse<List<MerchantUserDTO>> response = new DodopalResponse<List<MerchantUserDTO>> ();
        MerchantUser user = new MerchantUser();
        List<MerchantUserDTO> userDtoList = new ArrayList<MerchantUserDTO>();
        try {
            PropertyUtils.copyProperties(user,userDTO);
            //用户类型验证参数
            if(StringUtils.isBlank(user.getMerUserType())){
                response.setCode(ResponseCode.USERS_TYPE_NULL);
            }
//            else if(StringUtils.isNotBlank(user.getMerUserSource())){
//                response.setCode(ResponseCode.USERS_FIND_FLG_ERR);
//            }
            else{
                List <MerchantUser> userList = userService.findMerchantUserAndMerName(user);
                if(userList.size()>0){
                    for(MerchantUser tempUser:userList){
                        userDTO = new MerchantUserDTO();
                        PropertyUtils.copyProperties(userDTO,tempUser);
                        userDtoList.add(userDTO);
                    }
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(userDtoList);
            }
        }catch(Exception e) {
            log.debug("MerchantUserFacadeImpl call error", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public DodopalResponse<MerchantUserDTO> findUserInfoById(String id) {
        DodopalResponse<MerchantUserDTO> response = new DodopalResponse<MerchantUserDTO> ();
        MerchantUserDTO userDTO = new MerchantUserDTO();
        MerchantUser user = new MerchantUser();
        try {
            if(StringUtils.isBlank(id)){//验证参数
                response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
            }else{
                user = userService.findMerchantUserAndMerNameById(id);
                //是否找到匹配用户
                if(null!=user){
                    String merPro = user.getMerUserPro();
                    String merCity = user.getMerUserCity();
                    if (StringUtils.isNotBlank(merPro)) {
                        Area provice = areaService.findCityByCityCode(merPro);
                        if (provice != null) {
                            user.setMerUserProName(provice.getCityName());
                        }
                    }
                    if (StringUtils.isNotBlank(merCity)) {
                        Area city = areaService.findCityByCityCode(merCity);
                        if (city != null) {
                            user.setMerUserCityName(city.getCityName());
                        }
                    }
                    PropertyUtils.copyProperties(userDTO,user);
                }else{
                    userDTO = null;
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(userDTO);
            }    
        }catch(Exception e) {
            log.error("MerchantUserFacadeImpl call error", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public DodopalResponse<Map<String, String>> toStopOrStartUser(ActivateEnum startOrStop,List<String> idList,String updateUser) {
        DodopalResponse<Map<String, String>>response = new DodopalResponse<Map<String, String>>();
        if(idList.isEmpty()){
            response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
        }else if(checkAdmin(idList)){
            response.setCode(ResponseCode.USERS_USER_ADMIN_ERR);
        }else if(null==startOrStop){
            response.setCode(ResponseCode.USERS_FIND_USER_ACT_NULL);
        }else{
            userService.batchStopUser(startOrStop.getCode(),idList,updateUser);
            response.setCode(ResponseCode.SUCCESS);
        }
        return response;
    }
    private boolean checkAdmin(List<String> ids){
        for(String id:ids){
            MerchantUser user =  userService.findMerchantUserById(id);
            if(MerUserFlgEnum.ADMIN.getCode().equals(user.getMerUserFlag())){
                return true;
            }
        }
        return false;
    }
    @Override
    public DodopalResponse<Boolean> updateUser(MerchantUserDTO userDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        MerchantUser user = new MerchantUser();
        if(StringUtils.isNotBlank(userDTO.getId())){
            try {
                PropertyUtils.copyProperties(user, userDTO);
            }catch (Exception e) {
                log.error("MerchantUserFacadeImpl call error", e);
                dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
                dodopalResponse.setMessage(e.getMessage());
            }
            MerchantUser userTel = new MerchantUser();
            if(!DDPStringUtil.isMobile(user.getMerUserMobile())){
                dodopalResponse.setCode(ResponseCode.USERS_MOB_TEL_ERR);
                return dodopalResponse;
            }else if(MerUserFlgEnum.ADMIN.getCode().equals(user.getMerUserFlag())){
                dodopalResponse.setCode(ResponseCode.USERS_USER_ADMIN_ERR);
                return dodopalResponse;
            }
            userTel.setMerUserMobile(user.getMerUserMobile());
            //userTel.setId(user.getId());
            //判断手机号是否重复
            List <MerchantUser> userList = userService.findMerchantUser(userTel);
            if(CollectionUtils.isNotEmpty(userList)){
                MerchantUser merUser = userList.get(0);
                if(!user.getId().equals(merUser.getId())){
                    dodopalResponse.setCode(ResponseCode.USERS_MOB_EXIST);
                    return dodopalResponse;                    
                }
            } 
            int rows = userService.updateMerchantUserWithMerchant(user);
            if(rows>0){
                dodopalResponse.setResponseEntity(true);
                dodopalResponse.setCode(ResponseCode.SUCCESS);
            }else{
                dodopalResponse.setResponseEntity(false);
                dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_ERR);
            }
        }else{
            dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<Boolean> modifyPWD(MerchantUserDTO userDTO, String oldPassword) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        MerchantUser user = new MerchantUser();
        if(StringUtils.isBlank(oldPassword)){
            dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_PWD_NULL);
        }else if(StringUtils.isBlank(userDTO.getMerUserName())){
            dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_NAME_NULL);
        }else{
            try {
                PropertyUtils.copyProperties(user, userDTO);
            }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("MerchantUserFacadeImpl call error", e);
                dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            }
            boolean status = userService.modifyPWD(user, oldPassword);
            dodopalResponse.setResponseEntity(status);
            if(status){
                dodopalResponse.setCode(ResponseCode.SUCCESS);
                dodopalResponse.setMessage("success");
            }else{
                dodopalResponse.setCode(ResponseCode.USERS_PASSWORD_ERR);
                dodopalResponse.setMessage("false");
            }
        }
        return dodopalResponse;
    }
    
    @Override
    public DodopalResponse<Boolean> resetPWD(MerchantUserDTO userDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        MerchantUser user = new MerchantUser();
        try {
            PropertyUtils.copyProperties(user, userDTO);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("MerchantUserFacadeImpl call error", e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        if(StringUtils.isBlank(user.getId())){
            dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
        }else if(StringUtils.isBlank(user.getMerUserPWD())){
            dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_PWD_NULL);
        }else{
            boolean status = userService.resetPWD(user);
            dodopalResponse.setResponseEntity(status);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setMessage(status?"success":"fail");
        }
        return dodopalResponse;
    }
    
    public DodopalResponse<Boolean> resetPWDByMobile(String mobile,String pwd){
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        try{
	        if(null==mobile&&"".equals(mobile)){
	            dodopalResponse.setCode(ResponseCode.USERS_MOB_TEL_NULL);
	        }else if(null==pwd&&"".equals(pwd)){
	            dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_PWD_NULL);
	        }else{
	            boolean status = userService.resetPWDByMobile(mobile, pwd);
	            if(status){
	            	dodopalResponse.setCode(ResponseCode.SUCCESS);
	            }else{
	            	dodopalResponse.setCode(ResponseCode.USERS_MOB_NOT_EXIST);
	            }
	            
	        }
        }catch(Exception e){
        	dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        	e.printStackTrace();
        }
        return dodopalResponse;
    }
    
    @Override
    public DodopalResponse<Boolean> resetPWDSendMsg(MerchantUserDTO userDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        try {
            MerchantUser user = new MerchantUser();
            PropertyUtils.copyProperties(user, userDTO);

            if (StringUtils.isBlank(user.getId())) {
                dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
            } else if (StringUtils.isBlank(user.getMerUserMobile())) {
                dodopalResponse.setCode(ResponseCode.USERS_MOB_TEL_NULL);
            } else {
                log.info("resetPWDSendMsg:" + user.getId());
                MerchantUser userOld = userService.findMerchantUserById(user.getId());
                if (userOld == null) {
                    dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_ERR);
                    return dodopalResponse;
                }

                if(!userDTO.getMerUserMobile().equals(userOld.getMerUserMobile())) {
                    dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_ERR);
                    return dodopalResponse;
                }

                // 生成密码
                String merUserPWD = DDPStringUtil.getRandomStr(UsersConstants.MER_USER_PWD_LENGTH);
                String md5MerUserPWD = MD5.MD5Encode(MD5.MD5Encode(merUserPWD));
                user.setMerUserPWD(md5MerUserPWD);
                boolean status = userService.resetPWD(user);
                log.info("resetPWDSendMsg result:" + status);
                // 修改成功后发送短信
                if (status) {
                    try {
                        DodopalResponse<Map<String, String>> sendResponse = sendService.send(user.getMerUserMobile(), MoblieCodeTypeEnum.USER_PWD, merUserPWD);
                        if (!ResponseCode.SUCCESS.equals(sendResponse.getCode())) {
                            dodopalResponse.setCode(sendResponse.getCode());
                            status = false;
                        }
                    }
                    catch (Exception e) {
                        status = false;
                    }
                    
                }
                if (!status) {
                    // 修改失败改回原密码
                    user.setMerUserPWD(userOld.getMerUserPWD());
                    userService.resetPWD(user);
                }
                dodopalResponse.setResponseEntity(status);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUserDTO userDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        MerchantUser user = new MerchantUser();
        try {
            PropertyUtils.copyProperties(user, userDTO);
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("MerchantUserFacadeImpl call error", e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        if(StringUtils.isEmpty(userDTO.getPayInfoFlg())){
            dodopalResponse.setCode(ResponseCode.USERS_FIND_PAYINFO_NULL);
        }else if(StringUtils.isEmpty(userDTO.getId())){
            dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
        }else{
            boolean status = userService.modifyPayInfoFlg(user);
            dodopalResponse.setResponseEntity(status);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setMessage(status?"success":"fail");
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findUserInfoListByPage(MerchantUserQueryDTO userQueryDTO) {
        DodopalResponse<DodopalDataPage<MerchantUserDTO>> response = new DodopalResponse<DodopalDataPage<MerchantUserDTO>> ();
//        MerchantUser user = new MerchantUser();
        MerchantUserQuery userQuery = new MerchantUserQuery();
        List<MerchantUserDTO> userDtoList = new ArrayList<MerchantUserDTO>();
        try {
            
            PropertyUtils.copyProperties(userQuery,userQueryDTO);
            //用户类型验证参数
            //oss 模块 分个人与企业模块
            if(StringUtils.isBlank(userQuery.getMerUserType())){
                response.setCode(ResponseCode.USERS_TYPE_NULL);
            }
//            else if(StringUtils.isNotBlank(user.getMerUserSource())){
//                response.setCode(ResponseCode.USERS_FIND_FLG_ERR);
//            }
            else{
                DodopalDataPage<MerchantUser> userPageList = userService.findMerchantUserAndMerNameByPage(userQuery);
                List<MerchantUser> userList = userPageList.getRecords();
                if(userList.size()>0){
                    for(MerchantUser tempUser:userList){
                        MerchantUserDTO userTempDTO = new MerchantUserDTO();
                        String merPro = tempUser.getMerUserPro();
                        String merCity = tempUser.getMerUserCity();
                        if (StringUtils.isNotBlank(merPro)) {
                            Area provice = areaService.findCityByCityCode(merPro);
                            if (provice != null) {
                                tempUser.setMerUserProName(provice.getCityName());
                            }
                        }
                        if (StringUtils.isNotBlank(merCity)) {
                            Area city = areaService.findCityByCityCode(merCity);
                            if (city != null) {
                                tempUser.setMerUserCityName(city.getCityName());
                            }
                        }
                        PropertyUtils.copyProperties(userTempDTO,tempUser);
                        userDtoList.add(userTempDTO);
                    }
                }
               
                PageParameter page =  DodopalDataPageUtil.convertPageInfo(userPageList);
                DodopalDataPage<MerchantUserDTO> pages = new DodopalDataPage<MerchantUserDTO>(page, userDtoList);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(pages);
            }
        }catch(Exception e) {
            log.debug("MerchantUserFacadeImpl call error", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public DodopalResponse<String> findMerUserNameByMobile(String mobile) {
        DodopalResponse<String> response = new DodopalResponse<String> ();
        try{
            String merUserName = userService.findMerchantUserNameByMobile(mobile);
            if(null!=merUserName){
                response.setCode(ResponseCode.SUCCESS);
            }else{
                response.setCode(ResponseCode.USERS_MOB_NOT_EXIST);
            }
            response.setResponseEntity(merUserName);
           
        }catch(Exception e) {
            log.debug("MerchantUserFacadeImpl call error", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    public DodopalResponse<MerchantUserDTO> findUserInfoByUserName(String userNameOrMobile){
        DodopalResponse<MerchantUserDTO> response = new DodopalResponse<MerchantUserDTO> ();
        if(StringUtils.isBlank(userNameOrMobile)){
        	response.setCode(ResponseCode.USERS_USER_NAME_NO_EXIST);
        	return response;
        }
        if(DDPStringUtil.isMobile(userNameOrMobile)){
            userNameOrMobile = userService.findMerchantUserNameByMobile(userNameOrMobile);
        }
        if(StringUtils.isBlank(userNameOrMobile)){
            response.setCode(ResponseCode.SUCCESS);
            return response;
        }
        MerchantUser merUser = userService.findMerchantUserByUserName(userNameOrMobile);
        MerchantUserDTO dto = new MerchantUserDTO();
        try {
            if(null!=merUser){
                PropertyUtils.copyProperties(dto, merUser);
                response.setResponseEntity(dto);    
            }
            response.setCode(ResponseCode.SUCCESS);
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

	/**
	 * 修改用户
	 */
	public DodopalResponse<Boolean> updateMerchantUser(MerchantUserDTO userDTO) {
		 DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
	     MerchantUser user = new MerchantUser();
	     try {
			PropertyUtils.copyProperties(user, userDTO);
			int rows = userService.updateMerchantUser(user);
			if(rows>0){
				dodopalResponse.setResponseEntity(true);
				dodopalResponse.setCode(ResponseCode.SUCCESS);
			}else{
				dodopalResponse.setResponseEntity(false);
				dodopalResponse.setCode(ResponseCode.USERS_FIND_USER_ERR);
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			 log.error("MerchantUserFacadeImpl call error", e);
             dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
             dodopalResponse.setMessage(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode, String updateUser) {
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		if (StringUtils.isBlank(cityCode)) {
			response.setCode(ResponseCode.USERS_MER_CITY_NULL);
			return response;
		}
		if (StringUtils.isBlank(updateUser)) {
			response.setCode(ResponseCode.UPDATE_USER_NULL);
			return response;
		}
		log.info("updateMerUserBusCity---->cityCode:" + cityCode + ",updateUser:" + updateUser);
		response.setCode(ResponseCode.SUCCESS);
		try {
			// 对用户可以切换的城市做校验
			MerchantUser merUser = userService.findMerchantUserById(updateUser);
			if(merUser == null) {
				response.setCode(ResponseCode.USERS_FIND_USER_ERR);
				return response;
			}

			if(StringUtils.isNotBlank(merUser.getMerCode())) {
				List<String> merCityCodes = merBusRateService.findMerCitys(merUser.getMerCode());
				boolean isFound = false;
				if(CollectionUtils.isNotEmpty(merCityCodes)) {
					for(String cityCodeTemp : merCityCodes) {
						if(cityCodeTemp.equals(cityCode)) {
							isFound = true;
							break;
						}
					}
				}
				if(!isFound) {
					response.setCode(ResponseCode.USERS_USER_CITY_NOT_FOUND);
					return response;
				}
			} else {
				//TODO:个人用户
			}

			int result = userService.updateMerUserBusCity(cityCode, updateUser);
			if (result > 0) {
				response.setResponseEntity(true);
			} else {
				response.setResponseEntity(false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		log.info("updateMerUserBusCity---->responseCode:" + response.getCode());
		return response;
	}

	@Override
	public DodopalResponse<Boolean> updateMerUserMobile(String merUserMobile, String userId) {
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		if (StringUtils.isBlank(merUserMobile)) {
			response.setCode(ResponseCode.USERS_MER_CITY_NULL);
			return response;
		}
		if (StringUtils.isBlank(userId)) {
			response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
			return response;
		}
		log.info("updateMerUserMobile---->newMobile:" + merUserMobile + ",userId:" + userId);
		response.setCode(ResponseCode.SUCCESS);
		try {
			// 校验新手机号是否已注册
			String oldUserId = userService.checkMerUserByMobile(merUserMobile);
			if (StringUtils.isNotBlank(oldUserId)) {
				if (!oldUserId.equals(userId)) {
					response.setCode(ResponseCode.USERS_MOB_EXIST);
					return response;
				}
			}

			// 更新手机号
			int result = userService.updateMerUserMobile(merUserMobile, userId);
			if (result > 0) {
				response.setResponseEntity(true);
			} else {
				response.setResponseEntity(false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		log.info("updateMerUserMobile---->responseCode:" + response.getCode());
		return response;
	}

	@Override
	public DodopalResponse<Boolean> updateMerUserPWDById(String oldPwd, String newPwd, String updateUser) {
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		if (StringUtils.isBlank(oldPwd)) {
			response.setCode(ResponseCode.USERS_FIND_USER_PWD_NULL);
			return response;
		}
		if (StringUtils.isBlank(newPwd)) {
			response.setCode(ResponseCode.USERS_FIND_USER_PWD_NULL);
			return response;
		}
		if (StringUtils.isBlank(updateUser)) {
			response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
			return response;
		}
		log.info("updateMerUserPWDById---->userId:" + updateUser + ",newPwd:" + newPwd);
		response.setCode(ResponseCode.SUCCESS);
		try {
			// 校验旧密码
			boolean bool = userService.checkMerUserPWD(updateUser, oldPwd);
			if (!bool) {
				response.setCode(ResponseCode.USERS_PASSWORD_ERR);
				return response;
			}

			int result = userService.updateMerUserPWDById(newPwd, updateUser);
			if (result > 0) {
				response.setResponseEntity(true);
			} else {
				response.setResponseEntity(false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		log.info("updateMerUserPWDById---->responseCode:" + response.getCode());
		return response;
	}

	@Override
	public DodopalResponse<Boolean> validateMerUserPWD(String merUserPWD, String userId) {
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		if (StringUtils.isBlank(merUserPWD)) {
			response.setCode(ResponseCode.USERS_FIND_USER_PWD_NULL);
			return response;
		}
		if (StringUtils.isBlank(userId)) {
			response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
			return response;
		}
		response.setCode(ResponseCode.SUCCESS);
		try {
			// 校验旧密码
			boolean bool = userService.checkMerUserPWD(userId, merUserPWD);
			if (!bool) {
				response.setCode(ResponseCode.USERS_PASSWORD_ERR);
				return response;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<MerchantUserDTO> findUserInfoByUserCode(
			String userCode) {
		DodopalResponse<MerchantUserDTO> response = new DodopalResponse<MerchantUserDTO> ();
        if(StringUtils.isBlank(userCode)){
            response.setCode(ResponseCode.USERS_USER_CODE_NULL);
            return response;
        }
        MerchantUser merUser = userService.findMerchantUserByUserCode(userCode);
        MerchantUserDTO dto = new MerchantUserDTO();
        try {
            if(null!=merUser){
                PropertyUtils.copyProperties(dto, merUser);
                response.setResponseEntity(dto);    
            }
            response.setCode(ResponseCode.SUCCESS);
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
	}

    
    public DodopalResponse<String> findNickNameByUserId(String userId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        String nickName  = "";
        try {
            nickName = userService.findNickNameByUserId(userId);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(nickName);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }
    

    public List<MerchantUserDTO> getExportExcelMerUserList(MerchantUserQueryDTO userQueryDTO) {
        List<MerchantUserDTO> response =  new ArrayList<MerchantUserDTO>();
        MerchantUserQuery userQuery = new MerchantUserQuery();
        try {
            PropertyUtils.copyProperties(userQuery,userQueryDTO);
            List<MerchantUser> userPageList = userService.getExportExcelMerUserList(userQuery);
            if(userPageList.size()>0){
                for(MerchantUser tempUser:userPageList){
                    MerchantUserDTO userTempDTO = new MerchantUserDTO();
                    PropertyUtils.copyProperties(userTempDTO,tempUser);
                    response.add(userTempDTO);
                }
            }
        }catch(Exception e) {
            log.error("查询用户信息时发生错误..", e);
        }
        return response;
    }

	@Override
	public DodopalResponse<Boolean> updateUserPwdByOldUserId(String oldUserId, String oldUserType, String password) {
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		try {
			if (StringUtils.isBlank(oldUserId)) {
				response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
				return response;
			}

			if (StringUtils.isBlank(oldUserType)) {
				response.setCode(ResponseCode.USERS_TYPE_NULL);
				return response;
			}

			if (StringUtils.isBlank(password)) {
				response.setCode(ResponseCode.USERS_PWD_NULL);
				return response;
			}

			String userId = userService.findUserIdByOldUserId(oldUserId, oldUserType);
			if (StringUtils.isNotBlank(userId)) {
				int num = userService.updateMerUserPWDById(password, userId);
				if (num > 0) {
					response.setResponseEntity(true);
				} else {
					response.setResponseEntity(false);
				}
				response.setCode(ResponseCode.SUCCESS);
			} else {
				response.setCode(ResponseCode.USERS_FIND_USER_ERR);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<List<MerchantUserDTO>> findMerchantUserList(MerchantUserDTO merUserDTO) {
		DodopalResponse<List<MerchantUserDTO>> response = new DodopalResponse<List<MerchantUserDTO>>();
		try {
			if (StringUtils.isBlank(merUserDTO.getMerCode())) {
				response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
				return response;
			}

			MerchantUser paramUser = new MerchantUser();
			PropertyUtils.copyProperties(paramUser, merUserDTO);
			List<MerchantUser> userList = userService.findMerchantUser(paramUser);
			if (CollectionUtils.isNotEmpty(userList)) {
				List<MerchantUserDTO> userDTOList = new ArrayList<MerchantUserDTO>(userList.size());
				for (MerchantUser tempUser : userList) {
					MerchantUserDTO userDTO = new MerchantUserDTO();
					PropertyUtils.copyProperties(userDTO, tempUser);
					userDTOList.add(userDTO);
				}
				response.setResponseEntity(userDTOList);
			}
			response.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

}
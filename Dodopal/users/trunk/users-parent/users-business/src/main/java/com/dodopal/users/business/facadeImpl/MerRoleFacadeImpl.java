package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.query.MerRoleQueryDTO;
import com.dodopal.api.users.facade.MerRoleFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.model.MerRole;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerRoleQuery;
import com.dodopal.users.business.service.MerFunctionInfoService;
import com.dodopal.users.business.service.MerRoleService;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.MerchantUserService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service("merRoleFacade")
public class MerRoleFacadeImpl implements MerRoleFacade {
	private final static Logger logger = LoggerFactory.getLogger(MerRoleFacadeImpl.class);
    @Autowired
    private MerRoleService merRoleService;
    @Autowired
    private MerchantUserService merchantUserService;
    @Autowired
    private MerFunctionInfoService merFunctionInfoService;
    @Autowired
    private MerchantService merchantService;

    private static final String[] IGNORE_FIELDS = {"merRoleFunList"};

    @Override
    public DodopalResponse<List<MerRoleDTO>> findMerRole(MerRoleDTO merRoleDTO) {
        DodopalResponse<List<MerRoleDTO>> response = new DodopalResponse<List<MerRoleDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = findMerRoleCheck(merRoleDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerRole merRole = new MerRole();
                BeanUtils.copyProperties(merRoleDTO, merRole, IGNORE_FIELDS);
                List<MerRole> merRoleList = merRoleService.findMerRole(merRole);
                List<MerRoleDTO> merRoleDTOList = null;
                if (merRoleList != null && merRoleList.size() > 0) {
                    merRoleDTOList = new ArrayList<MerRoleDTO>(merRoleList.size());
                    for (MerRole merRoleTemp : merRoleList) {
                        MerRoleDTO merRoleDTOTemp = new MerRoleDTO();
                        BeanUtils.copyProperties(merRoleTemp, merRoleDTOTemp, IGNORE_FIELDS);
                        merRoleDTOList.add(merRoleDTOTemp);
                    }
                }
                response.setResponseEntity(merRoleDTOList);
            } else {
                response.setCode(resCode);
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
    public DodopalResponse<DodopalDataPage<MerRoleDTO>> findMerRoleByPage(MerRoleQueryDTO merRoleQueryDTO) {
        DodopalResponse<DodopalDataPage<MerRoleDTO>> response = new DodopalResponse<DodopalDataPage<MerRoleDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = findMerRoleByPageCheck(merRoleQueryDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerchantUser loginUser = merchantUserService.findMerchantUserById(merRoleQueryDTO.getLoginUserId());
                if (loginUser == null) {
                    response.setCode(ResponseCode.LOGIN_USER_NULL);
                    return response;
                }

                MerRoleQuery merRoleQuery = new MerRoleQuery();
                BeanUtils.copyProperties(merRoleQueryDTO, merRoleQuery);
                // 如果是管理员，可以查询所有角色
                if (MerUserFlgEnum.ADMIN.getCode().equals(loginUser.getMerUserFlag())) {
                    merRoleQuery.setLoginUserId(null);
                }

                DodopalDataPage<MerRole> ddpResult = merRoleService.findMerRoleByPage(merRoleQuery);
                List<MerRole> merRoleList = ddpResult.getRecords();
                if (!CollectionUtils.isEmpty(merRoleList)) {
                    List<MerRoleDTO> merRoleDTOList = new ArrayList<MerRoleDTO>(merRoleList.size());
                    for (MerRole merRoleTemp : merRoleList) {
                        MerRoleDTO merRoleDTOTemp = new MerRoleDTO();
                        BeanUtils.copyProperties(merRoleTemp, merRoleDTOTemp, IGNORE_FIELDS);
                        merRoleDTOList.add(merRoleDTOTemp);
                    }
                }

                List<MerRoleDTO> merRoleDTOList = null;
                if (merRoleList != null && merRoleList.size() > 0) {
                    merRoleDTOList = new ArrayList<MerRoleDTO>(merRoleList.size());
                    for (MerRole merRoleTemp : merRoleList) {
                        MerRoleDTO merRoleDTOTemp = new MerRoleDTO();
                        BeanUtils.copyProperties(merRoleTemp, merRoleDTOTemp, IGNORE_FIELDS);
                        merRoleDTOList.add(merRoleDTOTemp);
                    }
                    DodopalDataPage<MerRoleDTO> ddpDTOResult = new DodopalDataPage<MerRoleDTO>(merRoleQueryDTO.getPage(), merRoleDTOList);
                    response.setResponseEntity(ddpDTOResult);
                }
            } else {
                response.setCode(resCode);
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
    public DodopalResponse<MerRoleDTO> findMerRoleByMerRoleCode(String merCode, String merRoleCode) {
        DodopalResponse<MerRoleDTO> response = new DodopalResponse<MerRoleDTO>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = findMerRoleByMerRoleCodeCheck(merCode, merRoleCode);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerRole merRole = merRoleService.findMerRoleByMerRoleCode(merCode, merRoleCode);
                MerRoleDTO merRoleDTO = null;
                if (merRole != null) {
                    merRoleDTO = new MerRoleDTO();
                    BeanUtils.copyProperties(merRole, merRoleDTO, IGNORE_FIELDS);

                    List<MerFunctionInfo> merRoleFunList = merRole.getMerRoleFunList();
                    List<MerFunctionInfoDTO> merRoleFunDTOList = null;
                    /*if (!CollectionUtils.isEmpty(merRoleFunList)) {
                        // TODO待优化：由于前端树的原因，这里只获取叶子节点
                        List<MerFunctionInfo> allFunInfo = merFunctionInfoService.findMerFunctionInfo(new MerFunctionInfo());
                        // 先找出所有的父节点
                        Map<String, Object> parentNodeMap = new HashMap<String, Object>();
                        if (!CollectionUtils.isEmpty(allFunInfo)) {
                            for (MerFunctionInfo merFunInfo : allFunInfo) {
                                String parentCode = merFunInfo.getParentCode();
                                if (StringUtils.isNotBlank(parentCode)) {
                                    parentNodeMap.put(parentCode, null);
                                }
                            }
                        }

                        // 去掉父节点
                        List<MerFunctionInfo> childNodeList = new ArrayList<MerFunctionInfo>();
                        for (MerFunctionInfo merFunInfo : merRoleFunList) {
                            if (!parentNodeMap.containsKey(merFunInfo.getMerFunCode())) {
                                childNodeList.add(merFunInfo);
                            }
                        }

                        if (!CollectionUtils.isEmpty(childNodeList)) {
                            merRoleFunDTOList = new ArrayList<MerFunctionInfoDTO>(childNodeList.size());
                            for (MerFunctionInfo merFunInfoTemp : childNodeList) {
                                MerFunctionInfoDTO merFunInfoDTOTemp = new MerFunctionInfoDTO();
                                BeanUtils.copyProperties(merFunInfoTemp, merFunInfoDTOTemp);
                                merRoleFunDTOList.add(merFunInfoDTOTemp);
                            }
                        }
                    }*/
                    if (!CollectionUtils.isEmpty(merRoleFunList)) {
                        merRoleFunDTOList = new ArrayList<MerFunctionInfoDTO>(merRoleFunList.size());
                        for (MerFunctionInfo merFunInfoTemp : merRoleFunList) {
                            MerFunctionInfoDTO merFunInfoDTOTemp = new MerFunctionInfoDTO();
                            BeanUtils.copyProperties(merFunInfoTemp, merFunInfoDTOTemp);
                            merRoleFunDTOList.add(merFunInfoDTOTemp);
                        }
                    }
                    merRoleDTO.setMerRoleFunDTOList(merRoleFunDTOList);
                }
                response.setResponseEntity(merRoleDTO);
            } else {
                response.setCode(resCode);
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
    public DodopalResponse<Integer> addMerRole(MerRoleDTO merRoleDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = addMerRoleCheck(merRoleDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerRole merRole = new MerRole();
                BeanUtils.copyProperties(merRoleDTO, merRole, IGNORE_FIELDS);

                List<MerFunctionInfoDTO> merRoleFunDTOList = merRoleDTO.getMerRoleFunDTOList();
                if (merRoleFunDTOList != null && merRoleFunDTOList.size() > 0) {
                    List<MerFunctionInfo> merRoleFunList = new ArrayList<MerFunctionInfo>(merRoleFunDTOList.size());
                    for (MerFunctionInfoDTO merFunInfoDTOTemp : merRoleFunDTOList) {
                        MerFunctionInfo merFunInfoTemp = new MerFunctionInfo();
                        BeanUtils.copyProperties(merFunInfoDTOTemp, merFunInfoTemp);
                        merRoleFunList.add(merFunInfoTemp);
                    }
                    merRole.setMerRoleFunList(merRoleFunList);
                }
                int num = merRoleService.addMerRole(merRole);
                response.setResponseEntity(num);
            } else {
                response.setCode(resCode);
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
    public DodopalResponse<Integer> updateMerRole(MerRoleDTO merRoleDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = updateMerRoleCheck(merRoleDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerRole merRole = new MerRole();
                BeanUtils.copyProperties(merRoleDTO, merRole, IGNORE_FIELDS);

                List<MerFunctionInfoDTO> merRoleFunDTOList = merRoleDTO.getMerRoleFunDTOList();
                if (merRoleFunDTOList != null && merRoleFunDTOList.size() > 0) {
                    List<MerFunctionInfo> merRoleFunList = new ArrayList<MerFunctionInfo>(merRoleFunDTOList.size());
                    for (MerFunctionInfoDTO merFunInfoDTOTemp : merRoleFunDTOList) {
                        MerFunctionInfo merFunInfoTemp = new MerFunctionInfo();
                        BeanUtils.copyProperties(merFunInfoDTOTemp, merFunInfoTemp);
                        merRoleFunList.add(merFunInfoTemp);
                    }
                    merRole.setMerRoleFunList(merRoleFunList);
                }
                int num = merRoleService.updateMerRole(merRole);
                response.setResponseEntity(num);
            } else {
                response.setCode(resCode);
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
    public DodopalResponse<Integer> delMerRoleByMerRoleCode(String merCode, String merRoleCode) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = delMerRoleByMerRoleCodeCheck(merCode, merRoleCode);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                int num = merRoleService.delMerRoleByMerRoleCode(merCode, merRoleCode);
                response.setResponseEntity(num);
            } else {
                response.setCode(resCode);
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
    public DodopalResponse<Integer> batchDelMerRoleByCodes(String merCode, List<String> merRoleCodes) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = batchDelMerRoleByMerRoleCodeCheck(merCode, merRoleCodes);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                int num = merRoleService.batchDelMerRoleByCodes(merCode, merRoleCodes);
                response.setResponseEntity(num);
            } else {
                response.setCode(resCode);
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
    public DodopalResponse<List<MerFunctionInfoDTO>> findMerFuncInfoByUserCode(String userCode) {
        DodopalResponse<List<MerFunctionInfoDTO>> response = new DodopalResponse<List<MerFunctionInfoDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            if (StringUtils.isNotBlank(userCode)) {
                MerchantUser merUserParam = new MerchantUser();
                merUserParam.setUserCode(userCode);
                // 获取用户信息
                MerchantUser merUser = merchantUserService.findMerchantUserExact(merUserParam);
                if (merUser != null) {
                    List<MerFunctionInfo> merFunInfoList = null;
                    // 判断用户类型，获取权限信息
                    if (MerUserTypeEnum.PERSONAL.getCode().equals(merUser.getMerUserType())) { //个人
                        merFunInfoList = merFunctionInfoService.findMerFunctionInfoByMerType(MerTypeEnum.PERSONAL.getCode());
                    } else if (MerUserTypeEnum.MERCHANT.getCode().equals(merUser.getMerUserType())) { //企业
                        String merCode = merUser.getMerCode();
                        if (StringUtils.isNotBlank(merCode)) {
                            // 获取商户信息
                            Merchant mer = merchantService.findMerchantByMerCode(merCode);
                            if (mer != null) {
                                String merUserFlg = merUser.getMerUserFlag();
                                // 商户属性:0.标准商户,1.自定义商户
                                String merProperty = mer.getMerProperty();
                                String merType = mer.getMerType();
                                if (MerUserFlgEnum.ADMIN.getCode().equals(merUserFlg)) { //管理员
                                    // 商户属性:0.标准商户,1.自定义商户
                                    if (MerPropertyEnum.STANDARD.getCode().equals(merProperty)) { // 标准商户(管理员)
                                        merFunInfoList = merFunctionInfoService.findMerFunctionInfoByMerType(mer.getMerType());
                                    } else if (MerPropertyEnum.CUSTOM.getCode().equals(merProperty)) { //自定义商户(管理员)
                                        merFunInfoList = merFunctionInfoService.findMerFunctionInfoByMerCode(merCode);
                                    }
                                } else if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlg)){ // 操作员
                                    if (MerPropertyEnum.STANDARD.getCode().equals(merProperty)) { // 标准商户(操作员)
                                        merFunInfoList = merFunctionInfoService.findStandardOperatorFuns(userCode, merType);
                                    } else if (MerPropertyEnum.CUSTOM.getCode().equals(merProperty)) { //自定义商户(操作员)
                                        merFunInfoList = merFunctionInfoService.findCustomOperatorFuns(userCode, merCode);
                                    }
                                }
                            }
                        }
                    }

                    if (!CollectionUtils.isEmpty(merFunInfoList)) {
                        List<MerFunctionInfoDTO> merFunInfoDTOList = new ArrayList<MerFunctionInfoDTO>();
                        for (MerFunctionInfo merFunInfoTemp : merFunInfoList) {
                            MerFunctionInfoDTO merFunInfoDTOTemp = new MerFunctionInfoDTO();
                            BeanUtils.copyProperties(merFunInfoTemp, merFunInfoDTOTemp);
                            merFunInfoDTOList.add(merFunInfoDTOTemp);
                        }
                        response.setResponseEntity(merFunInfoDTOList);
                    }
                }
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
    public DodopalResponse<Boolean> checkMerRoleNameExist(String merCode, String merRoleName, String id) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                return response;
            }
            if (StringUtils.isBlank(merRoleName)) {
                response.setCode(ResponseCode.USERS_MER_ROLE_NAME_NULL);
                return response;
            }
            boolean roleNameExist = merRoleService.checkMerRoleNameExist(merCode, merRoleName, id);
            if (roleNameExist) {
                response.setCode(ResponseCode.USERS_MER_ROLE_NAME_EXIST);
            }
            response.setResponseEntity(roleNameExist);
        }
        catch (Exception e) {
        	if(logger.isErrorEnabled()) {
        		logger.error(e.getMessage(), e);
        	}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    private String findMerRoleCheck(MerRoleDTO merRoleDTO) {
        // TODO：参数校验
        if (StringUtils.isBlank(merRoleDTO.getMerCode())) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String findMerRoleByPageCheck(MerRoleQueryDTO merRoleQueryDTO) {
        if (StringUtils.isBlank(merRoleQueryDTO.getMerCode())) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        if (StringUtils.isBlank(merRoleQueryDTO.getLoginUserId())) {
            return ResponseCode.LOGIN_USER_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String findMerRoleByMerRoleCodeCheck(String merCode, String merRoleCode) {
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        if (StringUtils.isBlank(merRoleCode)) {
            return ResponseCode.USERS_MER_ROLE_CODE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String addMerRoleCheck(MerRoleDTO merRoleDTO) {
        String merCode = merRoleDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        String merRoleName = merRoleDTO.getMerRoleName();
        if (StringUtils.isBlank(merRoleName)) {
            return ResponseCode.USERS_MER_ROLE_NAME_NULL;
        }
        String createUser = merRoleDTO.getCreateUser();
        if (StringUtils.isBlank(createUser)) {
            return ResponseCode.CREATE_USER_NULL;
        }

        boolean roleNameExist = merRoleService.checkMerRoleNameExist(merCode, merRoleName, null);
        if(roleNameExist) {
            return ResponseCode.USERS_MER_ROLE_NAME_EXIST;
        }

        merRoleDTO.setActivate(ActivateEnum.ENABLE.getCode());
        return ResponseCode.SUCCESS;
    }

    private String updateMerRoleCheck(MerRoleDTO merRoleDTO) {
        String id = merRoleDTO.getId();
        if(StringUtils.isBlank(id)) {
            return ResponseCode.ID_NULL;
        }
        String merCode = merRoleDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        String merRoleCode = merRoleDTO.getMerRoleCode();
        if (StringUtils.isBlank(merRoleCode)) {
            return ResponseCode.USERS_MER_ROLE_CODE_NULL;
        }
        String merRoleName = merRoleDTO.getMerRoleName();
        if (StringUtils.isBlank(merRoleName)) {
            return ResponseCode.USERS_MER_ROLE_NAME_NULL;
        }
        boolean roleNameExist = merRoleService.checkMerRoleNameExist(merCode, merRoleName, id);
        if(roleNameExist) {
            MerRole oldMerRole = merRoleService.findMerRoleByMerRoleCode(merCode, merRoleCode);
            if(!merRoleName.equals(oldMerRole.getMerRoleName())) {
                return ResponseCode.USERS_MER_ROLE_NAME_EXIST;
            }
        }
        String updateUser = merRoleDTO.getUpdateUser();
        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String delMerRoleByMerRoleCodeCheck(String merCode, String merRoleCode) {
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        if (StringUtils.isBlank(merRoleCode)) {
            return ResponseCode.USERS_MER_ROLE_CODE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String batchDelMerRoleByMerRoleCodeCheck(String merCode, List<String> merRoleCodes) {
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        if (CollectionUtils.isEmpty(merRoleCodes)) {
            return ResponseCode.USERS_MER_ROLE_CODE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

}

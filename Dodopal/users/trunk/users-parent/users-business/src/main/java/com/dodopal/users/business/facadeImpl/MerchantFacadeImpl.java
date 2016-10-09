package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.DirectMerChantDTO;
import com.dodopal.api.users.dto.MerAutoAmtDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerDdpInfoDTO;
import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerRoleDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantExtendDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.constant.DdicConstant;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.enums.PosStatusEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.constant.UsersConstants;
import com.dodopal.users.business.model.DirectMerChant;
import com.dodopal.users.business.model.MerAutoAmt;
import com.dodopal.users.business.model.MerBusRate;
import com.dodopal.users.business.model.MerDdpInfo;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.model.MerKeyType;
import com.dodopal.users.business.model.MerRateSupplement;
import com.dodopal.users.business.model.MerRole;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantExtend;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.Pos;
import com.dodopal.users.business.model.query.MerchantQuery;
import com.dodopal.users.business.model.query.MerchantUserQuery;
import com.dodopal.users.business.service.MerBusRateService;
import com.dodopal.users.business.service.MerDdpInfoService;
import com.dodopal.users.business.service.MerRateSupplementService;
import com.dodopal.users.business.service.MerRoleService;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.business.service.PosService;
import com.dodopal.users.business.service.SendService;
import com.dodopal.users.business.util.ResponseMsgUtil;

/**
 * 类说明 :
 * @author lifeng
 */
@Service("merchantFacade")
public class MerchantFacadeImpl implements MerchantFacade {
    private final static Logger logger = LoggerFactory.getLogger(MerchantFacadeImpl.class);

    @Autowired
    private MerDdpInfoService merDdpInfoService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantUserService merchantUserService;
    @Autowired
    private MerBusRateService merBusRateService;
    @Autowired
    private MerRoleService merRoleService;
    @Autowired
    private SendService sendService;
    @Autowired
    private PosService posService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private MerRateSupplementService merRateSupplementService;
    @Autowired
    private DdicService ddicService;

    private static final String[] IGNORE_FIELDS = {"merchantUserDTO", "merchantUser", "merBusRateList", "merDefineFunList", "merRateSpmtList", "merKeyType", "merKeyTypeDTO", "merDdpInfo", "merAutoAmt", "merAutoAmtDTO"};

    @Override
    public DodopalResponse<List<MerchantDTO>> findMerchant(MerchantDTO merchantDTO) {
        DodopalResponse<List<MerchantDTO>> response = new DodopalResponse<List<MerchantDTO>>();
        try {
            Merchant merchant = new Merchant();
            BeanUtils.copyProperties(merchantDTO, merchant, IGNORE_FIELDS);

            List<Merchant> resultList = merchantService.findMerchant(merchant);
            if (resultList != null && resultList.size() > 0) {
                List<MerchantDTO> responseResult = new ArrayList<MerchantDTO>(resultList.size());
                for (Merchant mer : resultList) {
                    MerchantDTO merDTO = new MerchantDTO();
                    BeanUtils.copyProperties(mer, merDTO, IGNORE_FIELDS);
                    responseResult.add(merDTO);
                }
                response.setResponseEntity(responseResult);
            }
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<MerchantDTO>> findMerchantByPage(MerchantQueryDTO merQueryDTO) {
        DodopalResponse<DodopalDataPage<MerchantDTO>> response = new DodopalResponse<DodopalDataPage<MerchantDTO>>();
        try {
            MerchantQuery merQuery = new MerchantQuery();
            BeanUtils.copyProperties(merQueryDTO, merQuery);

            if (merQuery.getPage() == null) {
                merQuery.setPage(new PageParameter());
            }

            DodopalDataPage<Merchant> ddpResult = merchantService.findMerchantByPage(merQuery);
            if (ddpResult != null) {
                List<Merchant> resultList = ddpResult.getRecords();
                List<MerchantDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<MerchantDTO>(resultList.size());
                    for (Merchant mer : resultList) {
                        MerchantDTO merDTO = new MerchantDTO();
                        BeanUtils.copyProperties(mer, merDTO, IGNORE_FIELDS);
                        resResultList.add(merDTO);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
                DodopalDataPage<MerchantDTO> ddpDTOResult = new DodopalDataPage<MerchantDTO>(page, resResultList);
                response.setResponseEntity(ddpDTOResult);
            }

            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> findMerchantByPageCount(MerchantQueryDTO merQueryDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            MerchantQuery merQuery = new MerchantQuery();
            BeanUtils.copyProperties(merQueryDTO, merQuery);

            int count = merchantService.findMerchantByPageCount(merQuery);
            response.setResponseEntity(count);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<MerchantDTO>> findMerchantByPageList(MerchantQueryDTO merQueryDTO) {
        DodopalResponse<List<MerchantDTO>> response = new DodopalResponse<List<MerchantDTO>>();
        try {
            MerchantQuery merQuery = new MerchantQuery();
            BeanUtils.copyProperties(merQueryDTO, merQuery);

            List<Merchant> resultList = merchantService.findMerchantByPageList(merQuery);
            if (!CollectionUtils.isEmpty(resultList)) {
                List<MerchantDTO> resResultList = new ArrayList<MerchantDTO>(resultList.size());
                for (Merchant mer : resultList) {
                    MerchantDTO merDTO = convertToMerchantDTO(mer);
                    resResultList.add(merDTO);
                }
                response.setResponseEntity(resResultList);
            }
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<MerchantDTO> findMerchantById(String id) {
        DodopalResponse<MerchantDTO> response = new DodopalResponse<MerchantDTO>();
        try {
            Merchant mer = merchantService.findMerchantById(id);
            MerchantDTO merDTO = new MerchantDTO();
            BeanUtils.copyProperties(mer, merDTO);
            response.setResponseEntity(merDTO);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<MerchantDTO> findMerchantByMerCode(String merCode) {
        DodopalResponse<MerchantDTO> response = new DodopalResponse<MerchantDTO>();
        try {
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_MER_CODE_NULL);
                return response;
            }
            Merchant merchant = merchantService.findMerchantByMerCode(merCode);
            if (merchant == null) {
                response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                return response;
            }
            MerchantDTO merchantDTO = convertToMerchantDTO(merchant);
            response.setResponseEntity(merchantDTO);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<MerchantDTO> findMerchantInfoByMerCode(String merCode) {
        DodopalResponse<MerchantDTO> response = new DodopalResponse<MerchantDTO>();
        try {
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_MER_CODE_NULL);
                return response;
            }
            Merchant merchant = merchantService.findMerchantInfoByMerCode(merCode);
            if (merchant == null) {
                response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                return response;
            }
            MerchantDTO merchantDTO = convertToMerchantDTO(merchant);
            response.setResponseEntity(merchantDTO);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<MerchantDTO>> findMerchantRelationByMerCode(String merCode) {
        DodopalResponse<List<MerchantDTO>> response = new DodopalResponse<List<MerchantDTO>>();
        if (StringUtils.isBlank(merCode)) {
            response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
            return response;
        }
        response.setCode(ResponseCode.SUCCESS);
        try {
            List<Merchant> merchantList = merchantService.findMerchantRelationByMerCode(merCode);
            if (merchantList != null && merchantList.size() > 0) {
                List<MerchantDTO> resultList = new ArrayList<MerchantDTO>(merchantList.size());
                for (Merchant merTemp : merchantList) {
                    MerchantDTO merchantDTO = convertToMerchantDTO(merTemp);
                    resultList.add(merchantDTO);
                }
                response.setResponseEntity(resultList);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> updateMerchantActivate(String activate, List<String> merCodes, String updateUser) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        if (StringUtils.isBlank(activate) || ActivateEnum.getActivateByCode(activate) == null) {
            response.setCode(ResponseCode.ACTIVATE_ERROR);
        }
        if (CollectionUtils.isEmpty(merCodes)) {
            response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
        }
        List<String> msg = new ArrayList<String>();
        try {
            int updateNum = merchantService.batchUpdateMerchantActivate(activate, merCodes, msg, updateUser);
            response.setResponseEntity(updateNum);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        if (msg.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("商户");
            for (String msgStr : msg) {
                sb.append(msgStr);
            }
            sb.append("无法启用，请先启用相应上级商户");
            response.setCode(ResponseCode.USERS_PARENT_MERCHANT_DISABLE);
            response.setMessage(sb.toString());
        }
        return response;
    }

    @Override
    public DodopalResponse<String> merchantRegister(MerchantDTO merchantDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            // 1、校验参数
            List<String> msg = new ArrayList<String>();
            String resCode = registerCheck(merchantDTO, msg);
            // 2、构建merchant
            if (ResponseCode.SUCCESS.equals(resCode)) {
                Merchant merchant = buildMerchant(merchantDTO);
                // 3、调用服务
                AtomicReference<String> randomPWD = new AtomicReference<String>(); // 随机密码
                DodopalResponse<String> merResponse = merchantService.register(merchant, randomPWD);
                if (ResponseCode.SUCCESS.equals(merResponse.getCode())) {
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(merResponse.getResponseEntity());
                    // 开户成功发送随机生成的秘密
                    try {
                        String sendFlag = DodopalAppVarPropsUtil.getStringProp(UsersConstants.MERCHANT_REGISTER_SENDMSG);
                        String sendMobile = merchant.getMerLinkUserMobile();
                        if (Boolean.parseBoolean(sendFlag)) {
                            String testMobile = DodopalAppVarPropsUtil.getStringProp(UsersConstants.MERCHANT_REGISTER_TEST_MOBILE);
                            if (StringUtils.isNotBlank(testMobile) && DDPStringUtil.isMobile(testMobile)) {
                                sendMobile = testMobile;
                            }
                            DodopalResponse<Map<String, String>> sendResponse = sendService.send(sendMobile, MoblieCodeTypeEnum.USER_PWD, randomPWD.get(), false);
                            if (ResponseCode.SUCCESS.equals(sendResponse.getCode())) {
                                if (logger.isInfoEnabled()) {
                                    logger.info("pwd msg send success,mobile:" + sendMobile);
                                }
                            } else {
                                logger.warn("pwd msg send fail,mobile:" + sendMobile);
                            }
                        }
                    }
                    catch (Exception e) {
                        if (logger.isErrorEnabled()) {
                            logger.error("pwd msg send fail", e);
                        }
                    }
                } else {
                    response.setCode(merResponse.getCode());
                }
            } else {
                response.setCode(resCode);
                if (ResponseCode.USERS_MER_RATE_OVER_PARENT.equals(resCode) || ResponseCode.USERS_PARENT_RATE_NOT_FOUND.equals(resCode)) {
                    response.setMessage(ResponseMsgUtil.formatMerBusRateMsg(msg));
                }
            }
        }
        catch (HessianRuntimeException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> providerRegister(MerchantDTO merchantDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            // 1、校验参数
            String resCode = providerRegisterCheck(merchantDTO);
            // 2、构建merchant
            if (ResponseCode.SUCCESS.equals(resCode)) {
                Merchant merchant = buildMerchant(merchantDTO);
                // 3、调用服务
                AtomicReference<String> randomPWD = new AtomicReference<String>(); // 随机密码
                DodopalResponse<String> merResponse = merchantService.register(merchant, randomPWD);
                if (ResponseCode.SUCCESS.equals(merResponse.getCode())) {
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(merResponse.getResponseEntity());
                } else {
                    response.setCode(merResponse.getCode());
                }
            } else {
                response.setCode(resCode);
            }
        }
        catch (HessianRuntimeException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> providerUpdate(MerchantDTO merchantDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String resCode = providerUpdateCheck(merchantDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                Merchant merchant = buildMerchant(merchantDTO);
                int num = merchantService.updateMerchantProvider(merchant);
                if (num > 0) {
                    logger.info("merCode [" + merchantDTO.getMerCode() + "] update success, " + num);
                } else {
                    logger.warn("merCode [" + merchantDTO.getMerCode() + "] update fail, " + num);
                    response.setCode(ResponseCode.USERS_MER_PROVIDER_UPDATE_ERROR);
                    return response;
                }
            }
            response.setCode(resCode);
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> merchantAudit(MerchantDTO merchantDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            // 1、校验参数
            List<String> msg = new ArrayList<String>();
            String resCode = auditCheck(merchantDTO, msg);
            // 2、构建merchant
            if (ResponseCode.SUCCESS.equals(resCode)) {
                Merchant merchant = buildMerchant(merchantDTO);
                // 3、调用服务
                if (MerStateEnum.REJECT.getCode().equals(merchant.getMerState())) {
                    merchantService.rejectMerchantReg(merchant);
                } else {
                    merchantService.updateMerchant(merchant);
                }
            } else {
                response.setCode(resCode);
                if (ResponseCode.USERS_MER_RATE_OVER_PARENT.equals(resCode) || ResponseCode.USERS_PARENT_RATE_NOT_FOUND.equals(resCode) || ResponseCode.USERS_BELOW_CHILD_RATE.equals(resCode)) {
                    response.setMessage(ResponseMsgUtil.formatMerBusRateMsg(msg));
                }
            }
        }
        catch (HessianRuntimeException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> batchDelRejectMerchantByMerCodes(List<String> merCodes, String updateUser) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = batchDelRejectMerchantByMerCodesCheck(merCodes, updateUser);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                if (logger.isInfoEnabled()) {
                    logger.info("updateUser:" + updateUser + ",del merCodes:" + merCodes);
                }
                int num = merchantService.batchDelMerchantByMerCodes(merCodes);
                response.setResponseEntity(num);
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<MerchantUserDTO>> findMerOperators(MerchantUserDTO merchantUserDTO) {
        DodopalResponse<List<MerchantUserDTO>> response = new DodopalResponse<List<MerchantUserDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = findMerOperatorsCheck(merchantUserDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerchantUser merUser = new MerchantUser();
                BeanUtils.copyProperties(merchantUserDTO, merUser);
                List<MerchantUser> merUserList = merchantService.findMerOperators(merUser);
                if (merUserList != null && merUserList.size() > 0) {
                    List<MerchantUserDTO> merUserDTOList = new ArrayList<MerchantUserDTO>(merUserList.size());
                    for (MerchantUser merUserTemp : merUserList) {
                        MerchantUserDTO merchantUserDTOTemp = new MerchantUserDTO();
                        BeanUtils.copyProperties(merUserTemp, merchantUserDTOTemp);
                        merUserDTOList.add(merchantUserDTOTemp);
                    }
                    response.setResponseEntity(merUserDTOList);
                }
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findMerOperatorByPage(MerchantUserQueryDTO userQueryDTO) {
        DodopalResponse<DodopalDataPage<MerchantUserDTO>> response = new DodopalResponse<DodopalDataPage<MerchantUserDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = findMerOperatorByPageCheck(userQueryDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerchantUser loginUser = merchantUserService.findMerchantUserById(userQueryDTO.getLoginUserId());
                if (loginUser == null) {
                    response.setCode(ResponseCode.LOGIN_USER_NULL);
                    return response;
                }

                MerchantUserQuery userQuery = new MerchantUserQuery();
                BeanUtils.copyProperties(userQueryDTO, userQuery);
                // 如果是管理员，可以查询所有操作员
                if (MerUserFlgEnum.ADMIN.getCode().equals(loginUser.getMerUserFlag())) {
                    userQuery.setLoginUserId(null);
                }

                DodopalDataPage<MerchantUser> userPageList = merchantUserService.findMerOperatorByPage(userQuery);
                List<MerchantUser> userList = userPageList.getRecords();
                if (!CollectionUtils.isEmpty(userList)) {
                    List<MerchantUserDTO> userDtoList = new ArrayList<MerchantUserDTO>(userList.size());
                    for (MerchantUser tempUser : userList) {
                        MerchantUserDTO userTempDTO = new MerchantUserDTO();
                        BeanUtils.copyProperties(tempUser, userTempDTO);
                        userDtoList.add(userTempDTO);
                    }
                    PageParameter page = DodopalDataPageUtil.convertPageInfo(userPageList);
                    DodopalDataPage<MerchantUserDTO> pages = new DodopalDataPage<MerchantUserDTO>(page, userDtoList);
                    response.setResponseEntity(pages);
                }
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<MerchantUserDTO> findMerOperatorByUserCode(String merCode, String userCode) {
        DodopalResponse<MerchantUserDTO> response = new DodopalResponse<MerchantUserDTO>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = findMerOperatorByUserCodeCheck(merCode, userCode);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerchantUser merUser = merchantService.findMerOperatorByUserCode(merCode, userCode);
                if (merUser != null) {
                    MerchantUserDTO merUserDTO = new MerchantUserDTO();
                    if (StringUtils.isNotBlank(merUser.getMerUserPro())) {
                        Area provice = areaService.findCityByCityCode(merUser.getMerUserPro());
                        if (provice != null) {
                            merUser.setMerUserProName(provice.getCityName());
                        }
                    }
                    if (StringUtils.isNotBlank(merUser.getMerUserCity())) {
                        Area city = areaService.findCityByCityCode(merUser.getMerUserCity());
                        if (city != null) {
                            merUser.setMerUserCityName(city.getCityName());
                        }
                    }
                    BeanUtils.copyProperties(merUser, merUserDTO);

                    List<MerRole> merUserRoleList = merRoleService.findMerRoleByUserCode(userCode);
                    if (!CollectionUtils.isEmpty(merUserRoleList)) {
                        List<MerRoleDTO> merUserRoleDTOList = new ArrayList<MerRoleDTO>(merUserRoleList.size());
                        for (MerRole merRoleTemp : merUserRoleList) {
                            MerRoleDTO merRoleDTOTemp = new MerRoleDTO();
                            BeanUtils.copyProperties(merRoleTemp, merRoleDTOTemp);
                            merUserRoleDTOList.add(merRoleDTOTemp);
                        }
                        merUserDTO.setMerUserRoleList(merUserRoleDTOList);
                    }

                    response.setResponseEntity(merUserDTO);
                }
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> addMerOperator(MerchantUserDTO merchantUserDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        int num = 0;
        try {
            String resCode = addMerOperatorCheck(merchantUserDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerchantUser merUser = new MerchantUser();
                BeanUtils.copyProperties(merchantUserDTO, merUser);
                Merchant merchant = merchantService.findMerchantInfoByMerCode(merchantUserDTO.getMerCode());
                if (merchant == null) {
                    if (logger.isInfoEnabled()) {
                        logger.info("merchant not found, merCode:" + merchantUserDTO.getMerCode());
                    }
                } else {
                    String merClassify = merchant.getMerClassify();
                    if (StringUtils.isNotBlank(merClassify)) {
                        boolean bool = merchantUserService.insertMerchantUser(merUser, MerClassifyEnum.OFFICIAL.getCode().equals(merClassify) ? UserClassifyEnum.MERCHANT : UserClassifyEnum.MERCHANT_TEST);
                        if (bool) {
                            num = 1;
                        }
                    }
                }
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        response.setResponseEntity(num);
        return response;
    }

    @Override
    public DodopalResponse<Integer> updateMerOperator(MerchantUserDTO merchantUserDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        int num = 0;
        try {
            String resCode = updateMerOperatorCheck(merchantUserDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerchantUser merUser = new MerchantUser();
                BeanUtils.copyProperties(merchantUserDTO, merUser);
                num = merchantUserService.updateMerchantUserPortal(merUser);
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        response.setResponseEntity(num);
        return response;
    }

    @Override
    public DodopalResponse<Integer> batchActivateMerOperator(String merCode, String updateUser, ActivateEnum activate, List<String> userCodes) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        int num = 0;
        try {
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_MER_CODE_NULL);
                return response;
            }

            if (activate == null) {
                response.setCode(ResponseCode.ACTIVATE_ERROR);
                return response;
            }

            if (CollectionUtils.isEmpty(userCodes)) {
                response.setResponseEntity(num);
                return response;
            }

            num = merchantUserService.batchActivateUser(merCode, updateUser, activate, userCodes);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        response.setResponseEntity(num);
        return response;
    }

    @Override
    public DodopalResponse<Integer> configMerOperatorRole(MerchantUserDTO merchantUserDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        int num = 0;
        try {
            String resCode = configMerOperatorRoleCheck(merchantUserDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerchantUser merUser = new MerchantUser();
                merUser.setMerCode(merchantUserDTO.getMerCode());
                merUser.setUserCode(merchantUserDTO.getUserCode());
                merUser.setUpdateUser(merchantUserDTO.getUpdateUser());

                List<MerRoleDTO> merUserRoleDTOList = merchantUserDTO.getMerUserRoleList();
                List<MerRole> merUserRoleList = new ArrayList<MerRole>();
                for (MerRoleDTO merRoleDTOTemp : merUserRoleDTOList) {
                    MerRole merRoleTemp = new MerRole();
                    BeanUtils.copyProperties(merRoleDTOTemp, merRoleTemp);
                    merUserRoleList.add(merRoleTemp);
                }
                merUser.setMerUserRoleList(merUserRoleList);
                num = merchantService.configMerOperatorRole(merUser);
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        response.setResponseEntity(num);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> resetOperatorPwd(String merCode, String id, String password, String updateUser) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = resetOperatorPwdCheck(merCode, id, password, updateUser);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                MerchantUser merUser = new MerchantUser();
                merUser.setId(id);
                merUser.setMerCode(merCode);
                merUser.setMerUserPWD(password);
                merUser.setUpdateUser(updateUser);
                boolean bool = merchantUserService.resetPWD(merUser);
                response.setResponseEntity(bool);
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateMerchantForPortal(MerchantDTO merchantDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String resCode = updateMerchantForPortalCheck(merchantDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                Merchant merchant = new Merchant();
                BeanUtils.copyProperties(merchantDTO, merchant, IGNORE_FIELDS);

                MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
                MerchantUser merUser = new MerchantUser();
                BeanUtils.copyProperties(merUserDTO, merUser);
                merchant.setMerchantUser(merUser);

                merchantService.updateMerchantForPortal(merchant);
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<MerBusRateDTO>> findMerBusRateByMerCode(String merCode) {
        DodopalResponse<List<MerBusRateDTO>> response = new DodopalResponse<List<MerBusRateDTO>>();
        try {
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                return response;
            }

            List<MerBusRate> merBusRateList = merchantService.findMerBusRateByMerCode(merCode);
            if (!CollectionUtils.isEmpty(merBusRateList)) {
                List<MerBusRateDTO> merBusRateDTOList = new ArrayList<MerBusRateDTO>();
                for (MerBusRate merBusRateTemp : merBusRateList) {
                    MerBusRateDTO merBusRateDTOTemp = new MerBusRateDTO();
                    BeanUtils.copyProperties(merBusRateTemp, merBusRateDTOTemp);
                    merBusRateDTOList.add(merBusRateDTOTemp);
                }
                response.setResponseEntity(merBusRateDTOList);
            }

            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkRelationMerchantProviderAndRateType(MerchantDTO merchantDTO) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        response.setResponseEntity(false);
        response.setCode(ResponseCode.USERS_RELATION_MERCHANT_NOT_SAME);
        try {
            String resCode = relationMerchantProviderAndRateTypeCheck(merchantDTO);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                // 当前传入商户费率
                List<MerBusRateDTO> merBusRateList = merchantDTO.getMerBusRateList();
                // 1、判断是否与上级商户费率类型不一致
                Merchant curMer = merchantService.findMerchantInfoByMerCode(merchantDTO.getMerCode());
                if (StringUtils.isNotBlank(curMer.getMerParentCode())) {
                    List<MerBusRate> merParentBusRateList = merchantService.findMerBusRateByMerCode(curMer.getMerParentCode());
                    if (CollectionUtils.isEmpty(merParentBusRateList) && CollectionUtils.isEmpty(merBusRateList)) {
                        // Do nothing
                    } else if (!CollectionUtils.isEmpty(merParentBusRateList) && !CollectionUtils.isEmpty(merBusRateList)) {
                        for (MerBusRateDTO childMerBusRate : merBusRateList) {
                            for (MerBusRate parentMerBusRate : merParentBusRateList) {
                                if (parentMerBusRate.getProviderCode().equals(childMerBusRate.getProviderCode()) && parentMerBusRate.getRateCode().equals(childMerBusRate.getRateCode())) {
                                    if (!parentMerBusRate.getRateType().equals(childMerBusRate.getRateType())) {
                                        return response;
                                    }
                                }
                            }
                        }
                    } else {
                        return response;
                    }
                }

                // 2、少于下级通卡公司数量，或者与下级费率类型不一致
                Merchant findChildMer = new Merchant();
                findChildMer.setMerParentCode(merchantDTO.getMerCode());
                findChildMer.setMerState(MerStateEnum.THROUGH.getCode());
                List<Merchant> childMerchantList = merchantService.findMerchant(findChildMer);
                if (!CollectionUtils.isEmpty(childMerchantList)) {
                    List<String> childMerCodes = new ArrayList<String>();
                    for (Merchant childMer : childMerchantList) {
                        childMerCodes.add(childMer.getMerCode());
                    }
                    // 分组查询：通卡code,业务code,类型code
                    List<MerBusRate> childMerBusRateList = merBusRateService.findChildMerProRateCodeType(childMerCodes);
                    if (CollectionUtils.isEmpty(childMerBusRateList) && CollectionUtils.isEmpty(merBusRateList)) {
                        // Do nothing
                    } else if (!CollectionUtils.isEmpty(childMerBusRateList) && !CollectionUtils.isEmpty(merBusRateList)) {
                        Map<String, MerBusRateDTO> curMap = new HashMap<String, MerBusRateDTO>();
                        for (MerBusRateDTO parentMerBusRate : merBusRateList) {
                            String key = parentMerBusRate.getProviderCode() + "|" + parentMerBusRate.getRateCode();
                            curMap.put(key, parentMerBusRate);
                        }
                        for (MerBusRate childMerBusRate : childMerBusRateList) {
                            String key = childMerBusRate.getProviderCode() + "|" + childMerBusRate.getRateCode();
                            // 先判断下级通卡是否超过当前商户通卡
                            if (!curMap.containsKey(key)) {
                                return response;
                            } else {
                                // 判断类型是否不一致
                                MerBusRateDTO parentMerBusRate = curMap.get(key);
                                if (!parentMerBusRate.getRateType().equals(childMerBusRate.getRateType())) {
                                    return response;
                                }
                            }
                        }
                    } else {
                        return response;
                    }
                }
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
            return response;
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(true);
        return response;
    }

    @Override
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcRecharge(String merchantNum, String userId, String posId, String providerCode, String source) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        try {
            Map<String, String> returnParam = new HashMap<String, String>();
            String resCode = validateMerchantForIcdcCheck(returnParam, merchantNum, userId, posId, providerCode, RateCodeEnum.YKT_RECHARGE, source);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                response.setResponseEntity(returnParam);
            }
            response.setCode(resCode);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcPurchase(String merchantNum, String userId, String posId, String providerCode, String source) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        try {
            Map<String, String> returnParam = new HashMap<String, String>();
            String resCode = validateMerchantForIcdcCheck(returnParam, merchantNum, userId, posId, providerCode, RateCodeEnum.YKT_PAYMENT, source);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                response.setResponseEntity(returnParam);
            }
            response.setCode(resCode);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Map<String, String>> checkMerInfo(String merCode) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        try {
            Map<String, String> returnParam = new HashMap<String, String>();
            String resCode = checkMerAutoInfo(returnParam, merCode);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                response.setResponseEntity(returnParam);
            }
            response.setCode(resCode);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;

    }

    @Override
    public DodopalResponse<Map<String, Object>> validateMerchantForPayment(MerUserTypeEnum userType, String code) {
        DodopalResponse<Map<String, Object>> response = new DodopalResponse<Map<String, Object>>();
        try {
            String resCode = validateMerchantForPaymentCheck(userType, code);
            response.setCode(resCode);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcLoad(MerUserTypeEnum userType, String code, String providerCode) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        try {
            Map<String, String> returnParam = new HashMap<String, String>();
            String resCode = validateMerchantForLoadCheck(returnParam, userType, code, providerCode);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                response.setResponseEntity(returnParam);
            }
            response.setCode(resCode);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Map<String, Object>> validateMerchantUserForPayment(String userCode) {
        DodopalResponse<Map<String, Object>> response = new DodopalResponse<Map<String, Object>>();
        try {
            String resCode = validateMerchantUserForPaymentCheck(userCode);
            response.setCode(resCode);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<Area>> findMerCitys(String merCode) {
        DodopalResponse<List<Area>> response = new DodopalResponse<List<Area>>();
        try {
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                return response;
            }

            Merchant merchant = merchantService.findMerchantInfoByMerCode(merCode);
            if (merchant == null) {
                response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                return response;
            }

            String merType = merchant.getMerType();
            if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
                merCode = merchant.getMerParentCode();
            }

            List<String> merCityCodes = merBusRateService.findMerCitys(merCode);
            List<Area> merCitys = new ArrayList<Area>();
            if (!CollectionUtils.isEmpty(merCityCodes)) {
                for (String cityCode : merCityCodes) {
                    Area area = areaService.findCityByCityCode(cityCode);
                    if (area != null) {
                        merCitys.add(area);
                    }
                }
            }
            response.setResponseEntity(merCitys);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<Area>> findMerCitysPayment(String merCode) {
        DodopalResponse<List<Area>> response = new DodopalResponse<List<Area>>();
        try {
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                return response;
            }

            Merchant merchant = merchantService.findMerchantInfoByMerCode(merCode);
            if (merchant == null) {
                response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                return response;
            }

            String merType = merchant.getMerType();
            if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
                merCode = merchant.getMerParentCode();
            }

            List<String> merCityCodes = merBusRateService.findMerCitysPayment(merCode);
            List<Area> merCitys = new ArrayList<Area>();
            if (!CollectionUtils.isEmpty(merCityCodes)) {
                for (String cityCode : merCityCodes) {
                    Area area = areaService.findCityByCityCode(cityCode);
                    if (area != null) {
                        merCitys.add(area);
                    }
                }
            }
            response.setResponseEntity(merCitys);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<Area>> findYktCitys(MerUserTypeEnum custType, String custCode) {
        DodopalResponse<List<Area>> response = new DodopalResponse<List<Area>>();
        try {
            String resCode = findYktCitysCheck(custType, custCode);
            if (ResponseCode.SUCCESS.equals(resCode)) {
                List<String> merCityCodes = null;
                if (MerUserTypeEnum.MERCHANT == custType) {
                    Merchant merchant = merchantService.findMerchantInfoByMerCode(custCode);
                    if (merchant == null) {
                        response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                        return response;
                    }
                    String merType = merchant.getMerType();
                    if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
                        custCode = merchant.getMerParentCode();
                    }
                    merCityCodes = merBusRateService.findMerCitys(custCode);
                } else if (MerUserTypeEnum.PERSONAL == custType) {
                    merCityCodes = merBusRateService.findUserCitys();
                }
                if (!CollectionUtils.isEmpty(merCityCodes)) {
                    List<Area> merCitys = new ArrayList<Area>();
                    if (!CollectionUtils.isEmpty(merCityCodes)) {
                        for (String cityCode : merCityCodes) {
                            Area area = areaService.findCityByCityCode(cityCode);
                            if (area != null) {
                                String yktCode = merBusRateService.findYktCodeByCityCode(cityCode);
                                area.setBzCityCode(yktCode);
                                merCitys.add(area);
                            }
                        }
                    }
                    response.setResponseEntity(merCitys);
                }
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(resCode);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<MerRateSupplementDTO>> findMerRateSupplementsByMerCode(String merCode) {
        DodopalResponse<List<MerRateSupplementDTO>> response = new DodopalResponse<List<MerRateSupplementDTO>>();
        try {
            if (StringUtils.isBlank(merCode)) {
                response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                return response;
            }
            List<MerRateSupplement> findResult = merRateSupplementService.findMerRateSpmtsByMerCode(merCode);
            if (!CollectionUtils.isEmpty(findResult)) {
                List<MerRateSupplementDTO> resultList = new ArrayList<MerRateSupplementDTO>();
                for (MerRateSupplement temp : findResult) {
                    MerRateSupplementDTO tempDTO = new MerRateSupplementDTO();
                    BeanUtils.copyProperties(temp, tempDTO);
                    resultList.add(tempDTO);
                }
                response.setResponseEntity(resultList);
            }
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    // -----------------------------------------------------------------------------------------TODO:参数校验
    private String relationMerchantProviderAndRateTypeCheck(MerchantDTO merchantDTO) {
        String merCode = merchantDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }

        return ResponseCode.SUCCESS;
    }

    private String auditCheck(MerchantDTO merchantDTO, List<String> msg) {
        // 商户号
        String merCode = merchantDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
        if (merUserDTO == null) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        Merchant merchantOld = merchantService.findMerchantByMerCode(merCode);
        if (merchantOld == null) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        MerchantUser merUserOld = merchantOld.getMerchantUser();
        if (merUserOld == null) {
            return ResponseCode.USERS_FIND_USER_ERR;
        }

        // 审核状态
        String merState = merchantDTO.getMerState();
        if (StringUtils.isBlank(merState)) {
            return ResponseCode.USERS_MER_STATE_ERR;
        }
        // 更新人不能为空
        String updateUser = merchantDTO.getUpdateUser();
        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }
        if (!merState.equals(merchantOld.getMerState())) {
            // 审核人
            String stateUser = merchantDTO.getMerStateUser();
            if (StringUtils.isBlank(stateUser)) {
                return ResponseCode.USERS_MER_STATE_USER_NULL;
            }
            merchantDTO.setUpdateUser(stateUser);
            // 审核时间
            merchantDTO.setMerStateDate(new Date());

            // 用户审核状态
            merUserDTO.setMerUserState(merState);
        }

        /*
         * 用户名 1、必填 2、包含数字、英文字母、下划线，必须字母开头 3、4<=长度<=20位字符 4、唯一
         */
        String merUserName = merUserDTO.getMerUserName();
        if (StringUtils.isBlank(merUserName)) {
            return ResponseCode.USERS_USER_NAME_ERR;
        }
        if (!merUserOld.getMerUserName().equals(merUserName)) {
            MerchantUser checkUserName = new MerchantUser();
            checkUserName.setMerUserName(merUserName);
            if (merchantUserService.checkExist(checkUserName)) {
                return ResponseCode.USERS_MER_USER_NAME_EXIST;
            }
        }

        // --------------------TODO:如果审核不通过，后边的参数不再校验
        if (MerStateEnum.REJECT.getCode().equals(merState)) {
            return ResponseCode.SUCCESS;
        }

        // 账户类型
        String fundType = merchantDTO.getFundType();
        if (StringUtils.isBlank(fundType)) {
            return ResponseCode.USERS_FUND_TYPE_NULL;
        }

        // 用户编号不能为空
        String userCode = merUserDTO.getUserCode();
        if (StringUtils.isBlank(userCode)) {
            // return ResponseCode.USERS_USER_CODE_NULL;
        }

        /*
         * 商户名称 1、必填 2、全局唯一 3、中文、英文、数字 4、长度<=200字符
         */
        String merName = merchantDTO.getMerName();
        // 已审核编辑时商户名称不能修改
        if (MerStateEnum.THROUGH.getCode().equals(merchantOld.getMerState())) {
            merchantDTO.setMerName(null);
        } else {
            if (StringUtils.isBlank(merName)) {
                return ResponseCode.USERS_MER_NAME_NULL;
            }
            if (!merchantOld.getMerName().equals(merName)) {
                Merchant checkMerName = new Merchant();
                checkMerName.setMerName(merName);
                boolean merNameExist = merchantService.checkExist(checkMerName);
                if (merNameExist) {
                    return ResponseCode.USERS_MER_NAME_EXIST;
                }
            }
        }

        /*
         * 商户类型 1、必填
         */
        String merType = merchantDTO.getMerType();
        if (StringUtils.isBlank(merType) || MerTypeEnum.getMerTypeByCode(merType) == null) {
            return ResponseCode.USERS_MER_TYPE_ERR;
        }

        /*
         * 商户分类 1、必填，默认为正式商户
         */
        String merClassify = merchantDTO.getMerClassify();
        if (StringUtils.isBlank(merClassify) || MerClassifyEnum.getMerClassifyByCode(merClassify) == null) {
            return ResponseCode.USERS_MER_CLASSIFY_ERR;
        }

        /*
         * 商户属性 1、必填，默认为标准商户
         */
        String merProperty = merchantDTO.getMerProperty();
        if (StringUtils.isBlank(merProperty) || MerPropertyEnum.getMerPropertyByCode(merProperty) == null) {
            return ResponseCode.USERS_MER_PROPERTY_ERR;
        }

        /*
         * 联系人 1、必填 2、中文、英文 3、2<=长度<=20字符
         */
        String merLinkUser = merchantDTO.getMerLinkUser();
        if (StringUtils.isBlank(merLinkUser)) {
            return ResponseCode.USERS_MER_LINK_USER_NULL;
        }

        /*
         * 手机号码 1、必填 2、全局唯一 3、只能输入数字 4、长度=11
         */
        String merLinkUserMobile = merchantDTO.getMerLinkUserMobile();
        if (StringUtils.isBlank(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_NULL;
        }
        if (!DDPStringUtil.isMobile(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_ERR;
        }
        if (!merLinkUserMobile.equals(merchantOld.getMerLinkUserMobile()) || MerStateEnum.REJECT.getCode().equals(merchantOld.getMerState())) {
            MerchantUser checkUserMobile = new MerchantUser();
            checkUserMobile.setMerUserMobile(merLinkUserMobile);
            if (merchantUserService.checkExist(checkUserMobile)) {
                return ResponseCode.USERS_MOB_EXIST;
            }
        }

        String merPro = merchantDTO.getMerPro();
        String merCity = merchantDTO.getMerCity();
        String merAdds = merchantDTO.getMerAdds();
        if (StringUtils.isBlank(merPro)) {
            return ResponseCode.USERS_MER_PRO_NULL;
        }
        if (StringUtils.isBlank(merCity)) {
            return ResponseCode.USERS_MER_CITY_NULL;
        }
        if (StringUtils.isBlank(merAdds)) {
            return ResponseCode.USERS_MER_ADDS_NULL;
        }

        // 上级商户CODE
        String merParentCode = merchantDTO.getMerParentCode();
        if (MerTypeEnum.AGENT_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType)) {
            if (StringUtils.isBlank(merParentCode) || merParentCode.equalsIgnoreCase("null")) {
                return ResponseCode.USERS_MER_PARENT_CODE_NULL;
            } else {
                Merchant parentMer = merchantService.findMerchantInfoByMerCode(merParentCode);
                if (parentMer == null) {
                    return ResponseCode.USERS_FIND_PARENT_MERCHANT_ERR;
                }
                String merParentType = parentMer.getMerType();
                if (MerTypeEnum.AGENT.getCode().equals(merParentType)) {
                    // 代理商户可以创建代理商户、连锁商户、代理商自有网点
                    if (!(MerTypeEnum.AGENT.getCode().equals(merType) || MerTypeEnum.CHAIN.getCode().equals(merType) || MerTypeEnum.AGENT_MER.getCode().equals(merType))) {
                        return ResponseCode.USERS_MERCHANT_NOT_ALLOWED_CREATE;
                    }
                } else if (MerTypeEnum.CHAIN.getCode().equals(merParentType)) {
                    // 连锁商户可以创建连锁直营网点、连锁加盟网点
                    if (!(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType))) {
                        return ResponseCode.USERS_MERCHANT_NOT_ALLOWED_CREATE;
                    }
                }
            }
        } else if (MerTypeEnum.CHAIN.getCode().equals(merType) || MerTypeEnum.AGENT.getCode().equals(merType)) {
            if (StringUtils.isNotBlank(merParentCode)) {
                Merchant parentMer = merchantService.findMerchantInfoByMerCode(merParentCode);
                if (parentMer == null) {
                    return ResponseCode.USERS_FIND_PARENT_MERCHANT_ERR;
                }
            }
        } else {
            if (StringUtils.isNotBlank(merParentCode)) {
                if (merParentCode.equalsIgnoreCase("null")) {
                    merchantDTO.setMerParentCode(null);
                } else {
                    return ResponseCode.USERS_FIND_PARENT_MERCHANT_ERR;
                }
            }
        }

        // 启用标志
        String activate = merchantDTO.getActivate();
        if (StringUtils.isBlank(activate) || ActivateEnum.getActivateByCode(activate) == null) {
            return ResponseCode.ACTIVATE_ERROR;
        }

        // 删除标志(默认正常)
        merchantDTO.setDelFlg(DelFlgEnum.NORMAL.getCode());
        // 真实姓名
        merUserDTO.setMerUserNickName(merLinkUser);

        // 连锁直营网点不需要判断费率，使用时直接取上级费率
        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            List<MerBusRateDTO> merBusRateDTOList = merchantDTO.getMerBusRateList();
            if (!CollectionUtils.isEmpty(merBusRateDTOList)) {
                if (StringUtils.isNotBlank(merParentCode)) {
                    List<MerBusRate> parentMerBusRateList = merBusRateService.findMerBusRateByMerCode(merParentCode);
                    List<String> overMsg = new ArrayList<String>();
                    List<String> notFoundMsg = new ArrayList<String>();
                    for (MerBusRateDTO merBusRateNew : merBusRateDTOList) {
                        String providerCode = merBusRateNew.getProviderCode();
                        String rateCode = merBusRateNew.getRateCode();
                        String rateType = merBusRateNew.getRateType();
                        if (StringUtils.isBlank(providerCode)) {
                            return ResponseCode.USERS_PROVIDER_CODE_NULL;
                        }
                        if (StringUtils.isBlank(rateCode)) {
                            return ResponseCode.USERS_RATE_CODE_NULL;
                        }
                        if (StringUtils.isBlank(rateType)) {
                            return ResponseCode.USERS_RATE_TYPE_NULL;
                        }
                        if (RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCode)) {
                            continue;
                        }
                        boolean isFound = false;
                        for (MerBusRate merBusRateParent : parentMerBusRateList) {
                            String rateCodeOld = merBusRateParent.getRateCode();
                            if (!RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCodeOld)) {
                                if (rateCode.equals(merBusRateParent.getRateCode()) && providerCode.equals(merBusRateParent.getProviderCode())) {
                                    // 如果是已审核商户，不再判断费率类型必须一致，只判断相同类型的数值是否超过上级
                                    if (MerStateEnum.THROUGH.getCode().equals(merchantOld.getMerState())) {
                                        isFound = true;
                                        if (rateType.equals(merBusRateParent.getRateType())) {
                                            double rateNew = merBusRateNew.getRate();
                                            double rateOld = merBusRateParent.getRate();
                                            if (rateNew > rateOld) {
                                                overMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
                                            }
                                        }
                                    } else {
                                        if (rateType.equals(merBusRateParent.getRateType())) {
                                            isFound = true;
                                            double rateNew = merBusRateNew.getRate();
                                            double rateOld = merBusRateParent.getRate();
                                            if (rateNew > rateOld) {
                                                overMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                        if (!isFound) {
                            notFoundMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
                        }
                    }
                    if (overMsg.size() > 0) {
                        msg.addAll(overMsg);
                        return ResponseCode.USERS_MER_RATE_OVER_PARENT;
                    }
                    if (notFoundMsg.size() > 0) {
                        msg.addAll(notFoundMsg);
                        return ResponseCode.USERS_PARENT_RATE_NOT_FOUND;
                    }
                }

                // 查找下级商户，如果费率低于下级商户，不可保存
                Merchant findChildMerchant = new Merchant();
                findChildMerchant.setMerParentCode(merCode);
                findChildMerchant.setMerState(MerStateEnum.THROUGH.getCode());
                List<Merchant> childMerchant = merchantService.findMerchant(findChildMerchant);
                if (!CollectionUtils.isEmpty(childMerchant)) {
                    List<String> merCodes = new ArrayList<String>();
                    Map<String, Merchant> childMerMap = new HashMap<String, Merchant>();
                    for (Merchant childMer : childMerchant) {
                        String childMerCode = childMer.getMerCode();
                        merCodes.add(childMerCode);
                        childMerMap.put(childMerCode, childMer);
                    }
                    List<MerBusRate> childMerBusRateList = merBusRateService.batchFindMerBusRateByMerCodes(merCodes);
                    if (!CollectionUtils.isEmpty(childMerBusRateList)) {
                        Map<String, MerBusRate> maxRateMap = new HashMap<String, MerBusRate>();
                        for (MerBusRateDTO parentBusRate : merBusRateDTOList) {
                            String rateCode = parentBusRate.getRateCode();
                            if (RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCode)) {
                                continue;
                            }
                            for (MerBusRate childBusRate : childMerBusRateList) {
                                String childProviderCode = childBusRate.getProviderCode();
                                String childRateCode = childBusRate.getRateCode();
                                if (RateCodeEnum.YKT_PAYMENT.getCode().equals(childRateCode)) {
                                    continue;
                                }
                                String childRateType = childBusRate.getRateType();
                                double childRate = childBusRate.getRate();
                                if (parentBusRate.getProviderCode().equals(childProviderCode) && parentBusRate.getRateCode().equals(childRateCode) && parentBusRate.getRateType().equals(childRateType) && parentBusRate.getRate() < childRate) {
                                    String key = childProviderCode + "_" + childRateCode + "_" + childRateType;
                                    if (maxRateMap.containsKey(key)) {
                                        double maxRate = maxRateMap.get(key).getRate();
                                        if (childRate > maxRate) {
                                            maxRateMap.put(key, childBusRate);
                                        }
                                    } else {
                                        maxRateMap.put(key, childBusRate);
                                    }
                                }
                            }
                        }
                        // 有小于下级费率的情况
                        if (maxRateMap.size() > 0) {
                            List<String> belowMsg = new ArrayList<String>();
                            for (MerBusRate rateTemp : maxRateMap.values()) {
                                String rateType = rateTemp.getRateType();
                                double rate = rateTemp.getRate();
                                if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(rateType)) {
                                    rate = rate / 100;
                                }
                                belowMsg.add(rateTemp.getProviderCode() + "," + rateTemp.getRateCode() + "," + rateTemp.getRateType() + "," + rate + "," + childMerMap.get(rateTemp.getMerCode()).getMerName());
                            }
                            msg.addAll(belowMsg);
                            return ResponseCode.USERS_BELOW_CHILD_RATE;
                        }
                    }
                }
            }
        }

        return ResponseCode.SUCCESS;
    }

    private String registerCheck(MerchantDTO merchantDTO, List<String> msg) {
        /*
         * 商户名称 1、必填 2、全局唯一 3、中文、英文、数字及符号(未校验) 4、长度<=50字符(未校验)
         */
        String merName = merchantDTO.getMerName();
        if (StringUtils.isBlank(merName)) {
            return ResponseCode.USERS_MER_NAME_NULL;
        }
        Merchant checkMerName = new Merchant();
        checkMerName.setMerName(merName);
        if (merchantService.checkExist(checkMerName)) {
            return ResponseCode.USERS_MER_NAME_EXIST;
        }

        /*
         * 商户类型 1、必填
         */
        String merType = merchantDTO.getMerType();
        if (StringUtils.isBlank(merType) || MerTypeEnum.getMerTypeByCode(merType) == null) {
            return ResponseCode.USERS_MER_TYPE_ERR;
        }

        /*
         * 商户分类 1、必填，默认为正式商户
         */
        String merClassify = merchantDTO.getMerClassify();
        if (StringUtils.isBlank(merClassify) || MerClassifyEnum.getMerClassifyByCode(merClassify) == null) {
            return ResponseCode.USERS_MER_CLASSIFY_ERR;
        }

        /*
         * 商户属性 1、必填，默认为标准商户
         */
        String merProperty = merchantDTO.getMerProperty();
        if (StringUtils.isBlank(merProperty) || MerPropertyEnum.getMerPropertyByCode(merProperty) == null) {
            return ResponseCode.USERS_MER_PROPERTY_ERR;
        }

        // 账户类型
        String fundType = merchantDTO.getFundType();
        if (StringUtils.isBlank(fundType)) {
            return ResponseCode.USERS_FUND_TYPE_NULL;
        }

        /*
         * 联系人 1、必填 2、中文、英文(未校验) 3、2<=长度<=20字符(未校验)
         */
        String merLinkUser = merchantDTO.getMerLinkUser();
        if (StringUtils.isBlank(merLinkUser)) {
            return ResponseCode.USERS_MER_LINK_USER_NULL;
        }

        /*
         * 手机号码 1、必填 2、全局唯一 3、只能输入数字 4、长度=11
         */
        String merLinkUserMobile = merchantDTO.getMerLinkUserMobile();
        if (StringUtils.isBlank(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_NULL;
        }
        if (!DDPStringUtil.isMobile(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_ERR;
        }

        MerchantUser checkUserMobile = new MerchantUser();
        checkUserMobile.setMerUserMobile(merLinkUserMobile);
        if (merchantUserService.checkExist(checkUserMobile)) {
            return ResponseCode.USERS_MOB_EXIST;
        }

        /*
         * 省份、城市、地址
         */
        String merPro = merchantDTO.getMerPro();
        String merCity = merchantDTO.getMerCity();
        String merAdds = merchantDTO.getMerAdds();
        if (StringUtils.isBlank(merPro)) {
            return ResponseCode.USERS_MER_PRO_NULL;
        }
        if (StringUtils.isBlank(merCity)) {
            return ResponseCode.USERS_MER_CITY_NULL;
        }
        if (StringUtils.isBlank(merAdds)) {
            return ResponseCode.USERS_MER_ADDS_NULL;
        }

        // 上级商户CODE
        String merParentCode = merchantDTO.getMerParentCode();
        if (MerTypeEnum.AGENT_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType)) {
            if (StringUtils.isBlank(merParentCode) || merParentCode.equalsIgnoreCase("null")) {
                return ResponseCode.USERS_MER_PARENT_CODE_NULL;
            } else {
                Merchant parentMer = merchantService.findMerchantInfoByMerCode(merParentCode);
                if (parentMer == null) {
                    return ResponseCode.USERS_FIND_PARENT_MERCHANT_ERR;
                }
                String merParentType = parentMer.getMerType();
                if (MerTypeEnum.AGENT.getCode().equals(merParentType)) {
                    // 代理商户可以创建代理商户、连锁商户、代理商自有网点
                    if (!(MerTypeEnum.AGENT.getCode().equals(merType) || MerTypeEnum.CHAIN.getCode().equals(merType) || MerTypeEnum.AGENT_MER.getCode().equals(merType))) {
                        return ResponseCode.USERS_MERCHANT_NOT_ALLOWED_CREATE;
                    }
                } else if (MerTypeEnum.CHAIN.getCode().equals(merParentType)) {
                    // 连锁商户可以创建连锁直营网点、连锁加盟网点
                    if (!(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType))) {
                        return ResponseCode.USERS_MERCHANT_NOT_ALLOWED_CREATE;
                    }
                }
            }
        } else if (MerTypeEnum.CHAIN.getCode().equals(merType) || MerTypeEnum.AGENT.getCode().equals(merType)) {
            if (StringUtils.isNotBlank(merParentCode)) {
                Merchant parentMer = merchantService.findMerchantInfoByMerCode(merParentCode);
                if (parentMer == null) {
                    return ResponseCode.USERS_FIND_PARENT_MERCHANT_ERR;
                }
            }
        } else {
            if (StringUtils.isNotBlank(merParentCode)) {
                if (merParentCode.equalsIgnoreCase("null")) {
                    merchantDTO.setMerParentCode(null);
                } else {
                    return ResponseCode.USERS_FIND_PARENT_MERCHANT_ERR;
                }
            }
        }

        // 启用标识
        String activate = merchantDTO.getActivate();
        if (StringUtils.isBlank(activate) || ActivateEnum.getActivateByCode(activate) == null) {
            return ResponseCode.ACTIVATE_ERROR;
        }

        /*
         * 用户名 1、必填 2、包含数字、英文字母、下划线，必须字母开头 3、4<=长度<=20位字符 4、唯一
         */
        MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
        if (merUserDTO == null) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        String merUserName = merUserDTO.getMerUserName();
        if (StringUtils.isBlank(merUserName)) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }
        String reg = UsersConstants.REG_USER_NAME;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(merUserName);
        if (!matcher.matches()) {
            return ResponseCode.USERS_USER_NAME_ERR;
        }
        MerchantUser checkUserName = new MerchantUser();
        checkUserName.setMerUserName(merUserName);
        if (merchantUserService.checkExist(checkUserName)) {
            return ResponseCode.USERS_MER_USER_NAME_EXIST;
        }

        // 创建人
        String createUser = merchantDTO.getCreateUser();
        if (StringUtils.isBlank(createUser)) {
            return ResponseCode.CREATE_USER_NULL;
        }

        // 审核人
        merchantDTO.setMerStateUser(createUser);
        merchantDTO.setMerStateDate(new Date());
        // 用户注册来源
        merUserDTO.setMerUserSource(SourceEnum.OSS.getCode());
        // 用户启用标识
        merUserDTO.setActivate(activate);
        // 删除标志(默认正常)
        merchantDTO.setDelFlg(DelFlgEnum.NORMAL.getCode());
        // 真实姓名
        merUserDTO.setMerUserNickName(merLinkUser);
        // 商户注册来源
        merchantDTO.setSource(SourceEnum.OSS.getCode());

        // 连锁直营网点不需要判断费率，使用时直接取上级费率
        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            List<MerBusRateDTO> merBusRateDTOList = merchantDTO.getMerBusRateList();
            if (!CollectionUtils.isEmpty(merBusRateDTOList)) {
                if (StringUtils.isNotBlank(merParentCode)) {
                    List<String> overMsg = new ArrayList<String>();
                    List<String> notFoundMsg = new ArrayList<String>();
                    List<MerBusRate> parentMerBusRateList = merBusRateService.findMerBusRateByMerCode(merParentCode);
                    for (MerBusRateDTO merBusRateNew : merBusRateDTOList) {
                        String providerCode = merBusRateNew.getProviderCode();
                        String rateCode = merBusRateNew.getRateCode();
                        String rateType = merBusRateNew.getRateType();
                        if (StringUtils.isBlank(providerCode)) {
                            return ResponseCode.USERS_PROVIDER_CODE_NULL;
                        }
                        if (StringUtils.isBlank(rateCode)) {
                            return ResponseCode.USERS_RATE_CODE_NULL;
                        }
                        if (StringUtils.isBlank(rateType)) {
                            return ResponseCode.USERS_RATE_TYPE_NULL;
                        }
                        if (RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCode)) {
                            continue;
                        }
                        boolean isFound = false;
                        for (MerBusRate merBusRateOld : parentMerBusRateList) {
                            String rateCodeOld = merBusRateOld.getRateCode();
                            if (!RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCodeOld)) {
                                if (rateCode.equals(merBusRateOld.getRateCode()) && providerCode.equals(merBusRateOld.getProviderCode()) && rateType.equals(merBusRateOld.getRateType())) {
                                    isFound = true;
                                    double rateNew = merBusRateNew.getRate();
                                    double rateOld = merBusRateOld.getRate();
                                    if (rateNew > rateOld) {
                                        overMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
                                        // return
                                        // ResponseCode.USERS_MER_RATE_OVER_PARENT;
                                    }
                                    break;
                                }
                            }
                        }
                        if (!isFound) {
                            notFoundMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
                            // return ResponseCode.USERS_PARENT_RATE_NOT_FOUND;
                        }

                        if (overMsg.size() > 0) {
                            msg.addAll(overMsg);
                            return ResponseCode.USERS_MER_RATE_OVER_PARENT;
                        }
                        if (notFoundMsg.size() > 0) {
                            msg.addAll(notFoundMsg);
                            return ResponseCode.USERS_PARENT_RATE_NOT_FOUND;
                        }
                    }
                }
            }
        }

        return ResponseCode.SUCCESS;
    }

    private String providerRegisterCheck(MerchantDTO merchantDTO) {
        /*
         * 商户名称 1、必填 2、全局唯一 3、中文、英文、数字及符号(未校验) 4、长度<=50字符(未校验)
         */
        String merName = merchantDTO.getMerName();
        if (StringUtils.isBlank(merName)) {
            return ResponseCode.USERS_MER_NAME_NULL;
        }
        Merchant checkMerName = new Merchant();
        checkMerName.setMerName(merName);
        if (merchantService.checkExist(checkMerName)) {
            return ResponseCode.USERS_MER_NAME_EXIST;
        }
        merchantDTO.setMerName(merName);

        /*
         * 联系人 1、必填 2、中文、英文(未校验) 3、2<=长度<=20字符(未校验)
         */
        String merLinkUser = merchantDTO.getMerLinkUser();
        if (StringUtils.isBlank(merLinkUser)) {
            return ResponseCode.USERS_MER_LINK_USER_NULL;
        }

        /*
         * 手机号码 1、必填 2、全局唯一 3、只能输入数字 4、长度=11
         */
        String merLinkUserMobile = merchantDTO.getMerLinkUserMobile();
        if (StringUtils.isBlank(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_NULL;
        }

        MerchantUser checkUserMobile = new MerchantUser();
        checkUserMobile.setMerUserMobile(merLinkUserMobile);
        if (merchantUserService.checkExist(checkUserMobile)) {
            return ResponseCode.USERS_MOB_EXIST;
        }

        /*
         * 省份、城市、地址
         */
        String merPro = merchantDTO.getMerPro();
        String merCity = merchantDTO.getMerCity();
        String merAdds = merchantDTO.getMerAdds();
        if (StringUtils.isBlank(merPro)) {
            return ResponseCode.USERS_MER_PRO_NULL;
        }
        if (StringUtils.isBlank(merCity)) {
            return ResponseCode.USERS_MER_CITY_NULL;
        }
        if (StringUtils.isBlank(merAdds)) {
            return ResponseCode.USERS_MER_ADDS_NULL;
        }

        /*
         * 用户名 1、必填 2、包含数字、英文字母、下划线，必须字母开头 3、4<=长度<=20位字符 4、唯一
         * 通卡公司用户名采用: a~z随机4位 + 一卡通代码
         */
        MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
        if (merUserDTO == null) {
            return ResponseCode.USERS_MER_INFO_NULL;
        }
        String merUserName = merUserDTO.getMerUserName();
        if (StringUtils.isBlank(merUserName)) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }

        // 拼接4位随机字母
        merUserName = DDPStringUtil.getRandomLowerAZ(4) + merUserName;

        MerchantUser checkUserName = new MerchantUser();
        checkUserName.setMerUserName(merUserName);
        if (merchantUserService.checkExist(checkUserName)) {
            return ResponseCode.USERS_MER_USER_NAME_EXIST;
        }

        // 创建人
        String createUser = merchantDTO.getCreateUser();
        if (StringUtils.isBlank(createUser)) {
            return ResponseCode.CREATE_USER_NULL;
        }

        // 商户类型，默认为：供应商
        merchantDTO.setMerType(MerTypeEnum.PROVIDER.getCode());
        // 商户分类 ，默认为：正式商户
        merchantDTO.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());
        //商户属性，默认为：标准商户
        merchantDTO.setMerProperty(MerPropertyEnum.STANDARD.getCode());
        // 账户类型，默认为：资金账户
        merchantDTO.setFundType(FundTypeEnum.FUND.getCode());
        // 审核人
        merchantDTO.setMerStateUser(createUser);
        // 审核时间
        merchantDTO.setMerStateDate(new Date());
        // 用户注册来源
        merUserDTO.setMerUserSource(SourceEnum.OSS.getCode());
        // 启用标识
        merchantDTO.setActivate(ActivateEnum.ENABLE.getCode());
        // 删除标志(默认正常)
        merchantDTO.setDelFlg(DelFlgEnum.NORMAL.getCode());
        // 真实姓名
        merUserDTO.setMerUserNickName(merLinkUser);
        // 商户注册来源
        merchantDTO.setSource(SourceEnum.OSS.getCode());

        return ResponseCode.SUCCESS;
    }

    private String providerUpdateCheck(MerchantDTO merchantDTO) {
        // 商户号
        String merCode = merchantDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }

        Merchant merOld = merchantService.findMerchantByMerCode(merCode);
        if (merOld == null) {
            return ResponseCode.USERS_FIND_MERCHANT_ERR;
        }

        // 商户名称
        String merName = merchantDTO.getMerName();
        if (StringUtils.isBlank(merName)) {
            return ResponseCode.USERS_MER_NAME_NULL;
        }
        if (!merName.equals(merOld.getMerName())) {
            Merchant checkMerName = new Merchant();
            checkMerName.setMerName(merName);
            if (merchantService.checkExist(checkMerName)) {
                return ResponseCode.USERS_MER_NAME_EXIST;
            }
        }
        merchantDTO.setMerName(merName);

        // 联系人
        String merLinkUser = merchantDTO.getMerLinkUser();
        if (StringUtils.isBlank(merLinkUser)) {
            return ResponseCode.USERS_MER_LINK_USER_NULL;
        }

        // 手机号码
        String merLinkUserMobile = merchantDTO.getMerLinkUserMobile();
        if (StringUtils.isBlank(merLinkUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_NULL;
        }
        if (!merLinkUserMobile.equals(merOld.getMerLinkUserMobile())) {
            MerchantUser checkUserMobile = new MerchantUser();
            checkUserMobile.setMerUserMobile(merLinkUserMobile);
            if (merchantUserService.checkExist(checkUserMobile)) {
                return ResponseCode.USERS_MOB_EXIST;
            }
        }

        // 更新人不能为空
        String updateUser = merchantDTO.getUpdateUser();
        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }

        /*
         * 省份、城市、地址
         */
        String merPro = merchantDTO.getMerPro();
        String merCity = merchantDTO.getMerCity();
        String merAdds = merchantDTO.getMerAdds();
        if (StringUtils.isBlank(merPro)) {
            return ResponseCode.USERS_MER_PRO_NULL;
        }
        if (StringUtils.isBlank(merCity)) {
            return ResponseCode.USERS_MER_CITY_NULL;
        }
        if (StringUtils.isBlank(merAdds)) {
            return ResponseCode.USERS_MER_ADDS_NULL;
        }

        MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
        if (merUserDTO == null) {
            merUserDTO = new MerchantUserDTO();
        }
        merUserDTO.setMerCode(merCode);
        merUserDTO.setMerUserNickName(merLinkUser);
        merUserDTO.setMerUserMobile(merLinkUserMobile);
        merUserDTO.setUpdateUser(updateUser);

        return ResponseCode.SUCCESS;
    }

    private String batchDelRejectMerchantByMerCodesCheck(List<String> merCodes, String updateUser) {
        if (CollectionUtils.isEmpty(merCodes)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }

        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }

        // TODO:判断这些商户都为审核不通过商户

        return ResponseCode.SUCCESS;
    }

    private String findMerOperatorsCheck(MerchantUserDTO merchantUserDTO) {
        if (StringUtils.isBlank(merchantUserDTO.getMerCode())) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String findMerOperatorByPageCheck(MerchantUserQueryDTO userQueryDTO) {
        if (StringUtils.isBlank(userQueryDTO.getMerCode())) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        if (StringUtils.isBlank(userQueryDTO.getLoginUserId())) {
            return ResponseCode.LOGIN_USER_NULL;
        }
        userQueryDTO.setMerUserFlag(MerUserFlgEnum.COMMON.getCode());
        return ResponseCode.SUCCESS;
    }

    private String findMerOperatorByUserCodeCheck(String merCode, String userCode) {
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        if (StringUtils.isBlank(userCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String addMerOperatorCheck(MerchantUserDTO merchantUserDTO) {
        if (merchantUserDTO == null) {
            return ResponseCode.USERS_FIND_USER_ERR;
        }
        /*
         * 用户名： 必须输入 包含数字、英文字母、下划线 4<=长度<=20 全局唯一
         */
        String merUserName = merchantUserDTO.getMerUserName();
        if (StringUtils.isBlank(merUserName)) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }

        String reg = UsersConstants.REG_USER_NAME;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(merUserName);
        if (!matcher.matches()) {
            return ResponseCode.USERS_USER_NAME_ERR;
        }

        MerchantUser checkUserName = new MerchantUser();
        checkUserName.setMerUserName(merUserName);
        if (merchantUserService.checkExist(checkUserName)) {
            return ResponseCode.USERS_MER_USER_NAME_EXIST;
        }

        /*
         * 密码： 必须输入 包含数字、字母、特殊字符（不做校验） 6<=长度<=20（不做校验）
         */
        String merUserPWD = merchantUserDTO.getMerUserPWD();
        if (StringUtils.isBlank(merUserPWD)) {
            return ResponseCode.USERS_FIND_USER_PWD_NULL;
        }

        /*
         * 手机号码： 必填 全局唯一 只能输入数字 文本框输入长度限制到11位字符
         */
        String merUserMobile = merchantUserDTO.getMerUserMobile();
        if (StringUtils.isBlank(merUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_NULL;
        }
        if (!DDPStringUtil.isMobile(merUserMobile)) {
            return ResponseCode.USERS_MOB_TEL_ERR;
        }
        MerchantUser checkUserMobile = new MerchantUser();
        checkUserMobile.setMerUserMobile(merUserMobile);
        if (merchantUserService.checkExist(checkUserMobile)) {
            return ResponseCode.USERS_MOB_EXIST;
        }

        // 启用标识
        String activate = merchantUserDTO.getActivate();
        if (StringUtils.isBlank(activate) || ActivateEnum.getActivateByCode(activate) == null) {
            return ResponseCode.ACTIVATE_ERROR;
        }

        // 商户号、创建人
        String merCode = merchantUserDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_MER_CODE_NULL;
        }
        String createUser = merchantUserDTO.getCreateUser();
        if (StringUtils.isBlank(createUser)) {
            return ResponseCode.CREATE_USER_NULL;
        }

        // 用户标志
        merchantUserDTO.setMerUserFlag(MerUserFlgEnum.COMMON.getCode());
        // 用户类型（默认为商户）
        merchantUserDTO.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());
        // 用户注册来源（默认为门户）
        merchantUserDTO.setMerUserSource(SourceEnum.PORTAL.getCode());
        // 用户审核状态
        merchantUserDTO.setMerUserState(MerStateEnum.THROUGH.getCode());

        return ResponseCode.SUCCESS;
    }

    private String updateMerOperatorCheck(MerchantUserDTO merchantUserDTO) {
        if (merchantUserDTO == null) {
            return ResponseCode.USERS_FIND_USER_ERR;
        }

        String id = merchantUserDTO.getId();
        if (StringUtils.isBlank(id)) {
            return ResponseCode.USERS_FIND_USER_ID_NULL;
        }

        // 手机号不能更新
        merchantUserDTO.setMerUserMobile(null);
        // 商户号不能为空
        String merCode = merchantUserDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_MER_CODE_NULL;
        }
        // 用户名不能为空
        String userName = merchantUserDTO.getMerUserName();
        if (StringUtils.isBlank(userName)) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }
        MerchantUser findMerUser = new MerchantUser();
        findMerUser.setMerUserName(userName);
        MerchantUser merUser = merchantUserService.findMerchantUserExact(findMerUser);
        if (merUser == null || merUser.getMerCode() == null || !merUser.getMerCode().equals(merCode)) {
            return ResponseCode.USERS_FIND_USER_ERR;
        }

        // 用户编号不能为空
        String userCode = merchantUserDTO.getUserCode();
        if (StringUtils.isBlank(userCode)) {
            // return ResponseCode.USERS_USER_CODE_NULL;
        }

        // 更新人不能为空
        String updateUser = merchantUserDTO.getUpdateUser();
        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }

        return ResponseCode.SUCCESS;
    }

    private String configMerOperatorRoleCheck(MerchantUserDTO merchantUserDTO) {
        if (merchantUserDTO == null) {
            return ResponseCode.USERS_FIND_USER_ERR;
        }
        // 商户号不能为空
        String merCode = merchantUserDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_MER_CODE_NULL;
        }
        // 用户名不能为空
        String userName = merchantUserDTO.getMerUserName();
        if (StringUtils.isBlank(userName)) {
            return ResponseCode.USERS_FIND_USER_NAME_NULL;
        }
        MerchantUser findMerUser = new MerchantUser();
        findMerUser.setMerUserName(userName);
        MerchantUser merUser = merchantUserService.findMerchantUserExact(findMerUser);
        if (merUser == null || merUser.getMerCode() == null || !merUser.getMerCode().equals(merCode)) {
            return ResponseCode.USERS_FIND_USER_ERR;
        }
        merchantUserDTO.setUserCode(merUser.getUserCode());
        // 更新人不能为空
        String updateUser = merchantUserDTO.getUpdateUser();
        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }

        return ResponseCode.SUCCESS;
    }

    private String resetOperatorPwdCheck(String merCode, String id, String password, String updateUser) {
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }

        if (StringUtils.isBlank(id)) {
            return ResponseCode.USERS_FIND_USER_ID_NULL;
        }

        if (StringUtils.isBlank(password)) {
            return ResponseCode.USERS_FIND_USER_PWD_NULL;
        }

        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }

        return ResponseCode.SUCCESS;
    }

    private String updateMerchantForPortalCheck(MerchantDTO merchantDTO) {
        String merCode = merchantDTO.getMerCode();
        if (StringUtils.isBlank(merCode)) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }

        String updateUser = merchantDTO.getUpdateUser();
        if (StringUtils.isBlank(updateUser)) {
            return ResponseCode.UPDATE_USER_NULL;
        }

        String merLinkUser = merchantDTO.getMerLinkUser();
        if (StringUtils.isBlank(merLinkUser)) {
            return ResponseCode.USERS_MER_LINK_USER_NULL;
        }

        // =========================用户信息
        MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
        if (merUserDTO == null) {
            return ResponseCode.USERS_MER_USER_NULL;
        }

        MerchantUser paramMerUser = new MerchantUser();
        paramMerUser.setMerCode(merCode);
        paramMerUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
        List<MerchantUser> merUserList = merchantUserService.findMerchantUser(paramMerUser);
        if (CollectionUtils.isEmpty(merUserList)) {
            return ResponseCode.USERS_FIND_USER_ERR;
        }
        MerchantUser adminMerUser = merUserList.get(0);
        merUserDTO.setUserCode(adminMerUser.getUserCode());
        merUserDTO.setMerUserSex(adminMerUser.getMerUserSex());
        merUserDTO.setMerUserRemark(adminMerUser.getMerUserRemark());
        merUserDTO.setMerUserAdds(adminMerUser.getMerUserAdds());
        merUserDTO.setMerUserBirthday(adminMerUser.getMerUserBirthday());
        merUserDTO.setMerUserEmployeeDate(adminMerUser.getMerUserEmployeeDate());

        // 真实姓名
        merUserDTO.setMerUserNickName(merLinkUser);
        // Email
        merUserDTO.setMerUserEmail(merchantDTO.getMerEmail());
        // 固定电话
        merUserDTO.setMerUserTelephone(merchantDTO.getMerTelephone());

        return ResponseCode.SUCCESS;
    }

    private String validateMerchantForIcdcCheck(Map<String, String> returnParam, String merchantNum, String userId, String posId, String providerCode, RateCodeEnum rateCodeEnum, String source) {
        MerchantUser merUser = null;

        boolean isPersonal = false;
        if (StringUtils.isNotBlank(userId)) {
            // 查询用户信息
            merUser = merchantUserService.findMerchantUserById(userId);
            // 用户是否存在
            if (merUser == null) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }
            // 用户类型
            String merUserType = merUser.getMerUserType();
            if (MerUserTypeEnum.PERSONAL.getCode().equals(merUserType)) { // 个人用户对应的商户号不为空则数据有问题
                if (StringUtils.isNotBlank(merUser.getMerCode())) {
                    return ResponseCode.USERS_YKT_USER_NOT_PERSONAL;
                }
                isPersonal = true;
            }
        }

        if (isPersonal) { // 个人
            // 用户对应的商户号不为空则不是个人
            if (StringUtils.isNotBlank(merUser.getMerCode())) {
                return ResponseCode.USERS_YKT_USER_NOT_PERSONAL;
            }
            // 用户审核状态
            if (!MerStateEnum.THROUGH.getCode().equals(merUser.getMerUserState())) {
                return ResponseCode.USERS_MER_USER_STATE_ERR;
            }
            // 用户是否停用
            if (ActivateEnum.DISABLE.getCode().equals(merUser.getActivate())) {
                return ResponseCode.USERS_USER_DISABLE;
            }

            // 如果是个人，这里存放用户编号，可以为空
            if (StringUtils.isNotBlank(merchantNum) && !merchantNum.equals(merUser.getUserCode())) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }

            // 如果来源不是手机，则校验POS
            if (!SourceEnum.isPhone(source)) {
                // ----------校验POS
                if (StringUtils.isBlank(posId)) {
                    return ResponseCode.USERS_POS_ID_NULL;
                }
                Pos pos = posService.findPosByCode(posId);
                // POS是否存在
                if (pos == null) {
                    return ResponseCode.USERS_POS_NO_USE;
                }
                if (RateCodeEnum.YKT_RECHARGE == rateCodeEnum) {
                    // 充值业务，POS设备未处于启用或消费封锁状态，则不可用
                    if (!PosStatusEnum.ACTIVATE.getCode().equals(pos.getStatus()) && !PosStatusEnum.CONSUMER_DISABLE.getCode().equals(pos.getStatus())) {
                        return ResponseCode.USERS_YKT_POS_DISABLE;
                    }
                } else if (RateCodeEnum.YKT_PAYMENT == rateCodeEnum) {
                    // 消费业务，POS设备未处于启用或充值封锁状态，则不可用
                    if (!PosStatusEnum.ACTIVATE.getCode().equals(pos.getStatus()) && !PosStatusEnum.CHARGE_DISABLE.getCode().equals(pos.getStatus())) {
                        return ResponseCode.USERS_YKT_POS_DISABLE;
                    }
                }
            }

            // 个人(手机端)当日成功充值次数不能超过数据字典中设置的最大限额
            if (RateCodeEnum.YKT_RECHARGE == rateCodeEnum && SourceEnum.isPhone(source)) {
                String maxCountStr = ddicService.getDdicNameByCode(DdicConstant.CATEGORY_RECHARGE_CONFIG, DdicConstant.DDIC_PERSONAL_MAX_COUNT);
                if (StringUtils.isNotBlank(maxCountStr)) {
                    int maxCount = 0;
                    try {
                        maxCount = Integer.parseInt(maxCountStr);
                        if (maxCount > 0) {
                            int personalCount = merchantUserService.getCurDayPrdRcgCount(merUser.getUserCode());
                            if (personalCount >= maxCount) {
                                return ResponseCode.USERS_PERSONAL_RECHARGE_OVER_MAX;
                            }
                        }
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }

            // 个人用户商户类型为99
            returnParam.put("merType", MerTypeEnum.PERSONAL.getCode());
            // TODO:返回商户名称--个人
            // returnParam.put("merName", "");
            // TODO:返回费率类型、费率值
            // returnParam.put("rateType", RateTypeEnum.PERMILLAGE.getCode());
            // returnParam.put("rate", "0");
        } else { // 商户

            // ----------校验POS
            if (StringUtils.isBlank(posId)) {
                return ResponseCode.USERS_POS_ID_NULL;
            }
            Pos pos = posService.findPosByCode(posId);
            // POS是否存在
            if (pos == null) {
                return ResponseCode.USERS_POS_NO_USE;
            }
            if (RateCodeEnum.YKT_RECHARGE == rateCodeEnum) {
                // 充值业务，POS设备未处于启用或消费封锁状态，则不可用
                if (!PosStatusEnum.ACTIVATE.getCode().equals(pos.getStatus()) && !PosStatusEnum.CONSUMER_DISABLE.getCode().equals(pos.getStatus())) {
                    return ResponseCode.USERS_YKT_POS_DISABLE;
                }
            } else if (RateCodeEnum.YKT_PAYMENT == rateCodeEnum) {
                // 消费业务，POS设备未处于启用或充值封锁状态，则不可用
                if (!PosStatusEnum.ACTIVATE.getCode().equals(pos.getStatus()) && !PosStatusEnum.CHARGE_DISABLE.getCode().equals(pos.getStatus())) {
                    return ResponseCode.USERS_YKT_POS_DISABLE;
                }
            }

            // POS设备是否绑定
            if (!BindEnum.ENABLE.getCode().equals(pos.getBind()) || (!StringUtils.isBlank(merchantNum.replaceAll("0", "")) && !merchantNum.equals(pos.getMerchantCode()))) {
                return ResponseCode.USERS_YKT_POS_UNBIND;
            }

            // 针对V71，需要由POS编号查找对应的商户编号  START
            if (StringUtils.isBlank(merchantNum.replaceAll("0", ""))) {
                Pos merchantPos = posService.findMerchantCodeByPosCode(posId);
                if (merchantPos == null) {
                    return ResponseCode.USERS_FIND_MER_CODE_NULL;
                } else {
                    merchantNum = merchantPos.getMerchantCode();
                    // 返回商户编码
                    returnParam.put("merCode", merchantPos.getMerchantCode());
                }
            } else {
                returnParam.put("merCode", merchantNum);
            }
            // 针对V71，需要由POS编号查找对应的商户编号  END

            //			if (StringUtils.isBlank(merchantNum)) {
            //				return ResponseCode.USERS_FIND_MER_CODE_NULL;
            //			}
            Merchant merchant = merchantService.findMerchantInfoByMerCode(merchantNum);
            if (merchant == null) {
                return ResponseCode.USERS_FIND_MERCHANT_ERR;
            }
            // 判断商户是否启用
            if (ActivateEnum.DISABLE.getCode().equals(merchant.getActivate())) {
                return ResponseCode.USERS_YKT_MERCHANT_DISABLE;
            }

            // ====是否开通【一卡通充值业务\一卡通支付（消费）业务】
            if (StringUtils.isBlank(providerCode)) {
                return ResponseCode.USERS_PROVIDER_CODE_NULL;
            }
            // 商户类型
            String merType = merchant.getMerType();
            String findRateMerCode = null;
            // 连锁直营获取上级费率信息
            if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
                findRateMerCode = merchant.getMerParentCode();
                if (StringUtils.isBlank(findRateMerCode)) {
                    return ResponseCode.USERS_MER_PARENT_CODE_NULL;
                }
            } else {
                findRateMerCode = merchantNum;
            }
            // 商户费率
            List<MerBusRate> merBusRateList = merchantService.findMerBusRateByMerCode(findRateMerCode);
            if (CollectionUtils.isEmpty(merBusRateList)) {
                if (RateCodeEnum.YKT_RECHARGE == rateCodeEnum) {
                    return ResponseCode.USERS_YKT_RECHARGE_DISABLE;
                } else if (RateCodeEnum.YKT_PAYMENT == rateCodeEnum) {
                    return ResponseCode.USERS_YKT_PAYMENT_DISABLE;
                } else {
                    return ResponseCode.USERS_RATE_CODE_NULL;
                }
            } else {
                boolean isFound = false;
                for (MerBusRate temp : merBusRateList) {
                    if (temp.getProviderCode().equals(providerCode) && rateCodeEnum.getCode().equals(temp.getRateCode())) {
                        isFound = true;
                        // 返回费率类型、费率值
                        returnParam.put("rateType", temp.getRateType());
                        returnParam.put("rate", Double.toString(temp.getRate()));
                        break;
                    }
                }
                if (!isFound) {
                    if (RateCodeEnum.YKT_RECHARGE == rateCodeEnum) {
                        return ResponseCode.USERS_YKT_RECHARGE_DISABLE;
                    } else if (RateCodeEnum.YKT_PAYMENT == rateCodeEnum) {
                        return ResponseCode.USERS_YKT_PAYMENT_DISABLE;
                    } else {
                        return ResponseCode.USERS_RATE_CODE_NULL;
                    }
                }
            }

            // 返回商户名称
            returnParam.put("merName", merchant.getMerName());
            // 返回商户类型
            returnParam.put("merType", merchant.getMerType());

            // 外接商户直接取管理员id、userCode返回
            if (MerTypeEnum.EXTERNAL.getCode().equals(merType)) {
                MerchantUser findAdminUser = new MerchantUser();
                findAdminUser.setMerCode(merchantNum);
                findAdminUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
                List<MerchantUser> findAdminList = merchantUserService.findMerchantUser(findAdminUser);
                if (CollectionUtils.isEmpty(findAdminList) || findAdminList.size() != 1) {
                    return ResponseCode.USERS_FIND_USER_ADMIN_ERR;
                }
                merUser = findAdminList.get(0);
                // 外接商户需要返回管理员userName和userId
                returnParam.put("userName", merUser.getMerUserName());
                returnParam.put("userId", merUser.getId());

                // 获取一卡通充值业务对应的URL
                MerRateSupplement merRateSpmt = merRateSupplementService.findMerRateUrl(merchantNum, rateCodeEnum.getCode());
                if (merRateSpmt != null) {
                    // 页面回调通知外接商户
                    returnParam.put("pageCallbackUrl", merRateSpmt.getPageCallbackUrl());
                    // 服务器回调通知外接商户
                    returnParam.put("serviceNoticeUrl", merRateSpmt.getServiceNoticeUrl());
                }

                // 获取外接商户账户支付方式ID
                String payWayExtId = merchantService.findPayWayExtId(merchantNum);
                returnParam.put("payWayExtId", payWayExtId);
            } else {
                if (StringUtils.isBlank(userId) || StringUtils.isBlank(userId.replaceAll("0", ""))) {

                    MerchantUser findAdminUser = new MerchantUser();
                    findAdminUser.setMerCode(merchantNum);
                    findAdminUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
                    List<MerchantUser> findAdminList = merchantUserService.findMerchantUser(findAdminUser);
                    if (CollectionUtils.isEmpty(findAdminList) || findAdminList.size() != 1) {
                        return ResponseCode.USERS_FIND_USER_ADMIN_ERR;
                    }
                    merUser = findAdminList.get(0);
                    // 外接商户需要返回管理员userName和userId
                    returnParam.put("userName", merUser.getMerUserName());
                    returnParam.put("userId", merUser.getId());

                    userId = merUser.getId();
                    //return ResponseCode.USERS_FIND_USER_ID_NULL;
                }
                merUser = merchantUserService.findMerchantUserById(userId);
                // 用户是否存在
                if (merUser == null) {
                    return ResponseCode.USERS_FIND_USER_ERR;
                }
            }

            // 用户是否启用
            if (ActivateEnum.DISABLE.getCode().equals(merUser.getActivate())) {
                return ResponseCode.USERS_YKT_MER_USER_DISABLE;
            }

            // 用户对应的商户号为空
            if (StringUtils.isBlank(merUser.getMerCode())) {
                return ResponseCode.USERS_MER_CODE_NULL;
            }
            // 对应的商户信息不存在
            if (!merUser.getMerCode().equals(merchantNum)) {
                return ResponseCode.USERS_MER_NOT_FOUND_DISABLE;
            }

        }

        // 返回用户编码
        returnParam.put("userCode", merUser.getUserCode());
        // 返回用户ID
        returnParam.put("userId", merUser.getId());
        // 返回用户真实姓名
        returnParam.put("merUserNickName", merUser.getMerUserNickName());

        return ResponseCode.SUCCESS;
    }

    private String validateMerchantForLoadCheck(Map<String, String> returnParam, MerUserTypeEnum userType, String code, String providerCode) {
        if (userType == null) {
            return ResponseCode.USERS_TYPE_NULL;
        }

        if (userType == MerUserTypeEnum.MERCHANT) { // 商户
            if (StringUtils.isBlank(code)) {
                return ResponseCode.USERS_FIND_MER_CODE_NULL;
            }
            Merchant merchant = merchantService.findMerchantInfoByMerCode(code);
            if (merchant == null) {
                return ResponseCode.USERS_FIND_MERCHANT_ERR;
            }
            // 判断商户是否启用
            if (ActivateEnum.DISABLE.getCode().equals(merchant.getActivate())) {
                return ResponseCode.USERS_YKT_MERCHANT_DISABLE;
            }

            // ====是否开通【一卡通充值业务业务和圈存业务】
            if (StringUtils.isBlank(providerCode)) {
                return ResponseCode.USERS_PROVIDER_CODE_NULL;
            }
            // 商户费率
            List<MerBusRate> merBusRateList = merchantService.findMerBusRateByMerCode(code);
            if (CollectionUtils.isEmpty(merBusRateList)) {
                return ResponseCode.USERS_YKT_LOAD_DISABLE;
            } else {
                boolean isFound = false;
                for (MerBusRate temp : merBusRateList) {
                    if (temp.getProviderCode().equals(providerCode) && RateCodeEnum.IC_LOAD.getCode().equals(temp.getRateCode())) {
                        isFound = true;
                        // 返回费率类型、费率值
                        returnParam.put("rateType", temp.getRateType());
                        returnParam.put("rate", Double.toString(temp.getRate()));
                        break;
                    }
                }
                if (!isFound) {
                    return ResponseCode.USERS_YKT_LOAD_DISABLE;
                }
            }

            MerchantUser findUser = new MerchantUser();
            findUser.setMerCode(code);
            findUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
            List<MerchantUser> resultUserList = merchantUserService.findMerchantUser(findUser);
            if (CollectionUtils.isEmpty(resultUserList)) {
                return ResponseCode.USERS_FIND_USER_ADMIN_ERR;
            }

            MerchantUser adminUser = resultUserList.get(0);
            returnParam.put("userCode", adminUser.getUserCode());
            returnParam.put("userId", adminUser.getId());

            // 商户名称
            returnParam.put("custName", merchant.getMerName());

            // 获取外接商户账户支付方式ID
            String merType = merchant.getMerType();
            if (MerTypeEnum.EXTERNAL.getCode().equals(merType)) {
                String payWayExtId = merchantService.findPayWayExtId(code);
                returnParam.put("payWayExtId", payWayExtId);
            }
        } else if (userType == MerUserTypeEnum.PERSONAL) { // 个人
            if (StringUtils.isBlank(code)) {
                return ResponseCode.USERS_USER_CODE_NULL;
            }

            MerchantUser merUser = merchantUserService.findMerchantUserByUserCode(code);
            if (merUser == null) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }

            // 用户审核状态
            if (!MerStateEnum.THROUGH.getCode().equals(merUser.getMerUserState())) {
                return ResponseCode.USERS_MER_USER_STATE_ERR;
            }

            // 用户是否停用
            if (ActivateEnum.DISABLE.getCode().equals(merUser.getActivate())) {
                return ResponseCode.USERS_USER_DISABLE;
            }

            // 用户真实姓名
            returnParam.put("custName", merUser.getMerUserNickName());
        }

        return ResponseCode.SUCCESS;
    }

    private String checkMerAutoInfo(Map<String, String> returnParam, String merCode) {
        if (merCode == null) {
            return ResponseCode.USERS_FIND_MER_CODE_NULL;
        }
        Merchant merchant = merchantService.findMerchantInfoByMerCode(merCode);
        //自动转账不区分连锁直营与连锁加盟  ADD 2016/4/27
//        if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merchant.getMerType())) {
//            return ResponseCode.MER_CHAIN_STORE_MER_FAIL;
//        }
        //商户类型如果为连锁直营网点
        MerDdpInfo merDdpInfo = merDdpInfoService.findMerDdpInfoByMerCode(merCode);
        if (merDdpInfo.getIsAutoDistribute().equals("1")||"2".equals(merDdpInfo.getIsAutoDistribute())) {//自动分配额度：否
            return ResponseCode.MER_IS_AUTO_DISTRIBUTE_FAIL;
        }
        MerAutoAmt merAutoAmt = merchantService.findMerAutoAmtInfo(merCode);
        String managerId = merchantService.findManagerIdByMerCode(merCode);//当前商户管理员Id
        String thresholdAmt = merAutoAmt.getLimitThreshold();//额度阀值
        String transferAmt = merAutoAmt.getAutoLimitThreshold();//转账金额
        String parentMerCode = merchant.getMerParentCode();//上级商户编号
        String parentManaId = merchantService.findManagerIdByMerCode(parentMerCode);//上级商户管理员Id
        returnParam.put("thresholdAmt", thresholdAmt);
        returnParam.put("transferAmt", transferAmt);
        returnParam.put("parentMerCode", parentMerCode);
        returnParam.put("parentMerUserId", parentManaId);
        returnParam.put("merUserId", managerId);
        returnParam.put("isAuto",merDdpInfo.getIsAutoDistribute());
        return ResponseCode.SUCCESS;
    }

    private String validateMerchantForPaymentCheck(MerUserTypeEnum userType, String code) {
        if (userType == null) {
            return ResponseCode.USERS_TYPE_NULL;
        }

        if (StringUtils.isBlank(code)) {
            if (userType == MerUserTypeEnum.PERSONAL) {
                return ResponseCode.USERS_USER_CODE_NULL;
            } else if (userType == MerUserTypeEnum.MERCHANT) {
                return ResponseCode.USERS_FIND_MER_CODE_NULL;
            }
        }

        if (userType == MerUserTypeEnum.PERSONAL) {
            MerchantUser merUser = merchantUserService.findMerchantUserByUserCode(code);
            if (merUser == null) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }
            if (ActivateEnum.DISABLE.getCode().equals(merUser.getActivate())) {
                return ResponseCode.USERS_USER_DISABLE;
            }
            if (StringUtils.isNotBlank(merUser.getMerCode())) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }
            // 用户审核状态
            if (!MerStateEnum.THROUGH.getCode().equals(merUser.getMerUserState())) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }
            // TODO:是否校验停启用
            if (ActivateEnum.DISABLE.getCode().equals(merUser.getActivate())) {

            }
        } else if (userType == MerUserTypeEnum.MERCHANT) {
            Merchant merchant = merchantService.findMerchantInfoByMerCode(code);
            if (merchant == null) {
                return ResponseCode.USERS_FIND_MERCHANT_ERR;
            }
            if (ActivateEnum.DISABLE.getCode().equals(merchant.getActivate())) {
                return ResponseCode.USERS_MERCHANT_DISABLE;
            }
            if (!MerStateEnum.THROUGH.getCode().equals(merchant.getMerState())) {
                return ResponseCode.USERS_MER_STATE_ERR;
            }
        }

        return ResponseCode.SUCCESS;
    }

    private String validateMerchantUserForPaymentCheck(String userCode) {
        if (StringUtils.isBlank(userCode)) {
            return ResponseCode.USERS_USER_CODE_NULL;
        }
        MerchantUser merUser = merchantUserService.findMerchantUserByUserCode(userCode);
        if (merUser == null) {
            return ResponseCode.USERS_FIND_USER_ERR;
        }
        if (ActivateEnum.DISABLE.getCode().equals(merUser.getActivate())) {
            return ResponseCode.USERS_USER_DISABLE;
        }
        // 用户审核状态
        if (!MerStateEnum.THROUGH.getCode().equals(merUser.getMerUserState())) {
            return ResponseCode.USERS_MER_USER_STATE_ERR;
        }
        // TODO:是否校验停启用
        if (ActivateEnum.DISABLE.getCode().equals(merUser.getActivate())) {

        }

        String merUserType = merUser.getMerUserType();
        if (MerUserTypeEnum.PERSONAL.getCode().equals(merUserType)) {
            if (StringUtils.isNotBlank(merUser.getMerCode())) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }
        } else if (MerUserTypeEnum.MERCHANT.getCode().equals(merUserType)) {
            String merCode = merUser.getMerCode();
            if (StringUtils.isBlank(merCode)) {
                return ResponseCode.USERS_FIND_MERCHANT_ERR;
            }

            Merchant merchant = merchantService.findMerchantInfoByMerCode(merCode);
            if (merchant == null) {
                return ResponseCode.USERS_FIND_MERCHANT_ERR;
            }
            if (ActivateEnum.DISABLE.getCode().equals(merchant.getActivate())) {
                return ResponseCode.USERS_MERCHANT_DISABLE;
            }
            if (!MerStateEnum.THROUGH.getCode().equals(merchant.getMerState())) {
                return ResponseCode.USERS_MER_STATE_ERR;
            }
        }

        return ResponseCode.SUCCESS;
    }

    private String findYktCitysCheck(MerUserTypeEnum custType, String custCode) {
        if (custType == null) {
            return ResponseCode.USERS_TYPE_NULL;
        }

        if (StringUtils.isBlank(custCode)) {
            if (custType == MerUserTypeEnum.PERSONAL) {
                return ResponseCode.USERS_USER_CODE_NULL;
            } else if (custType == MerUserTypeEnum.MERCHANT) {
                return ResponseCode.USERS_FIND_MER_CODE_NULL;
            }
        }

        if (custType == MerUserTypeEnum.PERSONAL) {
            MerchantUser merUser = merchantUserService.findMerchantUserByUserCode(custCode);
            if (merUser == null) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }
            if (StringUtils.isNotBlank(merUser.getMerCode())) {
                return ResponseCode.USERS_FIND_USER_ERR;
            }
        } else if (custType == MerUserTypeEnum.MERCHANT) {
            Merchant merchant = merchantService.findMerchantInfoByMerCode(custCode);
            if (merchant == null) {
                return ResponseCode.USERS_FIND_MERCHANT_ERR;
            }
        }

        return ResponseCode.SUCCESS;
    }

    private Merchant buildMerchant(MerchantDTO merchantDTO) {
        // 商户信息
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDTO, merchant, IGNORE_FIELDS);

        // 用户信息
        MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
        MerchantUser merUser = new MerchantUser();
        BeanUtils.copyProperties(merUserDTO, merUser);
        merUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
        merchant.setMerchantUser(merUser);

        // 商户补充信息
        MerDdpInfoDTO merDdpInfoDTO = merchantDTO.getMerDdpInfo();
        if (merDdpInfoDTO != null) {
            MerDdpInfo merDdpInfo = new MerDdpInfo();
            BeanUtils.copyProperties(merDdpInfoDTO, merDdpInfo);
            merchant.setMerDdpInfo(merDdpInfo);
        }

        // 费率信息
        List<MerBusRateDTO> merBusRateDTOList = merchantDTO.getMerBusRateList();
        if (merBusRateDTOList != null && merBusRateDTOList.size() > 0) {
            List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();
            for (MerBusRateDTO merBusRateDTOTemp : merBusRateDTOList) {
                MerBusRate merBusRateTemp = new MerBusRate();
                BeanUtils.copyProperties(merBusRateDTOTemp, merBusRateTemp);
                merBusRateList.add(merBusRateTemp);
            }
            merchant.setMerBusRateList(merBusRateList);
        }

        // 自定义商户
        if (MerPropertyEnum.CUSTOM.getCode().equals(merchantDTO.getMerProperty())) {
            List<MerFunctionInfoDTO> merFunctionInfoDTOList = merchantDTO.getMerDefineFunList();
            if (!CollectionUtils.isEmpty(merFunctionInfoDTOList)) {
                List<MerFunctionInfo> merFunctionInfoList = new ArrayList<MerFunctionInfo>();
                for (MerFunctionInfoDTO merFunctionInfoDTOTemp : merFunctionInfoDTOList) {
                    MerFunctionInfo merFunctionInfoTemp = new MerFunctionInfo();
                    BeanUtils.copyProperties(merFunctionInfoDTOTemp, merFunctionInfoTemp);
                    merFunctionInfoList.add(merFunctionInfoTemp);
                }
                merchant.setMerDefineFunList(merFunctionInfoList);
            }
        }

        // 外接商户
		MerKeyTypeDTO merKeyTypeDTO = merchantDTO.getMerKeyTypeDTO();
		if (merKeyTypeDTO != null) {
			MerKeyType merKeyType = new MerKeyType();
			BeanUtils.copyProperties(merKeyTypeDTO, merKeyType);
			merchant.setMerKeyType(merKeyType);
		}

        // 商户业务信息
        List<MerRateSupplementDTO> merRateSpmtDTOList = merchantDTO.getMerRateSpmtList();
        if (!CollectionUtils.isEmpty(merRateSpmtDTOList)) {
            List<MerRateSupplement> merRateSpmtList = new ArrayList<MerRateSupplement>();
            for (MerRateSupplementDTO merFunctionInfoDTOTemp : merRateSpmtDTOList) {
                MerRateSupplement merRateSpmt = new MerRateSupplement();
                BeanUtils.copyProperties(merFunctionInfoDTOTemp, merRateSpmt);
                merRateSpmtList.add(merRateSpmt);
            }
            merchant.setMerRateSpmtList(merRateSpmtList);
        }

        // 自动分配额度
        MerAutoAmtDTO merAutoAmtDTO = merchantDTO.getMerAutoAmtDTO();
        if (merAutoAmtDTO != null) {
            MerAutoAmt merAutoAmt = new MerAutoAmt();
            BeanUtils.copyProperties(merAutoAmtDTO, merAutoAmt);
            merchant.setMerAutoAmt(merAutoAmt);
        }

        return merchant;
    }

    private MerchantDTO convertToMerchantDTO(Merchant merchant) {
        MerchantDTO merchantDTO = new MerchantDTO();
        BeanUtils.copyProperties(merchant, merchantDTO, IGNORE_FIELDS);

        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        MerchantUser merchantUser = merchant.getMerchantUser();
        if (merchantUser != null) {
            BeanUtils.copyProperties(merchantUser, merchantUserDTO);
            merchantDTO.setMerchantUserDTO(merchantUserDTO);
        }

        MerDdpInfoDTO merDdpInfoDTO = new MerDdpInfoDTO();
        MerDdpInfo merDdpInfo = merchant.getMerDdpInfo();
        if (merDdpInfo != null) {
            BeanUtils.copyProperties(merDdpInfo, merDdpInfoDTO);
            merchantDTO.setMerDdpInfo(merDdpInfoDTO);
        }

        List<MerBusRate> merBusRateList = merchant.getMerBusRateList();
        if (merBusRateList != null && merBusRateList.size() > 0) {
            List<MerBusRateDTO> merBusRateDTOList = new ArrayList<MerBusRateDTO>();
            for (MerBusRate merBusRateTemp : merBusRateList) {
                MerBusRateDTO merBusRateDTOTemp = new MerBusRateDTO();
                BeanUtils.copyProperties(merBusRateTemp, merBusRateDTOTemp);
                merBusRateDTOList.add(merBusRateDTOTemp);
            }
            merchantDTO.setMerBusRateList(merBusRateDTOList);
        }

        if (MerPropertyEnum.CUSTOM.getCode().equals(merchant.getMerProperty())) {
            List<MerFunctionInfo> merFunctionInfoList = merchant.getMerDefineFunList();
            if (merFunctionInfoList != null && merFunctionInfoList.size() > 0) {
                List<MerFunctionInfoDTO> merFunctionInfoDTOList = new ArrayList<MerFunctionInfoDTO>();
                for (MerFunctionInfo merFunctionInfoTemp : merFunctionInfoList) {
                    MerFunctionInfoDTO merFunctionInfoDTOTemp = new MerFunctionInfoDTO();
                    BeanUtils.copyProperties(merFunctionInfoTemp, merFunctionInfoDTOTemp);
                    merFunctionInfoDTOList.add(merFunctionInfoDTOTemp);
                }
                merchantDTO.setMerDefineFunList(merFunctionInfoDTOList);
            }
        }

		MerKeyType merKeyType = merchant.getMerKeyType();
		if (merKeyType != null) {
			MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
			BeanUtils.copyProperties(merKeyType, merKeyTypeDTO);
			merchantDTO.setMerKeyTypeDTO(merKeyTypeDTO);
		}

        List<MerRateSupplement> merRateSpmtList = merchant.getMerRateSpmtList();
        if (!CollectionUtils.isEmpty(merRateSpmtList)) {
            List<MerRateSupplementDTO> merRateSpmtDTOList = new ArrayList<MerRateSupplementDTO>();
            for (MerRateSupplement merFunctionInfoTemp : merRateSpmtList) {
                MerRateSupplementDTO merRateSpmtDTO = new MerRateSupplementDTO();
                BeanUtils.copyProperties(merFunctionInfoTemp, merRateSpmtDTO);
                merRateSpmtDTOList.add(merRateSpmtDTO);
            }
            merchantDTO.setMerRateSpmtList(merRateSpmtDTOList);
        }

        MerAutoAmt merAutoAmt = merchant.getMerAutoAmt();
        if (merAutoAmt != null) {
            MerAutoAmtDTO merAutoAmtDTO = new MerAutoAmtDTO();
            BeanUtils.copyProperties(merAutoAmt, merAutoAmtDTO);
            merchantDTO.setMerAutoAmtDTO(merAutoAmtDTO);
        }

        return merchantDTO;
    }

    // --------------------------------------------------------------------------------------------------------------
    // 根据上级商户的商户编码 查询直营网点
    @Override
    public DodopalResponse<List<DirectMerChantDTO>> findMerchantByParentCode(String merParentCode, String merName) {
        DodopalResponse<List<DirectMerChantDTO>> response = new DodopalResponse<List<DirectMerChantDTO>>();
        try {
            List<DirectMerChant> DirectMerChantList = merchantService.findMerchantByParentCode(merParentCode, merName);
            if (DirectMerChantList != null && DirectMerChantList.size() > 0) {
                List<DirectMerChantDTO> DirectMerChantDTOList = new ArrayList<DirectMerChantDTO>();
                try {
                    for (DirectMerChant directMerChant : DirectMerChantList) {
                        DirectMerChantDTO directMerChantDTO = new DirectMerChantDTO();
                        PropertyUtils.copyProperties(directMerChantDTO, directMerChant);
                        DirectMerChantDTOList.add(directMerChantDTO);
                    }
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(DirectMerChantDTOList);
                }
                catch (Exception e) {
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                    e.printStackTrace();
                    // TODO: handle exception
                }

            } else {
                response.setCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    // 根据上级商户的商户编码 查询直营网点
    @Override
    public DodopalResponse<List<DirectMerChantDTO>> findMerchantByParentCode(String merParentCode, String merName, String businessType) {
        DodopalResponse<List<DirectMerChantDTO>> response = new DodopalResponse<List<DirectMerChantDTO>>();
        try {
            List<DirectMerChant> DirectMerChantList = merchantService.findMerchantByParentCodeType(merParentCode, merName, businessType);
            if (DirectMerChantList != null && DirectMerChantList.size() > 0) {
                List<DirectMerChantDTO> DirectMerChantDTOList = new ArrayList<DirectMerChantDTO>();
                try {
                    for (DirectMerChant directMerChant : DirectMerChantList) {
                        DirectMerChantDTO directMerChantDTO = new DirectMerChantDTO();
                        PropertyUtils.copyProperties(directMerChantDTO, directMerChant);
                        DirectMerChantDTOList.add(directMerChantDTO);
                    }
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(DirectMerChantDTOList);
                }
                catch (Exception e) {
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                    e.printStackTrace();
                    // TODO: handle exception
                }

            } else {
                response.setCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<DirectMerChantDTO>> findDirectTransferFilter(String merParentCode, String merName, String businessType) {
        DodopalResponse<List<DirectMerChantDTO>> response = new DodopalResponse<List<DirectMerChantDTO>>();
        try {
            List<DirectMerChant> DirectMerChantList = merchantService.findDirectTransferFilter(merParentCode, merName, businessType);
            if (DirectMerChantList != null && DirectMerChantList.size() > 0) {
                List<DirectMerChantDTO> DirectMerChantDTOList = new ArrayList<DirectMerChantDTO>();
                try {
                    for (DirectMerChant directMerChant : DirectMerChantList) {
                        DirectMerChantDTO directMerChantDTO = new DirectMerChantDTO();
                        PropertyUtils.copyProperties(directMerChantDTO, directMerChant);
                        DirectMerChantDTOList.add(directMerChantDTO);
                    }
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(DirectMerChantDTOList);
                }
                catch (Exception e) {
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                    e.printStackTrace();
                    // TODO: handle exception
                }

            } else {
                response.setCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<MerchantExtendDTO> findMerchantExtend(String merCode) {
        DodopalResponse<MerchantExtendDTO> response = new DodopalResponse<MerchantExtendDTO>();
        try {
            MerchantExtend merchantExtend = merchantService.findMerchantExtend(merCode);
            MerchantExtendDTO merchantExtendDTO =  new MerchantExtendDTO();
            if(merchantExtend!=null){
                PropertyUtils.copyProperties(merchantExtendDTO, merchantExtend);
                response.setResponseEntity(merchantExtendDTO);
                response.setCode(ResponseCode.SUCCESS);
            }else{
                response.setCode(ResponseCode.MER_EXTEND_INFO_NULL);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }

        return response;
    }

    @Override
    public DodopalResponse<Map<String, String>> getMerchantByPosCode(String posId) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        try {
            Map<String, String> returnParam = new HashMap<String, String>();

            // 校验POS
            if (StringUtils.isBlank(posId)) {
                response.setCode(ResponseCode.USERS_POS_ID_NULL);
                return response;
            }

            Pos pos = posService.findPosByCode(posId);

            // POS是否存在
            if (pos == null) {
                response.setCode(ResponseCode.USERS_POS_NO_USE);
                return response;
            }
            
            // POS校验是否停用
            if (PosStatusEnum.STOP.getCode().equals(pos.getStatus()) || PosStatusEnum.INVALID.getCode().equals(pos.getStatus())) {
                response.setCode(ResponseCode.USERS_YKT_POS_DISABLE);
                return response;
            }            

            // POS设备是否绑定
            if (!BindEnum.ENABLE.getCode().equals(pos.getBind())) {
                response.setCode(ResponseCode.USERS_YKT_POS_UNBIND);
                return response;
            }

            Merchant merchant = merchantService.findMerchantInfoByMerCode(pos.getMerchantCode());
            if (merchant == null) {
                response.setCode(ResponseCode.USERS_FIND_MERCHANT_ERR);
                return response;
            }

            // 判断商户是否启用
            if (ActivateEnum.DISABLE.getCode().equals(merchant.getActivate())) {
                response.setCode(ResponseCode.USERS_YKT_MERCHANT_DISABLE);
                return response;
            }
            
            
            // 根据商户号获取管理员用户信息
            MerchantUser paramMerUser = new MerchantUser();
            paramMerUser.setMerCode(merchant.getMerCode());
            paramMerUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
            List<MerchantUser> merchantUserList = merchantUserService.findMerchantUser(paramMerUser);
            if (merchantUserList != null && merchantUserList.size() > 0) {
                MerchantUser merchantUser = merchantUserList.get(0);
                merchantUser.setMerUserPWD(null); //清除密码
                merchant.setMerchantUser(merchantUser);
            }
            
            // 返回商户编号
            returnParam.put("merCode", merchant.getMerCode());

            // 返回商户类型
            returnParam.put("merType", merchant.getMerType());
            
            // 返回商户名称
            returnParam.put("merName", merchant.getMerName());
            
            // 返回商户管理员ID
            returnParam.put("merUserid", merchant.getMerchantUser().getId());
            
            // 返回商户管理员编号
            returnParam.put("merUserCode", merchant.getMerchantUser().getUserCode());
            
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(returnParam);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * @description 获取是否自动转账标识
     * @return
     */
    @Override
    public DodopalResponse<String> getIsAuto(String merCode) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        MerDdpInfo merDdpInfo = merDdpInfoService.findMerDdpInfoByMerCode(merCode);
        if(merDdpInfo==null||merDdpInfo.getIsAutoDistribute()==null||"".equals(merDdpInfo.getIsAutoDistribute())){
            response.setCode(ResponseCode.MER_EXTEND_INFO_NULL);
        }
        response.setResponseEntity(merDdpInfo.getIsAutoDistribute());
        return response;
    }

    /**
     * @description 获取共享额度的上级商户的code
     * @return
     */
    @Override
    public DodopalResponse<String> getParentid(String merCode) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        Merchant merchant = merchantService.findMerchantInfoByMerCode(merCode);
        if(merchant==null || merchant.getMerParentCode()==null || "".equals(merchant.getMerParentCode())){
            response.setCode(ResponseCode.USERS_MER_PARENT_CODE_NULL);
        }
        response.setResponseEntity(merchant.getMerParentCode());
        return response;
    }

    @Override
    public DodopalResponse<Map<String, String>> validatePersonalUserForNfcRecharge(String userId) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();

        try {
            Map<String, String> returnParam = new HashMap<String, String>();

            // 查询用户信息
            MerchantUser merUser = merchantUserService.findMerchantUserById(userId);
            // 用户是否存在
            if (merUser == null) {
                response.setCode(ResponseCode.USERS_FIND_USER_ERR);
                response.setResponseEntity(returnParam);
                return response;
            }
            // 用户类型
            String merUserType = merUser.getMerUserType();
            if (MerUserTypeEnum.PERSONAL.getCode().equals(merUserType)) { // 个人用户对应的商户号不为空则数据有问题
                if (StringUtils.isNotBlank(merUser.getMerCode())) {
                    response.setCode(ResponseCode.USERS_YKT_USER_NOT_PERSONAL);
                    response.setResponseEntity(returnParam);
                    return response;
                }
            }

            // 用户对应的商户号不为空则不是个人
            if (StringUtils.isNotBlank(merUser.getMerCode())) {
                response.setCode(ResponseCode.USERS_YKT_USER_NOT_PERSONAL);
                response.setResponseEntity(returnParam);
                return response;
            }
            // 用户审核状态
            if (!MerStateEnum.THROUGH.getCode().equals(merUser.getMerUserState())) {
                response.setCode(ResponseCode.USERS_MER_USER_STATE_ERR);
                response.setResponseEntity(returnParam);
                return response;
            }
            // 用户是否停用
            if (ActivateEnum.DISABLE.getCode().equals(merUser.getActivate())) {
                response.setCode(ResponseCode.USERS_USER_DISABLE);
                response.setResponseEntity(returnParam);
                return response;
            }

            // 返回用户编码
            returnParam.put("userCode", merUser.getUserCode());
            // 返回用户真实姓名
            returnParam.put("userName", merUser.getMerUserNickName());

            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(returnParam);
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}

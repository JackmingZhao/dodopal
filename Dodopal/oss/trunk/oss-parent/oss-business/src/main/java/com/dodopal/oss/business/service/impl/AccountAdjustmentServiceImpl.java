package com.dodopal.oss.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.account.dto.AccountAdjustmentApproveDTO;
import com.dodopal.api.account.dto.AccountAdjustmentApproveListDTO;
import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccountAdjustmentStateEnum;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.dao.AccountAdjustmentMapper;
import com.dodopal.oss.business.model.AccountAdjustment;
import com.dodopal.oss.business.model.CustomerAccount;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.AccountAdjustmentQuery;
import com.dodopal.oss.business.model.dto.CustomerAccountQuery;
import com.dodopal.oss.business.service.AccountAdjustmentService;
import com.dodopal.oss.delegate.AccountManageDelegate;
import com.dodopal.oss.delegate.AccountQueryDelegate;
import com.dodopal.oss.delegate.MerUserDelegate;
import com.dodopal.oss.delegate.MerchantDelegate;

@Service
public class AccountAdjustmentServiceImpl implements AccountAdjustmentService {

    private Logger logger = LoggerFactory.getLogger(AccountAdjustmentServiceImpl.class);

    @Autowired
    private AccountAdjustmentMapper adjustmentMapper;

    @Autowired
    private AccountManageDelegate accountManageDelegate;
    
    @Autowired
    MerchantDelegate merchantdelegate;
    
    @Autowired
    private AccountQueryDelegate accountQueryDelegate;
    
    @Autowired
    MerUserDelegate merUserDelegate;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    @Transactional
    public DodopalResponse<String> applyAccountAdjustment(AccountAdjustment adjustment) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        List<String> msg = validateAdjustment(adjustment);
        if (!msg.isEmpty()) {
            response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
            response.setMessage(DDPStringUtil.concatenate(msg, "!\n"));
            logger.warn(response.getMessage());
            return response;
        } else {
            adjustment.setAccountAdjustCode(generateAdjustmentCode());
            adjustment.setAccountAdjustState(AccountAdjustmentStateEnum.UN_APPROVE.getCode());
            adjustmentMapper.applyAccountAdjustment(adjustment);
            response.setCode(ResponseCode.SUCCESS);
        }
        return response;
    }

    @Override
    public DodopalResponse<Boolean> approveAccountAdjustment(AccountAdjustment[] adjustments, User user) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();; 
        if(adjustments != null && adjustments.length > 0) {
            List<AccountAdjustmentApproveDTO> approveDTOList = new ArrayList<AccountAdjustmentApproveDTO>();
            for(AccountAdjustment bean: adjustments) {
                AccountAdjustment adjustmentDB = adjustmentMapper.findAccountAdjustment(bean.getId());
                if(adjustmentDB != null) {
                    String state = adjustmentDB.getAccountAdjustState();
                    if (!AccountAdjustmentStateEnum.UN_APPROVE.getCode().equals(state)) {
                        response.setCode(ResponseCode.ACC_ADJUSTMENT_INVALIDATE_STATE_APPROVE);
                        return response;
                    } 
                }
                AccountAdjustmentApproveDTO dto = new AccountAdjustmentApproveDTO();
                dto.setCustType(adjustmentDB.getCustomerType());
                dto.setCustNum(adjustmentDB.getCustomerNo());
                dto.setTradeNum(adjustmentDB.getAccountAdjustCode());
                dto.setAmount(adjustmentDB.getAccountAdjustAmount());
                dto.setFundType(adjustmentDB.getFundType());
                dto.setAccTranType(adjustmentDB.getAccountAdjustType());
                approveDTOList.add(dto);
            }
            AccountAdjustmentApproveListDTO approveListDTO = new AccountAdjustmentApproveListDTO();
            approveListDTO.setOperateId(user.getId());
            approveListDTO.setApproveDTOs(approveDTOList);
            response = accountManageDelegate.accountAdjustment(approveListDTO);  
         // 根据接口响应码更新申请单：调帐成功或调帐失败、审核人、审核时间、完成时间、审核说明。
            List<AccountAdjustment> validAdjustments = new ArrayList<AccountAdjustment>();
            if(ResponseCode.SUCCESS.equals(response.getCode())) {
                for (AccountAdjustment bean : adjustments) {
                    bean.setAccountAdjustAuditUser(user.getId());
                    bean.setUpdateUser(user.getId());
                    bean.setAccountAdjustState(AccountAdjustmentStateEnum.SUCCESS.getCode());
                    validAdjustments.add(bean);
                }
            } else if(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_STATE_DISABLE.equals(response.getCode()) || ResponseCode.ACC_ACCOUNT_ADJUSTMENT_AMOUNT_ERROR.equals(response.getCode())) {
                for (AccountAdjustment bean : adjustments) {
                    bean.setAccountAdjustAuditUser(user.getId());
                    bean.setUpdateUser(user.getId());
                    bean.setAccountAdjustState(AccountAdjustmentStateEnum.FAILURE.getCode());
                    validAdjustments.add(bean);
                }
            }
            if (validAdjustments.size() > 0) {
                for (AccountAdjustment dbBean : validAdjustments) {
                    adjustmentMapper.updateAuditAccountAdjustment(dbBean);
                }
            }
        } else {
            response.setCode(ResponseCode.ACC_ADJUSTMENT_EMPTY);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<AccountAdjustment> findAccountAdjustmentByPage(AccountAdjustmentQuery query) {
        List<AccountAdjustment> result = adjustmentMapper.findAccountAdjustmentByPage(query);
        DodopalDataPage<AccountAdjustment> pages = new DodopalDataPage<AccountAdjustment>(query.getPage(), result);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountAdjustment findAccountAdjustment(String adjustmentId) {
        if (StringUtils.isNotEmpty(adjustmentId)) {
            return adjustmentMapper.findAccountAdjustment(adjustmentId);
        } else {
            throw new DDPException("adjustmentId.empty:\n", "调账单ID为空.");
        }
    }

    @Override
    @Transactional
    public DodopalResponse<String> updatetAccountAdjustments(AccountAdjustment adjustment) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        if (adjustment != null && StringUtils.isNotEmpty(adjustment.getId())) {
            AccountAdjustment adjustmentDB = adjustmentMapper.findAccountAdjustment(adjustment.getId());
            if(adjustmentDB != null) {
             // 检查该申请单的状态是否为：未审批、审核不通过，否则提示：此状态不能进行修改。
                String state = adjustmentDB.getAccountAdjustState();
                if (AccountAdjustmentStateEnum.UN_APPROVE.getCode().equals(state) || AccountAdjustmentStateEnum.DIS_APPROVE.getCode().equals(state)) {
                    adjustmentMapper.updatetAccountAdjustment(adjustment);
                    response.setCode(ResponseCode.SUCCESS);
                } else {
                    response.setCode(ResponseCode.ACC_ADJUSTMENT_UPDATE_ERROR);
                    response.setMessage("申请单的当前状态不能进行修改");
                }
            } else {
                response.setCode(ResponseCode.ACC_ADJUSTMENT_UPDATE_ERROR);
            }
        } else {
            response.setCode(ResponseCode.ACC_ADJUSTMENT_UPDATE_ERROR);
        }
        return response;
    }

    @Override
    @Transactional
    public DodopalResponse<String> deleteAccountAdjustment(String[] adjustmentIds) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        
        if (adjustmentIds != null && adjustmentIds.length > 0) {
            adjustmentMapper.deleteAccountAdjustment(adjustmentIds);
            response.setCode(ResponseCode.SUCCESS);
        } else {
            response.setCode(ResponseCode.ACC_ADJUSTMENT_DELETE_ERROR);
            response.setMessage("调账申请单ID不能为空.");
        }
        return response;
    }

    /**
     * 调账单编号 Q+ 20150428222211 +五位数据库cycle sequence（循环使用）
     */
    private String generateAdjustmentCode() {
        String code = "Q";
        Date now = new Date();
        String timeStr = simpleDateFormat.format(now);
        code += timeStr;
        return code + adjustmentMapper.selectAdjustmentCodeSeq();
    }

    private List<String> validateAdjustment(AccountAdjustment adjustment) {
        List<String> msg = new ArrayList<String>();
        if (DDPStringUtil.isNotPopulated(adjustment.getAccountAdjustType())) {
            msg.add("调账方式不能为空");
        }
        if (DDPStringUtil.isNotPopulated(adjustment.getCustomerType())) {
            msg.add("客户类型不能为空");
        }

        if (DDPStringUtil.isNotPopulated(adjustment.getCustomerNo())) {
            msg.add("客户号不能为空");
        }

        if (DDPStringUtil.isNotPopulated(adjustment.getFundType())) {
            msg.add("账户类型不能为空");
        }
        if (adjustment.getAccountAdjustAmount() <= 0) {
            msg.add("调账金额 不能为空且必须为正数");
        }

        if (!DDPStringUtil.existingWithLength(adjustment.getAccountAdjustReason(), 256)) {
            msg.add("调账原因不能为空或长度不能超过256个字符");
        }

        if (DDPStringUtil.isNotPopulated(adjustment.getAccountAdjustApplyUser())) {
            msg.add("申请人 不能为空");
        }

        if (adjustment.getAccountAdjustAuditExplain() != null && adjustment.getAccountAdjustAuditExplain().trim().length() >= 256) {
            msg.add("审核说明长度不能超过256个字符");
        }
        return msg;
    }

    @Override
    @Transactional
    public DodopalResponse<Boolean> rejectAccountAdjustment(AccountAdjustment[] adjustments, User user) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        if (adjustments != null && adjustments.length > 0) {
            List<AccountAdjustment> validAdjustments = new ArrayList<AccountAdjustment>();
            for (AccountAdjustment bean : adjustments) {
                AccountAdjustment adjustmentDB = adjustmentMapper.findAccountAdjustment(bean.getId());
                String state = adjustmentDB.getAccountAdjustState();
                if (AccountAdjustmentStateEnum.UN_APPROVE.getCode().equals(state)) {
                    adjustmentDB.setAccountAdjustAuditExplain(bean.getAccountAdjustAuditExplain());
                    adjustmentDB.setAccountAdjustAuditUser(user.getId());
                    adjustmentDB.setAccountAdjustState(AccountAdjustmentStateEnum.DIS_APPROVE.getCode());
                    adjustmentDB.setUpdateUser(user.getId());
                    validAdjustments.add(adjustmentDB);
                } else {
                    response.setCode(ResponseCode.ACC_ADJUSTMENT_INVALIDATE_STATE_APPROVE);
                    return response;
                }
            }
            if (validAdjustments.size() > 0) {
                for (AccountAdjustment dbBean : validAdjustments) {
                    adjustmentMapper.updateAuditAccountAdjustment(dbBean);
                }
                response.setCode(ResponseCode.SUCCESS);
            }
        } else {
            response.setCode(ResponseCode.ACC_ADJUSTMENT_EMPTY);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<CustomerAccount>> findCustomerAccount(CustomerAccountQuery customerAccountQuery) {
        DodopalResponse<DodopalDataPage<CustomerAccount>> response;
        if (MerUserTypeEnum.MERCHANT.getCode().equals(customerAccountQuery.getCustomerType())) {
            response = findMerchantAccount(customerAccountQuery);
        } else if (MerUserTypeEnum.PERSONAL.getCode().equals(customerAccountQuery.getCustomerType())) {
            response = findPersonalAccount(customerAccountQuery);
        } else {
            response = new DodopalResponse<DodopalDataPage<CustomerAccount>>();
            response.setCode(ResponseCode.ACC_ADJUSTMENT_CUSTOMER_TYPE_ERROR);
        }
        return response;
    }

    private DodopalResponse<DodopalDataPage<CustomerAccount>> findMerchantAccount(CustomerAccountQuery customerAccountQuery) {
        DodopalResponse<DodopalDataPage<CustomerAccount>> response = new DodopalResponse<DodopalDataPage<CustomerAccount>>();
        MerchantQueryDTO merQueryDTO = new MerchantQueryDTO();
        merQueryDTO.setMerCode(customerAccountQuery.getCustomerNo());
        merQueryDTO.setMerName(customerAccountQuery.getCustomerName());
        merQueryDTO.setMerState(MerStateEnum.THROUGH.getCode());
        merQueryDTO.setActivate(ActivateEnum.ENABLE.getCode());
        merQueryDTO.setPage(customerAccountQuery.getPage());
        
        // 查询列表信息
        DodopalResponse<DodopalDataPage<MerchantDTO>> merchantDtoList = merchantdelegate.findMerchantListByPage(merQueryDTO);
        List<CustomerAccount> merchantBeanList = new ArrayList<CustomerAccount>();
        if (ResponseCode.SUCCESS.equals(merchantDtoList.getCode())) {
            if (merchantDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(merchantDtoList.getResponseEntity().getRecords())) {
                for (MerchantDTO merDTO : merchantDtoList.getResponseEntity().getRecords()) {
                    CustomerAccount merBean = new CustomerAccount();
                    merBean.setCustomerId(merDTO.getId());
                    merBean.setCustomerName(merDTO.getMerName());
                    merBean.setCustomerNo(merDTO.getMerCode());
                    merBean.setCustomerType(MerUserTypeEnum.MERCHANT.getCode());
                    merchantBeanList.add(merBean);
                }
            }
            logger.info(" return code:" + merchantDtoList.getCode());
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(merchantDtoList.getResponseEntity());
            DodopalDataPage<CustomerAccount> pages = new DodopalDataPage<CustomerAccount>(page, merchantBeanList);
            response.setCode(merchantDtoList.getCode());
            response.setResponseEntity(pages);
        } else {
            response.setCode(merchantDtoList.getCode());
        }
        return response;
    }

    private DodopalResponse<DodopalDataPage<CustomerAccount>> findPersonalAccount(CustomerAccountQuery customerAccountQuery) {
        DodopalResponse<DodopalDataPage<CustomerAccount>> response = new DodopalResponse<DodopalDataPage<CustomerAccount>>();
        MerchantUserQueryDTO queryDto = new MerchantUserQueryDTO();
        queryDto.setMerUserType(MerUserTypeEnum.PERSONAL.getCode());
        queryDto.setUserCode(customerAccountQuery.getCustomerNo());
        queryDto.setMerUserNickName(customerAccountQuery.getCustomerName());
        queryDto.setActivate(ActivateEnum.ENABLE.getCode());
        queryDto.setPage(customerAccountQuery.getPage());

        DodopalResponse<DodopalDataPage<MerchantUserDTO>> merchantUserDtoList = merUserDelegate.findUserInfoListByPage(queryDto);

        List<CustomerAccount> merchantBeanList = new ArrayList<CustomerAccount>();
        if (ResponseCode.SUCCESS.equals(merchantUserDtoList.getCode())) {
            if (merchantUserDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(merchantUserDtoList.getResponseEntity().getRecords())) {
                for (MerchantUserDTO merDTO : merchantUserDtoList.getResponseEntity().getRecords()) {
                    CustomerAccount merBean = new CustomerAccount();
                    merBean.setCustomerId(merDTO.getId());
                    merBean.setCustomerName(merDTO.getMerUserNickName());
                    merBean.setCustomerNo(merDTO.getUserCode());
                    merBean.setCustomerType(MerUserTypeEnum.PERSONAL.getCode());
                    merchantBeanList.add(merBean);
                }
            }
            logger.info(" return code:" + merchantUserDtoList.getCode());
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(merchantUserDtoList.getResponseEntity());
            DodopalDataPage<CustomerAccount> pages = new DodopalDataPage<CustomerAccount>(page, merchantBeanList);
            response.setCode(merchantUserDtoList.getCode());
            response.setResponseEntity(pages);
        } else {
            response.setCode(merchantUserDtoList.getCode());
        }
        return response;
    }

    /**
     * 资金授信查询 
     * 在OSS调账时，用户在确定客户类型和客户时，需要确定该客户的资金类别，选择需要调账资金或授信。
     * 
     * @param custType(客户类型：个人、商户)
     * @param custNum(类型是商户：商户号；类型是个人：用户编号)
     * @return
     */
    @Override
    public DodopalResponse<FundAccountInfoDTO> findFundAccountInfo(CustomerAccountQuery customerAccountQuery) { 
        if (customerAccountQuery == null || DDPStringUtil.isNotPopulated(customerAccountQuery.getCustomerType()) || DDPStringUtil.isNotPopulated(customerAccountQuery.getCustomerNo())) {
            DodopalResponse<FundAccountInfoDTO> response = new DodopalResponse<FundAccountInfoDTO>();
            response.setCode(ResponseCode.ACC_ADJUSTMENT_CUSTOMER_PARAM_ERROR);
            return response;
        }
        return accountQueryDelegate.findFundAccountInfo(customerAccountQuery.getCustomerType(), customerAccountQuery.getCustomerNo());
    }

	@Override
	public List<AccountAdjustment> findAccountAdjustment(AccountAdjustmentQuery aaq) {
		List<AccountAdjustment> list = adjustmentMapper.findAccountAdjustmentByPage(aaq);
		return list;
	}
    
}

package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountControlDTO;
import com.dodopal.api.account.dto.AccountDTO;
import com.dodopal.api.account.dto.AccountInfoDTO;
import com.dodopal.api.account.dto.AccountInfoFundDTO;
import com.dodopal.api.account.dto.AccountMainInfoDTO;
import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.account.dto.query.AccountInfoListQueryDTO;
import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccStatusEnum;
import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.AccountBean;
import com.dodopal.oss.business.bean.AccountControlBean;
import com.dodopal.oss.business.bean.AccountInfoBean;
import com.dodopal.oss.business.bean.AccountInfoFundBean;
import com.dodopal.oss.business.bean.AccountMainInfoBean;
import com.dodopal.oss.business.bean.FundChangeBean;
import com.dodopal.oss.business.bean.query.AccountInfoListQuery;
import com.dodopal.oss.business.model.dto.FundChangeQuery;
import com.dodopal.oss.business.service.AccountInfoService;
import com.dodopal.oss.delegate.AccountInfoDelegate;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    @Autowired
    AccountInfoDelegate accountInfoDelegate;

    /**
     * 资金授信账户信息列表查询
     * @param accountInfoListQuery
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<AccountMainInfoBean>> findAccountInfoListByPage(AccountInfoListQuery accountInfoListQuery) {
        DodopalResponse<DodopalDataPage<AccountMainInfoBean>> response = new DodopalResponse<DodopalDataPage<AccountMainInfoBean>>();
        AccountInfoListQueryDTO queryDTO = new AccountInfoListQueryDTO();
        if (StringUtils.isNotBlank(accountInfoListQuery.getaType())) {
            queryDTO.setaType(accountInfoListQuery.getaType());
        }
        if (StringUtils.isNotBlank(accountInfoListQuery.getCustName())) {
            queryDTO.setCustName(accountInfoListQuery.getCustName());
        }
        if (StringUtils.isNotBlank(accountInfoListQuery.getCustNum())) {
            queryDTO.setCustNum(accountInfoListQuery.getCustNum());
        }
        if (StringUtils.isNotBlank(accountInfoListQuery.getStatus())) {
            queryDTO.setStatus(accountInfoListQuery.getStatus());
        }
        if (StringUtils.isNotBlank(accountInfoListQuery.getFundType())) {
            queryDTO.setFundType(accountInfoListQuery.getFundType());
        }
        if (accountInfoListQuery.getPage() != null) {
            queryDTO.setPage(accountInfoListQuery.getPage());
        }
        //调用delegate查询列表信息
        DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> retResponse = accountInfoDelegate.findAccountInfoListByPage(queryDTO);
        List<AccountMainInfoBean> crdSysOrderDtoList = new ArrayList<AccountMainInfoBean>();
        if (!ResponseCode.SUCCESS.equals(retResponse.getCode())) {//调用失败
            response.setCode(ResponseCode.SYSTEM_ERROR);
        } else {
            if (retResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(retResponse.getResponseEntity().getRecords())) {
                for (AccountMainInfoDTO accountMainInfoDTO : retResponse.getResponseEntity().getRecords()) {
                    AccountMainInfoBean bean = new AccountMainInfoBean();
                    bean.setAcid(accountMainInfoDTO.getAcid());
                    bean.setAccountCode(accountMainInfoDTO.getAccountCode());
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getAvailableBalance())) {
                        bean.setAvailableBalance(String.format("%.2f", Double.valueOf(accountMainInfoDTO.getAvailableBalance())));
                    }
                    bean.setCuName(accountMainInfoDTO.getCuName());
                    bean.setCustomerNo(accountMainInfoDTO.getCustomerNo());
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getFrozenAmount())) {
                        bean.setFrozenAmount(String.format("%.2f", Double.valueOf(accountMainInfoDTO.getFrozenAmount())));
                    }
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getFundType())) {
                        bean.setFundType(accountMainInfoDTO.getFundType());
                        bean.setFundTypeView(FundTypeEnum.getFundTypeNameByCode(accountMainInfoDTO.getFundType()));
                    }
                    bean.setId(accountMainInfoDTO.getId());
                    bean.setState(accountMainInfoDTO.getState());
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getSumTotalAmount())) {
                        bean.setSumTotalAmount(String.format("%.2f", Double.valueOf(accountMainInfoDTO.getSumTotalAmount())));
                    }
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getTotalBalance())) {
                        bean.setTotalBalance(String.format("%.2f", Double.valueOf(accountMainInfoDTO.getTotalBalance())));
                    }
                    if (AccStatusEnum.DISABLE.getCode().equals(accountMainInfoDTO.getState())) {
                        bean.setStateView(AccStatusEnum.DISABLE.getName());
                    } else if (AccStatusEnum.ENABLE.getCode().equals(accountMainInfoDTO.getState())) {
                        bean.setStateView(AccStatusEnum.ENABLE.getName());
                    }
                    crdSysOrderDtoList.add(bean);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(retResponse.getResponseEntity());
            DodopalDataPage<AccountMainInfoBean> pages = new DodopalDataPage<AccountMainInfoBean>(page, crdSysOrderDtoList);
            response.setCode(retResponse.getCode());
            response.setResponseEntity(pages);
        }
        return response;
    }

    /**
     * 详细查询
     * @param acid
     * @return
     */
    @Override
    public DodopalResponse<AccountInfoBean> findAccountInfoByCode(String acid) {
        DodopalResponse<AccountInfoBean> response = new DodopalResponse<AccountInfoBean>();
        //调用delegate查询详细信息
        DodopalResponse<AccountInfoDTO> retResponse = accountInfoDelegate.findAccountInfoByCode(acid);
        AccountInfoBean accountInfoBean = new AccountInfoBean();
        if (!ResponseCode.SUCCESS.equals(retResponse.getCode())) {//失败
            response.setCode(ResponseCode.SYSTEM_ERROR);
        } else {
            if (retResponse.getResponseEntity().getAccountDTO() != null) {
                //主账户信息
                AccountDTO accountDTO = retResponse.getResponseEntity().getAccountDTO();
                AccountBean accountBean = new AccountBean();
                BeanUtils.copyProperties(accountDTO, accountBean);
                accountInfoBean.setAccountBean(accountBean);
            }
            if ((retResponse.getResponseEntity().getAccountInfoFundDTOs() != null) && (retResponse.getResponseEntity().getAccountInfoFundDTOs().size() > 0)) {
                //资产授信信息
                List<AccountInfoFundDTO> accountFundDTOs = retResponse.getResponseEntity().getAccountInfoFundDTOs();
                List<AccountInfoFundBean> accountInfoFundBeans = new ArrayList<AccountInfoFundBean>();
                for (AccountInfoFundDTO accountInfoFundDTO : accountFundDTOs) {
                    AccountInfoFundBean accountInfoFundBean = new AccountInfoFundBean();
                    BeanUtils.copyProperties(accountInfoFundDTO, accountInfoFundBean);
                    if (StringUtils.isNotBlank(accountInfoFundDTO.getAvailableBalance())) {
                        accountInfoFundBean.setAvailableBalance(String.format("%.2f", Double.valueOf(accountInfoFundDTO.getAvailableBalance())));
                    }
                    if (StringUtils.isNotBlank(accountInfoFundDTO.getBeforeChangeAvailableAmount())) {
                        accountInfoFundBean.setBeforeChangeAvailableAmount(String.format("%.2f", Double.valueOf(accountInfoFundDTO.getBeforeChangeAvailableAmount())));
                    }
                    if (StringUtils.isNotBlank(accountInfoFundDTO.getBeforeChangeFrozenAmount())) {
                        accountInfoFundBean.setBeforeChangeFrozenAmount(String.format("%.2f", Double.valueOf(accountInfoFundDTO.getBeforeChangeFrozenAmount())));
                    }
                    if (StringUtils.isNotBlank(accountInfoFundDTO.getBeforeChangeTotalAmount())) {
                        accountInfoFundBean.setBeforeChangeTotalAmount(String.format("%.2f", Double.valueOf(accountInfoFundDTO.getBeforeChangeTotalAmount())));
                    }
                    if (StringUtils.isNotBlank(accountInfoFundDTO.getFrozenAmount())) {
                        accountInfoFundBean.setFrozenAmount(String.format("%.2f", Double.valueOf(accountInfoFundDTO.getFrozenAmount())));
                    }
                    if (StringUtils.isNotBlank(accountInfoFundDTO.getLastChangeAmount())) {
                        accountInfoFundBean.setLastChangeAmount(String.format("%.2f", Double.valueOf(accountInfoFundDTO.getLastChangeAmount())));
                    }
                    if (StringUtils.isNotBlank(accountInfoFundDTO.getSumTotalAmount())) {
                        accountInfoFundBean.setSumTotalAmount(String.format("%.2f", Double.valueOf(accountInfoFundDTO.getSumTotalAmount())));
                    }
                    if (StringUtils.isNotBlank(accountInfoFundDTO.getTotalBalance())) {
                        accountInfoFundBean.setTotalBalance(String.format("%.2f", Double.valueOf(accountInfoFundDTO.getTotalBalance())));
                    }
                    accountInfoFundBeans.add(accountInfoFundBean);
                }
                accountInfoBean.setAccountInfoFundBeans(accountInfoFundBeans);
            }
            if ((retResponse.getResponseEntity().getAccountControlDTOs() != null) && (retResponse.getResponseEntity().getAccountControlDTOs().size() > 0)) {
                //风控信息
                List<AccountControlDTO> accountControlDTOs = retResponse.getResponseEntity().getAccountControlDTOs();
                List<AccountControlBean> accountControlBeans = new ArrayList<AccountControlBean>();
                for (AccountControlDTO accountControlDTO : accountControlDTOs) {
                    AccountControlBean accountControlBean = new AccountControlBean();
                    BeanUtils.copyProperties(accountControlDTO, accountControlBean);
                    if (StringUtils.isNotBlank(accountControlDTO.getDayConsumeSingleLimit())) {
                        accountControlBean.setDayConsumeSingleLimit(String.format("%.2f", Double.valueOf(accountControlDTO.getDayConsumeSingleLimit())));
                    }
                    if (StringUtils.isNotBlank(accountControlDTO.getDayConsumeSumLimit())) {
                        accountControlBean.setDayConsumeSumLimit(String.format("%.2f", Double.valueOf(accountControlDTO.getDayConsumeSumLimit())));
                    }
                    if (StringUtils.isNotBlank(accountControlDTO.getDayRechargeSingleLimit())) {
                        accountControlBean.setDayRechargeSingleLimit(String.format("%.2f", Double.valueOf(accountControlDTO.getDayRechargeSingleLimit())));
                    }
                    if (StringUtils.isNotBlank(accountControlDTO.getDayRechargeSumLimit())) {
                        accountControlBean.setDayRechargeSumLimit(String.format("%.2f", Double.valueOf(accountControlDTO.getDayRechargeSumLimit())));
                    }
                    if (StringUtils.isNotBlank(accountControlDTO.getDayTransferMax())) {
                        accountControlBean.setDayTransferMax(accountControlDTO.getDayTransferMax());
                    }
                    if (StringUtils.isNotBlank(accountControlDTO.getDayTransferSingleLimit())) {
                        accountControlBean.setDayTransferSingleLimit(String.format("%.2f", Double.valueOf(accountControlDTO.getDayTransferSingleLimit())));
                    }
                    if (StringUtils.isNotBlank(accountControlDTO.getDayTransferSumLimit())) {
                        accountControlBean.setDayTransferSumLimit(String.format("%.2f", Double.valueOf(accountControlDTO.getDayTransferSumLimit())));
                    }
                    accountControlBeans.add(accountControlBean);
                }
                accountInfoBean.setAccountControlBeans(accountControlBeans);
            }
            response.setCode(retResponse.getCode());
            response.setResponseEntity(accountInfoBean);
        }
        return response;
    }

    /**
     * 禁用、启用账户
     * @param request
     * @param operation
     * @return
     */
    @Override
    public DodopalResponse<String> operateFundAccountsById(String oper, List<String> fundAccountIds, String userId) {
        DodopalResponse<String> response = accountInfoDelegate.operateFundAccountsById(oper, fundAccountIds, userId);
        return response;
    }

    /**
     * 账户资金变动记录
     * @param fundChangeQueryDTO
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<FundChangeBean>> findFundsChangeRecordsByPage(FundChangeQuery fundChangeQuery) {
        FundChangeQueryDTO fundChangeQueryDTO = new FundChangeQueryDTO();
        BeanUtils.copyProperties(fundChangeQuery, fundChangeQueryDTO);
        //调用查询变动记录列表接口
        DodopalResponse<DodopalDataPage<FundChangeDTO>> retResponse = accountInfoDelegate.findFundsChangeRecordsByPage(fundChangeQueryDTO);
        DodopalResponse<DodopalDataPage<FundChangeBean>> response = new DodopalResponse<DodopalDataPage<FundChangeBean>>();
        List<FundChangeBean> list = new ArrayList<FundChangeBean>();
        if (!ResponseCode.SUCCESS.equals(retResponse.getCode())) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        } else {
            if (retResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(retResponse.getResponseEntity().getRecords())) {
                for (FundChangeDTO fundChangeDTO : retResponse.getResponseEntity().getRecords()) {
                    FundChangeBean bean = new FundChangeBean();
                    //资金账户号
                    bean.setFundAccountCode(fundChangeDTO.getFundAccountCode());
                    //账户类别枚举
                    if (StringUtils.isNotBlank(fundChangeDTO.getFundType())) {
                        bean.setFundType(FundTypeEnum.getFundTypeNameByCode(fundChangeDTO.getFundType()));
                    }
                    //时间戳
                    if (fundChangeDTO.getAccountChangeTime() != null) {
                        bean.setAccountChangeTime((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(fundChangeDTO.getAccountChangeTime()));
                    }
                    //交易流水号
                    bean.setTranCode(fundChangeDTO.getTranCode());
                    //变动类型枚举
                    if (StringUtils.isNotBlank(fundChangeDTO.getChangeType())) {
                        bean.setChangeType(AccTranTypeEnum.getTranTypeNameByCode(fundChangeDTO.getChangeType()));
                    }
                    //金额格式转换
                    bean.setChangeAmount(String.format("%.2f", Double.valueOf(fundChangeDTO.getChangeAmount() + "") / 100));
                    bean.setBeforeChangeAmount(String.format("%.2f", Double.valueOf(fundChangeDTO.getBeforeChangeAmount() + "") / 100));
                    bean.setBeforeChangeAvailableAmount(String.format("%.2f", Double.valueOf(fundChangeDTO.getBeforeChangeAvailableAmount() + "") / 100));
                    bean.setBeforeChangeFrozenAmount(String.format("%.2f", Double.valueOf(fundChangeDTO.getBeforeChangeFrozenAmount() + "") / 100));
                    list.add(bean);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(retResponse.getResponseEntity());
            DodopalDataPage<FundChangeBean> pages = new DodopalDataPage<FundChangeBean>(page, list);
            response.setCode(retResponse.getCode());
            response.setResponseEntity(pages);
        }
        return response;
    }
    
    /**
     * 导出
     */
    @Override
    public DodopalResponse<List<AccountMainInfoBean>> expAccountInfo(AccountInfoListQuery accountInfoListQuery) {
        DodopalResponse<List<AccountMainInfoBean>> response = new DodopalResponse<List<AccountMainInfoBean>>();
        AccountInfoListQueryDTO queryDTO = new AccountInfoListQueryDTO();
        if (StringUtils.isNotBlank(accountInfoListQuery.getaType())) {
            queryDTO.setaType(accountInfoListQuery.getaType());
        }
        if (StringUtils.isNotBlank(accountInfoListQuery.getCustName())) {
            queryDTO.setCustName(accountInfoListQuery.getCustName());
        }
        if (StringUtils.isNotBlank(accountInfoListQuery.getCustNum())) {
            queryDTO.setCustNum(accountInfoListQuery.getCustNum());
        }
        if (StringUtils.isNotBlank(accountInfoListQuery.getStatus())) {
            queryDTO.setStatus(accountInfoListQuery.getStatus());
        }
        if (StringUtils.isNotBlank(accountInfoListQuery.getFundType())) {
            queryDTO.setFundType(accountInfoListQuery.getFundType());
        }
        if (accountInfoListQuery.getPage() != null) {
            queryDTO.setPage(accountInfoListQuery.getPage());
        }
        //调用delegate查询列表信息
        DodopalResponse<List<AccountMainInfoDTO>> retResponse = accountInfoDelegate.expAccountInfo(queryDTO);
        List<AccountMainInfoBean> crdSysOrderDtoList = new ArrayList<AccountMainInfoBean>();
        if (!ResponseCode.SUCCESS.equals(retResponse.getCode())) {//调用失败
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        } else {
            if (retResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(retResponse.getResponseEntity())) {
                for (AccountMainInfoDTO accountMainInfoDTO : retResponse.getResponseEntity()) {
                    AccountMainInfoBean bean = new AccountMainInfoBean();
                    bean.setAcid(accountMainInfoDTO.getAcid());
                    bean.setAccountCode(accountMainInfoDTO.getAccountCode());
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getAvailableBalance())) {
                        bean.setAvailableBalance(String.format("%.2f", Double.valueOf(accountMainInfoDTO.getAvailableBalance())));
                    }
                    bean.setCuName(accountMainInfoDTO.getCuName());
                    bean.setCustomerNo(accountMainInfoDTO.getCustomerNo());
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getFrozenAmount())) {
                        bean.setFrozenAmount(String.format("%.2f", Double.valueOf(accountMainInfoDTO.getFrozenAmount())));
                    }
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getFundType())) {
                        bean.setFundType(accountMainInfoDTO.getFundType());
                        bean.setFundTypeView(FundTypeEnum.getFundTypeNameByCode(accountMainInfoDTO.getFundType()));
                    }
                    bean.setId(accountMainInfoDTO.getId());
                    bean.setState(accountMainInfoDTO.getState());
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getSumTotalAmount())) {
                        bean.setSumTotalAmount(String.format("%.2f", Double.valueOf(accountMainInfoDTO.getSumTotalAmount())));
                    }
                    if (StringUtils.isNotBlank(accountMainInfoDTO.getTotalBalance())) {
                        bean.setTotalBalance(String.format("%.2f", Double.valueOf(accountMainInfoDTO.getTotalBalance())));
                    }
                    if (AccStatusEnum.DISABLE.getCode().equals(accountMainInfoDTO.getState())) {
                        bean.setStateView(AccStatusEnum.DISABLE.getName());
                    } else if (AccStatusEnum.ENABLE.getCode().equals(accountMainInfoDTO.getState())) {
                        bean.setStateView(AccStatusEnum.ENABLE.getName());
                    }
                    crdSysOrderDtoList.add(bean);
                }
            }
            response.setCode(retResponse.getCode());
            response.setResponseEntity(crdSysOrderDtoList);
        }
        return response;
    }

}

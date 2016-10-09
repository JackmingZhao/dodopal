package com.dodopal.account.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.account.business.model.Account;
import com.dodopal.account.business.model.AccountChange;
import com.dodopal.account.business.model.AccountControl;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.model.AccountMainInfo;
import com.dodopal.account.business.model.query.AccountChangeQuery;
import com.dodopal.account.business.model.query.AccountInfoListQuery;
import com.dodopal.account.business.model.query.FundChangeQuery;
import com.dodopal.account.business.service.AccountQueryService;
import com.dodopal.api.account.dto.AccountBeanDTO;
import com.dodopal.api.account.dto.AccountChangeDTO;
import com.dodopal.api.account.dto.AccountControlDTO;
import com.dodopal.api.account.dto.AccountDTO;
import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.dto.AccountInfoDTO;
import com.dodopal.api.account.dto.AccountInfoFundDTO;
import com.dodopal.api.account.dto.AccountMainInfoDTO;
import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.account.dto.query.AccountChangeRequestDTO;
import com.dodopal.api.account.dto.query.AccountInfoListQueryDTO;
import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.api.account.facade.AccountQueryFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DDPLog;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;

/**
 * 门户首页账户查询
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月27日
 */

@Service("accountQueryFacade")
public class AccountQueryFacadeImpl implements AccountQueryFacade {
    //logger 日志
    private static Logger logger = Logger.getLogger(AccountQueryFacadeImpl.class);

    private DDPLog<AccountQueryFacadeImpl> ddpLog = new DDPLog<>(AccountQueryFacadeImpl.class);

    @Autowired
    private AccountQueryService accountQueryService;

    //查询门户首页账户余额
    public DodopalResponse<AccountFundDTO> findAccountBalance(String aType, String custNum) {

        DodopalResponse<AccountFundDTO> response = new DodopalResponse<AccountFundDTO>();
        logger.info("AccountQueryFacadeImpl findAccountBalance 查询门户首页账户余额  aType:" + aType + ",custNum:" + custNum);
        //主账户的数据库id
        String id = "";
        //主账户编号
        String accountCode = "";
        //主账户资金类别
        String fundType = "";

        //总的可用余额
        double availableBalance = 0;
        //总的冻结金额
        double frozenAmount = 0;

        //资金账户 可用余额
        double accountMoney = 0;
        //授信账户 可用余额 	
        double accountFuntMoney = 0;
        //资金账户 冻结金额
        double accountFrozenAmount = 0;
        //授信账户 冻结金额
        double accountFundFrozenAmount = 0;
        try {
            //根据个人or企业  ，用户号or商户号，查询主账户表和资金授信表，返回账户和资金授信账户表对应的实体
            List<AccountFund> AccountFundList = accountQueryService.findAccountBalance(aType, custNum);
            //门户首页需要的信息封装的实体
            AccountFundDTO accountFundDTO = new AccountFundDTO();

            if (AccountFundList != null && AccountFundList.size() > 0) {
                for (AccountFund accountFund : AccountFundList) {
                    //将查询的实体数据拼成门户首页需要的数据封装的实体DTO
                    //总的可用余额
                    availableBalance += (double) accountFund.getAvailableBalance() / 100;
                    //总的冻结金额
                    frozenAmount += (double) accountFund.getFrozenAmount() / 100;

                    //如果为资金账户 可用余额 和 冻结金额
                    if (FundTypeEnum.FUND.getCode().equals(accountFund.getFundTypeFund())) {
                        accountMoney = ((double) accountFund.getAvailableBalance() / 100);
                        accountFrozenAmount = (double) accountFund.getFrozenAmount() / 100;
                    }
                    //如果为授信账户，可用余额  和 冻结金额
                    if (FundTypeEnum.AUTHORIZED.getCode().equals(accountFund.getFundTypeFund())) {
                        accountFuntMoney = ((double) accountFund.getAvailableBalance() / 100);
                        accountFundFrozenAmount = (double) accountFund.getFrozenAmount() / 100;
                    }
                    //主账户表id
                    id = accountFund.getId();
                    //主账户编码
                    accountCode = accountFund.getAccountCode();
                    //主账户资金类别
                    fundType = accountFund.getFundType();

                }

                accountFundDTO.setAccountFuntMoney(accountFuntMoney);
                accountFundDTO.setAccountMoney(accountMoney);
                accountFundDTO.setAvailableBalance(availableBalance);
                accountFundDTO.setFrozenAmount(frozenAmount);
                accountFundDTO.setId(id);
                accountFundDTO.setAccountCode(accountCode);
                accountFundDTO.setFundType(fundType);
                accountFundDTO.setAccountFrozenAmount(accountFrozenAmount);
                accountFundDTO.setAccountFundFrozenAmount(accountFundFrozenAmount);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(accountFundDTO);
        }
        catch (Exception e) {
            logger.error("AccountQueryFacadeImpl findAccountBalance 查询门户首页账户余额  throws e:" + e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //查询资金变更记录(分页)
    public DodopalResponse<DodopalDataPage<FundChangeDTO>> findFundsChangeRecordsByPage(FundChangeQueryDTO fundChangeQueryDTO) {
        logger.info("AccountQueryFacadeImpl findFundsChangeRecordsByPage 查询资金变更记录 (分页): " + fundChangeQueryDTO.toString());
        DodopalResponse<DodopalDataPage<FundChangeDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<FundChangeDTO>>();
        //查询资金变更记录的查询条件封装的实体
        FundChangeQuery fundChangeQuery = new FundChangeQuery();
        try {
            //将 查询资金变更记录的 查询条件封装的在传输时的实体 fundChangeQueryDTO 复制到 查询资金变更记录的查询条件封装的实体 fundChangeQuery
            PropertyUtils.copyProperties(fundChangeQuery, fundChangeQueryDTO);
            //根据条件查询资金变更记录
            DodopalDataPage<AccountChange> pagelist = accountQueryService.findFundsChangeRecordsByPage(fundChangeQuery);
            List<AccountChange> fundChangeList = pagelist.getRecords();

            List<FundChangeDTO> fundChangeDTOList = new ArrayList<FundChangeDTO>();
            // 将查询到的记录 中的实体 转成 传输时的实体 DTO
            if (CollectionUtils.isNotEmpty(fundChangeList)) {
                for (AccountChange accountChange : fundChangeList) {
                    FundChangeDTO fundChangeDTO = new FundChangeDTO();

                    PropertyUtils.copyProperties(fundChangeDTO, accountChange);

                    fundChangeDTOList.add(fundChangeDTO);
                }
            }
            //分页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<FundChangeDTO> pages = new DodopalDataPage<FundChangeDTO>(page, fundChangeDTOList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.debug("findFundsChangeRecordsByPage call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    /**
     * 账户详细信息查询 根据主账户的数据库ID来查询该账户的详细信息。 OSS在账户详情页面展示上可以分为以下几个Panel：
     * 客户编号、客户名称和主账户信息； 资金账户信息； 资金账户风控。
     */
    @Override
    public DodopalResponse<AccountInfoDTO> findAccountInfo(String acid) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtils.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        DodopalResponse<AccountInfoDTO> response = new DodopalResponse<AccountInfoDTO>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            respexplain.append("[1.acid=" + acid + "]");
            //根据主键id查询主账户信息
            Account account = accountQueryService.findAccountByAcid(acid);
            respexplain.append("[2.accountCode=" + account.getAccountCode() + "]");
            //根据ACCOUNT_CODE查询资金授信账户信息
            List<AccountFund> accountFundList = accountQueryService.findAccountFundByActCode(account.getAccountCode());

            List<String> fundActCodes = new ArrayList<String>();
            for (AccountFund accountFund : accountFundList) {
                fundActCodes.add(accountFund.getFundAccountCode());
            }
            respexplain.append("[3.fundActCodes=" + JSONObject.toJSONString(fundActCodes) + "]");
            //根据FUND_ACCOUNT_CODE查询资金账户风控信息
            List<AccountControl> accountControlList = accountQueryService.findAccountControlByActCode(fundActCodes);

            //account合并到AccountInfoDTO
            AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
            AccountDTO accountDTO = new AccountDTO();
            BeanUtils.copyProperties(account, accountDTO);
            //accountFund合并到AccountInfoFundDTO
            List<AccountInfoFundDTO> accountInfoFundDTOs = new ArrayList<AccountInfoFundDTO>();
            if (accountFundList.size() > 0) {
                for (AccountFund accountFund : accountFundList) {
                    AccountInfoFundDTO accountInfoFundDTO = new AccountInfoFundDTO();
                    accountInfoFundDTO.setAccountCode(accountFund.getAccountCode());
                    accountInfoFundDTO.setAvailableBalance(Double.valueOf(accountFund.getAvailableBalance()) / 100 + "");
                    accountInfoFundDTO.setBeforeChangeAvailableAmount(Double.valueOf(accountFund.getBeforeChangeAvailableAmount()) / 100 + "");
                    accountInfoFundDTO.setBeforeChangeFrozenAmount(Double.valueOf(accountFund.getBeforeChangeFrozenAmount()) / 100 + "");
                    accountInfoFundDTO.setBeforeChangeTotalAmount(Double.valueOf(accountFund.getBeforeChangeTotalAmount()) / 100 + "");
                    accountInfoFundDTO.setFrozenAmount(Double.valueOf(accountFund.getFrozenAmount()) / 100 + "");
                    accountInfoFundDTO.setFundAccountCode(accountFund.getFundAccountCode());
                    accountInfoFundDTO.setFundType(accountFund.getFundType());
                    accountInfoFundDTO.setLastChangeAmount(Double.valueOf(accountFund.getLastChangeAmount()) / 100 + "");
                    accountInfoFundDTO.setState(accountFund.getState());
                    accountInfoFundDTO.setSumTotalAmount(Double.valueOf(accountFund.getSumTotalAmount()) / 100 + "");
                    accountInfoFundDTO.setTotalBalance(Double.valueOf(accountFund.getTotalBalance()) / 100 + "");
                    if (StringUtils.isNotBlank(accountFund.getCreateUser())) {
                        accountInfoFundDTO.setCreateUser(accountFund.getCreateUser());
                    } else {
                        accountInfoFundDTO.setCreateUser("");
                    }
                    accountInfoFundDTO.setCreateDate(accountFund.getCreateDate());
                    if (StringUtils.isNotBlank(accountFund.getUpdateUser())) {
                        accountInfoFundDTO.setUpdateUser(accountFund.getUpdateUser());
                    } else {
                        accountInfoFundDTO.setUpdateUser("");
                    }
                    accountInfoFundDTO.setUpdateDate(accountFund.getUpdateDate());
                    accountInfoFundDTOs.add(accountInfoFundDTO);
                }
            }
            //accountControl合并到AccountControlDTO
            List<AccountControlDTO> accountControlDTOs = new ArrayList<AccountControlDTO>();
            if (accountControlList.size() > 0) {
                for (AccountControl accountControl : accountControlList) {
                    AccountControlDTO accountControlDTO = new AccountControlDTO();
                    accountControlDTO.setDayConsumeSingleLimit(Double.valueOf(accountControl.getDayConsumeSingleLimit()) / 100 + "");
                    accountControlDTO.setDayConsumeSumLimit(Double.valueOf(accountControl.getDayConsumeSumLimit()) / 100 + "");
                    accountControlDTO.setDayRechargeSingleLimit(Double.valueOf(accountControl.getDayRechargeSingleLimit()) / 100 + "");
                    accountControlDTO.setDayRechargeSumLimit(Double.valueOf(accountControl.getDayRechargeSumLimit()) / 100 + "");
                    accountControlDTO.setDayTransferMax(accountControl.getDayTransferMax() + "");
                    accountControlDTO.setDayTransferSingleLimit(Double.valueOf(accountControl.getDayTransferSingleLimit()) / 100 + "");
                    accountControlDTO.setDayTransferSumLimit(Double.valueOf(accountControl.getDayTransferSumLimit()) / 100 + "");
                    accountControlDTO.setFundAccountCode(accountControl.getFundAccountCode());
                    if (StringUtils.isNotBlank(accountControl.getCreateUser())) {
                        accountControlDTO.setCreateUser(accountControl.getCreateUser());
                    } else {
                        accountControlDTO.setCreateUser("");
                    }
                    accountControlDTO.setCreateDate(accountControl.getCreateDate());
                    if (StringUtils.isNotBlank(accountControl.getUpdateUser())) {
                        accountControlDTO.setUpdateUser(accountControl.getUpdateUser());
                    } else {
                        accountControlDTO.setUpdateUser("");
                    }
                    accountControlDTO.setUpdateDate(accountControl.getUpdateDate());

                    accountControlDTOs.add(accountControlDTO);
                }
            }
            accountInfoDTO.setAccountDTO(accountDTO);
            accountInfoDTO.setAccountInfoFundDTOs(accountInfoFundDTOs);
            accountInfoDTO.setAccountControlDTOs(accountControlDTOs);
            response.setResponseEntity(accountInfoDTO);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e.getMessage());
        }
        //记录日志
        SysLog syslog = new SysLog();
        syslog.setInParas(acid);
        syslog.setTradeStart(tradeStart);
        syslog.setRespCode(logRespcode);
        syslog.setRespExplain(respexplain.toString());
        syslog.setDescription("账户详细信息查询接口(外部调用)");
        syslog.setOrderNum(acid);
        syslog.setClassName(this.getClass().getName());
        syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        syslog.setOutParas(JSONObject.toJSONString(response));
        ddpLog.info(syslog);

        return response;
    }

    /**
     * 资金授信查询 add by shenxiang 在OSS调账时，用户在确定客户类型和客户时，需要确定该客户的资金类别，选择需要调账资金或授信。
     * @param custType(客户类型：个人、商户)
     * @param custNum(类型是商户：商户号；类型是个人：用户编号)
     */
    @Override
    public DodopalResponse<FundAccountInfoDTO> findFundAccountInfo(String custType, String custNum) {
        DodopalResponse<FundAccountInfoDTO> response = new DodopalResponse<FundAccountInfoDTO>();
        // custType    String  1   Y   枚举，类型：个人、商户
        if (null == MerUserTypeEnum.getMerUserUserTypeByCode(custType)) {
            response.setCode(ResponseCode.ACC_CUSTOMER_TYPE_ERROR);
            return response;
        }
        // custNum  String  40  Y   类型是商户：商户号；类型是个人：用户编号
        if (!DDPStringUtil.existingWithLength(custNum, 40)) {
            response.setCode(ResponseCode.ACC_CUSTOMER_CODE_ERROR);
            return response;
        }
        FundAccountInfoDTO resultDto = null;
        try {
            resultDto = accountQueryService.findFundAccountInfo(custType, custNum);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            return response;
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(resultDto);
        return response;
    }

    /**
     * 资金授信账户信息列表查询
     * @param acid
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> findAccountInfoListByPage(AccountInfoListQueryDTO accountInfoListQueryDTO) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtils.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> response = new DodopalResponse<>();
        AccountInfoListQuery accountInfoListQuery = new AccountInfoListQuery();
        try {
            BeanUtils.copyProperties(accountInfoListQueryDTO, accountInfoListQuery);
            //查询数据
            DodopalDataPage<AccountMainInfo> dataPage = accountQueryService.findAccountInfoListByPage(accountInfoListQuery);
            List<AccountMainInfo> accountMainInfos = dataPage.getRecords();
            List<AccountMainInfoDTO> accountMainInfoDTOs = new ArrayList<AccountMainInfoDTO>();
            //accountMainInfo合并到AccountMainInfoDTO
            if (accountMainInfos.size() > 0) {
                for (AccountMainInfo accountMainInfo : accountMainInfos) {
                    AccountMainInfoDTO accountMainInfoDTO = new AccountMainInfoDTO();
                    BeanUtils.copyProperties(accountMainInfo, accountMainInfoDTO);
                    accountMainInfoDTO.setSumTotalAmount(Double.valueOf(accountMainInfoDTO.getSumTotalAmount()) / 100 + "");
                    accountMainInfoDTO.setTotalBalance(Double.valueOf(accountMainInfoDTO.getTotalBalance()) / 100 + "");
                    accountMainInfoDTO.setAvailableBalance(Double.valueOf(accountMainInfoDTO.getAvailableBalance()) / 100 + "");
                    accountMainInfoDTO.setFrozenAmount(Double.valueOf(accountMainInfoDTO.getFrozenAmount()) / 100 + "");
                    accountMainInfoDTOs.add(accountMainInfoDTO);
                }
            }

            PageParameter page = DodopalDataPageUtil.convertPageInfo(dataPage);
            DodopalDataPage<AccountMainInfoDTO> pages = new DodopalDataPage<AccountMainInfoDTO>(page, accountMainInfoDTOs);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e.getMessage());
        }

        SysLog syslog = new SysLog();
        syslog.setInParas(JSONObject.toJSONString(accountInfoListQueryDTO));
        syslog.setTradeStart(tradeStart);
        syslog.setRespCode(logRespcode);
        syslog.setRespExplain(respexplain.toString());
        syslog.setDescription("资金授信账户信息列表查询(外部调用)");
        syslog.setClassName(this.getClass().getName());
        syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        syslog.setOutParas(JSONObject.toJSONString(response));
        ddpLog.info(syslog);
        return response;
    }

    //查询资金变更记录
    public DodopalResponse<List<FundChangeDTO>> findFundsChangeRecordsAll(FundChangeQueryDTO fundChangeQueryDTO) {
        logger.info("AccountQueryFacadeImpl findFundsChangeRecordsAll 查询资金变更记录  ");
        DodopalResponse<List<FundChangeDTO>> dodopalResponse = new DodopalResponse<List<FundChangeDTO>>();
        //查询资金变更记录的查询条件封装的实体
        FundChangeQuery fundChangeQuery = new FundChangeQuery();
        try {
            //将 查询资金变更记录的 查询条件封装的在传输时的实体 fundChangeQueryDTO 复制到 查询资金变更记录的查询条件封装的实体 fundChangeQuery
            PropertyUtils.copyProperties(fundChangeQuery, fundChangeQueryDTO);
            //根据条件查询资金变更记录
            List<AccountChange> fundChangeList = accountQueryService.findFundsChangeRecordsAll(fundChangeQuery);

            List<FundChangeDTO> fundChangeDTOList = new ArrayList<FundChangeDTO>();
            // 将查询到的记录 中的实体 转成 传输时的实体 DTO
            if (CollectionUtils.isNotEmpty(fundChangeList)) {
                for (AccountChange accountChange : fundChangeList) {
                    FundChangeDTO fundChangeDTO = new FundChangeDTO();

                    PropertyUtils.copyProperties(fundChangeDTO, accountChange);

                    fundChangeDTOList.add(fundChangeDTO);
                }
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(fundChangeDTOList);
        }
        catch (Exception e) {
            logger.debug("findFundsChangeRecordsAll call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    //查询 资金变更记录（手机、VC端接入）
    public DodopalResponse<List<AccountChangeDTO>> queryAccountChangeByPhone(AccountChangeRequestDTO requestDTO) {
        DodopalResponse<List<AccountChangeDTO>> response = new DodopalResponse<List<AccountChangeDTO>>();

        try {
            AccountChangeQuery queryRequest = new AccountChangeQuery();
            PropertyUtils.copyProperties(queryRequest, requestDTO);
            List<AccountChange> AccountChangeList = accountQueryService.queryAccountChangeByPhone(queryRequest);

            List<AccountChangeDTO> accountChangeDTOList = new ArrayList<AccountChangeDTO>();
            if (CollectionUtils.isNotEmpty(AccountChangeList)) {
                for (AccountChange accountChange : AccountChangeList) {
                    AccountChangeDTO accountChangeDTO = new AccountChangeDTO();
                    accountChangeDTO.setAmount(accountChange.getChangeAmount());
                    accountChangeDTO.setFundtype(accountChange.getFundType());
                    accountChangeDTO.setChangetype(accountChange.getChangeType());
                    accountChangeDTO.setChangedate(accountChange.getChangeDate());
                    accountChangeDTO.setBefchangeamt(accountChange.getBeforeChangeAmount());
                    accountChangeDTO.setBefchangeavailableamt(accountChange.getBeforeChangeAvailableAmount());
                    accountChangeDTO.setBefchangefrozenamt(accountChange.getBeforeChangeFrozenAmount());
                    accountChangeDTOList.add(accountChangeDTO);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(accountChangeDTOList);

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("AccountQueryFacadeImpl queryAccountChange throw e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    //查询账户余额 （手机、VC端接入）
    public DodopalResponse<AccountBeanDTO> queryAccountBalance(String custtype, String custcode) {
        DodopalResponse<AccountBeanDTO> response = new DodopalResponse<AccountBeanDTO>();
        logger.info("AccountQueryFacadeImpl queryAccountBalance 查询账户余额 （手机、VC端接入）  custtype:" + custtype + ",custcode:" + custcode);

        //主账户资金类别
        String fundType = "";

        //资金总余额
        long totalbalance = 0;
        //授信总金额
        long authtotalbalance = 0;

        //资金账户 可用余额
        long accountMoney = 0;
        //资金账户 冻结金额
        long accountFrozenAmount = 0;

        //授信账户 可用余额
        long accountFuntMoney = 0;
        //授信账户 冻结金额
        long accountFundFrozenAmount = 0;
        try {
            //根据个人or企业  ，用户号or商户号，查询主账户表和资金授信表，返回账户和资金授信账户表对应的实体
            List<AccountFund> AccountFundList = accountQueryService.findAccountBalance(custtype, custcode);
            //门户首页需要的信息封装的实体
            AccountBeanDTO accountBeanDTO = new AccountBeanDTO();

            if (AccountFundList != null && AccountFundList.size() > 0) {
                for (AccountFund accountFund : AccountFundList) {
                    //将查询的实体数据拼成门户首页需要的数据封装的实体DTO

                    //如果为资金账户 可用余额 和 冻结金额 和资金总余额
                    if (FundTypeEnum.FUND.getCode().equals(accountFund.getFundTypeFund())) {
                        accountMoney = accountFund.getAvailableBalance();
                        accountFrozenAmount = accountFund.getFrozenAmount();
                        //资金总余额
                        totalbalance = accountMoney + accountFrozenAmount;
                    }
                    //如果为授信账户，可用余额  和 冻结金额 和 授信总余额
                    if (FundTypeEnum.AUTHORIZED.getCode().equals(accountFund.getFundTypeFund())) {
                        accountFuntMoney = accountFund.getAvailableBalance();
                        accountFundFrozenAmount = accountFund.getFrozenAmount();
                        //授信总金额
                        authtotalbalance = accountFuntMoney + accountFundFrozenAmount;
                    }
                    //主账户资金类别
                    fundType = accountFund.getFundType();

                }

                accountBeanDTO.setAuthavailablebalance(accountFuntMoney);
                accountBeanDTO.setAuthfrozenamount(accountFundFrozenAmount);
                accountBeanDTO.setAuthtotalbalance(authtotalbalance);

                accountBeanDTO.setTotalbalance(totalbalance);

                accountBeanDTO.setFundtype(fundType);

                accountBeanDTO.setAvailablebalance(accountMoney);
                accountBeanDTO.setFrozenamount(accountFrozenAmount);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(accountBeanDTO);
        }
        catch (Exception e) {
            logger.error("AccountQueryFacadeImpl queryAccountBalance 查询账户余额 （手机、VC端接入）  throws e:" + e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 导出账户信息
     */
    @Override
    public DodopalResponse<List<AccountMainInfoDTO>> expAccountInfo(AccountInfoListQueryDTO accountInfoListQueryDTO) {
        DodopalResponse<List<AccountMainInfoDTO>> response = new DodopalResponse<>();
        AccountInfoListQuery accountInfoListQuery = new AccountInfoListQuery();
        try {
            BeanUtils.copyProperties(accountInfoListQueryDTO, accountInfoListQuery);
            //查询数据
            List<AccountMainInfo> data = accountQueryService.expAccountInfo(accountInfoListQuery);
            List<AccountMainInfoDTO> accountMainInfoDTOs = new ArrayList<AccountMainInfoDTO>();
            //accountMainInfo合并到AccountMainInfoDTO
            if (data.size() > 0) {
                if (data.size() > ExcelUtil.EXPORT_MAX_COUNT) {
                    response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                    return response;
                } else {
                    for (AccountMainInfo accountMainInfo : data) {
                        AccountMainInfoDTO accountMainInfoDTO = new AccountMainInfoDTO();
                        BeanUtils.copyProperties(accountMainInfo, accountMainInfoDTO);
                        accountMainInfoDTO.setSumTotalAmount(Double.valueOf(accountMainInfoDTO.getSumTotalAmount()) / 100 + "");
                        accountMainInfoDTO.setTotalBalance(Double.valueOf(accountMainInfoDTO.getTotalBalance()) / 100 + "");
                        accountMainInfoDTO.setAvailableBalance(Double.valueOf(accountMainInfoDTO.getAvailableBalance()) / 100 + "");
                        accountMainInfoDTO.setFrozenAmount(Double.valueOf(accountMainInfoDTO.getFrozenAmount()) / 100 + "");
                        accountMainInfoDTOs.add(accountMainInfoDTO);
                    }
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(accountMainInfoDTOs);
                }
            } else {
                response.setCode(ResponseCode.EXCEL_EXPORT_NULL_DATA);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}

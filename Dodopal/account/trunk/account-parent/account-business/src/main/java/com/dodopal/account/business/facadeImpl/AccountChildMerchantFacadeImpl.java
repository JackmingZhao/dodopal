package com.dodopal.account.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.account.business.model.AccountChildMerchant;
import com.dodopal.account.business.model.ChildMerchantAccountChange;
import com.dodopal.account.business.model.query.AccountChildMerchantQuery;
import com.dodopal.account.business.model.query.ChildMerFundChangeQuery;
import com.dodopal.account.business.service.AccountChildMerchantService;
import com.dodopal.api.account.dto.AccountChildMerchantDTO;
import com.dodopal.api.account.dto.ChildMerchantAccountChangeDTO;
import com.dodopal.api.account.dto.query.AccountChildMerchantQueryDTO;
import com.dodopal.api.account.dto.query.ChildMerFundChangeQueryDTO;
import com.dodopal.api.account.facade.AccountChildMerchantFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DDPLog;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;

@Service("AccountChildMerchantFacade")
public class AccountChildMerchantFacadeImpl implements AccountChildMerchantFacade {
    //logger 日志
    private static Logger logger = Logger.getLogger(AccountChildMerchantFacadeImpl.class);

    private DDPLog<AccountChildMerchantFacadeImpl> ddpLog = new DDPLog<>(AccountChildMerchantFacadeImpl.class);

    @Autowired
    AccountChildMerchantService accountChildMerchantService;

    //查询子商户的可用余额及账户信息（分页）
    public DodopalResponse<DodopalDataPage<AccountChildMerchantDTO>> findAccountChildMerByPage(AccountChildMerchantQueryDTO queryDTO) {
        logger.info("查询子商户的可用余额及账户信息（分页） AccountChildMerchantFacadeImpl findAccountChildMerByPage  queryDTO:"+queryDTO.toString());
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtils.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        DodopalResponse<DodopalDataPage<AccountChildMerchantDTO>> response = new DodopalResponse<DodopalDataPage<AccountChildMerchantDTO>>();
        AccountChildMerchantQuery accountQuery = new AccountChildMerchantQuery();
        try {
            PropertyUtils.copyProperties(accountQuery, queryDTO);
            //查询数据
            DodopalDataPage<AccountChildMerchant> rtResponse = accountChildMerchantService.findAccountChildMerByPage(accountQuery);

            List<AccountChildMerchant> accountList = rtResponse.getRecords();
            List<AccountChildMerchantDTO> accountDTOList = new ArrayList<AccountChildMerchantDTO>();

            if (CollectionUtils.isNotEmpty(accountList)) {
                for (AccountChildMerchant accountChildMerchant : accountList) {
                    AccountChildMerchantDTO accountDTO = new AccountChildMerchantDTO();
                    PropertyUtils.copyProperties(accountDTO, accountChildMerchant);
                    accountDTOList.add(accountDTO);
                }
            }

            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse);
            DodopalDataPage<AccountChildMerchantDTO> pages = new DodopalDataPage<AccountChildMerchantDTO>(page, accountDTOList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.error("查询子商户的可用余额及账户信息（分页） AccountChildMerchantFacadeImpl findAccountChildMerByPage  throws e:"+e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e.getMessage());
        }

        SysLog syslog = new SysLog();
        syslog.setInParas(JSONObject.toJSONString(queryDTO));
        syslog.setTradeStart(tradeStart);
        syslog.setRespCode(logRespcode);
        syslog.setRespExplain(respexplain.toString());
        syslog.setDescription("根据上级商户 查询其所有子商户 总余额信息列表(外部调用(门户 商户管理))");
        syslog.setClassName(this.getClass().getName());
        syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        syslog.setOutParas(JSONObject.toJSONString(response));
        ddpLog.info(syslog);
        return response;

    }

    //查询子商户的可用余额及账户信息(导出)
    public DodopalResponse<List<AccountChildMerchantDTO>> findAccountChildMerByList(AccountChildMerchantQueryDTO queryDTO) {
        logger.info("查询子商户的可用余额及账户信息  AccountChildMerchantFacadeImpl findAccountChildMerByList  queryDTO:"+queryDTO.toString());
        DodopalResponse<List<AccountChildMerchantDTO>> response = new DodopalResponse<List<AccountChildMerchantDTO>>();
        List<AccountChildMerchantDTO> accountDTOList = new ArrayList<AccountChildMerchantDTO>();
        AccountChildMerchantQuery accountQuery = new AccountChildMerchantQuery();
        try {
            PropertyUtils.copyProperties(accountQuery, queryDTO);
            List<AccountChildMerchant> accountList = accountChildMerchantService.findAccountChildMerByList(accountQuery);
            if (CollectionUtils.isNotEmpty(accountList)) {
                for (AccountChildMerchant accountChildMerchant : accountList) {
                    AccountChildMerchantDTO accountDTO = new AccountChildMerchantDTO();
                    PropertyUtils.copyProperties(accountDTO, accountChildMerchant);
                    accountDTOList.add(accountDTO);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(accountDTOList);
          
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error("根据上级商户查询所有子商户的可用余额 账户信息 AccountChildMerchantFacadeImpl  findAccountChildMerByList throws e:"+e);
        }
        return response;
    }

    //根据上级商户编号 查询 子商户资金变更记录（分页）
    public DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeDTO>> findAccountChangeChildMerByPage(ChildMerFundChangeQueryDTO query) {
        logger.info("根据上级商户编号 查询 子商户资金变更记录（分页）AccountChildMerchantFacadeImpl  findAccountChangeChildMerByPage  query: "+query.toString());
        DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeDTO>> response = new DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeDTO>>();
        ChildMerFundChangeQuery fundchangeQuery = new ChildMerFundChangeQuery();
        try {
            PropertyUtils.copyProperties(fundchangeQuery, query);
            DodopalDataPage<ChildMerchantAccountChange> rtResponse = accountChildMerchantService.findAccountChangeChildMerByPage(fundchangeQuery);
            List<ChildMerchantAccountChange> accountFundList = rtResponse.getRecords();
            List<ChildMerchantAccountChangeDTO> accountFundDTOList = new ArrayList<ChildMerchantAccountChangeDTO>();
            if (CollectionUtils.isNotEmpty(accountFundList)) {
                for(ChildMerchantAccountChange accountFund :accountFundList){
                    ChildMerchantAccountChangeDTO accountFundDTO = new ChildMerchantAccountChangeDTO();
                    PropertyUtils.copyProperties(accountFundDTO, accountFund);
                    accountFundDTOList.add(accountFundDTO);
                }
            }
            
            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse);
            DodopalDataPage<ChildMerchantAccountChangeDTO> pages = new DodopalDataPage<ChildMerchantAccountChangeDTO>(page, accountFundDTOList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages); 
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("根据上级商户编号 查询 子商户资金变更记录（导出）AccountChildMerchantFacadeImpl  findAccountChangeChildMerByPage throws e:"+e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    
  //根据上级商户编号 查询 子商户资金变更记录（导出）
    public DodopalResponse<List<ChildMerchantAccountChangeDTO>> findAccountChangeChildMerByList(ChildMerFundChangeQueryDTO query) {
        logger.info("根据上级商户编号 查询 子商户资金变更记录（导出）AccountChildMerchantFacadeImpl  findAccountChangeChildMerByList  query: "+query.toString());
        DodopalResponse<List<ChildMerchantAccountChangeDTO>> response = new DodopalResponse<List<ChildMerchantAccountChangeDTO>>();
        ChildMerFundChangeQuery fundchangeQuery = new ChildMerFundChangeQuery();
        try {
            PropertyUtils.copyProperties(fundchangeQuery, query);
            List<ChildMerchantAccountChange> accountFundList = accountChildMerchantService.findAccountChangeChildMerByList(fundchangeQuery);
            List<ChildMerchantAccountChangeDTO> accountFundDTOList = new ArrayList<ChildMerchantAccountChangeDTO>();
            if (CollectionUtils.isNotEmpty(accountFundList)) {
                for(ChildMerchantAccountChange accountFund :accountFundList){
                    ChildMerchantAccountChangeDTO accountFundDTO = new ChildMerchantAccountChangeDTO();
                    PropertyUtils.copyProperties(accountFundDTO, accountFund);
                    accountFundDTOList.add(accountFundDTO);
                }
               
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(accountFundDTOList);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("根据上级商户编号 查询 子商户资金变更记录（导出）AccountChildMerchantFacadeImpl  findAccountChangeChildMerByList throws e:"+e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}

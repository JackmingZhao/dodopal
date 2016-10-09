package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.account.dto.AccountBeanDTO;
import com.dodopal.api.account.dto.AccountChangeDTO;
import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.dto.query.AccountChangeRequestDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.AccountBean;
import com.dodopal.product.business.bean.AccountFundBean;
import com.dodopal.product.business.bean.QueryAccountChangeRequestDTO;
import com.dodopal.product.business.bean.QueryAccountChangeResultDTO;
import com.dodopal.product.business.service.AccountQueryService;
import com.dodopal.product.delegate.AccountQueryDelegate;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {
    private final static Logger log = LoggerFactory.getLogger(AccountQueryServiceImpl.class);
    @Autowired
    AccountQueryDelegate accountQueryDelegate;

    @Override
    public DodopalResponse<AccountFundBean> findAccountBalance(String aType, String custNum) {
        DodopalResponse<AccountFundBean> dodopalResponse = new DodopalResponse<AccountFundBean>();
        try {
            DodopalResponse<AccountFundDTO> dtoResponse = accountQueryDelegate.findAccountBalance(aType, custNum);
            if (ResponseCode.SUCCESS.equals(dtoResponse.getCode())) {
                if (null != dtoResponse.getResponseEntity()) {
                    AccountFundBean accountFundBean = new AccountFundBean();
                    PropertyUtils.copyProperties(accountFundBean, dtoResponse.getResponseEntity());
                    dodopalResponse.setResponseEntity(accountFundBean);
                }
            }
            dodopalResponse.setCode(dtoResponse.getCode());
        }
        catch (HessianRuntimeException e) {
            dodopalResponse.setCode(ResponseCode.PRODUCT_CALL_ACCOUNT_ERROR);
            log.error("AccountQueryServiceImpl's findAccountBalance call an error:", e);
        }
        catch (Exception e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("AccountQueryServiceImpl's findAccountBalance call an error:", e);
        }
        return dodopalResponse;
    }

    //查询资金变更记录（手机、 VC端）
    public DodopalResponse<List<QueryAccountChangeResultDTO>> queryAccountChange(QueryAccountChangeRequestDTO queryRequestDto) {
        DodopalResponse<List<QueryAccountChangeResultDTO>> response = new DodopalResponse<List<QueryAccountChangeResultDTO>>();
        try {
            AccountChangeRequestDTO requestDTO = new AccountChangeRequestDTO();
            PropertyUtils.copyProperties(requestDTO, queryRequestDto);
            DodopalResponse<List<AccountChangeDTO>> rtResponse = accountQueryDelegate.queryAccountChange(requestDTO);
            List<QueryAccountChangeResultDTO> accountChangeDTOList = new ArrayList<QueryAccountChangeResultDTO>();
            
            if(rtResponse.getCode().equals(ResponseCode.SUCCESS)&&rtResponse.getResponseEntity()!=null){
                for(AccountChangeDTO accountChangeDTO:rtResponse.getResponseEntity()){
                    QueryAccountChangeResultDTO accountChangeResultDTO = new QueryAccountChangeResultDTO();
                    PropertyUtils.copyProperties(accountChangeResultDTO, accountChangeDTO);
                    accountChangeDTOList.add(accountChangeResultDTO);
                }
                
            }
            response.setCode(rtResponse.getCode());
            response.setResponseEntity(accountChangeDTOList);
            
        }
        catch (HessianRuntimeException e) {
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
            log.error("AccountQueryServiceImpl queryAccountChange   call HessianRuntimeException e:", e);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("AccountQueryServiceImpl queryAccountChange   call Exception e:", e);
        }
        return response;
    }

    //查询账户余额（手机、VC端）
    public DodopalResponse<AccountBean> queryAccountBalance(String custtype, String custcode) {
        DodopalResponse<AccountBean> dodopalResponse = new DodopalResponse<AccountBean>();
        try {
            DodopalResponse<AccountBeanDTO> dtoResponse = accountQueryDelegate.queryAccountBalance(custtype, custcode);
            if (ResponseCode.SUCCESS.equals(dtoResponse.getCode())) {
                if (null != dtoResponse.getResponseEntity()) {
                    AccountBean accountBean = new AccountBean();
                    PropertyUtils.copyProperties(accountBean, dtoResponse.getResponseEntity());
                    dodopalResponse.setResponseEntity(accountBean);
                }
            }
            dodopalResponse.setCode(dtoResponse.getCode());
        }
        catch (HessianRuntimeException e) {
            dodopalResponse.setCode(ResponseCode.PRODUCT_CALL_ACCOUNT_ERROR);
            log.error("查询账户余额（手机、VC端） AccountQueryServiceImpl's queryAccountBalance call an error:", e);
        }
        catch (Exception e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("查询账户余额（手机、VC端）AccountQueryServiceImpl's queryAccountBalance call an error:", e);
        }
        return dodopalResponse;
    }

}

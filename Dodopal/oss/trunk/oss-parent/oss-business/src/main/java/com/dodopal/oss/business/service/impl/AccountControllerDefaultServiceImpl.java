package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountControllerDefaultDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.AccountControllerDefaultService;
import com.dodopal.oss.delegate.AccountRiskControllerDelegate;

@Service
public class AccountControllerDefaultServiceImpl implements AccountControllerDefaultService {

    private Logger logger = LoggerFactory.getLogger(AccountControllerDefaultServiceImpl.class);

    @Autowired
    private AccountRiskControllerDelegate accountRiskControllerDelegate;

    @Override
    public DodopalResponse<List<AccountControllerDefaultDTO>> findAccountRiskControllerDefaultItemListByPage() {
        DodopalResponse<List<AccountControllerDefaultDTO>> response;
        try {
            response = accountRiskControllerDelegate.findAccountRiskControllerDefaultItemListByPage();
        }
        catch (Exception e) {
            response = new DodopalResponse<List<AccountControllerDefaultDTO>>();
            logger.error("资金账户风控默认值查询出错", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateAccountRiskControllerDefaultItem(AccountControllerDefaultDTO acctDefault, User user) {
        DodopalResponse<String> response;
        try {
            if (acctDefault != null && user != null) {
                acctDefault.setUpdateUser(user.getId());
            }
            response = accountRiskControllerDelegate.updateAccountRiskControllerDefaultItem(acctDefault);
        }
        catch (Exception e) {
            response = new DodopalResponse<String>();
            logger.error("资金账户风控默认值更新出错", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<AccountControllerDefaultDTO> findAccountRiskControllerDefaultById(String id) {
        DodopalResponse<AccountControllerDefaultDTO> response;
        try {
            response = accountRiskControllerDelegate.findAccountRiskControllerDefaultById(id);
        }
        catch (Exception e) {
            response = new DodopalResponse<AccountControllerDefaultDTO>();
            logger.error("资金账户风控默认值查询出错", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
}

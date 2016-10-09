package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountControllerCustomerDTO;
import com.dodopal.api.account.dto.query.AccountControllerQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.AccountControllerService;
import com.dodopal.oss.delegate.AccountRiskControllerDelegate;

@Service
public class AccountControllerServiceImpl implements AccountControllerService {

    private Logger logger = LoggerFactory.getLogger(AccountControllerDefaultServiceImpl.class);

    @Autowired
    private AccountRiskControllerDelegate accountRiskControllerDelegate;

    /**
     * OSS以分页列表的方式提供资金授信账户风控主要信息的查询
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> findAccountRiskControllerItemListByPage(AccountControllerQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> response;
        try {
            response = accountRiskControllerDelegate.findAccountRiskControllerItemListByPage(queryDTO);
        }
        catch (Exception e) {
            response = new DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>>();
            logger.error("资金账户查询出错", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateAccountRiskControllerItem(AccountControllerCustomerDTO acctController, User user) {
        DodopalResponse<String> response;
        try {
            if (acctController != null && user != null) {
                acctController.setUpdateUser(user.getId());
            }
            response = accountRiskControllerDelegate.updateAccountRiskControllerItem(acctController);
        }
        catch (Exception e) {
            response = new DodopalResponse<String>();
            logger.error("资金账户风控更新出错", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

	@Override
	public List<AccountControllerCustomerDTO> getAccountRiskControllerItemList(AccountControllerQueryDTO queryDTO) {
		List<AccountControllerCustomerDTO> res;
		try {
			res = accountRiskControllerDelegate.getAccountRiskControllerItemList(queryDTO);
		}catch(Exception e) {
			res = new ArrayList<AccountControllerCustomerDTO>();
			logger.error("导出查询出错", e);
		}
		return res;
	}
	

}

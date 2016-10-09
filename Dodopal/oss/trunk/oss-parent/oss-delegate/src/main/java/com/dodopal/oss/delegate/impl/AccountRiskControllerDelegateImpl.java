package com.dodopal.oss.delegate.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountControllerCustomerDTO;
import com.dodopal.api.account.dto.AccountControllerDefaultDTO;
import com.dodopal.api.account.dto.query.AccountControllerQueryDTO;
import com.dodopal.api.account.facade.AccountRiskControllerFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.AccountRiskControllerDelegate;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("accountRiskControllerDelegate")
public class AccountRiskControllerDelegateImpl extends BaseDelegate implements AccountRiskControllerDelegate {

    @Override
    public DodopalResponse<List<AccountControllerDefaultDTO>> findAccountRiskControllerDefaultItemListByPage() {
        DodopalResponse<List<AccountControllerDefaultDTO>> response; 
        try {
            AccountRiskControllerFacade facade = getFacade(AccountRiskControllerFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
            response = facade.findAccountRiskControllerDefaultItemListByPage();
        }
        catch (Exception e) {
            response = new DodopalResponse<List<AccountControllerDefaultDTO>>();
            response.setCode(ResponseCode.ACC_SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateAccountRiskControllerDefaultItem(AccountControllerDefaultDTO acctDefault) {
        DodopalResponse<String> response;
        try {
            AccountRiskControllerFacade facade = getFacade(AccountRiskControllerFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
            response = facade.updateAccountRiskControllerDefaultItem(acctDefault);
        }
        catch (Exception e) {
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.ACC_SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<AccountControllerDefaultDTO> findAccountRiskControllerDefaultById(String id) {
        DodopalResponse<AccountControllerDefaultDTO> response;
        try {
            AccountRiskControllerFacade facade = getFacade(AccountRiskControllerFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
            response = facade.findAccountRiskControllerDefaultById(id);
        }
        catch (Exception e) {
            response = new DodopalResponse<AccountControllerDefaultDTO>();
            response.setCode(ResponseCode.ACC_SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> findAccountRiskControllerItemListByPage(AccountControllerQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> response;
        try {
            AccountRiskControllerFacade facade = getFacade(AccountRiskControllerFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
            response = facade.findAccountRiskControllerItemListByPage(queryDTO);
        }
        catch (Exception e) {
            response = new DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>>();
            response.setCode(ResponseCode.ACC_SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateAccountRiskControllerItem(AccountControllerCustomerDTO acctController) {
        DodopalResponse<String> response;
        try {
            AccountRiskControllerFacade facade = getFacade(AccountRiskControllerFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
            response = facade.updateAccountRiskControllerItem(acctController);
        }
        catch (Exception e) {
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.ACC_SYSTEM_ERROR);
        }
        return response;
    }

	public List<AccountControllerCustomerDTO> getAccountRiskControllerItemList(AccountControllerQueryDTO queryDTO) {
		List<AccountControllerCustomerDTO> resp;
		try {
			AccountRiskControllerFacade facade = getFacade(AccountRiskControllerFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
			resp = facade.findAccountRiskControllerItemList(queryDTO);
		}catch(Exception e) {
			resp = new ArrayList<AccountControllerCustomerDTO>();
		}
		return resp;
	}

	
}

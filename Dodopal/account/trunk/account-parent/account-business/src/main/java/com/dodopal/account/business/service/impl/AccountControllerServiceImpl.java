package com.dodopal.account.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.account.business.dao.AccountControlMapper;
import com.dodopal.account.business.dao.AccountFundMapper;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.service.AccountControllerService;
import com.dodopal.api.account.dto.AccountControllerCustomerDTO;
import com.dodopal.api.account.dto.query.AccountControllerQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;

@Service
public class AccountControllerServiceImpl implements AccountControllerService {
    @Autowired
    private AccountControlMapper accountControlMapper;

    @Autowired
    private AccountFundMapper accountFundMapper;
    /**
     * OSS以分页列表的方式提供资金授信账户风控主要信息的查询
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> findAccountRiskControllerItemListByPage(AccountControllerQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> response = new DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>>();

        if (queryDTO == null || queryDTO.getPage() == null) {
            response.setCode(ResponseCode.ACC_RiSK_SEARCH_PARAM_ERROR);
        } else {
            List<AccountControllerCustomerDTO> list = accountControlMapper.findAccountRiskControllerItemListByPage(queryDTO);
            DodopalDataPage<AccountControllerCustomerDTO> pages = new DodopalDataPage<AccountControllerCustomerDTO>(queryDTO.getPage(), list);
            response.setResponseEntity(pages);
            response.setCode(ResponseCode.SUCCESS);
        }
        return response;
    }

    @Override
    @Transactional
    public DodopalResponse<String> updateAccountRiskControllerItem(AccountControllerCustomerDTO acctController) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        if (acctController == null || DDPStringUtil.isNotPopulated(acctController.getAccountControllerId())) {
            response.setCode(ResponseCode.ACC_RiSK_SEARCH_PARAM_ERROR);
        } else {
            accountControlMapper.updateAccountRiskControllerItem(acctController);
            AccountFund accountFund = new AccountFund();
            accountFund.setFundAccountCode(acctController.getFundAccountCode());
            accountFund.setCreditAmt(acctController.getCreditAmt());
            accountFundMapper.updateFundAccountCreditAmt(accountFund);
            response.setCode(ResponseCode.SUCCESS);
        }
        return response;
    }

	public List<AccountControllerCustomerDTO> findAccountRiskControllerItemList(AccountControllerQueryDTO queryDTO) {
		List<AccountControllerCustomerDTO> resp;
		if(queryDTO == null || queryDTO.getPage() == null) {
			resp = new ArrayList<AccountControllerCustomerDTO>();
		}else {
			resp = accountControlMapper.findAccountRiskControllerItemListforExcel(queryDTO);
		}
		return resp;
	}


}

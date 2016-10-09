package com.dodopal.account.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.account.business.model.AccountControllerDefault;
import com.dodopal.account.business.service.AccountControllerDefaultService;
import com.dodopal.account.business.service.AccountControllerService;
import com.dodopal.api.account.dto.AccountControllerCustomerDTO;
import com.dodopal.api.account.dto.AccountControllerDefaultDTO;
import com.dodopal.api.account.dto.query.AccountControllerQueryDTO;
import com.dodopal.api.account.facade.AccountRiskControllerFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;

@Service("accountRiskControllerFacade")
public class AccountRiskControllerFacadeImpl implements AccountRiskControllerFacade {

    private static Logger logger = Logger.getLogger(AccountRiskControllerFacadeImpl.class);

    @Autowired
    private AccountControllerDefaultService accountControllerDefaultService;

    @Autowired
    private AccountControllerService accountControllerService;

    @Override
    public DodopalResponse<List<AccountControllerDefaultDTO>> findAccountRiskControllerDefaultItemListByPage() {
        DodopalResponse<List<AccountControllerDefaultDTO>> response;
        try {
            response = accountControllerDefaultService.findAccountRiskControllerDefaultItemListByPage();
        }
        catch (Exception e) {
            response = new DodopalResponse<List<AccountControllerDefaultDTO>>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error("资金账户风控默认值查询时发生错误", e);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateAccountRiskControllerDefaultItem(AccountControllerDefaultDTO acctDefault) {
        DodopalResponse<String> response;
        if (acctDefault == null || DDPStringUtil.isNotPopulated(acctDefault.getId())) {
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.ACC_RiSK_DEFAULT_PARAM_ERROR);
        } else {
            AccountControllerDefault bean = new AccountControllerDefault();
            BeanUtils.copyProperties(acctDefault, bean);
            try {
                response = accountControllerDefaultService.updateAccountRiskControllerDefaultItem(bean);
            }
            catch (Exception e) {
                response = new DodopalResponse<String>();
                response.setCode(ResponseCode.UNKNOWN_ERROR);
                logger.error("资金账户风控默认值更新时发生错误", e);
            }
        }
        return response;
    }

    @Override
    public DodopalResponse<AccountControllerDefaultDTO> findAccountRiskControllerDefaultById(String id) {
        DodopalResponse<AccountControllerDefaultDTO> response;
        try {
            response = accountControllerDefaultService.findAccountRiskControllerDefaultById(id);
        }
        catch (Exception e) {
            response = new DodopalResponse<AccountControllerDefaultDTO>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error("资金账户风控默认值查询时发生错误", e);
        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> findAccountRiskControllerItemListByPage(AccountControllerQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> response;
        try {
            response = accountControllerService.findAccountRiskControllerItemListByPage(queryDTO);
        }
        catch (Exception e) {
            response = new DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error("资金账户风控查询时发生错误", e);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> updateAccountRiskControllerItem(AccountControllerCustomerDTO acctController) {
        DodopalResponse<String> response;
        try {
            response = accountControllerService.updateAccountRiskControllerItem(acctController);
        }
        catch (Exception e) {
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error("更新资金账户风控时发生错误", e);
        }
        return response;
    }
    
	public List<AccountControllerCustomerDTO> findAccountRiskControllerItemList(AccountControllerQueryDTO queryDTO) {
		List<AccountControllerCustomerDTO> resp;
		try {
			resp = accountControllerService.findAccountRiskControllerItemList(queryDTO);
		}catch(Exception e) {
			resp = new ArrayList<AccountControllerCustomerDTO>();
			logger.error("查询资金账户风控时发生错误..", e);
		}
		return resp;
	}


}

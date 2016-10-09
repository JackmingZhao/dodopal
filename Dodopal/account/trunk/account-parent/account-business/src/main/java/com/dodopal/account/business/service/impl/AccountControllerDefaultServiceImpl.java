package com.dodopal.account.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.account.business.dao.AccountRiskControllerDefaultMapper;
import com.dodopal.account.business.model.AccountControllerDefault;
import com.dodopal.account.business.service.AccountControllerDefaultService;
import com.dodopal.api.account.dto.AccountControllerDefaultDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;

@Service
public class AccountControllerDefaultServiceImpl implements AccountControllerDefaultService {

    @Autowired
    private AccountRiskControllerDefaultMapper accountControllerDefaultMapper;
    
    @Override
    public DodopalResponse<List<AccountControllerDefaultDTO>> findAccountRiskControllerDefaultItemListByPage() {
        DodopalResponse<List<AccountControllerDefaultDTO>> response = new DodopalResponse<List<AccountControllerDefaultDTO>>();
        List<AccountControllerDefault> list = accountControllerDefaultMapper.findAccountRiskControllerDefaultItemList();
        List<AccountControllerDefaultDTO> dtoList = new ArrayList<AccountControllerDefaultDTO>();
        if (list != null) {
            for (AccountControllerDefault acct : list) {
                AccountControllerDefaultDTO dto = new AccountControllerDefaultDTO();
                BeanUtils.copyProperties(acct, dto);
                dtoList.add(dto);
            }
        }
        response.setResponseEntity(dtoList);
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

    @Override
    public DodopalResponse<String> updateAccountRiskControllerDefaultItem(AccountControllerDefault acctDefault) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        if (acctDefault == null || DDPStringUtil.isNotPopulated(acctDefault.getId())) {
            response.setCode(ResponseCode.ACC_RiSK_DEFAULT_PARAM_ERROR);
        } else {
            accountControllerDefaultMapper.updateAccountRiskControllerDefaultItem(acctDefault);
            response.setCode(ResponseCode.SUCCESS);
        }
        return response;
    }

    @Override
    public DodopalResponse<AccountControllerDefaultDTO> findAccountRiskControllerDefaultById(String id) {
        DodopalResponse<AccountControllerDefaultDTO> response = new DodopalResponse<AccountControllerDefaultDTO>();
        if(DDPStringUtil.isNotPopulated(id)) {
            response.setCode(ResponseCode.ACC_RiSK_DEFAULT_SEARCH_PARAM_ERROR);
        } else {
            AccountControllerDefault acctDefault = accountControllerDefaultMapper.findAccountRiskControllerDefaultById(id);
            AccountControllerDefaultDTO dto = new AccountControllerDefaultDTO();
            if (acctDefault != null) {
                BeanUtils.copyProperties(acctDefault, dto);
            }
            response.setResponseEntity(dto);
            response.setCode(ResponseCode.SUCCESS);
        }
        return response;
    }

}

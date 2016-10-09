package com.dodopal.account.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.dao.AccountFundMapper;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.service.AccountFundService;
import com.dodopal.account.business.service.AccountQueryService;
import com.dodopal.api.account.dto.AccountTransferDTO;
import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.common.model.DodopalResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountFundServiceTest {
    @Autowired
    private AccountFundMapper accountFundMapper;
    @Autowired
    private AccountManagementFacade accountFundService;

    @Test
    public void updateAccoundFunder(){
        AccountFund account = new AccountFund();
        account.setFundAccountCode("F0A2015090611455310071");
        account.setAvailableBalance(0);
        int i = accountFundMapper.updateFundAccount(account);
        System.out.println(i);
    }
    
//    @Test
//    public void accountTransfer() {
//        List<AccountTransferDTO> accountTransferDTOList = new ArrayList<AccountTransferDTO>();
//        AccountTransferDTO accountTransferDTO = new AccountTransferDTO();
//        accountTransferDTO.setAmount(6L);
//        accountTransferDTO.setComments("yuanyueceshi2");
//        accountTransferDTO.setSourceCustNum("060271000000127");
//        accountTransferDTO.setSourceCustType("1");
//        accountTransferDTO.setTargetCustNum("023061000000209");
//        accountTransferDTO.setTargetCustType("1");
//        accountTransferDTO.setTradeNum("123465");
//        accountTransferDTOList.add(accountTransferDTO);
//        DodopalResponse<Boolean> response = accountFundService.accountTransfer(accountTransferDTOList);
//        System.out.println(response.getMessage());
//    }


}

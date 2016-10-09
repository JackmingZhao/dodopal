package com.dodopal.account.facade;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.account.dto.AccountAdjustmentApproveDTO;
import com.dodopal.api.account.dto.AccountAdjustmentApproveListDTO;
import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.common.model.DodopalResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountManagementFacadeTest {
    
    @Autowired
    AccountManagementFacade accountManagementFacade;
    
    //账户调帐
    @Test
    public void accountAdjustment(){
        
        AccountAdjustmentApproveListDTO approveListDTO = new AccountAdjustmentApproveListDTO();
        List<AccountAdjustmentApproveDTO> approveDTOList = new ArrayList<AccountAdjustmentApproveDTO>();
        
        AccountAdjustmentApproveDTO dto = new AccountAdjustmentApproveDTO();
        dto.setAccTranType("7");
        dto.setAmount(30000);
        dto.setCustNum("001751000000012");
        dto.setCustType("1");
        dto.setFundType("0");
        dto.setTradeNum("Q2015091316221410009");
        approveDTOList.add(dto);
        
        AccountAdjustmentApproveDTO dto2 = new AccountAdjustmentApproveDTO();
        dto2.setAccTranType("7");
        dto2.setAmount(30000);
        dto2.setCustNum("001751000000012");
        dto2.setCustType("1");
        dto2.setFundType("0");
        dto2.setTradeNum("Q2015091316221410010");
        approveDTOList.add(dto2);

        AccountAdjustmentApproveDTO dto3 = new AccountAdjustmentApproveDTO();
        dto3.setAccTranType("8");
        dto3.setAmount(256000);
        dto3.setCustNum("001751000000012");
        dto3.setCustType("1");
        dto3.setFundType("0");
        dto3.setTradeNum("Q2015091316221410011");
        approveDTOList.add(dto3);
        
        AccountAdjustmentApproveDTO dto4 = new AccountAdjustmentApproveDTO();
        dto4.setAccTranType("7");
        dto4.setAmount(30000);
        dto4.setCustNum("001751000000013");
        dto4.setCustType("1");
        dto4.setFundType("0");
        dto4.setTradeNum("Q2015091316221410012");
        approveDTOList.add(dto4);
        approveListDTO.setApproveDTOs(approveDTOList);
        approveListDTO.setOperateId("99999999999");
        DodopalResponse<Boolean> response = null;
        try {
            response =accountManagementFacade.accountAdjustment(approveListDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
        if(response != null){
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");
        }
        
    }
}

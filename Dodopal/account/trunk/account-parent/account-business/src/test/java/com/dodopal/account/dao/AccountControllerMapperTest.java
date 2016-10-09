package com.dodopal.account.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.dao.AccountControlMapper;
import com.dodopal.api.account.dto.AccountControllerCustomerDTO;
import com.dodopal.api.account.dto.query.AccountControllerQueryDTO;
import com.dodopal.common.interceptor.PageParameter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class AccountControllerMapperTest {
    @Autowired
    private AccountControlMapper mapper;

    @Test
    public void testFindAccountRiskControllerItemListByPage() {
        try {
            AccountControllerQueryDTO queryDTO = new AccountControllerQueryDTO();
            PageParameter page = new PageParameter();
            page.setPageNo(1);
            page.setPageSize(20);
            queryDTO.setCustNum("004771000000578");
            queryDTO.setPage(page);
            List<AccountControllerCustomerDTO> resultDto = mapper.findAccountRiskControllerItemListByPage(queryDTO);

            if (resultDto != null) {
                System.out.println("##########################################");
                for (AccountControllerCustomerDTO dto : resultDto) {
                    System.out.println(ReflectionToStringBuilder.toString(dto, ToStringStyle.MULTI_LINE_STYLE));
                }
                System.out.println("##########################################");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

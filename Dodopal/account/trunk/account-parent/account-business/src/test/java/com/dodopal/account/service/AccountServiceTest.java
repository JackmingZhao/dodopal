package com.dodopal.account.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.account.business.dao.AccountFundMapper;
import com.dodopal.api.account.facade.AccountManagementFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:account-business-test-context.xml"})
public class AccountServiceTest {
    @Autowired
    private AccountFundMapper accountFundMapper;
    @Autowired
    private AccountManagementFacade accountFundService;
    
    
}

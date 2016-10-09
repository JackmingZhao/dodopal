/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.facade;

import com.dodopal.account.business.constant.AccountConstants;
import com.dodopal.account.business.model.Account;
import com.dodopal.account.business.model.AccountControl;
import com.dodopal.account.business.model.AccountControllerDefault;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.service.AccountService;
import com.dodopal.common.enums.AccStatusEnum;
import com.dodopal.common.enums.FundTypeEnum;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lenovo on 2015/8/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-business-test-context.xml"})
public class InterfaceTest {
    @Autowired
    private AccountService accountService;
   /* @Before
    public void setUp(){
        String hessianUrl ="http://localhost:8080/account-web/hessian/index.do?id=";
        facade= RemotingCallUtil.getHessianService(hessianUrl, AccountManagementFacade.class);
    }*/
    @After
    public void tearDown(){

    }
    //创建账户
   //@Test
    public void createAccount(){
        String aType="1";
        String custNum="1231231231";
        String accType="1";
        String merType="13";
        String seqId = accountService.getSequenceNextId();
        String preId= AccountConstants.ACC_A+new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())+seqId;
        Account account = new Account();
        account.setAccountCode(preId);
        account.setFundType(accType);
        account.setCustomerNo(custNum);
        account.setCustomerType(aType);
        AccountFund fundAccount = new AccountFund();
        String fundAccountId=AccountConstants.ACC_F + FundTypeEnum.FUND.getCode() + preId;
        fundAccount.setFundAccountCode(fundAccountId);
        fundAccount.setAccountCode(preId);
        fundAccount.setFundType(FundTypeEnum.FUND.getCode());
        fundAccount.setSumTotalAmount(0);
        fundAccount.setTotalBalance(0);
        fundAccount.setAvailableBalance(0);
        fundAccount.setFrozenAmount(0);
        fundAccount.setLastChangeAmount(0);
        fundAccount.setBeforeChangeTotalAmount(0);
        fundAccount.setBeforeChangeAvailableAmount(0);
        fundAccount.setBeforeChangeFrozenAmount(0);
        fundAccount.setState(AccStatusEnum.ENABLE.getCode());
        AccountFund fundAccount1=null;
        AccountControl accountControl1=null;
        if(FundTypeEnum.AUTHORIZED.getCode().equals(accType)){
            fundAccount1 = new AccountFund();
            //授信账户号 = "F" + 资金类别 + 主账户号
            fundAccount1.setFundAccountCode(AccountConstants.ACC_F + FundTypeEnum.AUTHORIZED.getCode() + preId);
            fundAccount1.setAccountCode(preId);
            fundAccount1.setFundType(accType);
            fundAccount1.setSumTotalAmount(0);
            fundAccount1.setTotalBalance(0);
            fundAccount1.setAvailableBalance(0);
            fundAccount1.setFrozenAmount(0);
            fundAccount1.setLastChangeAmount(0);
            fundAccount1.setBeforeChangeTotalAmount(0);
            fundAccount1.setBeforeChangeAvailableAmount(0);
            fundAccount1.setBeforeChangeFrozenAmount(0);
        }
        //根据merType查询到风控默认所需要设置的数据
        AccountControllerDefault accountControllerDefault=accountService.queryControlDefault(merType);
        AccountControl accountControl = new AccountControl();
        accountControl.setFundAccountCode(fundAccount.getFundAccountCode());
        accountControl.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
        accountControl.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
        accountControl.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
        accountControl.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
        accountControl.setDayTransferMax(accountControllerDefault.getDayTransferMax());
        accountControl.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
        accountControl.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
        //3.2 授信风控 TODO
        if(FundTypeEnum.AUTHORIZED.getCode().equals(accType)){
            accountControl1 = new AccountControl();
            accountControl1.setFundAccountCode(fundAccount1.getFundAccountCode());
            accountControl.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
            accountControl.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
            accountControl.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
            accountControl.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
            accountControl.setDayTransferMax(accountControllerDefault.getDayTransferMax());
            accountControl.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
            accountControl.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
        }
        try {
            accountService.createAccount(account, fundAccount, accountControl,accountControl1,fundAccount1,accType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //充值
   //@Test
    public void recharge() {
        String custType="0", custNum="31480100000000645", tradeNum="20150916011000002";
       long amount=1;String operateId = "000000001";
       try {
           accountService.accountRecharge(custType,custNum,tradeNum,amount,operateId);
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
    //冻结
   //@Test
    public void freeze() {
        String custType="1", custNum="013541000000190", tradeNum="20150828011000002";
        long amount=1;String operateId = "000000001";
        try {
            accountService.accountFreeze(custType, custNum, tradeNum, amount,operateId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //解冻
   //@Test
    public void unfreeze() {
        String custType="1", custNum="013541000000190", tradeNum="20150828011000002";
        long amount=1;String operateId = "000000001";
        try {
            accountService.accountUnfreeze(custType, custNum, tradeNum, amount,operateId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //扣款
    @Test
    public void deduct() {
        String custType="1", custNum="013541000000190", tradeNum="20150828011000002";
        long amount=1;String operateId = "000000001";
        try {
            accountService.accountDeduct(custType, custNum, tradeNum, amount,operateId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

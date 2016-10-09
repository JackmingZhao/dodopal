package com.dodopal.account.business.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.account.business.dao.AccountChangeMapper;
import com.dodopal.account.business.dao.AccountControlMapper;
import com.dodopal.account.business.dao.AccountFundMapper;
import com.dodopal.account.business.dao.AccountMapper;
import com.dodopal.account.business.model.Account;
import com.dodopal.account.business.model.AccountChange;
import com.dodopal.account.business.model.AccountControl;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.model.AccountMainInfo;
import com.dodopal.account.business.model.query.AccountChangeQuery;
import com.dodopal.account.business.model.query.AccountInfoListQuery;
import com.dodopal.account.business.model.query.FundChangeQuery;
import com.dodopal.account.business.service.AccountQueryService;
import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.model.DodopalDataPage;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {

    @Autowired
    private AccountFundMapper accountFundMapper;
    @Autowired
    private AccountChangeMapper accountChangeMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountControlMapper accountControlMapper;
    
    //查询门户首页资金授信账户信息
    @Transactional(readOnly = true)
    public List<AccountFund> findAccountBalance(String aType, String custNum) throws SQLException{
        return accountFundMapper.findAccountBalance(aType, custNum);
    }

 
    //查询资金变更记录
    @Transactional(readOnly = true)
    public DodopalDataPage<AccountChange> findFundsChangeRecordsByPage(FundChangeQuery query) {
        List<AccountChange> result = accountChangeMapper.findFundsChangeRecordsByPage(query);
        DodopalDataPage<AccountChange> pages = new DodopalDataPage<AccountChange>(query.getPage(), result);
        return pages;
    }

    /**
     * 根据主键id查询主账户信息
     */
    @Override
    @Transactional(readOnly = true)
    public Account findAccountByAcid(String acid) {
        Account account = accountMapper.findAccountByAcid(acid);
        return account;
    }

    /**
     * 根据ACCOUNT_CODE查询资金授信账户信息
     * @param actCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountFund> findAccountFundByActCode(String actCode) {
        List<AccountFund> accountFunds = accountFundMapper.findAccountFundByActCode(actCode);
        return accountFunds;
    }
    
    /**
     * 根据FUND_ACCOUNT_CODE查询资金账户风控信息
     * @param fundActCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountControl> findAccountControlByActCode(List<String> fundActCodes){
        List<AccountControl> accountControls = accountControlMapper.findfindAccountControlByActCode(fundActCodes);
        return accountControls;
    }

    /**
     * 资金授信查询 add by shenxiang
     * 在OSS调账时，用户在确定客户类型和客户时，需要确定该客户的资金类别，选择需要调账资金或授信。
     * 
     * @param custType
     * @param custNum
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public FundAccountInfoDTO findFundAccountInfo(String custType, String custNum) {
        List<Map<String, String>> mapList = accountFundMapper.findAccountFundInfo(custType, custNum);
        FundAccountInfoDTO resultDto = new FundAccountInfoDTO();
        for (Map<String, String> map:mapList) {
            resultDto.setAccountCode(map.get("ACCOUTCODE"));
            resultDto.setFundType(map.get("ACCOUTTYPE"));
            if (FundTypeEnum.FUND.getCode().equals(map.get("FUNDTYPE"))) {
                resultDto.setFundAccountCode(map.get("FUNDACCOUTCODE"));
            } else if (FundTypeEnum.AUTHORIZED.getCode().equals(map.get("FUNDTYPE"))) {
                resultDto.setAuthorizedAccountCode(map.get("FUNDACCOUTCODE"));
                resultDto.setCreditAmt(map.get("CREDITAMT")==null?"0":map.get("CREDITAMT"));
                resultDto.setAvailableBalance(map.get("AVAILABLEBALANCE")==null?"0":map.get("AVAILABLEBALANCE"));
            }
        }
        return resultDto;
    }

    /**
     * 资金授信账户信息列表查询
     * @param accountInfoListQuery
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<AccountMainInfo> findAccountInfoListByPage(AccountInfoListQuery accountInfoListQuery) {
        List<AccountMainInfo> accountMainInfos = new ArrayList<AccountMainInfo>();
        //客户类型为商户
        if ("1".equals(accountInfoListQuery.getaType())) {
            List<AccountMainInfo> merList = accountMapper.findAccountInfoListMerByPage(accountInfoListQuery);
            accountMainInfos.addAll(merList);
        }else if("0".equals(accountInfoListQuery.getaType())){//个人
            List<AccountMainInfo> userList = accountMapper.findAccountInfoListUserByPage(accountInfoListQuery);
            accountMainInfos.addAll(userList);
        }
        DodopalDataPage<AccountMainInfo> pages = new DodopalDataPage<AccountMainInfo>(accountInfoListQuery.getPage(), accountMainInfos);
        return pages;
    }


	//查询资金变更记录
	public List<AccountChange> findFundsChangeRecordsAll(FundChangeQuery query) {
		List<AccountChange> result = accountChangeMapper.findFundsChangeRecordsAll(query);
        return result;
	}


    //查询资金变更记录（手机 、VC端接入）
    public List<AccountChange> queryAccountChangeByPhone(AccountChangeQuery queryRequest) {
        List<AccountChange> result = accountChangeMapper.queryAccountChangeByPhone(queryRequest);
        return result;
    }

    //导出账户信息
    @Override
    public List<AccountMainInfo> expAccountInfo(AccountInfoListQuery accountInfoListQuery) {
        List<AccountMainInfo> accountMainInfos = new ArrayList<AccountMainInfo>();
        //客户类型为商户
        if ("1".equals(accountInfoListQuery.getaType())) {
            List<AccountMainInfo> merList = accountMapper.expAccountInfoListMer(accountInfoListQuery);
            accountMainInfos.addAll(merList);
        }else if("0".equals(accountInfoListQuery.getaType())){//个人
            List<AccountMainInfo> userList = accountMapper.expAccountInfoListUser(accountInfoListQuery);
            accountMainInfos.addAll(userList);
        }
        return accountMainInfos;
    }
}

/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.account.business.model.AccountControl;
import com.dodopal.account.business.model.AccountControllerDefault;
import com.dodopal.api.account.dto.AccountControllerCustomerDTO;
import com.dodopal.api.account.dto.query.AccountControllerQueryDTO;

/**
 * Created by lenovo on 2015/8/21.
 */
public interface AccountControlMapper {
    /**
     * @description 新增风控信息表记录
     * @param account
     */
    public void addAccountControl(AccountControl account);

    /**
     * @param fundAccountCode
     * @return
     */
    public AccountControl queryAccountControl(@Param("fundAccountCode") String fundAccountCode);
    
    /**
     * 根据FUND_ACCOUNT_CODE查询资金账户风控信息
     * @param fundActCodes
     * @return
     */
    public List<AccountControl> findfindAccountControlByActCode(List<String> fundActCodes);

    public AccountControllerDefault queryControlDefault(@Param("merType")String merType);
    
    /**
     * 查询资金授信账户风控主要信息的查询
     * @param queryDTO
     * @return
     */
    public List<AccountControllerCustomerDTO> findAccountRiskControllerItemListByPage(AccountControllerQueryDTO queryDTO);
    
    /**
     * 授权用户选择一条记录，点击“修改”按钮：   
     * 在修改详细信息页面上只能修改风控项的值。保存的时候，需要做数据正确性校验。
     * @param acctController
     * @return
     */
    public void updateAccountRiskControllerItem(AccountControllerCustomerDTO acctController);
    
    /**
     * 查询资金授信账户风控主要信息的查询 列更多 Excel导出
     * @param queryDTO
     * @return
     */
    public List<AccountControllerCustomerDTO> findAccountRiskControllerItemListforExcel(AccountControllerQueryDTO queryDTO);

}
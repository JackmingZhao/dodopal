/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.account.business.model.Account;
import com.dodopal.account.business.model.AccountMainInfo;
import com.dodopal.account.business.model.query.AccountInfoListQuery;

/**
 * Created by lenovo on 2015/8/21.
 */
public interface AccountMapper {
    /**
     * @description 新增主账户信息表记录
     * @param account
     */
    public void addAccount(Account account);

    /**
     * 获取sequence序列的下一个值
     */
    public String getSequenceNextId();

    /**
     * @description 根据客户号和客户类型得到祝账户编号
     * @param custType 客户类型
     * @param custNum 客户号
     * @return
     */
    public Account queryAccoutCode(@Param("custType")String custType,@Param("custNum")String custNum);
    
    /**
     * 根据主键id查询主账户信息
     * @param acid
     * @return
     */
    public Account findAccountByAcid(String acid);
    
    /**
     * 资金授信账户信息列表查询(商户)
     * @param accountInfoListQuery
     * @return
     */
    public List<AccountMainInfo> findAccountInfoListMerByPage(AccountInfoListQuery accountInfoListQuery);
    
    /**
     * 资金授信账户信息列表查询(个人)
     * @param accountInfoListQuery
     * @return
     */
    public List<AccountMainInfo> findAccountInfoListUserByPage(AccountInfoListQuery accountInfoListQuery);
    
    /**
     * 商户账户导出
     * @param accountInfoListQuery
     * @return
     */
    public List<AccountMainInfo> expAccountInfoListMer(AccountInfoListQuery accountInfoListQuery);
    
    /**
     * 个人账户导出
     * @param accountInfoListQuery
     * @return
     */
    public List<AccountMainInfo> expAccountInfoListUser(AccountInfoListQuery accountInfoListQuery);
}

/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.dao;

import com.dodopal.account.business.model.AccountChangeSum;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by lenovo on 2015/8/21.
 */
public interface AccountChangeSumMapper {
    /**
     * @description 根据资金账户编号查询资金累计变动表信息
     * @param fundAccountCode
     * @return
     */
    public AccountChangeSum queryAccountChangeSum(@Param("fundAccountCode")String fundAccountCode,@Param("today")String today);
    
    /**
     * @description 根据资金账户编号查询资金累计变动表信息
     * @param fundAccountCode
     * @return
     */
    public AccountChangeSum queryAccountChangeSumByDateAndCodeForUpdate(@Param("fundAccountCode")String fundAccountCode,@Param("today")String today);

    /**
     * @description 更改资金累计信息表信息
     * @param accountChangeSum
     */
    public void updateAccountChangeSum(AccountChangeSum accountChangeSum);
    
    /**
     * @description 根据授信资金账号和当前时间更改资金累计信息表信息
     * @param accountChangeSum
     */
    public int updateAccountChangeSumByDateAndCode(AccountChangeSum accountChangeSum);
    
    /**
     * @description 充值首次，如果相应的累计变动表中没有记录，则新增一条记录
     * @param accountChangeSum
     */
    public void addAccountChangeSum(AccountChangeSum accountChangeSum);
}
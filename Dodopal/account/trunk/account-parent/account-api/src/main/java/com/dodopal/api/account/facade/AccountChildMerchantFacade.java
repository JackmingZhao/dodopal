package com.dodopal.api.account.facade;

import java.util.List;

import com.dodopal.api.account.dto.ChildMerchantAccountChangeDTO;
import com.dodopal.api.account.dto.AccountChildMerchantDTO;
import com.dodopal.api.account.dto.query.AccountChildMerchantQueryDTO;
import com.dodopal.api.account.dto.query.ChildMerFundChangeQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 查询子商户的账户信息
 * @author xiongzhijing@dodopal.com
 * @version 2015年9月8日
 *
 */
public interface AccountChildMerchantFacade {
    
    /**
     * 根据上级商户 查询其子商户的总余额 及账户信息 （分页） 
     * @param query 查询条件封装的实体 
     * @return DodopalDataPage<AccountChildMerchantDTO>
     */
    public DodopalResponse<DodopalDataPage<AccountChildMerchantDTO>>  findAccountChildMerByPage(AccountChildMerchantQueryDTO query);
    
    /**
     * 根据上级商户 查询其子商户的总余额 及账户信息(导出)
     * @param query 查询条件封装的实体
     * @return List<AccountChildMerchantDTO>
     */
    public DodopalResponse<List<AccountChildMerchantDTO>> findAccountChildMerByList(AccountChildMerchantQueryDTO query);
    
    /**
     * 根据上级商户编号 查询 子商户的资金变更记录(分页)
     * @param query 查询条件封装的实体
     * @return DodopalResponse<DodopalDataPage<AccountFundDTO>>
     */
    public DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeDTO>>  findAccountChangeChildMerByPage(ChildMerFundChangeQueryDTO query);
   
    /**
     * 根据上级商户编号 查询 子商户的资金变更记录(导出)
     * @param query 查询条件封装的实体
     * @return DodopalResponse<List<AccountFundDTO>>
     */
    public DodopalResponse<List<ChildMerchantAccountChangeDTO>> findAccountChangeChildMerByList(ChildMerFundChangeQueryDTO query);
     
}

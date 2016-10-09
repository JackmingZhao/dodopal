package com.dodopal.api.account.facade;

import java.util.List;

import com.dodopal.api.account.dto.AccountBeanDTO;
import com.dodopal.api.account.dto.AccountChangeDTO;
import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.dto.AccountInfoDTO;
import com.dodopal.api.account.dto.AccountMainInfoDTO;
import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.account.dto.query.AccountChangeRequestDTO;
import com.dodopal.api.account.dto.query.AccountInfoListQueryDTO;
import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface AccountQueryFacade {

    /**
     * 查询门户首页 可用余额  资金授信账户信息
     * @param aType 个人or企业
     * @param custNum 用户号 or 商户号
     * @return
     */
   public DodopalResponse<AccountFundDTO> findAccountBalance(String aType,String custNum);
  
   /**
    * 资金变更记录查询（分页）
    * @param fundChangeQuery 查询条件封装的实体
    * @return  DodopalResponse<DodopalDataPage<FundChangeDTO>>
    */
   DodopalResponse<DodopalDataPage<FundChangeDTO>> findFundsChangeRecordsByPage(FundChangeQueryDTO fundChangeQueryDTO);
   
   /**
    * 账户详细信息查询
    * @param acid
    * @return
    */
   public DodopalResponse<AccountInfoDTO> findAccountInfo(String acid);
   
   /**
    * 资金授信查询 add by shenxiang
    * 在OSS调账时，用户在确定客户类型和客户时，需要确定该客户的资金类别，选择需要调账资金或授信。
    * 
    * @param custType(客户类型：个人、商户)
    * @param custNum(类型是商户：商户号；类型是个人：用户编号)
    * @return
    */
   public DodopalResponse<FundAccountInfoDTO> findFundAccountInfo(String custType, String custNum);
   
   /**
    * 资金授信账户信息列表查询
    * @param acid
    * @return
    */
   public DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> findAccountInfoListByPage(AccountInfoListQueryDTO accountInfoListQueryDTO);
   
   /**
    * 资金变更记录
    * @return DodopalResponse<List<FundChangeDTO>>
    */
   public DodopalResponse<List<FundChangeDTO>> findFundsChangeRecordsAll(FundChangeQueryDTO fundChangeQueryDTO);
  
   
   
   /**
    * 查询资金变更记录（手机、VC端接入）
    * @param requestDTO
    * @return DodopalResponse<List<AccountChangeDTO>>
    */
   public DodopalResponse<List<AccountChangeDTO>> queryAccountChangeByPhone(AccountChangeRequestDTO requestDTO);
   
   /**
    * 查询 可用余额  资金授信账户信息(手机，VC端接入)
    * @param custtype 个人or企业
    * @param custcode 用户号 or 商户号
    * @return
    */
   public DodopalResponse<AccountBeanDTO> queryAccountBalance(String custtype, String custcode);
   
   /**
    * 账户信息导出
    * @param accountInfoListQueryDTO
    * @return
    */
   public DodopalResponse<List<AccountMainInfoDTO>> expAccountInfo(AccountInfoListQueryDTO accountInfoListQueryDTO);
}

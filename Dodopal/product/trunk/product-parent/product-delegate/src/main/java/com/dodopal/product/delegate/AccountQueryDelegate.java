package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.account.dto.AccountBeanDTO;
import com.dodopal.api.account.dto.AccountChangeDTO;
import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.dto.query.AccountChangeRequestDTO;
import com.dodopal.common.model.DodopalResponse;

public interface AccountQueryDelegate {
	  /**
     * 查询门户首页 可用余额  资金授信账户信息
     * @param aType 个人or企业
     * @param custNum 用户号 or 商户号
     * @return
     */
   public DodopalResponse<AccountFundDTO> findAccountBalance(String aType,String custNum);
   
   /**
    * 查询资金变更记录（手机 、VC端）
    * @param requestDto
    * @return DodopalResponse<List<AccountChangeDTO>>
    */
   DodopalResponse<List<AccountChangeDTO>> queryAccountChange(AccountChangeRequestDTO requestDto);
   
   /**
    * 查询资金变更记录 （手机 、VC端）
    * @param custtype 个人or企业
    * @param custcode 用户号or商户号 
    * @return DodopalResponse<AccountBeanDTO>
    */
   public DodopalResponse<AccountBeanDTO> queryAccountBalance(String custtype, String custcode);

}

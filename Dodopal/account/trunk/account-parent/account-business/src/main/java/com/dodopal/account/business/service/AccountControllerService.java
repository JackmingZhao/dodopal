package com.dodopal.account.business.service;

import java.util.List;

import com.dodopal.api.account.dto.AccountControllerCustomerDTO;
import com.dodopal.api.account.dto.query.AccountControllerQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface AccountControllerService {

    /**
     * OSS以分页列表的方式提供资金授信账户风控主要信息的查询
     * @return
     */
    DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> findAccountRiskControllerItemListByPage(AccountControllerQueryDTO queryDTO);

    /**
     * 授权用户选择一条记录，点击“修改”按钮：   
     * 在修改详细信息页面上只能修改风控项的值。保存的时候，需要做数据正确性校验。
     * @param acctController
     * @return
     */
    DodopalResponse<String> updateAccountRiskControllerItem(AccountControllerCustomerDTO acctController);
    
    /**
     * OSS 资金授信账户风控主要信息的查询
     * @param queryDTO
     * @return
     */
    List<AccountControllerCustomerDTO> findAccountRiskControllerItemList(AccountControllerQueryDTO queryDTO);
    

}

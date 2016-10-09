package com.dodopal.account.business.service;

import java.util.List;

import com.dodopal.account.business.model.AccountControllerDefault;
import com.dodopal.api.account.dto.AccountControllerDefaultDTO;
import com.dodopal.common.model.DodopalResponse;

public interface AccountControllerDefaultService {

    /**
     * 在OSS中，当打开这个菜单页面的时候，就将所有记录全部查询出来，不需要分页，不需要任何查询条件。 查询结果依次显示：
     * 类型、日消费交易单笔限额、日消费交易累计限额、日充值交易单笔限额、日充值交易累计限额、日转账交易最大次数、日转账交易单笔限额、日转账交易累计限额。
     * 代码中保留分页接口，目的是方便日后维护。当前数据库仅10条数据，分页对其不产生任何影响
     * @return
     */
    DodopalResponse<List<AccountControllerDefaultDTO>> findAccountRiskControllerDefaultItemListByPage();
    
    DodopalResponse<AccountControllerDefaultDTO> findAccountRiskControllerDefaultById(String id);

    /**
     * 授权用户选择一条记录，点击“修改”按钮： 在修改详细信息页面上只能修改风控项的值。保存的时候，需要做数据正确性校验。
     */
    DodopalResponse<String> updateAccountRiskControllerDefaultItem(AccountControllerDefault acctDefault);

}

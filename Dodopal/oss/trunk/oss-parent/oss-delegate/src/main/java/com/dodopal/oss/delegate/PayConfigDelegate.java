package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayConfigQueryDTO;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月30日 下午4:50:31
 */
public interface PayConfigDelegate {
    public DodopalResponse<DodopalDataPage<PayConfigDTO>> findPayConfigByPage(PayConfigQueryDTO queryDTO);
    
    public DodopalResponse<PayConfigDTO> findPayConfigById(String id);
    
    public DodopalResponse<Boolean> batchActivatePayConfig(String flag, List<String> ids, String updateUser);
    
    public DodopalResponse<Boolean> updatePayConfig(PayConfigDTO payConfigDTO);
    
    public DodopalResponse<Boolean> updatePayConfigBankGateway(List<String> ids,String updateUser,String payConfigId,BankGatewayTypeEnum toBankGateWayType);
}

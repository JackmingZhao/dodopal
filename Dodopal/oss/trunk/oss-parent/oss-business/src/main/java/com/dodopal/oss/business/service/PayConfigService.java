package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.bean.query.PayConfigQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月30日 下午5:51:30
 */
public interface PayConfigService {
    public  DodopalResponse<DodopalDataPage<PayConfigBean>> findPayConfigByPage (PayConfigQuery  payConfigQuery);

    public DodopalResponse<PayConfigBean> findPayConfigById(String id);
    
    public DodopalResponse<Boolean> batchActivatePayConfig(String flag, List<String> ids, String updateUser);
    
    public DodopalResponse<Boolean> updatePayConfig(PayConfigBean payConfigBean);
    
    public DodopalResponse<Boolean> updatePayConfigBankGateway(List<String> ids,String updateUser,String payConfigId,BankGatewayTypeEnum gateType);

}

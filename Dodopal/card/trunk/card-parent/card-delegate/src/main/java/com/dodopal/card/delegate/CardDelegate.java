package com.dodopal.card.delegate;

import java.util.Map;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;
public interface CardDelegate {
    DodopalResponse<Map<String, String>> sayHello(String name);
    DodopalResponse<CrdSysOrderDTO> checkCard(CrdSysOrderDTO crdDTO);
    DodopalResponse<CrdSysOrderDTO> createCard(CrdSysOrderDTO crdDTO);
    DodopalResponse<CrdSysOrderDTO> getLoadOrderFun(CrdSysOrderDTO crdDTO);
    DodopalResponse<CrdSysOrderDTO> upload(CrdSysOrderDTO crdDTO);
    DodopalResponse<CrdSysOrderDTO> uploadBef(CrdSysOrderDTO crdDTO);
    
    DodopalResponse<CrdSysOrderDTO> checkCardCosum(CrdSysOrderDTO crdDTO);
    DodopalResponse<CrdSysOrderDTO> getLoadOrderCosum(CrdSysOrderDTO crdDTO);
    DodopalResponse<CrdSysOrderDTO> uploadCosum(CrdSysOrderDTO crdDTO);
    DodopalResponse<CrdSysOrderDTO> failCosum(CrdSysOrderDTO crdDTO);
//    DodopalResponse<AccountInfoDTO> findAccountInfo(String acid);
//    DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> findAccountInfoList(AccountInfoListQueryDTO accountInfoListQueryDTO);
}

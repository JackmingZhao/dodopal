package com.dodopal.card.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.CardFacade;
import com.dodopal.api.card.facade.IcdcConsumeCardFacade;
import com.dodopal.api.card.facade.IcdcRechargeCardFacade;
import com.dodopal.api.card.facade.QueryCheckCardFacade;
import com.dodopal.card.delegate.CardDelegate;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

@Service("cardDelegate")
public class CardDelegateImpl implements CardDelegate {

    @Override
    public DodopalResponse<Map<String, String>> sayHello(String name) {
        CardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", CardFacade.class);
        return cardService.sayHello(name);
    }

    @Override
    public DodopalResponse<CrdSysOrderDTO> checkCard(CrdSysOrderDTO crdDTO) {
        QueryCheckCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", QueryCheckCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.queryCheckCardFun(crdDTO);
        return res;
    }

    @Override
    public DodopalResponse<CrdSysOrderDTO> createCard(CrdSysOrderDTO crdDTO) {
        QueryCheckCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", QueryCheckCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.createCrdOrderCardFun(crdDTO);
        return res;
    }

    @Override
    public DodopalResponse<CrdSysOrderDTO> getLoadOrderFun(CrdSysOrderDTO crdDTO) {
        IcdcRechargeCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", IcdcRechargeCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.getLoadOrderFun(crdDTO);
        return res;
    }

    @Override
    public DodopalResponse<CrdSysOrderDTO> checkCardCosum(CrdSysOrderDTO crdDTO) {
        IcdcConsumeCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", IcdcConsumeCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.queryCheckCardConsFun(crdDTO);
        return res;
    }
    
    @Override
    public DodopalResponse<CrdSysOrderDTO> uploadBef(CrdSysOrderDTO crdDTO) {
        IcdcRechargeCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", IcdcRechargeCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.uploadResultChkUdpOrderFun(crdDTO);
        return res;
    }

    @Override
    public DodopalResponse<CrdSysOrderDTO> upload(CrdSysOrderDTO crdDTO) {
        IcdcRechargeCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", IcdcRechargeCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.uploadResultFun(crdDTO);
        return res;
    }

    @Override
    public DodopalResponse<CrdSysOrderDTO> getLoadOrderCosum(CrdSysOrderDTO crdDTO) {
        IcdcConsumeCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", IcdcConsumeCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.getDeductOrderConsFun(crdDTO);
        return res;
    }
    
    @Override
    public DodopalResponse<CrdSysOrderDTO> uploadCosum(CrdSysOrderDTO crdDTO) {
        IcdcConsumeCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", IcdcConsumeCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.uploadResultConsFun(crdDTO);
        return res;
    }

    @Override
    public DodopalResponse<CrdSysOrderDTO> failCosum(CrdSysOrderDTO crdDTO) {
        IcdcConsumeCardFacade cardService = RemotingCallUtil.getHessianService("http://localhost:8086/card-web/hessian/index.do?id=", IcdcConsumeCardFacade.class);
        DodopalResponse<CrdSysOrderDTO> res = cardService.frontFailReportConsFun(crdDTO);
        return res;
    }

    /*    @Override
        public DodopalResponse<AccountInfoDTO> findAccountInfo(String acid) {
            AccountQueryFacade accountService = RemotingCallUtil.getHessianService("http://localhost:8087/account-web/hessian/index.do?id=", AccountQueryFacade.class);
            DodopalResponse<AccountInfoDTO> res = accountService.findAccountInfo(acid);
            return res;
        }

        @Override
        public DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> findAccountInfoList(AccountInfoListQueryDTO accountInfoListQueryDTO) {
            AccountQueryFacade accountService = RemotingCallUtil.getHessianService("http://localhost:8087/account-web/hessian/index.do?id=", AccountQueryFacade.class);
            DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> res = accountService.findAccountInfoListByPage(accountInfoListQueryDTO);
            return res;
        }*/

}

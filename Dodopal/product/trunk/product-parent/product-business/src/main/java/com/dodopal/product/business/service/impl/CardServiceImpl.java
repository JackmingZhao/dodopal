package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.product.business.bean.CardListResultDTO;
import com.dodopal.product.business.service.CardService;
import com.dodopal.product.delegate.CardDelegate;

@Service
public class CardServiceImpl implements CardService {
    private final static Logger log = LoggerFactory.getLogger(CardServiceImpl.class);
    @Autowired
    CardDelegate CardDelegate;

    //用户绑卡
    public DodopalResponse<MerUserCardBDDTO> saveMerUserCardBD(MerUserCardBDDTO cardBDDTO) {
        DodopalResponse<MerUserCardBDDTO> rtPesponse = new DodopalResponse<MerUserCardBDDTO>();
        try {
            rtPesponse = CardDelegate.saveMerUserCardBD(cardBDDTO);

        }
        catch (HessianRuntimeException e) {
            rtPesponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
            log.error("用户绑卡  CardServiceImpl saveMerUserCardBD call an  HessianRuntimeException error:", e);
        }
        catch (Exception e) {
            rtPesponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("用户绑卡 CardServiceImpl saveMerUserCardBD call an error:", e);
        }
        return rtPesponse;
    }

    //用户解绑卡片
    public DodopalResponse<String> unBundlingCard(String userId, String operName, List<String> ids, String source) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            response = CardDelegate.unBundlingCard(userId, operName, ids, source);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
            log.error("用户解绑卡片  CardServiceImpl unBundlingCard call an  HessianRuntimeException error:", e);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("用户解绑卡片  CardServiceImpl unBundlingCard call an error:", e);
        }
        return response;
    }

    //查询用户已绑定卡片 的列表
    public DodopalResponse<List<CardListResultDTO>> findMerUserCardBD(MerUserCardBDFindDTO cardBDFindDTO) {
        DodopalResponse<List<CardListResultDTO>> dodopalResponse = new DodopalResponse<List<CardListResultDTO>>();
        try {
            List<CardListResultDTO>  cardListResultDTOList = new ArrayList<CardListResultDTO>();
            
            DodopalResponse<List<MerUserCardBDDTO>> rtResponse = CardDelegate.findMerUserCardBD(cardBDFindDTO);
            if(ResponseCode.SUCCESS.equals(rtResponse.getCode())&&rtResponse.getResponseEntity()!=null){
                List<MerUserCardBDDTO> MerUserCardBDDTOList = rtResponse.getResponseEntity();
                for(MerUserCardBDDTO merUserCardBDDTO:MerUserCardBDDTOList){
                    CardListResultDTO cardListResultDTO = new CardListResultDTO();
                    cardListResultDTO.setId(merUserCardBDDTO.getId());
                    cardListResultDTO.setTradecard(merUserCardBDDTO.getCardCode());
                    cardListResultDTO.setTradecardname(merUserCardBDDTO.getCardName());
                    cardListResultDTO.setBinddate(DateUtils.formatDate(merUserCardBDDTO.getCreateDate(),DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
                    cardListResultDTOList.add(cardListResultDTO);
                }
                
            }
            dodopalResponse.setCode(rtResponse.getCode());
            dodopalResponse.setResponseEntity(cardListResultDTOList);

        }
        catch (HessianRuntimeException e) {
            dodopalResponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
            log.error("查询用户已绑定卡片 的列表 CardServiceImpl findMerUserCardBD call an  HessianRuntimeException error:", e);
        }
        catch (Exception e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("查询用户已绑定卡片 的列表 CardServiceImpl findMerUserCardBD call an error:", e);
        }
        return dodopalResponse;
    }

}

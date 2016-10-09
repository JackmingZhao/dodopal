package com.dodopal.product.web.param;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.product.business.bean.CardListResultDTO;

public class CardListResponse extends BaseResponse{

    //用户卡片列表
    List<CardListResultDTO> cardListResultDTOList = new ArrayList<CardListResultDTO>();

    public List<CardListResultDTO> getCardListResultDTOList() {
        return cardListResultDTOList;
    }

    public void setCardListResultDTOList(List<CardListResultDTO> cardListResultDTOList) {
        this.cardListResultDTOList = cardListResultDTOList;
    }

    
}

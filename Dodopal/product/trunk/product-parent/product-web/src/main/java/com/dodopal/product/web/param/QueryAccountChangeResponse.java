package com.dodopal.product.web.param;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.product.business.bean.QueryAccountChangeResultDTO;

public class QueryAccountChangeResponse extends BaseResponse {

    //资金变更记录
    List<QueryAccountChangeResultDTO> accountChangeDTOList = new ArrayList<QueryAccountChangeResultDTO>();

    public List<QueryAccountChangeResultDTO> getAccountChangeDTOList() {
        return accountChangeDTOList;
    }

    public void setAccountChangeDTOList(List<QueryAccountChangeResultDTO> accountChangeDTOList) {
        this.accountChangeDTOList = accountChangeDTOList;
    }

}

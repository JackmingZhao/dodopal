package com.dodopal.product.web.param;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.product.web.mobileBean.QueryProductOrderResultDTO;


/**
 * 3.6  公交卡充值订单查询接口 返回参数
 * @author dodopal
 *
 */
public class QueryProductOrderResponse extends BaseResponse {
    
    private List<QueryProductOrderResultDTO> resultDTOs = new ArrayList<QueryProductOrderResultDTO>();
    
    public List<QueryProductOrderResultDTO> getResultDTOs() {
        return resultDTOs;
    }

    public void setResultDTOs(List<QueryProductOrderResultDTO> resultDTOs) {
        this.resultDTOs = resultDTOs;
    }
}

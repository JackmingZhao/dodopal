package com.dodopal.product.web.param;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.product.business.bean.TranscationListResultBean;

/**
 * 3.20 交易记录查询 返回结果（手机端和VC端）
 * @author lenovo
 *
 */
public class QueryTranscationResponse extends BaseResponse{
    List<TranscationListResultBean> transcationListResultDTO = new ArrayList<TranscationListResultBean>();

    public List<TranscationListResultBean> getTranscationListResultDTO() {
        return transcationListResultDTO;
    }

    public void setTranscationListResultDTO(List<TranscationListResultBean> transcationListResultDTO) {
        this.transcationListResultDTO = transcationListResultDTO;
    }
    
    

}

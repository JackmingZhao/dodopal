package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.PrdRateDTO;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.PrdRateBean;
import com.dodopal.oss.business.service.PrdRateService;
import com.dodopal.oss.delegate.PrdRateDelegate;
@Service
public class PrdRateServiceImpl implements PrdRateService {

    @Autowired
    PrdRateDelegate prdRateDelegate;
    @Override
    public DodopalResponse<List<PrdRateBean>> findPrdRate(String merType) {
        DodopalResponse<List<PrdRateBean>> respone= new DodopalResponse<List<PrdRateBean>>();
        DodopalResponse<List<PrdRateDTO>> prdDto = prdRateDelegate.findPrdRate();
        List<PrdRateDTO> prdDtoList =prdDto.getResponseEntity();
        try {
            List<PrdRateBean> prdRateBeanList = new ArrayList<PrdRateBean>();
            for(PrdRateDTO prdRateDTO :prdDtoList){
            	if(MerTypeEnum.AGENT.getCode().equals(merType) && RateCodeEnum.YKT_PAYMENT.getCode().equals(prdRateDTO.getRateCode())) {
            		continue;
            	}
            	if(!(MerTypeEnum.EXTERNAL.getCode().equals(merType) || MerTypeEnum.DDP_MER.getCode().equals(merType)) && RateCodeEnum.IC_LOAD.getCode().equals(prdRateDTO.getRateCode())){
            	    continue;
            	}
            	
                PrdRateBean prdRateBean =new PrdRateBean();
                prdRateBean.setId(prdRateDTO.getId());
                prdRateBean.setRateCode(prdRateDTO.getRateCode());
                prdRateBean.setRateName(prdRateDTO.getRateName());
                prdRateBean.setCreateUser(prdRateDTO.getCreateUser());
                prdRateBean.setCreateDate(prdRateDTO.getCreateDate());
                prdRateBean.setUpdateUser(prdRateDTO.getUpdateUser());
                prdRateBean.setUpdateDate(prdRateDTO.getUpdateDate());
                prdRateBeanList.add(prdRateBean);
            }
            respone.setCode(prdDto.getCode());
            respone.setResponseEntity(prdRateBeanList);
        }
        catch (Exception e) {
           respone.setCode(prdDto.getCode());
           e.printStackTrace();
        }
        return respone;
    }

}

package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.PrdRateDTO;
import com.dodopal.api.product.facade.PrdRateFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.PrdRate;
import com.dodopal.product.business.service.PrdRateService;

@Service("prdRateFacade")
public class PrdRateFacadeImpl implements PrdRateFacade {

    @Autowired
    PrdRateService prdRateService;
    @Override
    public DodopalResponse<List<PrdRateDTO>> findPrdRate() {
        DodopalResponse<List<PrdRateDTO>> response = new DodopalResponse<List<PrdRateDTO>>();
        List<PrdRate> prdRateList =new ArrayList<PrdRate>();
        prdRateList= prdRateService.findPrdRate();
        List<PrdRateDTO> prdDtoList = new ArrayList<PrdRateDTO>();
        try {
            if(prdRateList.size()!=0){
                for(PrdRate prdRate : prdRateList){
                    PrdRateDTO prdRateDTO = new PrdRateDTO();
                    prdRateDTO.setId(prdRate.getId());
                    prdRateDTO.setRateCode(prdRate.getRateCode());
                    prdRateDTO.setRateName(prdRate.getRateName());
                    prdRateDTO.setComments(prdRate.getComments());
                    prdRateDTO.setCreateUser(prdRate.getCreateUser());
                    prdRateDTO.setCreateDate(prdRate.getCreateDate());
                    prdRateDTO.setUpdateUser(prdRate.getUpdateUser());
                    prdRateDTO.setUpdateDate(prdRate.getUpdateDate());
                    prdDtoList.add(prdRateDTO);
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(prdDtoList);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}

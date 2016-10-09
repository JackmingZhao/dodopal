package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerDiscountReferDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.api.users.facade.MerDiscountReferFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.MerDiscountRefer;
import com.dodopal.users.business.model.query.MerDiscountQuery;
import com.dodopal.users.business.service.MerDiscountReferService;

@Service("merDiscountReferFacade")
public class MerDiscountReferFacadeImpl implements MerDiscountReferFacade {

    @Autowired
    MerDiscountReferService merDiscountReferService;
    
    @Override
    public DodopalResponse<List<MerDiscountReferDTO>> findMerDiscountReferByList(String merDiscountId) {
        DodopalResponse<List<MerDiscountReferDTO>> response = new DodopalResponse<List<MerDiscountReferDTO>>();
        try {
            List<MerDiscountReferDTO> merDiscountReferDTOList = new ArrayList<MerDiscountReferDTO>();
            List<MerDiscountRefer> merDiscountReferList =  merDiscountReferService.findMerDiscountReferByList(merDiscountId);
            if(null!=merDiscountReferList&&merDiscountReferList.size()>0){
                for(MerDiscountRefer merDiscountRefer:merDiscountReferList){
                    MerDiscountReferDTO  merDiscountReferDTO = new MerDiscountReferDTO();
                    PropertyUtils.copyProperties(merDiscountReferDTO, merDiscountRefer);
                    merDiscountReferDTO.setDiscount(Double.toString(Double.valueOf(merDiscountRefer.getDiscount())/10));
                    merDiscountReferDTOList.add(merDiscountReferDTO);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(merDiscountReferDTOList);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> insertMerDiscountRefer(MerDiscountReferDTO merDiscountReferDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int num = 0;
        try {
            MerDiscountRefer  merDiscountRefer = new MerDiscountRefer();
            PropertyUtils.copyProperties(merDiscountRefer, merDiscountReferDTO);
            
            num = merDiscountReferService.insertMerDiscountRefer(merDiscountRefer);
            
            if(num>0){
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(num);
            }else{
                response.setCode(ResponseCode.PORTAL_USER_CHILD_MER_ADD_DISCOUNT_FAIL);
                response.setResponseEntity(num);
            }
           
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> deleteMerDiscountRefer(String merDiscountId) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int num = 0;
        try {
            num = merDiscountReferService.deleteMerDiscountRefer(merDiscountId);
            
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(num);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //商户是直营网点  查询其商户折扣
    public DodopalResponse<List<MerDiscountReferDTO>> findMerDiscountRefer(MerDiscountQueryDTO merDiscountQueryDTO) {
        DodopalResponse<List<MerDiscountReferDTO>> response = new DodopalResponse<List<MerDiscountReferDTO>>();
        try {
            MerDiscountQuery merDiscountQuery = new MerDiscountQuery();
            PropertyUtils.copyProperties(merDiscountQuery, merDiscountQueryDTO);
            List<MerDiscountRefer> merDiscountReferList =  merDiscountReferService.findMerDiscountRefer(merDiscountQuery);
            List<MerDiscountReferDTO> merDiscountReferDTOList = new ArrayList<MerDiscountReferDTO>();
            
            if(null!=merDiscountReferList&&merDiscountReferList.size()>0){
                for(MerDiscountRefer merDiscountRefer:merDiscountReferList){
                    MerDiscountReferDTO  merDiscountReferDTO = new MerDiscountReferDTO();
                    PropertyUtils.copyProperties(merDiscountReferDTO, merDiscountRefer);
                    merDiscountReferDTO.setDiscount(Double.toString(Double.valueOf(merDiscountRefer.getDiscount())/10));
                    merDiscountReferDTOList.add(merDiscountReferDTO);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(merDiscountReferDTOList);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }


}

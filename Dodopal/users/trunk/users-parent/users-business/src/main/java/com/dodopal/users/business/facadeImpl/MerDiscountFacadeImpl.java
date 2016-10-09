package com.dodopal.users.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.api.users.facade.MerDiscountFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.model.MerDiscount;
import com.dodopal.users.business.model.query.MerDiscountQuery;
import com.dodopal.users.business.service.MerDiscountService;

@Service("merDiscountFacade")
public class MerDiscountFacadeImpl implements MerDiscountFacade {
    @Autowired
    private MerDiscountService merDiscountService;

    @Override
    public DodopalResponse<DodopalDataPage<MerDiscountDTO>> findMerDiscountByPage(MerDiscountQueryDTO merDiscountQueryDTO) {
        DodopalResponse<DodopalDataPage<MerDiscountDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<MerDiscountDTO>>();

        try {
            MerDiscountQuery merDiscountQuery = new MerDiscountQuery();
            PropertyUtils.copyProperties(merDiscountQuery, merDiscountQueryDTO);

            DodopalDataPage<MerDiscount> rtResponse = merDiscountService.findMerDiscountByPage(merDiscountQuery);

            List<MerDiscount> discountList = rtResponse.getRecords();

            List<MerDiscountDTO> discountDTOList = new ArrayList<MerDiscountDTO>();

            if (CollectionUtils.isNotEmpty(discountList)) {

                for (MerDiscount merDiscount : discountList) {
                    MerDiscountDTO merDiscountDTO = new MerDiscountDTO();
                    PropertyUtils.copyProperties(merDiscountDTO, merDiscount);
                    merDiscountDTO.setDiscount(Double.toString(Double.valueOf(merDiscount.getDiscount())/10));
                    discountDTOList.add(merDiscountDTO);
                }
            }
            //获取分页信息
            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse);
            DodopalDataPage<MerDiscountDTO> pages = new DodopalDataPage<MerDiscountDTO>(page, discountDTOList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<Integer> startOrStopMerDiscount(String activate, List<String> ids) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int num = 0;
        try {
            if (activate == null) {
                response.setCode(ResponseCode.ACTIVATE_ERROR);
                return response;
            }
            if (CollectionUtils.isEmpty(ids)) {
                response.setResponseEntity(num);
                return response;
            }
            num = merDiscountService.startOrStopMerDiscount(activate, ids);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(num);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> updateMerDiscount(MerDiscountDTO merDiscountDTO) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        MerDiscount merDiscount = new MerDiscount();
        int num = 0;
        try {
            PropertyUtils.copyProperties(merDiscount, merDiscountDTO);

            num = merDiscountService.updateMerDiscount(merDiscount);
            if(num>0){
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(num);
            }else{
                response.setCode(ResponseCode.PORTAL_USER_CHILD_MER_Edit_DISCOUNT_FAIL);
                response.setResponseEntity(num);
            }
           
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> saveMerDiscount(MerDiscountDTO merDiscountDTO) {
        
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();

        MerDiscount merDiscount = new MerDiscount();
        int num = 0;
        try {
            PropertyUtils.copyProperties(merDiscount, merDiscountDTO);

            num = merDiscountService.saveMerDiscount(merDiscount);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(num);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
     
        return response;
    }

    @Override
    public DodopalResponse<MerDiscountDTO> findMerDiscountById(String id) {
        DodopalResponse<MerDiscountDTO> response = new DodopalResponse<MerDiscountDTO>();
        try {
            MerDiscountDTO merDiscountDTO = new MerDiscountDTO();
            MerDiscount merDiscount=  merDiscountService.findMerDiscountById(id);
            if(merDiscount!=null){
                PropertyUtils.copyProperties(merDiscountDTO, merDiscount);
                merDiscountDTO.setDiscount(Double.toString(Double.valueOf(merDiscount.getDiscount())/10));
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(merDiscountDTO);
            }else{
                response.setCode(ResponseCode.SYSTEM_ERROR);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    public DodopalResponse<Integer> findMerDiscountNum(String merCode, String discount) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int num = 0;
        try {
            num = merDiscountService.findMerDiscountNum(merCode,discount);
            
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(num);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<MerDiscountDTO> findMerDiscountByCode(String merCode, String discount) {
        DodopalResponse<MerDiscountDTO> response = new DodopalResponse<MerDiscountDTO>();
        try {
            MerDiscountDTO merDiscountDTO = new MerDiscountDTO();
            MerDiscount merDiscount = merDiscountService.findMerDiscountByCode(merCode, discount);
            if(merDiscount!=null){
                PropertyUtils.copyProperties(merDiscountDTO, merDiscount);
                merDiscountDTO.setDiscount(Double.toString(Double.valueOf(merDiscount.getDiscount())/10));
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(merDiscountDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<MerDiscountDTO>> findMerDiscountByList(MerDiscountQueryDTO merDiscountQueryDTO) {
        DodopalResponse<List<MerDiscountDTO>>  dodopalResponse = new DodopalResponse<List<MerDiscountDTO>>();
        MerDiscountQuery merDiscountQuery = new MerDiscountQuery();

        try {
            PropertyUtils.copyProperties(merDiscountQuery, merDiscountQueryDTO);

            List<MerDiscount> discountList = merDiscountService.findMerDiscountByList(merDiscountQuery);

            List<MerDiscountDTO> discountDTOList = new ArrayList<MerDiscountDTO>();

            if (CollectionUtils.isNotEmpty(discountList)) {

                for (MerDiscount merDiscount : discountList) {
                    MerDiscountDTO merDiscountDTO = new MerDiscountDTO();
                    PropertyUtils.copyProperties(merDiscountDTO, merDiscount);
                    merDiscountDTO.setDiscount(Double.toString(Double.valueOf(merDiscount.getDiscount())/10));
                    discountDTOList.add(merDiscountDTO);
                }
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(discountDTOList);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
        
    }

}

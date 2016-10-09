package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerLimitExtractConfirmDTO;
import com.dodopal.api.users.dto.query.MerLimitExtractConfirmQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.portal.business.bean.MerLimitExtractConfirmBean;
import com.dodopal.portal.business.bean.MerLimitExtractConfirmBeanQueryDTO;
import com.dodopal.portal.business.service.MerLimitExtractConfirmService;
import com.dodopal.portal.delegate.MerLimitExtractConfirmDelegate;

@Service
public class MerLimitExtractConfirmServiceImpl implements MerLimitExtractConfirmService {

	@Autowired
	private MerLimitExtractConfirmDelegate merLimitExtractConfirmDelegate;
	
	@Override
	public DodopalResponse<Boolean> insertMerLimitExtractConfirm(MerLimitExtractConfirmDTO merLimitExtractConfirm) {
		DodopalResponse<Boolean> response = merLimitExtractConfirmDelegate.insertMerLimitExtractConfirm(merLimitExtractConfirm);
		return response;
	}

	@Override
	public DodopalResponse<Integer> findMerLimitExtractConfirmByCode(MerLimitExtractConfirmDTO merLimitExtractConfirm) {
		DodopalResponse<Integer> response = merLimitExtractConfirmDelegate.findMerLimitExtractConfirmByCode(merLimitExtractConfirm);
		return response;
	}

    @Override
    public DodopalResponse<DodopalDataPage<MerLimitExtractConfirmBean>> findMerLimitExtractConfirmByPage(MerLimitExtractConfirmBeanQueryDTO merLimitBeanQueryDto) {
        DodopalResponse<DodopalDataPage<MerLimitExtractConfirmBean>> response = new DodopalResponse<DodopalDataPage<MerLimitExtractConfirmBean>>();
        try {
            MerLimitExtractConfirmQueryDTO merQueryDto =new MerLimitExtractConfirmQueryDTO();
            if(StringUtils.isNotBlank(merLimitBeanQueryDto.getParentMerCode())){
                merQueryDto.setParentMerCode(merLimitBeanQueryDto.getParentMerCode());
            }
            if(StringUtils.isNotBlank(merLimitBeanQueryDto.getChildMerCode())){
                merQueryDto.setChildMerCode(merLimitBeanQueryDto.getChildMerCode());
            }
            if(StringUtils.isNotBlank(merLimitBeanQueryDto.getChildMerName())){
                merQueryDto.setChildMerName(merLimitBeanQueryDto.getChildMerName());
            }
            if(StringUtils.isNotBlank(merLimitBeanQueryDto.getExtractStartDate())){
                merQueryDto.setExtractStartDate(merLimitBeanQueryDto.getExtractStartDate());
            }
            if(StringUtils.isNotBlank(merLimitBeanQueryDto.getExtractEndDate())){
                merQueryDto.setExtractEndDate(merLimitBeanQueryDto.getExtractEndDate());
            }
            if(StringUtils.isNotBlank(merLimitBeanQueryDto.getState())){
                merQueryDto.setState(merLimitBeanQueryDto.getState());
            }
            if (merLimitBeanQueryDto.getPage()!=null) {
                merQueryDto.setPage(merLimitBeanQueryDto.getPage());
            }
            DodopalResponse<DodopalDataPage<MerLimitExtractConfirmDTO>> ddpMerLimitResult = merLimitExtractConfirmDelegate.findMerLimitExtractConfirmByPage(merQueryDto);
            if(ResponseCode.SUCCESS.equals(ddpMerLimitResult.getCode())){
                List<MerLimitExtractConfirmDTO> merLimitDtoList =ddpMerLimitResult.getResponseEntity().getRecords();
                List<MerLimitExtractConfirmBean> merLimitBeanList = new ArrayList<MerLimitExtractConfirmBean>();
                if(ddpMerLimitResult.getResponseEntity() != null && CollectionUtils.isNotEmpty(ddpMerLimitResult.getResponseEntity().getRecords())){
                    for(MerLimitExtractConfirmDTO merDtoLimit : merLimitDtoList){
                        MerLimitExtractConfirmBean merLimitBean =new MerLimitExtractConfirmBean();
                        merLimitBean.setId(merDtoLimit.getId());
                        merLimitBean.setChildMerCode(merDtoLimit.getChildMerCode());
                        merLimitBean.setConcelDate(merDtoLimit.getConcelDate());
                        merLimitBean.setConcelUser(merDtoLimit.getConcelUser());
                        merLimitBean.setConfirmUser(merDtoLimit.getConfirmUser());
                        merLimitBean.setConfirmDate(merDtoLimit.getConfirmDate());
                        if(StringUtils.isNotBlank(merDtoLimit.getExtractAmt())){
                            merLimitBean.setExtractAmt(String.format("%.2f", Double.parseDouble(merDtoLimit.getExtractAmt())/ 100));
                        }
                        if(MerTypeEnum.CHAIN.getCode().equals(merLimitBeanQueryDto.getMerType())){
                            merLimitBean.setMerName(merDtoLimit.getChildMerName());   
                        }else if(MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merLimitBeanQueryDto.getMerType())){
                            merLimitBean.setMerName(merDtoLimit.getParentMerName());  
                        }else if(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merLimitBeanQueryDto.getMerType())){
                            merLimitBean.setMerName(merDtoLimit.getParentMerName());  
                        }
                        merLimitBean.setExtractDate(merDtoLimit.getExtractDate());
                        merLimitBean.setExtractUser(merDtoLimit.getExtractUser());
                        merLimitBean.setParentMerCode(merDtoLimit.getParentMerCode());
                        merLimitBean.setState(merDtoLimit.getState());
                        merLimitBeanList.add(merLimitBean);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpMerLimitResult.getResponseEntity());
                DodopalDataPage<MerLimitExtractConfirmBean> ddpDTOResult = new DodopalDataPage<MerLimitExtractConfirmBean>(page, merLimitBeanList);
                response.setCode(ddpMerLimitResult.getCode());
                response.setResponseEntity(ddpDTOResult);
            }else{
                response.setCode(ddpMerLimitResult.getCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.MER_LIMIT_EXTRACT_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<Integer> updateMerLimitExtractConfirmByCode(MerLimitExtractConfirmBean merLimitBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        MerLimitExtractConfirmDTO  merLimitDto = new MerLimitExtractConfirmDTO();
        BeanUtils.copyProperties(merLimitBean, merLimitDto);
        try {
            response  = merLimitExtractConfirmDelegate.updateMerLimitExtractConfirmByCode(merLimitDto);
        }
        catch (Exception e) {
           e.printStackTrace();
           response.setCode(ResponseCode.UP_MER_LIMIT_EXTRACT_ERROR);
        }
        return response;
    }

  

}

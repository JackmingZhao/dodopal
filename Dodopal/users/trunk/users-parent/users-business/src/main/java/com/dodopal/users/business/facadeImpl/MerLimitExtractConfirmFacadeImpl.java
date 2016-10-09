package com.dodopal.users.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.users.dto.MerLimitExtractConfirmDTO;
import com.dodopal.api.users.dto.query.MerLimitExtractConfirmQueryDTO;
import com.dodopal.api.users.facade.MerLimitExtractConfirmFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.model.MerLimitExtractConfirm;
import com.dodopal.users.business.model.query.MerLimitExtractConfirmQuery;
import com.dodopal.users.business.service.MerLimitExtractConfirmService;
@Service("merLimitExtractConfirmFacade")
public class MerLimitExtractConfirmFacadeImpl implements MerLimitExtractConfirmFacade {
	
	@Autowired
	private MerLimitExtractConfirmService merLimitExtractConfirmService;
	
	@Override
	public DodopalResponse<Boolean> insertMerLimitExtractConfirm(MerLimitExtractConfirmDTO merLimitExtractConfirm) {
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		try {
			MerLimitExtractConfirm extractConfirm = new MerLimitExtractConfirm();
			PropertyUtils.copyProperties(extractConfirm, merLimitExtractConfirm);
			merLimitExtractConfirmService.insertMerLimitExtractConfirm(extractConfirm);
			response.setCode(ResponseCode.SUCCESS);
			response.setResponseEntity(true);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
		return response;
	}

	@Override
	public DodopalResponse<Integer> findMerLimitExtractConfirmByCode(MerLimitExtractConfirmDTO merLimitExtractConfirm) {
		DodopalResponse<Integer> response = new DodopalResponse<Integer>();
		try {
			MerLimitExtractConfirm extractConfirm = new MerLimitExtractConfirm();
			PropertyUtils.copyProperties(extractConfirm, merLimitExtractConfirm);
			int flag= merLimitExtractConfirmService.findMerLimitExtractConfirmByCode(extractConfirm);
			response.setCode(ResponseCode.SUCCESS);
			response.setResponseEntity(flag);
		}catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
		return response;
	}

    @Override
    public DodopalResponse<DodopalDataPage<MerLimitExtractConfirmDTO>> findMerLimitExtractConfirmByPage(MerLimitExtractConfirmQueryDTO merLimitQueryDto) {
        DodopalResponse<DodopalDataPage<MerLimitExtractConfirmDTO>> response = new DodopalResponse<DodopalDataPage<MerLimitExtractConfirmDTO>>();
        try {
            MerLimitExtractConfirmQuery merQuery =new MerLimitExtractConfirmQuery();
            BeanUtils.copyProperties(merLimitQueryDto, merQuery);
            if (merQuery.getPage() == null) {
                merQuery.setPage(new PageParameter());
            }
            DodopalDataPage<MerLimitExtractConfirm> ddpMerLimitResult = merLimitExtractConfirmService.findMerLimitExtractConfirmByPage(merQuery);
            if(ddpMerLimitResult!=null){
                List<MerLimitExtractConfirm> merLimitList =ddpMerLimitResult.getRecords();
                List<MerLimitExtractConfirmDTO> merLimitDtoList = new ArrayList<MerLimitExtractConfirmDTO>();
                if(merLimitList!=null){
                    for(MerLimitExtractConfirm merLimit : merLimitList){
                        MerLimitExtractConfirmDTO merLimitDto =new MerLimitExtractConfirmDTO();
                        merLimitDto.setId(merLimit.getId());
                        merLimitDto.setChildMerCode(merLimit.getChildMerCode());
                        merLimitDto.setChildMerName(merLimit.getChildMerName());
                        merLimitDto.setConcelDate(merLimit.getConcelDate());
                        merLimitDto.setConcelUser(merLimit.getConcelUser());
                        merLimitDto.setConfirmUser(merLimit.getConfirmUser());
                        merLimitDto.setConfirmDate(merLimit.getConfirmDate());
                        merLimitDto.setExtractAmt(merLimit.getExtractAmt());
                        merLimitDto.setExtractDate(merLimit.getExtractDate());
                        merLimitDto.setExtractUser(merLimit.getExtractUser());
                        merLimitDto.setParentMerCode(merLimit.getParentMerCode());
                        merLimitDto.setParentMerName(merLimit.getParentMerName());
                        merLimitDto.setState(merLimit.getState());
                        merLimitDtoList.add(merLimitDto);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpMerLimitResult);
                DodopalDataPage<MerLimitExtractConfirmDTO> ddpDTOResult = new DodopalDataPage<MerLimitExtractConfirmDTO>(page, merLimitDtoList);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(ddpDTOResult);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.MER_LIMIT_EXTRACT_ERROR);
        }
        return response;
    }
    @Transactional
    @Override
    public DodopalResponse<Integer> updateMerLimitExtractConfirmByCode(MerLimitExtractConfirmDTO merLimitExtractConfirm) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        MerLimitExtractConfirm  merLimit = new MerLimitExtractConfirm();
        BeanUtils.copyProperties(merLimitExtractConfirm, merLimit);
        try {
            response  = merLimitExtractConfirmService.updateMerLimitExtractConfirmByCode(merLimit);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
           e.printStackTrace();
           response.setCode(ResponseCode.UP_MER_LIMIT_EXTRACT_ERROR);
        }
        return response;
    }

}

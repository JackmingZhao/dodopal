package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.facade.MerFunctionInfoFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.service.MerFunctionInfoService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service("merFunctionInfoFacade")
public class MerFunctionInfoFacadeImpl implements MerFunctionInfoFacade {
    private final static Logger logger = LoggerFactory.getLogger(MerFunctionInfoFacadeImpl.class);
    @Autowired
    private MerFunctionInfoService merFunctionInfoService;

    private static final String[] IGNORE_FIELDS = {"appName"};

    @Override
    public DodopalResponse<List<MerFunctionInfoDTO>> findAllFuncInfoForOSS(String merType) {
        DodopalResponse<List<MerFunctionInfoDTO>> response = new DodopalResponse<List<MerFunctionInfoDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            List<MerFunctionInfo> merFunInfoList = merFunctionInfoService.findAllFuncInfoForOSS(merType);
            if (!CollectionUtils.isEmpty(merFunInfoList)) {
                List<MerFunctionInfoDTO> merFunctionInfoDTOList = new ArrayList<MerFunctionInfoDTO>(merFunInfoList.size());
                for (MerFunctionInfo merFunInfoTemp : merFunInfoList) {
                    MerFunctionInfoDTO merFunInfoDTOTemp = new MerFunctionInfoDTO();
                    BeanUtils.copyProperties(merFunInfoTemp, merFunInfoDTOTemp, IGNORE_FIELDS);
                    merFunctionInfoDTOList.add(merFunInfoDTOTemp);
                }
                response.setResponseEntity(merFunctionInfoDTOList);
            }
        }
        catch (Exception e) {
        	if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<MerFunctionInfoDTO>> findFuncInfoByMerType(MerTypeEnum merType) {
        DodopalResponse<List<MerFunctionInfoDTO>> response = new DodopalResponse<List<MerFunctionInfoDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            if (merType != null) {
                List<MerFunctionInfo> merFunInfoList = merFunctionInfoService.findMerFunctionInfoByMerType(merType.getCode());
                if (!CollectionUtils.isEmpty(merFunInfoList)) {
                    List<MerFunctionInfoDTO> merFunctionInfoDTOList = new ArrayList<MerFunctionInfoDTO>(merFunInfoList.size());
                    for (MerFunctionInfo merFunInfoTemp : merFunInfoList) {
                        MerFunctionInfoDTO merFunInfoDTOTemp = new MerFunctionInfoDTO();
                        BeanUtils.copyProperties(merFunInfoTemp, merFunInfoDTOTemp, IGNORE_FIELDS);
                        merFunctionInfoDTOList.add(merFunInfoDTOTemp);
                    }
                    response.setResponseEntity(merFunctionInfoDTOList);
                }
            }
        }
        catch (Exception e) {
        	if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}

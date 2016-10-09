package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.PosLogDTO;
import com.dodopal.api.users.dto.PosLogQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.PosLogBDBean;
import com.dodopal.oss.business.bean.query.PosLogBDFindBean;
import com.dodopal.oss.business.service.PosLogService;
import com.dodopal.oss.delegate.PosLogDelegate;

@Service
public class PosLogServiceImpl implements PosLogService {
    
    private final static Logger log = LoggerFactory.getLogger(PosLogServiceImpl.class);

	@Autowired
	PosLogDelegate posLogDelegate;
   
    @Override
    public DodopalResponse<DodopalDataPage<PosLogBDBean>> findPosLogByPage(PosLogBDFindBean findBean) {
        DodopalResponse<DodopalDataPage<PosLogBDBean>> response = new DodopalResponse<DodopalDataPage<PosLogBDBean>>();
        
        try {
            
            PosLogQueryDTO findDto = new PosLogQueryDTO();
            PropertyUtils.copyProperties(findDto, findBean);
            
            DodopalResponse<DodopalDataPage<PosLogDTO>>  getResponse  = posLogDelegate.findPogLogsByPage(findDto); 
            
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                List<PosLogBDBean> resResult = new ArrayList<PosLogBDBean>();
                PosLogBDBean retBean = null;
                for (PosLogDTO retDTO : getResponse.getResponseEntity().getRecords()) {
                    retBean = new PosLogBDBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    resResult.add(retBean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(getResponse.getResponseEntity());
                DodopalDataPage<PosLogBDBean> pages = new DodopalDataPage<PosLogBDBean>(page, resResult);
                response.setCode(getResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(getResponse.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("PosLogServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("PosLogServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<PosLogBDBean>> findPogLogsByList(PosLogBDFindBean logQueryDTO) {
        
        DodopalResponse<List<PosLogBDBean>> response = new DodopalResponse<List<PosLogBDBean>>();
      try {
            
            PosLogQueryDTO findDto = new PosLogQueryDTO();
            PropertyUtils.copyProperties(findDto, logQueryDTO);
            
            DodopalResponse<List<PosLogDTO>>  getResponse  = posLogDelegate.findPogLogsByList(findDto);
            
            List<PosLogBDBean> resResult = new ArrayList<PosLogBDBean>();
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                PosLogBDBean retBean = null;
                for (PosLogDTO retDTO : getResponse.getResponseEntity()) {
                    retBean = new PosLogBDBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    resResult.add(retBean);
                }
                response.setCode(getResponse.getCode());
                response.setResponseEntity(resResult);
            } else {
                response.setCode(getResponse.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("PosLogServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("PosLogServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}

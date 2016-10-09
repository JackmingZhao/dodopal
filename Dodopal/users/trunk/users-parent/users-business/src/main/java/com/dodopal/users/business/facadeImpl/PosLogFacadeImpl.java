package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.PosLogDTO;
import com.dodopal.api.users.dto.PosLogQueryDTO;
import com.dodopal.api.users.facade.PosLogFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.PosLog;
import com.dodopal.users.business.model.PosLogQuery;
import com.dodopal.users.business.service.PosLogService;

@Service("posLogFacade")
public class PosLogFacadeImpl implements PosLogFacade {
	
   @Autowired
   private PosLogService posLogService;
   
	@Override
	public DodopalResponse<DodopalDataPage<PosLogDTO>> findPosLogList(PosLogQueryDTO findDto) {
		DodopalResponse<DodopalDataPage<PosLogDTO>> response =new DodopalResponse<DodopalDataPage<PosLogDTO>>();
		if(null == findDto){
			findDto = new PosLogQueryDTO();
		}
		PosLogQuery findBean = new PosLogQuery(); 
		try {
            PropertyUtils.copyProperties(findBean, findDto);
            List<PosLogDTO> dtoList = new ArrayList<PosLogDTO>();
            List<PosLog> posLogList =posLogService.findPosLogList(findBean);
            
            if(posLogList!=null && posLogList.size()>0){	            	
                for(PosLog itme:posLogList){
                	PosLogDTO poLogDto = new PosLogDTO();
                PropertyUtils.copyProperties(poLogDto,itme);
                dtoList.add(poLogDto);
                }	                
            }            
            PageParameter page = findBean.getPage();   
            DodopalDataPage<PosLogDTO> pages = new DodopalDataPage<PosLogDTO>(page,dtoList);
            response.setResponseEntity(pages);
            response.setCode(ResponseCode.SUCCESS);
    }
    catch (Exception e) {
        response.setCode(ResponseCode.SYSTEM_ERROR);
        //throw new RuntimeException(e);
        e.printStackTrace();
    }	       
    return response;
	}
	
	
	

    //pos操作日志 导出
    public DodopalResponse<List<PosLogDTO>> findPogLogsByList(PosLogQueryDTO findDto) {
        DodopalResponse<List<PosLogDTO>> response = new DodopalResponse<List<PosLogDTO>>();
        if(null == findDto){
            findDto = new PosLogQueryDTO();
        }
        PosLogQuery findBean = new PosLogQuery(); 
        try {
            PropertyUtils.copyProperties(findBean, findDto);
            List<PosLogDTO> dtoList = new ArrayList<PosLogDTO>();
            List<PosLog> posLogList =posLogService.findPosLogByList(findBean);
            
            if(posLogList!=null && posLogList.size()>0){                    
                for(PosLog itme:posLogList){
                    PosLogDTO poLogDto = new PosLogDTO();
                PropertyUtils.copyProperties(poLogDto,itme);
                dtoList.add(poLogDto);
                }                   
            }            
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(dtoList);
      }
       catch (Exception e) {
        response.setCode(ResponseCode.SYSTEM_ERROR);
        //throw new RuntimeException(e);
        e.printStackTrace();
      }          
        return response;
    }

}

package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.ProfitCollectDTO;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.api.users.facade.ProfitFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.model.ProfitCollect;
import com.dodopal.users.business.model.ProfitDetails;
import com.dodopal.users.business.model.query.ProfitQuery;
import com.dodopal.users.business.service.ProfitCollectService;
import com.dodopal.users.business.service.ProfitDetailsService;
@Service("profitFacade")
public class ProfitFacadeImpl implements ProfitFacade  {
	private final static Logger log = LoggerFactory.getLogger(ProfitFacadeImpl.class);
	@Autowired
	ProfitCollectService profitCollectService;
	@Autowired
	ProfitDetailsService profitDetailsService;
	
	
	@Override
	public DodopalResponse<DodopalDataPage<ProfitCollectDTO>> findProfitCollectListByPage(ProfitQueryDTO queryDTO,SourceEnum source) {
		DodopalResponse<DodopalDataPage<ProfitCollectDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<ProfitCollectDTO>>();
		try{
			checkParam(queryDTO, source);
			ProfitQuery queryBean = new ProfitQuery();
			PropertyUtils.copyProperties(queryBean, queryDTO);
			List<ProfitCollectDTO> dtoList = new ArrayList<ProfitCollectDTO>();
			DodopalDataPage<ProfitCollect> beanList = profitCollectService.findProfitCollectByPage(queryBean);
			if(CollectionUtils.isNotEmpty(beanList.getRecords())){
				for(ProfitCollect temp:beanList.getRecords()){
					ProfitCollectDTO collectDTO = new ProfitCollectDTO();
					PropertyUtils.copyProperties(collectDTO, temp);
					dtoList.add(collectDTO);
				}
			}
			PageParameter page =  DodopalDataPageUtil.convertPageInfo(beanList);
			DodopalDataPage<ProfitCollectDTO> pages = new DodopalDataPage<ProfitCollectDTO>(page, dtoList);
			dodopalResponse.setCode(ResponseCode.SUCCESS);
			dodopalResponse.setResponseEntity(pages);
		}catch(DDPException e){
			log.error("ProfitFacadeImpl findProfitCollectListByPage call an error",e);
			dodopalResponse.setCode(e.getCode());
		}catch(Exception e){
			log.error("ProfitFacadeImpl findProfitCollectListByPage call an error",e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> findProfitDetailsListByPage(ProfitQueryDTO queryDTO,SourceEnum source) {
		DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<ProfitDetailsDTO>>();
		try{
			checkParam(queryDTO, source);
			ProfitQuery queryBean = new ProfitQuery();
			PropertyUtils.copyProperties(queryBean, queryDTO);
			List<ProfitDetailsDTO> dtoList = new ArrayList<ProfitDetailsDTO>();
			DodopalDataPage<ProfitDetails> beanList = profitDetailsService.findProfitDetailsByPage(queryBean);
			if(CollectionUtils.isNotEmpty(beanList.getRecords())){
				for(ProfitDetails temp:beanList.getRecords()){
					ProfitDetailsDTO collectDTO = new ProfitDetailsDTO();
					PropertyUtils.copyProperties(collectDTO, temp);
					dtoList.add(collectDTO);
				}
			}
			PageParameter page =  DodopalDataPageUtil.convertPageInfo(beanList);
			DodopalDataPage<ProfitDetailsDTO> pages = new DodopalDataPage<ProfitDetailsDTO>(page, dtoList);
			dodopalResponse.setCode(ResponseCode.SUCCESS);
			dodopalResponse.setResponseEntity(pages);
		}catch(DDPException e){
			log.error("ProfitFacadeImpl findProfitDetailsListByPage call an error",e);
			dodopalResponse.setCode(e.getCode());
		}catch(Exception e){
			log.error("ProfitFacadeImpl findProfitDetailsListByPage call an error",e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}
	
	private void checkParam(ProfitQueryDTO queryDTO,SourceEnum source){
		if(null==source){
			throw new DDPException(ResponseCode.USERS_FIND_FLG_ERR);
		}
		if(SourceEnum.PORTAL.getCode().equals(source.getCode())){
			if(StringUtils.isBlank(queryDTO.getMerCode()))
				throw new DDPException(ResponseCode.USERS_FIND_MER_CODE_NULL);
		}
	}

	@Override
	public DodopalResponse<List<ProfitCollectDTO>> findProfitCollect(ProfitQueryDTO queryDTO, SourceEnum source) {
		DodopalResponse<List<ProfitCollectDTO>> dodopalResponse = new DodopalResponse<List<ProfitCollectDTO>>();
		try{
			checkParam(queryDTO, source);
			ProfitQuery queryBean = new ProfitQuery();
			PropertyUtils.copyProperties(queryBean, queryDTO);
			List<ProfitCollectDTO> dtoList = new ArrayList<ProfitCollectDTO>();
			List<ProfitCollect> beanList = profitCollectService.findProfitCollect(queryBean);
			if(CollectionUtils.isNotEmpty(beanList)){
				for(ProfitCollect temp:beanList){
					ProfitCollectDTO collectDTO = new ProfitCollectDTO();
					PropertyUtils.copyProperties(collectDTO, temp);
					dtoList.add(collectDTO);
				}
			}
			dodopalResponse.setResponseEntity(dtoList);
			dodopalResponse.setCode(ResponseCode.SUCCESS);
		}catch(DDPException e){
			log.error("ProfitFacadeImpl findProfitCollectList call an error",e);
			dodopalResponse.setCode(e.getCode());
		}catch(Exception e){
			log.error("ProfitFacadeImpl findProfitCollectList call an error",e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}
}

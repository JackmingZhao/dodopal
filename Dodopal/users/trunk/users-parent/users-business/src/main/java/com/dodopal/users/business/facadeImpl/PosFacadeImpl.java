package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.api.users.facade.PosFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.Pos;
import com.dodopal.users.business.model.PosQuery;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.PosService;

@Service("posFacade")
public class PosFacadeImpl implements PosFacade {
	
	@Autowired
	private MerchantService merService;
	
	@Autowired
	private PosService posService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private MerchantFacade merchantFacade;

	@Override
	public DodopalResponse<String> posOper(PosOperTypeEnum posOper,
			String merCode, String[] posCodes, OperUserDTO operUser,SourceEnum source,String comments) {
		DodopalResponse<String> response = new DodopalResponse<>();
		try{
			//操作类型
			if(null == posOper){
				response.setCode(ResponseCode.USERS_POS_OPER_NULL);
				return response;
			}
			//pos号
			if(null == posCodes || posCodes.length == 0){
				response.setCode(ResponseCode.USERS_POS_CODE_NULL);
			    return response;
			}
			//pos绑定，商户号
			if(PosOperTypeEnum.OPER_BUNDLING  == posOper){
				if(DDPStringUtil.isNotPopulated(merCode)){
					response.setCode(ResponseCode.USERS_POS_BUND_MER_CODE_NULL);
				    return response;
				}			
			}
			//操作人
			if(null == operUser || DDPStringUtil.isNotPopulated(operUser.getId())){
				response.setCode(ResponseCode.USERS_POS_OPE_USER_NULL);
			    return response;
			}
			Merchant mer =null;
			//POS绑定
			if(PosOperTypeEnum.OPER_BUNDLING  == posOper){
				for(String code:posCodes){
					Pos pos = posService.findPosByCode(code);
					//int count = posService.getPosCount(code);
					//if(count==0){
					if(null==pos){
						response.setCode(ResponseCode.USERS_POS_NO_USE);
						return response;
					}
					
					//获取商户充值业务城市
					DodopalResponse<List<Area>> reAreaListRe = merchantFacade.findMerCitys(merCode);
					//获取商户消费业务城市
				    DodopalResponse<List<Area>> coAreaListRe = merchantFacade.findMerCitysPayment(merCode);
				    List<Area> reAreaList = reAreaListRe.getResponseEntity();
			        List<Area> coAreaList = coAreaListRe.getResponseEntity(); 
					List<Area> allArea = new ArrayList<Area>();
					
					if(CollectionUtils.isNotEmpty(reAreaList)){
			        	allArea.addAll(reAreaList);
			        }
			        if(CollectionUtils.isNotEmpty(coAreaList)){
			        	allArea.addAll(coAreaList);
			        }
					if(StringUtils.isBlank(pos.getCityCode())){
						response.setCode(ResponseCode.USERS_POS_NO_BIND_CITY);
						return response;
		            }
					boolean flag = false;
				    for(Area tempArea:allArea){
	                	if(tempArea.getCityCode().equals(pos.getCityCode())){
	                		flag = true;
	                		break;
	                	}
	                }
	                if(!flag){
	                	response.setCode(ResponseCode.USERS_POS_CITY_IS_ERROR);
						return response;
	                }
				}
				int posBindedcount = posService.getPosBindedCountByCodes(posCodes);
				if (posBindedcount > 0) {
				    response.setCode(ResponseCode.USERS_POS_BUND_MER_CODE_EXIST);
				    if(posService.getPosBindedCountByCodesAndMerCode(posCodes, merCode)>0){
				        response.setCode(ResponseCode.USERS_POS_BUND_MER_CODE_AGAIN_EXIST);
				    }
                    return response;
				}
				
				mer = merService.findMerchantInfoByMerCode(merCode);
				posService.updatePosBundling(mer,posOper, posCodes, operUser,source,comments);
			}
			//POS解绑
			else if(PosOperTypeEnum.OPER_UNBUNDLING  == posOper){
				posService.updatePosUnBundling(posOper,posCodes, operUser,source);
			}
			//POS启用/禁用
			else if(PosOperTypeEnum.OPER_ENABLE  == posOper || PosOperTypeEnum.OPER_DISABLE == posOper){
				posService.updatePosActivate(posOper, posCodes, operUser,source);
			}
			//POS删除
			else if(PosOperTypeEnum.OPER_DELETE  == posOper){
			    posService.deletePos(posOper, posCodes, operUser,source);
			}	
			
			response.setCode(ResponseCode.SUCCESS);
		}catch(Exception e){
			 response.setCode(ResponseCode.SYSTEM_ERROR);
	            //throw new RuntimeException(e);
	         e.printStackTrace();
		}
		
		return response;
	}
	
	@Override
	public DodopalResponse<DodopalDataPage<PosDTO>> findPosListPage(PosQueryDTO findDto){
		DodopalResponse<DodopalDataPage<PosDTO>> response =new DodopalResponse<DodopalDataPage<PosDTO>>();
		if(null == findDto || DDPStringUtil.isNotPopulated(findDto.getMerchantCode())){
			response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
			return response;
		}
		PosQuery findBean = new PosQuery(); 
		try {
			//外部对象转为内部使用对象
            PropertyUtils.copyProperties(findBean, findDto);
            List<PosDTO> dtoList = new ArrayList<PosDTO>();
            List<Pos> posList =posService.findPosListPage(findBean);
          
            if(posList!=null && posList.size()>0){	            	
                for(Pos itme:posList){
                	PosDTO poDto = new PosDTO();
                PropertyUtils.copyProperties(poDto,itme);
                dtoList.add(poDto);
                }	                
            }
            //分页信息设置
            PageParameter page = findBean.getPage();
//            if(page != null) {
//                pages.setPageNo(page.getPageNo());
//                pages.setPageSize(page.getPageSize());
//                pages.setRows(page.getRows());
//                pages.setTotalPages(page.getTotalPages());
//            }
//            pages.setRecords(dtoList);
            DodopalDataPage<PosDTO> pages = new DodopalDataPage<PosDTO>(page,dtoList);
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

    @Override
    public DodopalResponse<DodopalDataPage<PosDTO>> findChildrenMerPosListPage(PosQueryDTO findDto) {
        DodopalResponse<DodopalDataPage<PosDTO>> response =new DodopalResponse<DodopalDataPage<PosDTO>>();
        if(null == findDto || DDPStringUtil.isNotPopulated(findDto.getMerchantCode())){
            response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
            return response;
        }
        PosQuery findBean = new PosQuery(); 
        try {
            //外部对象转为内部使用对象
            PropertyUtils.copyProperties(findBean, findDto);
            List<PosDTO> dtoList = new ArrayList<PosDTO>();
            List<Pos> posList =posService.findChildrenMerPosListPage(findBean);
          
            if(posList!=null && posList.size()>0){                  
                for(Pos itme:posList){
                    PosDTO poDto = new PosDTO();
	                PropertyUtils.copyProperties(poDto,itme);
	                dtoList.add(poDto);
                }                   
            }
            //分页信息设置
            PageParameter page = findBean.getPage();
            DodopalDataPage<PosDTO> pages = new DodopalDataPage<PosDTO>(page,dtoList);
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

	@Override
	public DodopalResponse<PosDTO> findPosInfoByCode(String posCode, String merCode) {
		DodopalResponse<PosDTO> response =new DodopalResponse<PosDTO>();
		PosDTO posDTO = new PosDTO();
		try {
			Pos pos = posService.findPosInfoByCode(posCode, merCode);
			PropertyUtils.copyProperties(posDTO, pos);
			response.setResponseEntity(posDTO);
			response.setCode(ResponseCode.SUCCESS);
		}catch(Exception e) {
	        response.setCode(ResponseCode.SYSTEM_ERROR);
	        //throw new RuntimeException(e);
	        e.printStackTrace();
		}
		return response;
	}

	@Override
	public DodopalResponse<String> savePosComments(String posCode, String comments) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		try{
			posService.savePosComments(posCode, comments);
			response.setCode(ResponseCode.SUCCESS);
		}catch(Exception e){
			 response.setCode(ResponseCode.SYSTEM_ERROR);
	         e.printStackTrace();
		}
		return response;
	}

}

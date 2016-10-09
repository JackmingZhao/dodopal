package com.dodopal.oss.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.MerUserCardBDLogDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerUserCardBDDTOQuery;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.api.users.facade.UserCardLogQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.MerUserCardBDBean;
import com.dodopal.oss.business.bean.MerUserCardBDLogBean;
import com.dodopal.oss.business.bean.MerchantUserBean;
import com.dodopal.oss.business.bean.MerchantUserExpBean;
import com.dodopal.oss.business.bean.query.MerUserCardBDFindBean;
import com.dodopal.oss.business.bean.query.UserCardLogQuery;
import com.dodopal.oss.business.model.dto.MerchantUserQuery;
import com.dodopal.oss.business.service.MerUserService;
import com.dodopal.oss.business.service.MerchantService;
import com.dodopal.oss.delegate.MerUserDelegate;

@Service
public class MerUserServiceImpl implements MerUserService {
    private final static Logger log = LoggerFactory.getLogger(MerUserServiceImpl.class);
	@Autowired
	MerUserDelegate merUserDelegate;
    @Autowired
    MerchantService merchantService;
    
	@Override
	public DodopalResponse<List<MerchantUserBean>> findMerUsers(
			MerchantUserBean merchantUserBean) {
		DodopalResponse<List<MerchantUserBean>> rtResponse = new DodopalResponse<List<MerchantUserBean>>();
		try {
			MerchantUserDTO merUserDto = new MerchantUserDTO();
			PropertyUtils.copyProperties(merUserDto, merchantUserBean);
			DodopalResponse<List<MerchantUserDTO>> getResponse = merUserDelegate
					.findMerUserList(merUserDto);
			if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
				List<MerchantUserBean> resResult = new ArrayList<MerchantUserBean>();
				MerchantUserBean merUserBean = null;
				for (MerchantUserDTO merUserDTO : getResponse
						.getResponseEntity()) {
					merUserBean = new MerchantUserBean();
					PropertyUtils.copyProperties(merUserBean, merUserDTO);
					resResult.add(merUserBean);
				}
				rtResponse.setResponseEntity(resResult);
				rtResponse.setCode(ResponseCode.SUCCESS);
			} else {
				rtResponse.setCode(getResponse.getCode());
			}
		}catch(HessianRuntimeException e){
		    log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
		}catch (Exception e) {
		    log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
			// throw new DDPException(ResponseCode.SYSTEM_ERROR);
		}
		return rtResponse;
	}

    /**
     * 查看用户信息
     * @param merUserBean
     * @return 
     */
    public  DodopalResponse<MerchantUserBean> findMerUser(String userId){
    	DodopalResponse<MerchantUserBean> rtResponse = new DodopalResponse<MerchantUserBean>();
    	try {

    		//取得个人用户信息
			DodopalResponse<MerchantUserDTO> getResponse = merUserDelegate
					.findMerUser(userId);
			if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
				MerchantUserBean merUserBean = new MerchantUserBean();
				//转为前端对象
				PropertyUtils.copyProperties(merUserBean, getResponse.getResponseEntity());
				rtResponse.setResponseEntity(merUserBean);
				rtResponse.setCode(ResponseCode.SUCCESS);
			} else {
				rtResponse.setCode(getResponse.getCode());
			}
		}catch(HessianRuntimeException e){
		    log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
		}catch (Exception e) {
		    log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
			// throw new DDPException(ResponseCode.SYSTEM_ERROR);
		}
		return rtResponse;    	
    }

    /**
     * 重置用户密码并发送短信
     * @param 用户ID
     * @return 
     */
    public  DodopalResponse<String> resetPwd(String userId){
    	DodopalResponse<String> rtResponse = new DodopalResponse<String>();
    	try {

    		//取得个人用户信息
			DodopalResponse<MerchantUserDTO> getResponse = merUserDelegate.findMerUser(userId);
			if (!ResponseCode.SUCCESS.equals(getResponse.getCode())) {
				rtResponse.setCode(getResponse.getCode());
				return rtResponse;
			}
			MerchantUserDTO userDTO = getResponse.getResponseEntity();
			//判断商户是否审核通过，判断是否是个人用户   
			//如果不是个人，则进行审核状态的判断
			if(!MerUserTypeEnum.PERSONAL.getCode().equals(userDTO.getMerUserType())){
			    if(!userDTO.getMerchantState().equals(MerStateEnum.THROUGH.getCode())){
			        rtResponse.setCode(ResponseCode.USERS_MER_STATE_ERR);
                    return rtResponse;
			    }
			}
		    //修改密码并发送短信
			DodopalResponse<Boolean> getRspResetPwd = merUserDelegate.resetPwd(userDTO);
			if (!ResponseCode.SUCCESS.equals(getRspResetPwd.getCode())) {
				rtResponse.setCode(getRspResetPwd.getCode());
				return rtResponse;
			}

			rtResponse.setCode(ResponseCode.SUCCESS);
			
		}catch(HessianRuntimeException e){
		    log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
		}catch (Exception e) {
		    log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
			// throw new DDPException(ResponseCode.SYSTEM_ERROR);
		}
		return rtResponse;    	
    }

	@Override
	public DodopalResponse<List<MerUserCardBDBean>> findMerUserCards(MerUserCardBDFindBean findBean) {
		DodopalResponse<List<MerUserCardBDBean>> rtResponse = new DodopalResponse<List<MerUserCardBDBean>>();
		try {
			MerUserCardBDFindDTO findDto = new MerUserCardBDFindDTO();
			PropertyUtils.copyProperties(findDto, findBean);
			DodopalResponse<List<MerUserCardBDDTO>> getResponse = merUserDelegate.findMerUserCardList(findDto);
			if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
				List<MerUserCardBDBean> resResult = new ArrayList<MerUserCardBDBean>();
				MerUserCardBDBean retBean = null;
				for (MerUserCardBDDTO retDTO : getResponse
						.getResponseEntity()) {
					retBean = new MerUserCardBDBean();
					PropertyUtils.copyProperties(retBean, retDTO);
					resResult.add(retBean);
				}
				rtResponse.setResponseEntity(resResult);
				rtResponse.setCode(ResponseCode.SUCCESS);
			} else {
				rtResponse.setCode(getResponse.getCode());
			}
		}catch(HessianRuntimeException e){
		    log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
			// throw new DDPException(ResponseCode.SYSTEM_ERROR);
		}
		return rtResponse;
	}
	
	@Override
	public DodopalResponse<DodopalDataPage<MerUserCardBDBean>> findMerUserCardBDListByPage(MerUserCardBDFindBean findBean) {
		DodopalResponse<DodopalDataPage<MerUserCardBDBean>> rtResponse = new DodopalResponse<DodopalDataPage<MerUserCardBDBean>>();
		try {
			MerUserCardBDDTOQuery findDto = new MerUserCardBDDTOQuery();
			PropertyUtils.copyProperties(findDto, findBean);
			DodopalResponse<DodopalDataPage<MerUserCardBDDTO>> getResponse = merUserDelegate.findMerUserCardBDListByPage(findDto);
			if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
				List<MerUserCardBDBean> resResult = new ArrayList<MerUserCardBDBean>();
				MerUserCardBDBean retBean = null;
				for (MerUserCardBDDTO retDTO : getResponse.getResponseEntity().getRecords()) {
					retBean = new MerUserCardBDBean();
					PropertyUtils.copyProperties(retBean, retDTO);
					resResult.add(retBean);
				}
				PageParameter page = DodopalDataPageUtil.convertPageInfo(getResponse.getResponseEntity());
	            DodopalDataPage<MerUserCardBDBean> pages = new DodopalDataPage<MerUserCardBDBean>(page, resResult);
	            rtResponse.setCode(getResponse.getCode());
	            rtResponse.setResponseEntity(pages);
			} else {
				rtResponse.setCode(getResponse.getCode());
			}
		}catch(HessianRuntimeException e){
		    log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
			// throw new DDPException(ResponseCode.SYSTEM_ERROR);
		}
		return rtResponse;
	}

	@Override
	public  DodopalResponse<String> unbundUserCards(String operName,List<String> userCardIds,String longName){
		
		DodopalResponse<String> rtResponse = new DodopalResponse<String>();
		try {
			rtResponse = merUserDelegate.unbundUserCards(operName,userCardIds, longName);			
		}catch(HessianRuntimeException e){
		    log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
		}catch (Exception e) {
		    log.debug("MerUserServiceImpl call error", e);
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
			// throw new DDPException(ResponseCode.SYSTEM_ERROR);
		}
		return rtResponse;
	}


	@Override
	public DodopalResponse<String> activateMechant(MerchantUserBean merUserBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DodopalResponse<String> editMerchant(MerchantUserBean merUserBean) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public DodopalResponse<Boolean> updateMerUser(MerchantUserBean merUserBean) {

        DodopalResponse<Boolean> rtResponse = new DodopalResponse<Boolean>();
        MerchantUserDTO userDTO = new MerchantUserDTO();
        try {
            PropertyUtils.copyProperties(userDTO, merUserBean);
            rtResponse = merUserDelegate.updateUser(userDTO);            
        }catch(HessianRuntimeException e){
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
            // throw new DDPException(ResponseCode.SYSTEM_ERROR);
        }
        return rtResponse;
    }

    @Override
    public DodopalResponse<Map<String,String>> startOrStopUser(List<String> idList, String activate,String updateUser) {
        DodopalResponse<Map<String,String>> rtResponse = new DodopalResponse<Map<String,String>>();
        try {
            rtResponse = merUserDelegate.startOrStop(idList, ActivateEnum.getActivateByCode(activate),updateUser);            
        }catch(HessianRuntimeException e){
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return rtResponse;
    }

    @Override
    public DodopalResponse<DodopalDataPage<MerchantUserBean>> findMerUsersByPage(MerchantUserQuery userQuery) {
        DodopalResponse<DodopalDataPage<MerchantUserBean>> response = new DodopalResponse<DodopalDataPage<MerchantUserBean>>();
        MerchantUserQueryDTO queryDto = new MerchantUserQueryDTO();
       
        try {
            PropertyUtils.copyProperties(queryDto, userQuery);
            DodopalResponse<DodopalDataPage<MerchantUserDTO>>  dtoPageRes  = merUserDelegate.findMerUsersByPage(queryDto); 
            
            if (ResponseCode.SUCCESS.equals(dtoPageRes.getCode())) {
                List<MerchantUserBean> resResult = new ArrayList<MerchantUserBean>();
                MerchantUserBean merUserBean = null;
                for (MerchantUserDTO merUserDTO : dtoPageRes.getResponseEntity().getRecords()) {
                    merUserBean = new MerchantUserBean();
                    PropertyUtils.copyProperties(merUserBean, merUserDTO);
                    resResult.add(merUserBean);
                }
//              //后台分页的总页数与count                
                PageParameter page = DodopalDataPageUtil.convertPageInfo(dtoPageRes.getResponseEntity());
	            DodopalDataPage<MerchantUserBean> pages = new DodopalDataPage<MerchantUserBean>(page, resResult);
	            response.setCode(dtoPageRes.getCode());
	            response.setResponseEntity(pages);
            }else{
                response.setCode(dtoPageRes.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
        }
        return response;
    }

	//卡片操作日志查询
	public DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> findUserCardLogByPage(UserCardLogQuery dto) {
		DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> response = new DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>>();
		UserCardLogQueryDTO queryDTO = new UserCardLogQueryDTO();
		try {
			PropertyUtils.copyProperties(queryDTO, dto);
			
			DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> dodopalResponse = merUserDelegate.findUserCardLogByPage(queryDTO);
			if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
				List<MerUserCardBDLogBean> list = new ArrayList<MerUserCardBDLogBean>();
				for(MerUserCardBDLogDTO logDTO : dodopalResponse.getResponseEntity().getRecords()){
					MerUserCardBDLogBean logBean = new MerUserCardBDLogBean();
					PropertyUtils.copyProperties(logBean, logDTO);
					list.add(logBean);
				}
				//后台分页的总页数与count                
				PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
				DodopalDataPage<MerUserCardBDLogBean> pages = new DodopalDataPage<MerUserCardBDLogBean>(page, list);
				response.setCode(dodopalResponse.getCode());
				response.setResponseEntity(pages);
			}else{
				response.setCode(dodopalResponse.getCode());
			}
			
		}catch(HessianRuntimeException e){
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
		catch (Exception e) {
			   log.debug("MerUserServiceImpl call error", e);
		       e.printStackTrace();
		}
		return response;
	}
	@Override
    public List<MerchantUserBean> getExportExcelMerUserList(MerchantUserQuery userQuery) {
	    List<MerchantUserBean> resResult = new ArrayList<MerchantUserBean>();
	    MerchantUserQueryDTO queryDto = new MerchantUserQueryDTO();
        try {
           PropertyUtils.copyProperties(queryDto, userQuery);
           List<MerchantUserDTO> dtoPageRes = merUserDelegate.getExportExcelMerUserList(queryDto); 
           if(dtoPageRes.size()>0){
               MerchantUserBean merUserBean = null;
               for ( MerchantUserDTO merUserDTO : dtoPageRes) {
                   merUserBean = new MerchantUserBean();
                   PropertyUtils.copyProperties(merUserBean, merUserDTO);
                   if(merUserDTO.getIncome() != null){
                       merUserBean.setIncome(merUserDTO.getIncome()/100);
                   }
                   resResult.add(merUserBean);
               }
           }
        }catch(Exception e) {
            log.error("导出查询出错", e);
            e.printStackTrace();
        }
        return resResult;
    }
	
	 @Override
    public List<MerUserCardBDBean> getExportExcelMerUserCardList(MerUserCardBDFindBean findBean) {
        List<MerUserCardBDBean> resResult = new ArrayList<MerUserCardBDBean>();
        try {
            MerUserCardBDDTOQuery findDto = new MerUserCardBDDTOQuery();
            PropertyUtils.copyProperties(findDto, findBean);
           List<MerUserCardBDDTO> getResponse = merUserDelegate.getExportExcelMerUserCardList(findDto);
           if(getResponse.size()>0){
               MerUserCardBDBean retBean = null;
               for (MerUserCardBDDTO retDTO : getResponse) {
                   retBean = new MerUserCardBDBean();
                   PropertyUtils.copyProperties(retBean, retDTO);
                   resResult.add(retBean);
               }
           }
        }catch(Exception e) {
            log.error("导出查询出错", e);
            e.printStackTrace();
        }
        return resResult;
    }
	 
   @Override
    public List<MerUserCardBDLogBean> getExportExcelUserCardLog(UserCardLogQuery dto) {
        List<MerUserCardBDLogBean> resResult = new ArrayList<MerUserCardBDLogBean>();
        try {
            UserCardLogQueryDTO queryDTO = new UserCardLogQueryDTO();
            PropertyUtils.copyProperties(queryDTO, dto);
           List<MerUserCardBDLogDTO> dodopalResponse = merUserDelegate.getExportExcelUserCardLog(queryDTO);
           if(dodopalResponse.size()>0){
              // List<MerUserCardBDLogBean> list = new ArrayList<MerUserCardBDLogBean>();
               for(MerUserCardBDLogDTO logDTO : dodopalResponse){
                   MerUserCardBDLogBean logBean = new MerUserCardBDLogBean();
                   PropertyUtils.copyProperties(logBean, logDTO);
                   resResult.add(logBean);
               }
           }
        }catch(Exception e) {
            log.error("导出查询出错", e);
            e.printStackTrace();
        }
        return resResult;
    }
   
   @Override
   public DodopalResponse<DodopalDataPage<MerchantUserExpBean>> findMerUsersExpByPage(MerchantUserQuery userQuery) {
       DodopalResponse<DodopalDataPage<MerchantUserExpBean>> response = new DodopalResponse<DodopalDataPage<MerchantUserExpBean>>();
       MerchantUserQueryDTO queryDto = new MerchantUserQueryDTO();
      
       try {
           PropertyUtils.copyProperties(queryDto, userQuery);
           DodopalResponse<DodopalDataPage<MerchantUserDTO>>  dtoPageRes  = merUserDelegate.findMerUsersByPage(queryDto); 
           
           if (ResponseCode.SUCCESS.equals(dtoPageRes.getCode())) {
               List<MerchantUserExpBean> resResult = new ArrayList<MerchantUserExpBean>();
               
               for (MerchantUserDTO merUserDTO : dtoPageRes.getResponseEntity().getRecords()) {
                   MerchantUserExpBean merUserBean =  new MerchantUserExpBean();
                   PropertyUtils.copyProperties(merUserBean, merUserDTO);
                   resResult.add(merUserBean);
               }
//             //后台分页的总页数与count                
               PageParameter page = DodopalDataPageUtil.convertPageInfo(dtoPageRes.getResponseEntity());
               DodopalDataPage<MerchantUserExpBean> pages = new DodopalDataPage<MerchantUserExpBean>(page, resResult);
               response.setCode(dtoPageRes.getCode());
               response.setResponseEntity(pages);
           }else{
               response.setCode(dtoPageRes.getCode());
           }
           
       }catch(HessianRuntimeException e){
           log.debug("MerUserServiceImpl call error", e);
           e.printStackTrace();
           response.setCode(ResponseCode.HESSIAN_ERROR);
       }
       catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
           log.debug("MerUserServiceImpl call error", e);
           e.printStackTrace();
       }
       return response;
   }
}

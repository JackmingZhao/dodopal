package com.dodopal.users.business.facadeImpl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerGroupUserDTO;
import com.dodopal.api.users.dto.MerGroupUserFindDTO;
import com.dodopal.api.users.dto.PosLogDTO;
import com.dodopal.api.users.dto.query.MerGroupUserQueryDTO;
import com.dodopal.api.users.facade.MerGroupUserFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.EmpTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.users.business.constant.UsersConstants;
import com.dodopal.users.business.model.MerGroupDepartment;
import com.dodopal.users.business.model.MerGroupUser;
import com.dodopal.users.business.model.MerGroupUserFind;
import com.dodopal.users.business.service.MerGroupDepartmentService;
import com.dodopal.users.business.service.MerGroupUserService;


@Service("merGroupUserFacade")
public class MerGroupUserFacadeImpl implements MerGroupUserFacade {

//	private Logger logger = Logger.getLogger(MerGroupUserFacadeImpl.class);

	@Autowired
	private MerGroupUserService merGroupUserService;
	
	@Autowired
	private MerGroupDepartmentService merGpDepService;
	
	@Override
	public DodopalResponse<List<MerGroupUserDTO>> findMerGpUsers(MerGroupUserFindDTO findDTO,String findFlg) {
		MerGroupUserFind findBean = new MerGroupUserFind();
		List<MerGroupUserDTO> dtoList = new ArrayList<MerGroupUserDTO>();
		DodopalResponse<List<MerGroupUserDTO>> response =new DodopalResponse<List<MerGroupUserDTO>>();
		if(findDTO==null){
			findDTO =new MerGroupUserFindDTO();
		}
		//查询来源
		if(DDPStringUtil.isNotPopulated(findFlg)){
			response.setCode(ResponseCode.USERS_FIND_FLG_ERR);
			return response;
		}else{
			if(UsersConstants.FIND_WEB.equals(findFlg)){
				//商户号
				if(DDPStringUtil.isNotPopulated(findDTO.getMerCode())){
					response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
					return response;
				}
			}
		}
		
		try {
	            PropertyUtils.copyProperties(findBean, findDTO);
	            List<MerGroupUser> gpUserList =merGroupUserService.findMerGpUsers(findBean);
	            if(gpUserList.size()>0){	            	
	                for(MerGroupUser itme:gpUserList){
	                	MerGroupUserDTO gpUserDto = new MerGroupUserDTO();
	                	itme.setRechargeAmount(itme.getRechargeAmount()/100);
	                PropertyUtils.copyProperties(gpUserDto,itme);
	                dtoList.add(gpUserDto);
	                }	                
	            }
	            response.setResponseEntity(dtoList);
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
	public DodopalResponse<DodopalDataPage<MerGroupUserDTO>> findMerGpUsersByPage(MerGroupUserQueryDTO findDTO,SourceEnum source) {
		MerGroupUserFind findBean = new MerGroupUserFind();
		List<MerGroupUserDTO> dtoList = new ArrayList<MerGroupUserDTO>();
		DodopalResponse<DodopalDataPage<MerGroupUserDTO>> response =new DodopalResponse<DodopalDataPage<MerGroupUserDTO>>();
		if(findDTO==null){
			findDTO =new MerGroupUserQueryDTO();
		}
		//查询来源
		if(null == source){
			response.setCode(ResponseCode.USERS_FIND_FLG_ERR);
			return response;
		}else{
			if(source == SourceEnum.PORTAL){
				//商户号
				if(DDPStringUtil.isNotPopulated(findDTO.getMerCode())){
					response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
					return response;
				}
			}
		}
		
		try {
	            PropertyUtils.copyProperties(findBean, findDTO);
	            List<MerGroupUser> gpUserList =merGroupUserService.findMerGpUsersByPage(findBean);
	            if(gpUserList.size()>0){	            	
	                for(MerGroupUser itme:gpUserList){
	                	MerGroupUserDTO gpUserDto = new MerGroupUserDTO();
	                	itme.setRechargeAmount(itme.getRechargeAmount()/100);
		                PropertyUtils.copyProperties(gpUserDto,itme);
		                dtoList.add(gpUserDto);
	                }	                
	            }
	            PageParameter page = findBean.getPage();   
	            DodopalDataPage<MerGroupUserDTO> pages = new DodopalDataPage<MerGroupUserDTO>(page,dtoList);
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
	public DodopalResponse<MerGroupUserDTO> findMerGpUserById(String gpUserId) {
		DodopalResponse<MerGroupUserDTO> response = new DodopalResponse<MerGroupUserDTO> ();
		//用户IDcheck
		if(DDPStringUtil.isNotPopulated(gpUserId)){
			response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
			return response;
		}
		
		MerGroupUserDTO userDTO = new MerGroupUserDTO();
		MerGroupUser user = new MerGroupUser();
		
        try {
        	//查询用户详细信息
            user = merGroupUserService.findMerGpUserById(gpUserId);
            if(null!=user){
            	user.setRechargeAmount(user.getRechargeAmount()/100);
                PropertyUtils.copyProperties(userDTO,user);
                response.setCode(ResponseCode.SUCCESS);
            }else{
                userDTO = null;
                response.setCode(ResponseCode.USERS_FIND_USER_ERR);
            }
            response.setResponseEntity(userDTO);
        }catch(Exception e) {
        	response.setCode(ResponseCode.SYSTEM_ERROR);
        	e.printStackTrace();
            //throw new RuntimeException(e);
        }        
        return response;
	}

	@Override
	public DodopalResponse<String> saveMerGpUsers(List<MerGroupUserDTO> gpUserDtos) {
		DodopalResponse<String> response = new DodopalResponse<String> ();		
		MerGroupUser user = new MerGroupUser();
		List<MerGroupUser> userList =new ArrayList<MerGroupUser>();		
		if(null==gpUserDtos){
			response.setCode(ResponseCode.USERS_MER_USER_NULL);
			return response;
		}
        try {
        	for(MerGroupUserDTO itme:gpUserDtos){
        		//用户姓名
        		if(DDPStringUtil.isNotPopulated(itme.getGpUserName())
        		   //公交卡号
        		   // || DDPStringUtil.isNotPopulated(itme.getCardCode())
        		   //部门ID
        		   || DDPStringUtil.isNotPopulated(itme.getDepName())
        		   //商户号
        		   || DDPStringUtil.isNotPopulated(itme.getMerCode())
        		   //充值金额
        		   || 0 == itme.getRechargeAmount()){
        			
        			continue;
        		}       		
        		//过滤掉已存在的公交卡号
        		if(DDPStringUtil.isPopulated(itme.getCardCode())){
        			MerGroupUserFind findBean = new MerGroupUserFind();
            		findBean.setMerCode(itme.getMerCode());
            		findBean.setCardCode(itme.getCardCode());        		
            		if(merGroupUserService.getMerGpUserCount(findBean)>0){
            			continue;
            		}
        		}        		
        		//检测部门
        		MerGroupDepartment dep = new MerGroupDepartment();
        		dep.setDepName(itme.getDepName());
        		dep.setMerCode(itme.getMerCode());
        		List<MerGroupDepartment> depTemp=merGpDepService.findMerGpDepByMerCodeAndDeptName(dep);
        		//检测不到
        		if(depTemp==null || depTemp.size()==0){
        			//创建部门
        			merGpDepService.saveMerGroupDepartment(dep);        			
        			if(!DDPStringUtil.isNotPopulated(dep.getId())){
        				itme.setDepId(dep.getId());
        			}else{
        				continue;
        			}
        		}else{
        			//检测到取得部门ID
        			itme.setDepId(depTemp.get(0).getId());
        		}
        		user = new MerGroupUser();
        		itme.setRechargeAmount(itme.getRechargeAmount()*100);
        		PropertyUtils.copyProperties(user,itme);
        		userList.add(user);
        	}        	
        	merGroupUserService.insertMerGpUsers(userList); 
        	response.setCode(ResponseCode.SUCCESS);
        }catch(Exception e) {
        	response.setCode(ResponseCode.SYSTEM_ERROR);
        	e.printStackTrace();
            //throw new RuntimeException(e);
        }        
        return response;
		
		
	}
	
	@Override
	public DodopalResponse<String> saveMerGpUser(MerGroupUserDTO gpUserDto) {
		DodopalResponse<String> response = new DodopalResponse<String> ();		
		MerGroupUser user = new MerGroupUser();
		if(null==gpUserDto){
			response.setCode(ResponseCode.USERS_MER_USER_NULL);
			return response;
		}
        try {
        		//用户姓名
        		if(DDPStringUtil.isNotPopulated(gpUserDto.getGpUserName())
        		   //公交卡号
        		   //|| DDPStringUtil.isNotPopulated(gpUserDto.getCardCode())
        		   //部门ID
        		   || DDPStringUtil.isNotPopulated(gpUserDto.getDepId())
        		   //商户号
        		   || DDPStringUtil.isNotPopulated(gpUserDto.getMerCode())
        		   //充值金额
        		   || 0 == gpUserDto.getRechargeAmount()){
        			response.setCode(ResponseCode.USERS_MER_USER_INFO_NULL);
        			return response;
        		}
      	        if(DDPStringUtil.isPopulated(gpUserDto.getCardCode())){
      	        //检查该商户下，公交卡号的唯一约束
            		MerGroupUserFind findBean = new MerGroupUserFind();
            		findBean.setMerCode(gpUserDto.getMerCode());
            		findBean.setCardCode(gpUserDto.getCardCode());
            		
            		if(merGroupUserService.getMerGpUserCount(findBean)>0){
            			response.setCode(ResponseCode.USERS_CARD_CODE_EXIST);
            			return response;
            		}
      	        }
        		
        		
        	PropertyUtils.copyProperties(user,gpUserDto);
        	user.setRechargeAmount(user.getRechargeAmount()*100);
        	merGroupUserService.insertMerGpUser(user); 
        	response.setCode(ResponseCode.SUCCESS);
        }catch(Exception e) {
        	response.setCode(ResponseCode.SYSTEM_ERROR);
        	e.printStackTrace();
            //throw new RuntimeException(e);
        }        
        return response;
		
		
	}
	
    /**
     * 检测公交卡号是否重复
     * 
     * @param merCode 商户code
     * @param cardCode 卡号
     * @param gpUserId 集团用户ID：新增时为null，
     * @return
     */
	public DodopalResponse<String> checkCardCode(String merCode,String cardCode,String gpUserId){
		DodopalResponse<String> response = new DodopalResponse<String> ();	
		response.setCode(ResponseCode.SUCCESS);
		//判读是否重复
    	if(DDPStringUtil.isPopulated(cardCode)){
   	        //检查该商户下，公交卡号的唯一约束
         		MerGroupUserFind findBean = new MerGroupUserFind();
         		findBean.setMerCode(merCode);
         		findBean.setCardCode(cardCode);
         		MerGroupUser userTemp = merGroupUserService.getMerGpUserByCarCode(findBean);
         		if(DDPStringUtil.isPopulated(gpUserId)){
         			if(userTemp !=null && DDPStringUtil.isPopulated(userTemp.getId()) && !userTemp.getId().equals(gpUserId)){
             			response.setCode(ResponseCode.USERS_CARD_CODE_EXIST);
             			return response;
             		}
         		}else {
         			if(userTemp !=null && DDPStringUtil.isPopulated(userTemp.getId())){
             			response.setCode(ResponseCode.USERS_CARD_CODE_EXIST);
             			return response;
             		}
         			
         		}
   	        } 
    	return response;
	}

	@Override
	public DodopalResponse<Integer> updateMerGpUser(MerGroupUserDTO gpUserDto) {
		DodopalResponse<Integer> response = new DodopalResponse<Integer> ();		
		MerGroupUser user = new MerGroupUser();
		if(null==gpUserDto){
			response.setCode(ResponseCode.USERS_MER_USER_NULL);
			return response;
		}
        try {
        	if(DDPStringUtil.isNotPopulated(gpUserDto.getId())){
        		response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
    			return response;
        	}
        	//用户姓名
    		if(DDPStringUtil.isNotPopulated(gpUserDto.getGpUserName())
    		   //公交卡号
    		   //|| DDPStringUtil.isNotPopulated(gpUserDto.getCardCode())
    		   //部门ID
    		   || DDPStringUtil.isNotPopulated(gpUserDto.getDepId())
    		   //商户号
    		   || DDPStringUtil.isNotPopulated(gpUserDto.getMerCode())
    		   //充值金额
    		   || 0 == gpUserDto.getRechargeAmount()){
    			response.setCode(ResponseCode.USERS_MER_USER_INFO_NULL);
    			return response;
    		}
        	//判读是否重复
        	if(DDPStringUtil.isPopulated(gpUserDto.getCardCode()) && EmpTypeEnum.EMP.getCode().equals(gpUserDto.getEmpType())){
       	        //检查该商户下，公交卡号的唯一约束
             		MerGroupUserFind findBean = new MerGroupUserFind();
             		findBean.setMerCode(gpUserDto.getMerCode());
             		findBean.setCardCode(gpUserDto.getCardCode());
             		MerGroupUser userTemp = merGroupUserService.getMerGpUserByCarCode(findBean);
             		if(userTemp !=null && DDPStringUtil.isPopulated(userTemp.getId()) && !userTemp.getId().equals(gpUserDto.getId())){
             			response.setCode(ResponseCode.USERS_CARD_CODE_EXIST);
             			return response;
             		}
       	        }        	 
        	PropertyUtils.copyProperties(user,gpUserDto);
        	user.setRechargeAmount(user.getRechargeAmount()*100);
        	Integer row =merGroupUserService.updateMerGpUser(user);
        	response.setResponseEntity(row);
        	response.setCode(ResponseCode.SUCCESS);
        }catch(Exception e) {
        	response.setCode(ResponseCode.SYSTEM_ERROR);
        	e.printStackTrace();
            //throw new RuntimeException(e);
        }        
        return response;
	}

	@Override
	public DodopalResponse<Integer> deleteMerGpUser(String gpUserId) {
		
		//TODO 该人员的公交卡是否有充值记录，则提示用户不能删除。 充值记录待开发
		DodopalResponse<Integer> response = new DodopalResponse<Integer> ();
		//用户IDcheck
		if(DDPStringUtil.isNotPopulated(gpUserId)){
			response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
			return response;
		}
		try{
			Integer row = merGroupUserService.deleteMerGpUser(gpUserId);
			response.setResponseEntity(row);
        	response.setCode(ResponseCode.SUCCESS);
		}catch(Exception e){
			response.setCode(ResponseCode.SYSTEM_ERROR);
        	e.printStackTrace();
            //throw new RuntimeException(e);
		}
		return response;
	}
	
}

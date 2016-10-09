package com.dodopal.portal.business.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dodopal.api.users.dto.MerGroupUserDTO;
import com.dodopal.api.users.dto.MerGroupUserFindDTO;
import com.dodopal.api.users.dto.query.MerGroupUserQueryDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.common.validator.DDPValidationHandler;
import com.dodopal.common.validator.DdpValidationBoxEnum;
import com.dodopal.portal.business.bean.MerGroupUserBean;
import com.dodopal.portal.business.bean.MerGroupUserQueryBean;
import com.dodopal.portal.business.constant.PortalConstants;
import com.dodopal.portal.business.constant.PortalGroupUserConstant;
import com.dodopal.portal.business.service.MerGroupUserService;
import com.dodopal.portal.delegate.MerGroupUserDelegate;

@Service("merGroupUserServiceImpl")
public class MerGroupUserServiceImpl implements MerGroupUserService {

	@Autowired
	private MerGroupUserDelegate delegate;
	
	@Override
	public DodopalResponse<List<MerGroupUserBean>> findMerGroupUserDTOList(MerGroupUserQueryBean bean) {
		MerGroupUserFindDTO findDTO = new MerGroupUserFindDTO();
		findDTO.setCardCode(bean.getCardCode());
		findDTO.setMerCode(bean.getMerCode());
		findDTO.setDepId(bean.getDepId());
		findDTO.setGpUserName(bean.getGpUserName());
		findDTO.setEmpType(bean.getEmpType());

		DodopalResponse<List<MerGroupUserDTO>> rep = delegate.findMerGpUsers(findDTO, PortalConstants.FIND_WEB);
		DodopalResponse<List<MerGroupUserBean>> response = new DodopalResponse<List<MerGroupUserBean>>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			List<MerGroupUserBean> beans = new ArrayList<MerGroupUserBean>();
			for (MerGroupUserDTO d : rep.getResponseEntity()) {
				MerGroupUserBean b = new MerGroupUserBean();

				try {
					BeanUtils.copyProperties(b, d);
				}
				catch (Exception e) {
					e.printStackTrace();
					response.setMessage(e.getMessage());
					response.setCode(ResponseCode.SYSTEM_ERROR);
					return response;
				}

				beans.add(b);
			}
			response.setCode(rep.getCode());
			response.setResponseEntity(beans);
		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}

		return response;
	}

	@Override
	public DodopalResponse<DodopalDataPage<MerGroupUserBean>> findMerGpUsersByPage(MerGroupUserQueryBean findDTO, SourceEnum source) {
		MerGroupUserQueryDTO queryQueryDTO = new MerGroupUserQueryDTO();
		queryQueryDTO.setCardCode(findDTO.getCardCode());
		queryQueryDTO.setDepId(findDTO.getDepId());
		queryQueryDTO.setEmpType(findDTO.getEmpType());
		queryQueryDTO.setGpUserName(findDTO.getGpUserName());
		queryQueryDTO.setMerCode(findDTO.getMerCode());
		queryQueryDTO.setPage(findDTO.getPage());
		
		DodopalResponse<DodopalDataPage<MerGroupUserDTO>> rep = delegate.findMerGpUsersByPage(queryQueryDTO, source);
		DodopalResponse<DodopalDataPage<MerGroupUserBean>> response = new DodopalResponse<DodopalDataPage<MerGroupUserBean>>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			List<MerGroupUserBean> beans = new ArrayList<MerGroupUserBean>();
			PageParameter page = new PageParameter(rep.getResponseEntity().getPageNo(),rep.getResponseEntity().getPageSize());

			for(MerGroupUserDTO dto : rep.getResponseEntity().getRecords()){
				MerGroupUserBean b = new MerGroupUserBean();
				try {
					BeanUtils.copyProperties(b, dto);
				}	catch (Exception e) {
					e.printStackTrace();
					response.setMessage(e.getMessage());
					response.setCode(ResponseCode.SYSTEM_ERROR);
					return response;
				}	
				
				beans.add(b);
			}
			
			DodopalDataPage<MerGroupUserBean> dataPage = new DodopalDataPage<MerGroupUserBean>(page, beans);
			dataPage.setRows(rep.getResponseEntity().getRows());
			dataPage.setTotalPages(rep.getResponseEntity().getTotalPages());
			
			response.setResponseEntity(dataPage);
			response.setCode(rep.getCode());
		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}

		return response;
	}

	@Override
	public DodopalResponse<String> saveMerGroupUserDTOList(MerGroupUserBean merDep) {

		MerGroupUserDTO dto = new MerGroupUserDTO();
		DodopalResponse<String> response = new DodopalResponse<String>();
		try {
			BeanUtils.copyProperties(dto, merDep);
		}	catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.SYSTEM_ERROR);
			response.setResponseEntity(CommonConstants.FAILURE);
			return response;
		}

		DodopalResponse<String> rep = delegate.saveMerGpUser(dto);
		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			response.setResponseEntity(CommonConstants.SUCCESS);
			response.setCode(ResponseCode.SUCCESS);
		} else {
			response.setResponseEntity(CommonConstants.FAILURE);
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}
		
		return response;

	}

	@Override
	public DodopalResponse<MerGroupUserBean> findMerGroupUserById(String Id) {

		DodopalResponse<MerGroupUserDTO> rep = delegate.findMerGpUserById(Id);
		DodopalResponse<MerGroupUserBean> response = new DodopalResponse<MerGroupUserBean>();

		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			response.setCode(rep.getCode());
			MerGroupUserBean bean = new MerGroupUserBean();
			try {
				BeanUtils.copyProperties(bean, rep.getResponseEntity());
				response.setResponseEntity(bean);
			}
			catch (Exception e) {
				e.printStackTrace();
				response.setMessage(e.getMessage());
				response.setCode(ResponseCode.SYSTEM_ERROR);
				return response;
			}

		} else {
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}

		return response;
	}

	@Override
	public DodopalResponse<String> deleteMerGroupUser(String id) {
		DodopalResponse<Integer> rep = delegate.deleteMerGpUser(id);
		DodopalResponse<String> response = new DodopalResponse<String>();
		
		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			response.setResponseEntity(CommonConstants.SUCCESS);
			response.setCode(ResponseCode.SUCCESS);
		} else {
			response.setResponseEntity(CommonConstants.FAILURE);
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}
		
		return response;
	}

	@Override
	public DodopalResponse<String> updateMerGroupUser(MerGroupUserBean bean) {
		MerGroupUserDTO dto = new MerGroupUserDTO();
		DodopalResponse<String> response = new DodopalResponse<String>();
		
		try {
			BeanUtils.copyProperties(dto,bean);
		}	catch (Exception e) {
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setCode(ResponseCode.SYSTEM_ERROR);
			return response;
		}	
		
		DodopalResponse<Integer> rep = delegate.updateMerGpUser(dto);
		
		if (ResponseCode.SUCCESS.equals(rep.getCode())) {
			response.setResponseEntity(CommonConstants.SUCCESS);
			response.setCode(ResponseCode.SUCCESS);
		} else {
			response.setResponseEntity(CommonConstants.FAILURE);
			response.setCode(rep.getCode());
			response.setMessage(rep.getMessage());
		}
		
		return response;
	}

	@Override
	public DodopalResponse<String> importMerGpUsers(CommonsMultipartFile file,String merCode) {

		DodopalResponse<String> response = new DodopalResponse<String>();
		if (file != null && !file.isEmpty()) {
			// 文件上传大小
			long fileSize = 10 * 1024 * 1024;
			if (file.getSize() > fileSize) {
				response.setCode(ResponseCode.PORTAL_MERGPUSER_BATCH_UPLOAD_OVER_MAXSIZE);
				return response;
			}
			String OriginalFilename = file.getOriginalFilename();
			String fileSuffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.asList(new String[] {"xls", "xlsx"}).contains(fileSuffix)) {
				response.setCode(ResponseCode.PORTAL_MERGPUSER_BATCH_UPLOAD_INCORRECT_FILE);
				response.setMessage("文件格式有误，上传无效文档");
				return response;
			}

			List<MerGroupUserDTO> groupUsers = parseGroupUsers(file.getBytes(),merCode);
			
			if(!validateGroupUsers(groupUsers,response)){
				return response;
			}
			
			if(CollectionUtils.isEmpty(groupUsers)){
				response.setCode(ResponseCode.PORTAL_MERGPUSER_BATCH_UPLOAD_FILE_EMPTY);
				response.setMessage("文件格式有误，上传无效文档");
			}else{
				DodopalResponse<String> rep = delegate.saveMerGpUsers(groupUsers);
				if(ResponseCode.SUCCESS.equals(rep.getCode())){
					response.setCode(ResponseCode.SUCCESS);
				}else{
					response.setCode(rep.getCode());
					response.setMessage(rep.getMessage());
				}
			}
			
			return response;
		} else {
			response.setCode(ResponseCode.PORTAL_MERGPUSER_BATCH_UPLOAD_FILE_EMPTY);
			response.setMessage("文件格式有误，上传无效文档");
		}
		
		return response;
	}
	
	@Override
	public DodopalResponse<String> checkCardCode(String merCode,String cardCode,String gpUserId) {
		return delegate.checkCardCode(merCode,cardCode,gpUserId);
	}
	private boolean validateGroupUsers(List<MerGroupUserDTO> groupUsers,DodopalResponse<String> rep){
		StringBuffer sb = new StringBuffer();
		boolean isValid = true;
		
		int i = 1;
		
		if(CollectionUtils.isNotEmpty(groupUsers) && groupUsers.size() > 2000){
			sb.append("最大导入数量为2000条");
			popMessage(rep,sb.toString());
			return false;
		}
		
		Map<String,String> importCards = new HashMap<String,String>();
		for(MerGroupUserDTO user : groupUsers){
			
			if(StringUtils.isNotBlank(user.getCardCode())){
				if(importCards.keySet().contains(user.getCardCode())){
					sb.append("第" + i + "行与第" + importCards.get(user.getCardCode()) + " 行卡号重复");
					isValid = false;
					
					popMessage(rep,sb.toString());
					return isValid;
				}else{
					importCards.put(user.getCardCode(), new Integer(i).toString());
				}
			}
			
			if(StringUtils.isEmpty(user.getDepName())){
				sb.append("第" + i + "行 , 部门名称不能为空");
				
				isValid = false;
				popMessage(rep,sb.toString());
				return isValid;
			}else if(!DDPValidationHandler.validate(user.getDepName(), true, 2, 20, DdpValidationBoxEnum.CN_EN_NO)){
				sb.append("第" + i + "行 , 输入的部门名称格式不正确");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}
			
			if(StringUtils.isEmpty(user.getGpUserName())){
				sb.append("第" + i + "行 , 用户姓名不能为空");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}else if(!DDPValidationHandler.validate(user.getGpUserName(), true, 2, 20, DdpValidationBoxEnum.EN_CN)){
				sb.append("第" + i + "行 , 用户姓名输入格式不正确");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}
			
			if(StringUtils.isNotBlank(user.getCardCode()) 
				&& ( !StringUtils.isNumeric(user.getCardCode()) || user.getCardCode().length() > 20 )){
				sb.append("第" + i + "行 , 公交卡号输入格式不正确");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}
			
			if(user.getRechargeAmount() == 0 || user.getRechargeAmount() % 10 != 0){
				sb.append("第" + i + "行 , 充值金额为空或不合法");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}
			
			if(StringUtils.isNotBlank(user.getCardType()) 
							&& !"CPU".equals(user.getCardType())
							&& !"M1".equals(user.getCardType())){
				sb.append("第" + i + "行 , 卡类型格式错误");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}
			
			if(StringUtils.isEmpty(user.getMobiltle())){
				sb.append("第" + i + "行 , 手机号不能为空");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}else if(!DDPValidationHandler.validate(user.getMobiltle(), true, DdpValidationBoxEnum.MOBILE)){
				sb.append("第" + i + "行 , 手机号不合法");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}
			
			if(StringUtils.isNotBlank(user.getIdentityNum()) 
				&& !DDPValidationHandler.validate(user.getIdentityNum(), true, DdpValidationBoxEnum.ID_CARD)){
				sb.append("第" + i + "行 , 身份证不合法");
				isValid = false;
				
				popMessage(rep,sb.toString());
				return isValid;
			}
			i++;
		}
		
		if(!isValid){
			rep.setCode(ResponseCode.PORTAL_MERGPUSER_BATCH_UPDATE_VALIDATE_ERROR);
			rep.setMessage(sb.toString());
		}
		
		return isValid;
	}

	private void popMessage(DodopalResponse<String> rep, String message){
		rep.setCode(ResponseCode.PORTAL_MERGPUSER_BATCH_UPDATE_VALIDATE_ERROR);
		rep.setMessage(message);
	}
	
	private List<MerGroupUserDTO> parseGroupUsers(byte[] bytes,String merCode) {

		List<MerGroupUserDTO> userList = new ArrayList<MerGroupUserDTO>();
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			Workbook wb = WorkbookFactory.create(in);
			Sheet sheet = wb.getSheetAt(0);
			for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				
				MerGroupUserDTO dto = new MerGroupUserDTO();
				
				String depName = ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_DEP_NAME));
				String gpName = ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_GP_USER_NAME));
				String cardCode= ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_CARD_CODE));
				String cardType = ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_CARD_TYPE));
				String rechargeAmt = ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_RECHARGE_AMT));
				String mobile = ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_MOBILE));
				String phone = ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_PHONE));
				String idNum  = ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_IDENTITY_NUM));
				Date employeeDate = null;
				try{
					employeeDate = ExcelUtil.getDateValue(row.getCell(PortalGroupUserConstant.COL_CODE_EMPLOYEE_DATE));
				}catch(DDPException e){
					throw new DDPException("importGroupUser.employeeDate.error", "第"+(rowNum - 1)+"行日期格式出错", e);
				}
				String remark = ExcelUtil.getValue(row.getCell(PortalGroupUserConstant.COL_CODE_REMARK));
				
				if(StringUtils.isEmpty(depName) 
							&& StringUtils.isEmpty(gpName) 
							&& StringUtils.isEmpty(cardCode) 
							&& StringUtils.isEmpty(cardType)
							&& StringUtils.isEmpty(rechargeAmt)
							&& StringUtils.isEmpty(mobile)
							&& StringUtils.isEmpty(phone)
							&& StringUtils.isEmpty(idNum)){
					continue;
				}
				
				dto.setDepName(depName);
				dto.setGpUserName(gpName);
				dto.setCardCode(cardCode);
				dto.setCardType(cardType);
				dto.setMerCode(merCode);
				if(StringUtils.isNotBlank(rechargeAmt) ){
					try{
						dto.setRechargeAmount(Long.valueOf(rechargeAmt));
					}catch(Exception e){
						e.printStackTrace();
						throw new DDPException("importGroupUser.rechargeAmt.error", "第"+(rowNum - 1)+"行充值金额出错", e);
					}
				}
				dto.setMobiltle(mobile);
				dto.setPhone(phone);
				dto.setIdentityNum(idNum);
				dto.setEmployeeDate(employeeDate);
				dto.setRechargeWay("0");
				dto.setRemark(remark);
				
				userList.add(dto);
			}
			
			return userList;
		}	catch(DDPException de){
			throw de;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DDPException("importGroupUser.error", "导入文件出错", e);
		}

	}

}

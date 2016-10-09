package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianConnectionException;
import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.PosBean;
import com.dodopal.portal.business.bean.PosOperationBean;
import com.dodopal.portal.business.bean.query.PosQuery;
import com.dodopal.portal.business.service.PosService;
import com.dodopal.portal.delegate.PosDelegate;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年6月24日 上午10:33:22
 */
@Service("posServiceImpl")
public class PosServiceImpl implements PosService{
    private final static Logger log = LoggerFactory.getLogger(PosServiceImpl.class);

    @Autowired
    private PosDelegate posDelegate;
    /**
     * POS操作 绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param operUser 操作员信息
     * @return
     */
    @Override
    public DodopalResponse<String> posOper(PosOperationBean operation, MerchantUserBean user) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        if (user == null) {
            response.setCode(ResponseCode.LOGIN_USER_NULL);
        } else if (operation == null) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        } else if (null==operation.getOperation()) {
            response.setCode(ResponseCode.OSS_POS_OPERATE_EMPTY);
        } else if (PosOperTypeEnum.OPER_BUNDLING.name().equals(operation.getOperation()) && (DDPStringUtil.isNotPopulated(operation.getMerCode()) || "sel".equalsIgnoreCase(operation.getMerCode()))) {
            response.setCode(ResponseCode.OSS_MER_NULL);
        } else if (operation.getPos() == null || operation.getPos().length<=0) {
            response.setCode(ResponseCode.OSS_POS_OPERATE_EMPTY);
        } else {
            OperUserDTO operUser = new OperUserDTO();
            operUser.setOperName(user.getMerUserName());
            operUser.setId(user.getId());
            response = posDelegate.posOper(operation.getOperation(), operation.getMerCode(), operation.getPos(), operUser, operation.getRemarks());
        }
        return response;
    }
    @Override
    public DodopalResponse<DodopalDataPage<PosBean>> findPosListByPage(PosQuery posQuery) {
        PosQueryDTO posDTO = new PosQueryDTO();
        DodopalResponse<DodopalDataPage<PosBean>>  response = new DodopalResponse<DodopalDataPage<PosBean>>();
        try {
            List<PosBean> resResult = new ArrayList<PosBean>();
            PropertyUtils.copyProperties(posDTO, posQuery);
            DodopalResponse<DodopalDataPage<PosDTO>>  dodopalResponse = posDelegate.findPosListPage(posDTO);
            if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
                for(PosDTO dto:dodopalResponse.getResponseEntity().getRecords()){
                    PosBean bean = new PosBean();
                    PropertyUtils.copyProperties(bean, dto);
                    resResult.add(bean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
                DodopalDataPage<PosBean> pages = new DodopalDataPage<PosBean>(page, resResult);
                response.setResponseEntity(pages);
            }
            response.setCode(dodopalResponse.getCode());
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            log.error("PosServiceImpl findPosListByPage throws:", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }catch (HessianConnectionException e) {
            e.printStackTrace();
            log.error("PosServiceImpl findPosListByPage throws:" , e);
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        return response;
    }
    @Override
    public DodopalResponse<DodopalDataPage<PosBean>> findChildrenPosListByPage(PosQuery posQuery) {
        PosQueryDTO posDTO = new PosQueryDTO();
        DodopalResponse<DodopalDataPage<PosBean>>  response = new DodopalResponse<DodopalDataPage<PosBean>>();
        try {
            List<PosBean> resResult = new ArrayList<PosBean>();
            PropertyUtils.copyProperties(posDTO, posQuery);
            DodopalResponse<DodopalDataPage<PosDTO>>  dodopalResponse = posDelegate.findChildrenMerPosListPage(posDTO);
            if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
                for(PosDTO dto:dodopalResponse.getResponseEntity().getRecords()){
                    PosBean bean = new PosBean();
                    PropertyUtils.copyProperties(bean, dto);
                    resResult.add(bean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
                DodopalDataPage<PosBean> pages = new DodopalDataPage<PosBean>(page, resResult);
                response.setResponseEntity(pages);
            }
            response.setCode(dodopalResponse.getCode());
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            log.error("PosServiceImpl findChildrenPosListByPage throws:", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }catch (HessianConnectionException e) {
            e.printStackTrace();
            log.error("PosServiceImpl findChildrenPosListByPage throws:" , e);
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        return response;
    }
    
    
	@Override
	public DodopalResponse<PosBean> findPosInfoByCode(String posCode, String merCode) {
		DodopalResponse<PosBean>  response = new DodopalResponse<PosBean>();
		try {
			PosBean resResult = new PosBean();
			PosDTO posDTO = posDelegate.findPosInfoByCode(posCode, merCode).getResponseEntity();
			PropertyUtils.copyProperties(resResult, posDTO);
			response.setResponseEntity(resResult);
            response.setCode(ResponseCode.SUCCESS);
		}catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
            log.error("PosServiceImpl findChildrenPosListByPage throws:", e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}catch(HessianConnectionException e) {
			e.printStackTrace();
            log.error("PosServiceImpl findChildrenPosListByPage throws:" , e);
            response.setCode(ResponseCode.HESSIAN_ERROR);
		}
		return response;
	}
	
	@Override
	public DodopalResponse<String> savePosComments(String posCode, String comments) {
		DodopalResponse<String>  response = new DodopalResponse<String>();
		try{
			response = posDelegate.savePosComments(posCode, comments);
		} catch(HessianConnectionException e) {
			e.printStackTrace();
            log.error("PosServiceImpl findChildrenPosListByPage throws:" , e);
            response.setCode(ResponseCode.HESSIAN_ERROR);
		}
		return response;
	}
    
    
    
}

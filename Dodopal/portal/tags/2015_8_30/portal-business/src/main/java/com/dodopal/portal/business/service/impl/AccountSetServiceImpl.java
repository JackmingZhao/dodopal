package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.bean.PayWayCommonBean;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.delegate.AccountSetDelegate;

@Service
public class AccountSetServiceImpl implements AccountSetService{

	private final static Logger log = LoggerFactory.getLogger(AccountSetServiceImpl.class);

	@Autowired
	private AccountSetDelegate accountSetDelegate;
	
	/**
	 * 根据id查询
	 */
	public DodopalResponse<MerchantUserBean> findUserInfoById(String id) {
		log.info("query this id:"+id);
		DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
		//根据id获取用户信息
		DodopalResponse<MerchantUserDTO> merchantUserDTO = accountSetDelegate.findUserInfoById(id);
		try {
			if(merchantUserDTO.getCode().equals(ResponseCode.SUCCESS)){
				MerchantUserBean merchantUserBean = new MerchantUserBean();
				PropertyUtils.copyProperties(merchantUserBean, merchantUserDTO.getResponseEntity());
				response.setResponseEntity(merchantUserBean);
				response.setCode(ResponseCode.SUCCESS);
			}else{
				response.setCode(merchantUserDTO.getCode());
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			  e.printStackTrace();
              log.error("AccountSetServiceImpl findUserInfoById throws:" ,e);
              response.setCode(merchantUserDTO.getCode());
		}
		return response;
	}

	/**
	 * 修改
	 */
	public DodopalResponse<Boolean> updateAccountSetInfo(MerchantUserBean dto) {
		log.info("update AccountSetInfo start:" + dto);
		MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		try {
			PropertyUtils.copyProperties(merchantUserDTO, dto);
			DodopalResponse<Boolean> updateUser = accountSetDelegate.updateAccountSetInfo(merchantUserDTO);
			response.setResponseEntity(updateUser.getResponseEntity());
			
		} catch (Exception e) {
			log.debug("AccountSetServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		log.info("update AccountSetInfo end:" + response);
		return response;
	}

	/**
	 * 常用支付方式
	 */
	public DodopalResponse<List<PayWayBean>> findCommonPayWay(boolean ext,
			String userCode) {
        log.info("findCommonPayWay this userCode:"+userCode+",ext:"+ext);
        DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
        try {
            DodopalResponse<List<PayWayDTO>> payWayDTOList = accountSetDelegate.findCommonPayWay(ext, userCode);
            List<PayWayBean> payWayBeanList = new ArrayList<PayWayBean>();
            if (payWayDTOList.getResponseEntity() != null && payWayDTOList.getResponseEntity().size() > 0) {
                for (PayWayDTO payWayDTO : payWayDTOList.getResponseEntity()) {
                    PayWayBean payWayBean = new PayWayBean();
                    PropertyUtils.copyProperties(payWayBean, payWayDTO);
                    payWayBeanList.add(payWayBean);
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(payWayBeanList);
                
            }
        }
        catch (Exception e) {
            log.error("AccountSetServiceImpl findCommonPayWay throws: " +e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
        
    }

	/**
	 * 更多支付方式
	 */
	public DodopalResponse<List<PayWayBean>> findMorePayWay(boolean ext,
			String merCode) {
        log.info("findMorePayWay this merCode:"+merCode+",ext:"+ext);
        DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
        try {
            DodopalResponse<List<PayWayDTO>> payWayDTOList = accountSetDelegate.findMorePayWay(ext, merCode);
            List<PayWayBean> payWayBeanList = new ArrayList<PayWayBean>();
            if (payWayDTOList.getResponseEntity() != null && payWayDTOList.getResponseEntity().size() > 0) {
                for (PayWayDTO payWayDTO : payWayDTOList.getResponseEntity()) {
                    PayWayBean payWayBean = new PayWayBean();
                    PropertyUtils.copyProperties(payWayBean, payWayDTO);
                    payWayBeanList.add(payWayBean);
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(payWayBeanList);
                
            }
        }
        catch (Exception e) {
            log.error("AccountSetServiceImpl findMorePayWay throws: " +e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

	/**
	 * 新增
	 */
	public DodopalResponse<List<Integer>> insertPayWayCommon(
			List<PayWayCommonBean> commons) {
		log.info("insertPayWayCommon this commons:"+commons);
		DodopalResponse<List<Integer>> response = new DodopalResponse<List<Integer>>();
		List<PayWayCommonDTO> list = new ArrayList<PayWayCommonDTO>();
		try {
			for(PayWayCommonBean common : commons){
				PayWayCommonDTO payWayCommonDTO = new PayWayCommonDTO();
				PropertyUtils.copyProperties(payWayCommonDTO, common);
				list.add(payWayCommonDTO);
			}
			response = accountSetDelegate.insertPayWayCommon(list);
			response.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			log.error("AccountSetServiceImpl insertPayWayCommon throws: " +e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	/**
	 * 删除
	 */
	public DodopalResponse<Integer> deletePaywayCommon() {
		return accountSetDelegate.deletePaywayCommon();
	}

	/**
	 * 根据merCode查询业务城市
	 */
	public DodopalResponse<List<Area>> findMerCitys(String merCode) {
		DodopalResponse<List<Area>> merCitys = accountSetDelegate.findMerCitys(merCode);
		return merCitys;
	}

	/**
	 * 修改业务城市
	 */
	public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode,
			String updateUser) {
		DodopalResponse<Boolean> response = accountSetDelegate.updateMerUserBusCity(cityCode, updateUser);
		return response;
	}

}

package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MobileTypeWhiteListDTO;
import com.dodopal.api.users.facade.MobileTypeWhiteListFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.MobileTypeWhiteList;
import com.dodopal.users.business.service.MobileTypeWhiteListService;

/**
 * @author lifeng@dodopal.com
 */
@Service("mobileTypeWhiteListFacade")
public class MobileTypeWhiteListFacadeImpl implements MobileTypeWhiteListFacade {
	private final static Logger logger = LoggerFactory.getLogger(MobileTypeWhiteListFacadeImpl.class);
	@Autowired
	MobileTypeWhiteListService mobileTypeWhiteListService;

	@Override
	public DodopalResponse<List<MobileTypeWhiteListDTO>> findAllWhiteList() {
		DodopalResponse<List<MobileTypeWhiteListDTO>> response = new DodopalResponse<List<MobileTypeWhiteListDTO>>();
		try {
			List<MobileTypeWhiteList> list = mobileTypeWhiteListService.findAllWhiteList();
			if (CollectionUtils.isNotEmpty(list)) {
				List<MobileTypeWhiteListDTO> resultList = new ArrayList<MobileTypeWhiteListDTO>(list.size());
				for (MobileTypeWhiteList temp : list) {
					MobileTypeWhiteListDTO tempDTO = new MobileTypeWhiteListDTO();
					BeanUtils.copyProperties(temp, tempDTO);
					resultList.add(tempDTO);
				}
				response.setResponseEntity(resultList);
			}
			response.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

}

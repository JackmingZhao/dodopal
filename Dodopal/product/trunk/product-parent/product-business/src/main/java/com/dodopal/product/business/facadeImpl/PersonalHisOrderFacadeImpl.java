package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.PersonalHisOrderDTO;
import com.dodopal.api.product.dto.query.PersonalHisOrderQueryDTO;
import com.dodopal.api.product.facade.PersonalHisOrderFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.PersonalHisOrder;
import com.dodopal.product.business.model.query.PersonalHisOrderQuery;
import com.dodopal.product.business.service.PersonalHisOrderService;

/**
 * @author lifeng@dodopal.com
 */
@Service("personalHisOrderFacade")
public class PersonalHisOrderFacadeImpl implements PersonalHisOrderFacade {
	Logger logger = LoggerFactory.getLogger(PersonalHisOrderFacadeImpl.class);
	@Autowired
	private PersonalHisOrderService personalHisOrderService;

	@Override
	public DodopalResponse<DodopalDataPage<PersonalHisOrderDTO>> findRechargeOrderByPage(PersonalHisOrderQueryDTO personalHisOrderQueryDTO) {
		DodopalResponse<DodopalDataPage<PersonalHisOrderDTO>> response = new DodopalResponse<DodopalDataPage<PersonalHisOrderDTO>>();
		try {
			PersonalHisOrderQuery personalHisOrderQuery = new PersonalHisOrderQuery();
			BeanUtils.copyProperties(personalHisOrderQueryDTO, personalHisOrderQuery);
			if (personalHisOrderQuery.getPage() == null) {
				personalHisOrderQuery.setPage(new PageParameter());
			}
			
			DodopalDataPage<PersonalHisOrder> ddpResult = personalHisOrderService.findRechargeOrderByPage(personalHisOrderQuery);
			if (ddpResult != null) {
				List<PersonalHisOrder> resultList = ddpResult.getRecords();
				List<PersonalHisOrderDTO> resResultList = null;
				if (CollectionUtils.isNotEmpty(resultList)) {
					resResultList = new ArrayList<PersonalHisOrderDTO>();
					for (PersonalHisOrder order : resultList) {
						PersonalHisOrderDTO orderDTO = new PersonalHisOrderDTO();
						BeanUtils.copyProperties(order, orderDTO);
						resResultList.add(orderDTO);
					}
				}
				PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
				DodopalDataPage<PersonalHisOrderDTO> ddpDTOResult = new DodopalDataPage<PersonalHisOrderDTO>(page, resResultList);
				response.setResponseEntity(ddpDTOResult);
			}

			response.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

}

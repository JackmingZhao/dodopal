package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.api.users.dto.MobileTypeWhiteListDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.service.MobileService;
import com.dodopal.product.web.mobileBean.MobileTypeWhiteListModel;
import com.dodopal.product.web.param.BaseRequest;
import com.dodopal.product.web.param.MobileTypeWhiteListResponse;

/**
 * @author lifeng@dodopal.com
 */
@Controller
@RequestMapping("/mobile")
public class MobileController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(MobileController.class);
	@Autowired
	MobileService mobileService;

	@RequestMapping("/findAllWhiteList")
	public @ResponseBody
	MobileTypeWhiteListResponse findAllWhiteList(HttpServletRequest request) {
		MobileTypeWhiteListResponse response = new MobileTypeWhiteListResponse();
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if (StringUtils.isBlank(jsondata)) {
				if (logger.isInfoEnabled()) {
					logger.info("接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}

			// 转换jsondata
			BaseRequest baseRequest = convertJsonToRequest(jsondata, BaseRequest.class);
			// 通用参数校验
			baseCheck(baseRequest);
			// 验签

			// 业务逻辑
			DodopalResponse<List<MobileTypeWhiteListDTO>> whiteListResponse = mobileService.findAllWhiteList();
			// 响应码
			response.setRespcode(whiteListResponse.getCode());
			if (ResponseCode.SUCCESS.equals(whiteListResponse.getCode())) {
				List<MobileTypeWhiteListDTO> list = whiteListResponse.getResponseEntity();
				if (CollectionUtils.isNotEmpty(list)) {
					List<MobileTypeWhiteListModel> resultList = new ArrayList<MobileTypeWhiteListModel>();
					for (MobileTypeWhiteListDTO temp : list) {
						MobileTypeWhiteListModel tempModel = new MobileTypeWhiteListModel();
						tempModel.setMobiletype(temp.getMobileType());
						tempModel.setMobilename(temp.getMobileName());
						tempModel.setRemark(temp.getRemark());
						resultList.add(tempModel);
					}
					response.setList(resultList);
				}
			}

			// 签名

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
}

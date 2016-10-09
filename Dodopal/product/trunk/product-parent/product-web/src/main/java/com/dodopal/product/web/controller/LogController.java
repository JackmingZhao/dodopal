package com.dodopal.product.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.TrackIdHolder;
import com.dodopal.product.business.model.LogDlog;
import com.dodopal.product.business.service.LogDlogService;
import com.dodopal.product.web.param.DlogRequest;

/**
 * @author lifeng@dodopal.com
 */
@Controller
@RequestMapping("/log")
public class LogController {
	private final static Logger logger = LoggerFactory.getLogger(LogController.class);
	@Autowired
	private LogDlogService logDlogService;

	/**
	 * DLL交易日志请求，无需返回结果
	 * 
	 * @param request
	 */
	@RequestMapping("/addDlog")
	public @ResponseBody
	String addDlog(HttpServletRequest request) {
		// TODO:签名验签秘钥如何获取
		String key = "123456";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if (StringUtils.isBlank(jsondata)) {
				if (logger.isInfoEnabled()) {
					logger.info("接收到的jsondata参数为空");
				}
				return ResponseCode.JSON_ERROR;
			}

			String trackId = TrackIdHolder.setDefaultRandomTrackId();
			if (logger.isInfoEnabled()) {
				logger.info("trackId:" + trackId + ", receive jsondata:" + jsondata);
			}

			// 转换jsondata
			DlogRequest dlogRequest = convertJsonToRequest(jsondata, DlogRequest.class);
			// 参数校验
			dlogCheck(dlogRequest);
			// 验签
			dlogSignCheck(key, dlogRequest);
			// 业务逻辑
			LogDlog dlog = new LogDlog();
			dlog.setDlogStage(dlogRequest.getDlog_stage());
			dlog.setDlogSystemdatetime(dlogRequest.getDlog_systemdatetime());
			dlog.setDlogCode(dlogRequest.getDlog_code());
			dlog.setDlogMessage(dlogRequest.getDlog_message());
			dlog.setDlogPrdordernum(dlogRequest.getDlog_prdordernum());
			dlog.setDlogTradecard(dlogRequest.getDlog_tradecard());
			dlog.setDlogApdu(dlogRequest.getDlog_apdu());
			dlog.setDlogApdudata(dlogRequest.getDlog_apdudata());
			dlog.setDlogStatecode(dlogRequest.getDlog_statecode());

			int num = logDlogService.addLog(dlog);
			if (logger.isInfoEnabled()) {
				logger.info("trackId:" + TrackIdHolder.get() + ", add dlog result:" + num);
			}
		} catch (DDPException e) {
			if (logger.isErrorEnabled()) {
				logger.error("trackId:" + TrackIdHolder.get() + ", DDPException:code:" + e.getCode());
			}
			return e.getCode();
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("trackId:" + TrackIdHolder.get() + ", add dlog error:" + e.getMessage(), e);
			}
			return ResponseCode.SYSTEM_ERROR;
		}
		return "success";
	}

	private <T> T convertJsonToRequest(String jsondata, Class<T> clazz) {
		T request = null;
		try {
			request = JSONObject.parseObject(jsondata, clazz);
		} catch (Exception e) {
			throw new DDPException(ResponseCode.JSON_ERROR);
		}
		return request;
	}

	private void dlogCheck(DlogRequest request) {
		String dSign = request.getD_sign();
		if (DDPStringUtil.isNotPopulated(dSign)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SIGN_NULL);
		}
		String prdOrderNum = request.getDlog_prdordernum();
		if (DDPStringUtil.isNotPopulated(prdOrderNum)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_PRD_ORDER_NUM_NULL);
		}
	}

	private void dlogSignCheck(String key, DlogRequest request) {
		String signOld = request.getD_sign();

		// 不参与验签
		request.setD_sign(null);

		// 获取新的签名MAP
		DodopalResponse<Map<String, String>> rep = SignUtils.getSignMap(request);
		// 新的数据签名
		String signNew = SignUtils.sign(SignUtils.createLinkString(rep.getResponseEntity()), key, CommonConstants.CHARSET_UTF_8);

		// 对比签名
		if (!signNew.equals(signOld)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_SIGN_ERROR);
		}
	}
}

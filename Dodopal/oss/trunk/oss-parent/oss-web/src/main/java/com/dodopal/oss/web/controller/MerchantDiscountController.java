package com.dodopal.oss.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.DiscountMerchantInfo;
import com.dodopal.oss.business.model.MerchantDiscount;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.MerchantDiscountQuery;
import com.dodopal.oss.business.model.dto.MerchantQuery;
import com.dodopal.oss.business.service.MerchantDiscountService;

@Controller
@RequestMapping("/merchant")
public class MerchantDiscountController extends CommonController {
	private Logger logger = LoggerFactory.getLogger(MerchantDiscount.class);
	
	@Autowired
	private MerchantDiscountService merchantDiscountService;
	
	/**
	 * @author Mikaelyan
	 * 从菜单跳转到折扣信息列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("enterprise/discount")
	public ModelAndView merchantDiscount(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("merchant/enterprise/discount");
		return mv;
	}
	
	/**
	 * @author Mikaelyan
	 * 根据折扣值查询折扣信息列表
	 * @param request
	 * @param mdq
	 * @return
	 */
	@RequestMapping("enterprise/findDiscountsPage")
	public @ResponseBody DodopalResponse<DodopalDataPage<MerchantDiscount>> findDiscountsByUserDiscountByPage(HttpServletRequest request, @RequestBody MerchantDiscountQuery mdq) {
		DodopalResponse<DodopalDataPage<MerchantDiscount>> response = new DodopalResponse<DodopalDataPage<MerchantDiscount>>();
		try {
			DodopalDataPage<MerchantDiscount> result = merchantDiscountService.findMerchantDiscountsByUserDiscountByPage(mdq);
			response.setResponseEntity(result);
			response.setCode(ResponseCode.SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.UNKNOWN_ERROR);
		}
		return response;
	}
	
	/**
	 * @author Mikaelyan
	 * 根据折扣信息Id 得到以绑定的商户和未绑定的商户列表
	 * @param request
	 * @param merchantQuery
	 * @return
	 */
	@RequestMapping("enterprise/findMerchantsByDiscountIdByPage")
	public @ResponseBody DodopalResponse<DodopalDataPage<DiscountMerchantInfo>> findMerchantsByDiscountIdByPage(HttpServletRequest request, @RequestBody MerchantQuery merchantQuery) {
		DodopalResponse<DodopalDataPage<DiscountMerchantInfo>> resp = new DodopalResponse<DodopalDataPage<DiscountMerchantInfo>>();
		try {
			DodopalDataPage<DiscountMerchantInfo> result = merchantDiscountService.findMerchantsByDiscountIdByPage(merchantQuery);
			resp.setResponseEntity(result);
			resp.setCode(ResponseCode.SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resp.setCode(ResponseCode.UNKNOWN_ERROR);
		}
		return resp;
	}
	
	/**
	 * @author Mikaelyan
	 * 保存折扣信息和折扣绑定的商户数组
	 * @param request
	 * @param merDiscount
	 * @return
	 */
	@RequestMapping("enterprise/saveDiscountAndMer")
	public @ResponseBody DodopalResponse<String> saveDiscountAndMer(HttpServletRequest request, @RequestBody MerchantDiscount merDiscount) {
		User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
		DodopalResponse<String> res = new DodopalResponse<String>();
		try {
			if(StringUtils.isNotBlank(merDiscount.getId())) {
				merDiscount.setUpdateUser(user.getId());
				merDiscount.setUpdateDate(new Date());
			}else {
				merDiscount.setUpdateUser("");
				merDiscount.setCreateUser(user.getId());
				merDiscount.setCreateDate(new Date());
			}
			String result = merchantDiscountService.saveOrUpdateMerDiscount(merDiscount);
			if("000000".equals(result)) {
				res.setCode(ResponseCode.SUCCESS);
				res.setResponseEntity(CommonConstants.SUCCESS);
			}else {
				res.setCode("*@_@*");
				res.setMessage(result);
			}
		}catch(DDPException ddpe) {
			ddpe.printStackTrace();
            res.setCode(ResponseCode.OSS_DDIC_VALIDATOR);
            res.setMessage(ddpe.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
            res.setCode(ResponseCode.UNKNOWN_ERROR);
            res.setMessage(e.getMessage());
		}
		return res;
	}

	/**
	 * @author Mikaelyan
	 * 根据折扣信息Id 得到折扣信息 不包含商户列表
	 * @param request
	 * @param discountId
	 * @return
	 */
	@RequestMapping("enterprise/viewDiscountById")
	public @ResponseBody DodopalResponse<MerchantDiscount> viewDiscountById(HttpServletRequest request, @RequestBody String discountId) {
		DodopalResponse<MerchantDiscount> res = new DodopalResponse<MerchantDiscount>();
		try {
			MerchantDiscount md = merchantDiscountService.findDiscountById(discountId);
			res.setResponseEntity(md);
			res.setCode(ResponseCode.SUCCESS);
		}catch(DDPException ddpe) {
			ddpe.printStackTrace();
			res.setCode(ResponseCode.OSS_DDIC_VALIDATOR);
			res.setMessage(ddpe.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			res.setCode(ResponseCode.UNKNOWN_ERROR);
			res.setMessage(e.getMessage());
		}
		return res;
	}
	
	@RequestMapping("enterprise/viewMerArrByDiscountId")
	public @ResponseBody DodopalResponse<List<DiscountMerchantInfo>> findMerArrByDiscountId(HttpServletRequest request, @RequestBody String discountId) {
		DodopalResponse<List<DiscountMerchantInfo>> resp = new DodopalResponse<List<DiscountMerchantInfo>>();
		try {
			List<DiscountMerchantInfo> list = merchantDiscountService.findMerArrByDiscountId(discountId);
			resp.setResponseEntity(list);
			resp.setCode(ResponseCode.SUCCESS);
		}catch(DDPException ddpe) {
			ddpe.printStackTrace();
			resp.setCode(ResponseCode.OSS_DDIC_VALIDATOR);
			resp.setMessage(ddpe.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			resp.setCode(ResponseCode.UNKNOWN_ERROR);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}
	
	@RequestMapping("enterprise/findMerInfoByIdArr")
	public @ResponseBody DodopalResponse<List<DiscountMerchantInfo>> findMerInfoByIdArr(HttpServletRequest request, @RequestBody String[] merCodeArr) {
		DodopalResponse<List<DiscountMerchantInfo>> resp = new DodopalResponse<List<DiscountMerchantInfo>>();
			try {
				List<DiscountMerchantInfo> list = merchantDiscountService.findMerInfoByIdArr(merCodeArr);
				resp.setResponseEntity(list);
				resp.setCode(ResponseCode.SUCCESS);
			}catch(DDPException ddpe) {
				ddpe.printStackTrace();
				resp.setCode(ResponseCode.OSS_DDIC_VALIDATOR);
				resp.setMessage(ddpe.getMessage());
			}catch(Exception e) {
				e.printStackTrace();
				resp.setCode(ResponseCode.UNKNOWN_ERROR);
				resp.setMessage(e.getMessage());
			}
		return resp;
	}
	
	@RequestMapping("enterprise/deleteMerDiscountByIds")
	public @ResponseBody DodopalResponse<String> deleteMerDiscountByIds(HttpServletRequest request, @RequestBody String[] ids) {
		DodopalResponse<String> resp = new DodopalResponse<String>();
		try {
			String result = merchantDiscountService.deleteMerDiscountByIds(ids);
			resp.setCode(ResponseCode.SUCCESS);
			resp.setResponseEntity(result);
		}catch(DDPException ddpe) {
			ddpe.printStackTrace();
			resp.setCode(ResponseCode.OSS_DDIC_VALIDATOR);
			resp.setMessage(ddpe.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			resp.setCode(ResponseCode.UNKNOWN_ERROR);
			resp.setMessage(e.getMessage());
		}
		
		return resp;
	}
	

}

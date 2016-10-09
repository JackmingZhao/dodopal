package com.dodopal.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.DdicService;
import com.dodopal.portal.business.bean.MerGroupUserBean;
import com.dodopal.portal.business.bean.MerGroupUserQueryBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.ProductYKTBean;
import com.dodopal.portal.business.model.MerOcxCity;
import com.dodopal.portal.business.service.MerGroupUserService;
import com.dodopal.portal.business.service.MerchantService;
import com.dodopal.portal.business.service.MerchantUserService;
import com.dodopal.portal.business.service.ProductYktService;

@Controller
@RequestMapping("/merchantGroupUser")
public class MerGroupUserController extends CommonController{
	private final static Logger log = LoggerFactory.getLogger(MerGroupUserController.class);

	@Autowired
	private MerGroupUserService service;
	@Autowired
	private MerchantService merService;
	@Autowired
	private MerchantUserService userService;
	@Autowired
	private ProductYktService productYKTService;
	@Autowired
    DdicService ddicService;

	@RequestMapping(value = "/findMerchnatGroupUsers", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DodopalResponse<DodopalDataPage<MerGroupUserBean>> findMerchnatGroupUser(HttpServletRequest request,@RequestBody MerGroupUserQueryBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
 		return service.findMerGpUsersByPage(bean, SourceEnum.PORTAL);
	}
	
	@RequestMapping("/merchantGroupUserMgmt")
	public ModelAndView verifiedMgmt(HttpServletRequest request,@RequestParam(value="addFlag",required=false)String addFlag) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("merchant/merchantGroupUserMgr/merGroupUser");
		if(null!=addFlag){
		    //前台的新增操作 flag标志  cookie中存在禁用问题，以参数方式传回传出0代表打开增加集团用户页面
			if(!"0".equals(addFlag)){
				mv.addObject("flag", "1");
			}else{
				mv.addObject("flag", "0");
			}
		}else{
			mv.addObject("flag", "1");
		}
		return mv;
	}
	
	@RequestMapping("/findMerchnatGroupUserById")
	public @ResponseBody DodopalResponse<MerGroupUserBean> findMerchnatGroupUserById(Model model, HttpServletRequest request, @RequestBody String id) {
		return service.findMerGroupUserById(id);
	}

	@RequestMapping("/deleteMerchnatGroupUserById")
	public @ResponseBody DodopalResponse<String> deleteMerchnatGroupUserById(Model model, HttpServletRequest request, @RequestBody String id) {
			return service.deleteMerGroupUser(id);
	}

	@RequestMapping("/saveMerchnatGroupUser")
	public @ResponseBody DodopalResponse<String> saveMerchnatGroupUser(HttpServletRequest request, @RequestBody MerGroupUserBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		
		if(StringUtils.isBlank(bean.getId())){
			bean.setCreateUser(super.getCurrentUserId(request.getSession()));
			return service.saveMerGroupUserDTOList(bean);
		}else{
		    bean.setUpdateUser(super.getCurrentUserId(request.getSession()));
			return service.updateMerGroupUser(bean);
		}
	}
	
	@RequestMapping("/checkMerGroupUserCardExist")
	public @ResponseBody DodopalResponse<String> checkMerGroupUserCardExist(Model model, HttpServletRequest request, @RequestBody MerGroupUserBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		return service.checkCardCode(bean.getMerCode(), bean.getCardCode(), bean.getId());
	} 
	
	@RequestMapping(value = "/importGroupUser")
	public @ResponseBody DodopalResponse<String> importGroupUser(@RequestParam("groupUserFile") CommonsMultipartFile file, HttpServletRequest request) {
		DodopalResponse<String> response = null;
		try {
			return service.importMerGpUsers(file,super.getCurrentMerchantCode(request.getSession()));
		}
		catch (DDPException ddp) {
			ddp.printStackTrace();
			response = new DodopalResponse<String>();
			response.setCode(ResponseCode.SYSTEM_ERROR);
			response.setMessage(ddp.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			response = new DodopalResponse<String>();
			response.setCode(ResponseCode.UNKNOWN_ERROR);
		}
		return response;
	}
	
	
	@RequestMapping("/getMerCity")
    public @ResponseBody DodopalResponse<MerOcxCity> getMerCity(Model model, HttpServletRequest request) {
        DodopalResponse<MerOcxCity> dodopalResponse = new DodopalResponse<MerOcxCity>();
        MerOcxCity merOcxCity = new MerOcxCity();
        try{
            PortalUserDTO dto = super.getLoginUser(request.getSession());
            DodopalResponse<List<Area>> areaList = merService.findMerCitys(dto.getMerCode());
            //查找商户所开通的业务城市
            if(ResponseCode.SUCCESS.equals(areaList.getCode())){
                if(CollectionUtils.isEmpty(areaList.getResponseEntity())){
                    dodopalResponse.setCode(ResponseCode.PORTAL_APP_NOT_OPPEN_ERR);
                    return dodopalResponse;
                }
                merOcxCity.setAreaList(areaList.getResponseEntity());
                DodopalResponse<MerchantUserBean>  user = userService.findUserInfoByMobileOrUserName(dto.getMerUserName());
                if(ResponseCode.SUCCESS.equals(user.getCode())){
                    //查询成功  并且用户不为空的情况下
                    if(null!=user.getResponseEntity()){
                        //用户设置了默认的城市
                        if(StringUtils.isBlank(user.getResponseEntity().getCityCode())){
                            merOcxCity.setCityCode(areaList.getResponseEntity().get(0).getCityCode());
                            merOcxCity.setCityName(areaList.getResponseEntity().get(0).getCityName());
                        }else{
                            for(Area area:merOcxCity.getAreaList()){
                                if(user.getResponseEntity().getCityCode().equals(area.getCityCode())){
                                    merOcxCity.setCityCode(area.getCityCode());
                                    merOcxCity.setCityName(area.getCityName());
                                }
                            }
                        }
                        merOcxCity.setUserCode(user.getResponseEntity().getUserCode());
                        merOcxCity.setUserId(user.getResponseEntity().getUserId());
                        merOcxCity.setMerCode(user.getResponseEntity().getMerCode());
                        DodopalResponse<ProductYKTBean> yktResponse = productYKTService.getYktInfoByBusinessCityCode(merOcxCity.getCityCode());
                        if(ResponseCode.SUCCESS.equals(yktResponse.getCode())){
                            ProductYKTBean yktBean = yktResponse.getResponseEntity();
                            //根据通卡公司code和城市code 取得对应的版本号        
                            merOcxCity.setOcxVersionId(ddicService.getDdicNameByCodeFormDB(yktBean.getYktCode(), merOcxCity.getCityCode().toString()));
                            //根据通卡公司code取得 对应的class id
                            merOcxCity.setOcxCLSID(ddicService.getDdicNameByCodeFormDB(yktBean.getYktCode(), CommonConstants.OCX_CLASS_ID).toString());
                            
                            merOcxCity.setOcxName(yktBean.getYktCode());
                            dodopalResponse.setCode(ResponseCode.SUCCESS);
                            dodopalResponse.setResponseEntity(merOcxCity);
                        }else{
                            dodopalResponse.setCode(user.getCode());
                            return dodopalResponse;
                        }
                    }else{
                        dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
                        return dodopalResponse;
                    }
                }else{
                    dodopalResponse.setCode(user.getCode());
                    return dodopalResponse;
                }
            }else{
                dodopalResponse.setCode(areaList.getCode());
                return dodopalResponse;
            }
        }catch(Exception e){
            log.error("MerGroupUserController getMerCity call an error:",e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
	
	
	
		@RequestMapping("/toChangeCity")
	    public String toChangeCity(HttpServletRequest request,RedirectAttributes attr,@RequestParam String cityCode) {
			try{
				PortalUserDTO dto = super.getLoginUser(request.getSession());
				userService.updateMerUserBusCity(cityCode, dto.getId());
				attr.addAttribute("addFlag","0");
			    //返回应用中心页面
				return "redirect:merchantGroupUserMgmt";
			}catch(Exception e){
				log.error("MerGroupUserController toChangeCity call an error",e);
				return "redirect:merchantGroupUserMgmt";
			}
		}
}

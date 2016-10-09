package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerGroupDepartmentBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.PosBean;
import com.dodopal.portal.business.bean.PosOperationBean;
import com.dodopal.portal.business.bean.query.PosQuery;
import com.dodopal.portal.business.service.MerchantService;
import com.dodopal.portal.business.service.PosService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年6月23日 下午8:28:45
 */
@Controller
@RequestMapping("/pos")
public class PosController extends CommonController{
    @Autowired
    PosService posService;
    @RequestMapping("/toPosList")
    public ModelAndView toPosList(HttpServletRequest request) {
        return new ModelAndView("merchant/posManger/posManger");
    }
    
    @RequestMapping("/findPosList")
    public  @ResponseBody DodopalResponse<DodopalDataPage<PosBean>>  findPosList(HttpServletRequest request,@RequestBody PosQuery posQuery) {
        DodopalResponse<DodopalDataPage<PosBean>> dodopalResponse = new DodopalResponse<DodopalDataPage<PosBean>>();
        posQuery.setMerchantCode(super.getCurrentMerchantCode(request.getSession()));
        dodopalResponse = posService.findPosListByPage(posQuery);
        return dodopalResponse;
    }
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: bindingPos 
     * @Description: 商户Pos管理绑定操作
     * @param request
     * @param posBean
     * @return    设定文件 
     * DodopalResponse<DodopalDataPage<PosBean>>    返回类型 
     * @throws 
     */
    @RequestMapping("/bindingPos")
    public  @ResponseBody DodopalResponse<String>  bindingPos(HttpServletRequest request,@RequestBody PosOperationBean posBean) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        MerchantUserBean user = new MerchantUserBean();
        user.setId(super.getCurrentUserId(request.getSession()));
        user.setMerUserName(super.getCurrentUserName(request.getSession()));
        posBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
        posBean.setOperation(PosOperTypeEnum.OPER_BUNDLING);
        dodopalResponse = posService.posOper(posBean, user);
        return dodopalResponse;
    }
    
    @RequestMapping("/unBindingPos")
    public  @ResponseBody DodopalResponse<String>  unBindingPos(HttpServletRequest request,@RequestBody PosOperationBean posBean) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        MerchantUserBean user = new MerchantUserBean();
        user.setId(super.getCurrentUserId(request.getSession()));
        user.setMerUserName(super.getCurrentUserName(request.getSession()));
        posBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
        posBean.setOperation(PosOperTypeEnum.OPER_UNBUNDLING);
        dodopalResponse = posService.posOper(posBean, user);
        return dodopalResponse;
    }
    
    /*********************************子商户POS管理开始****************************************/
    @RequestMapping("/toChildrenPosList")
    public ModelAndView toChildrenPosList(HttpServletRequest request) {
        return new ModelAndView("merchant/posManger/posChildrenManger");
    }
    
    @RequestMapping("/startPos")
    public  @ResponseBody DodopalResponse<String>  startPos(HttpServletRequest request,@RequestBody PosOperationBean posBean) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        MerchantUserBean user = new MerchantUserBean();
        user.setId(super.getLoginUser(request.getSession()).getId());
        user.setMerUserName(super.getCurrentUserName(request.getSession()));
        posBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
        posBean.setOperation(PosOperTypeEnum.OPER_ENABLE);
        dodopalResponse = posService.posOper(posBean, user);
        return dodopalResponse;
    }
    
    @RequestMapping("/stopPos")
    public  @ResponseBody DodopalResponse<String>  stopPos(HttpServletRequest request,@RequestBody PosOperationBean posBean) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        MerchantUserBean user = new MerchantUserBean();
        user.setId(super.getLoginUser(request.getSession()).getId());
        user.setMerUserName(super.getCurrentUserName(request.getSession()));
        posBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));

        posBean.setOperation(PosOperTypeEnum.OPER_DISABLE);
        dodopalResponse = posService.posOper(posBean, user);
        return dodopalResponse;
    }
    
    @RequestMapping("/findChildrenPosList")
    public  @ResponseBody DodopalResponse<DodopalDataPage<PosBean>>  findChildrenPosList(HttpServletRequest request,@RequestBody PosQuery posQuery) {
        DodopalResponse<DodopalDataPage<PosBean>> dodopalResponse = new DodopalResponse<DodopalDataPage<PosBean>>();
        posQuery.setMerchantCode(super.getCurrentMerchantCode(request.getSession()));
        dodopalResponse = posService.findChildrenPosListByPage(posQuery);
        return dodopalResponse;
    }
    
    /*********************************子商户POS管理结束****************************************/
}

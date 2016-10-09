package com.dodopal.portal.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.portal.business.bean.CcOrderFormBean;
import com.dodopal.portal.business.bean.CzOrderByPosBean;
import com.dodopal.portal.business.bean.CzOrderByPosCountBean;
import com.dodopal.portal.business.bean.query.QueryCcOrderFormBean;
import com.dodopal.portal.business.bean.query.QueryCzOrderByPosCountBean;
import com.dodopal.portal.business.service.CcOrderService;

@Controller
@RequestMapping("/ccOrder")
public class CcOrderController extends CommonController{
    @Autowired
    CcOrderService ccOrderService;
    @Autowired
    ExpTempletService expTempletService;
    
    @RequestMapping("/queryCcOrderMgr")
    public ModelAndView ccOrderMgmt(HttpServletRequest request) {
        return new ModelAndView("tranRecord/historyTran/merchant/ccOrderPos");
    }
    
    @RequestMapping("/queryCzOrderCountMgr")
    public ModelAndView czOrderCountMgmt(HttpServletRequest request) {
        return new ModelAndView("tranRecord/historyTran/merchant/ccOrderPosCount");
    }
    /***
     * 4.2.2.4  手持pos机充值订单查询
     * @param request
     * @param queryCcOrderFormBean
     * @return
     */
    @RequestMapping(value = "/findCcOrderPos", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody DodopalResponse<DodopalDataPage<CcOrderFormBean>> findCcOrderPos(HttpServletRequest request,@RequestBody QueryCcOrderFormBean queryCcOrderFormBean) {
        DodopalResponse<DodopalDataPage<CcOrderFormBean>> response = new DodopalResponse<DodopalDataPage<CcOrderFormBean>>();
        try {
            queryCcOrderFormBean.setMchnitid(super.getCurrentMerchantCode(request.getSession()));
            response = ccOrderService.findCcOrderByPage(queryCcOrderFormBean);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PORTAL_CHILD_MERCHANT_FIND_ERR);
        }
        return response;
    }
    
    @RequestMapping(value = "/findCzOrderByPosByPage", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody DodopalResponse<DodopalDataPage<CzOrderByPosBean>> findCzOrderByPosByPage(HttpServletRequest request,@RequestBody QueryCzOrderByPosCountBean queryCzOrderByPosCountBean) {
        DodopalResponse<DodopalDataPage<CzOrderByPosBean>> response = new DodopalResponse<DodopalDataPage<CzOrderByPosBean>>();
        try {
            queryCzOrderByPosCountBean.setMchnitid(super.getCurrentMerchantCode(request.getSession()));
            response = ccOrderService.findCzOrderByPosByPage(queryCzOrderByPosCountBean);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PORTAL_CHILD_MERCHANT_FIND_ERR);
        }
        return response;
    }
    
    @RequestMapping(value = "/findCzOrderCount", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody DodopalResponse<CzOrderByPosCountBean> findCzOrderCount(HttpServletRequest request,@RequestBody QueryCzOrderByPosCountBean queryCzOrderByPosCountBean) {
        DodopalResponse<CzOrderByPosCountBean> response = new DodopalResponse<CzOrderByPosCountBean>();
        try {
            queryCzOrderByPosCountBean.setMchnitid(super.getCurrentMerchantCode(request.getSession()));
            response = ccOrderService.findCzOrderByPosCount(queryCzOrderByPosCountBean);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PORTAL_CHILD_MERCHANT_FIND_ERR);
        }
        return response;
    }
    
    /**
     * 充值查询导出
     * @param req
     * @param res
     * @return
     */
      @RequestMapping(value = "/exportQueryExcel", method = {RequestMethod.GET, RequestMethod.POST})
      public @ResponseBody DodopalResponse<String> exportQueryExcel(HttpServletRequest request, HttpServletResponse response) {
          DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
          try {
              QueryCcOrderFormBean queryCcOrderFormBean = new QueryCcOrderFormBean();
              String bankorderid = (String)request.getParameter("bankorderid");
              String startdate = (String)request.getParameter("startdate");
              String enddate = (String)request.getParameter("enddate");
              String posid = (String)request.getParameter("posid");
              String cardno = (String)request.getParameter("cardno");
              String username = (String)request.getParameter("username");
              String status = (String)request.getParameter("status");
              //商户订单号
              if(StringUtils.isNotBlank(bankorderid)){
                  queryCcOrderFormBean.setBankorderid(bankorderid);
              }
              //查询起始时间
              if(StringUtils.isNotBlank(startdate)){
                  queryCcOrderFormBean.setStartdate(startdate);
              }
              //查询结束时间
              if(StringUtils.isNotBlank(enddate)){
                  queryCcOrderFormBean.setEnddate(enddate);
              }
              //Pos编号
              if(StringUtils.isNotBlank(posid)){
                  queryCcOrderFormBean.setPosid(posid);
              }
              //卡号
              if(StringUtils.isNotBlank(cardno)){
                  queryCcOrderFormBean.setCardno(cardno);
              }
              //用户名称
              if(StringUtils.isNotBlank(username)){
                  queryCcOrderFormBean.setUsername(username);
              }
              //订单状态
              if(StringUtils.isNotBlank(status)){
                  queryCcOrderFormBean.setStatus(status);
              }
              queryCcOrderFormBean.setMchnitid(getCurrentMerchantCode(request.getSession()));
              dodopalResponse = ccOrderService.findCcOrderExcel(request,response,queryCcOrderFormBean);
          }
          catch (Exception e) {
              e.printStackTrace();
              dodopalResponse.setCode(ResponseCode.UNKNOWN_ERROR);
          }
          return dodopalResponse;
      }
    
  /**
   * 消费统计导出
   * @param req
   * @param res
   * @return
   */
    @RequestMapping(value = "/exportExcel", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody DodopalResponse<String> exportExcel(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        try {
            QueryCzOrderByPosCountBean queryCzOrderByPosCountBean = new QueryCzOrderByPosCountBean();
            String startdate = (String)request.getParameter("startdate");
            String enddate = (String)request.getParameter("enddate");
            //查询起始时间
            if(StringUtils.isNotBlank(queryCzOrderByPosCountBean.getStartdate())){
                queryCzOrderByPosCountBean.setStartdate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(startdate)));
            }
            //查询结束时间
            if(StringUtils.isNotBlank(queryCzOrderByPosCountBean.getEnddate())){
                queryCzOrderByPosCountBean.setEnddate(new SimpleDateFormat("yyyyMMdd").format(DateFormat.getDateInstance().parse(enddate)));
            }
            queryCzOrderByPosCountBean.setMchnitid(getCurrentMerchantCode(request.getSession()));
            dodopalResponse = ccOrderService.findCzOrderByPosCountExcel(request,response,queryCzOrderByPosCountBean);
        }
        catch (Exception e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return dodopalResponse;
    }
}

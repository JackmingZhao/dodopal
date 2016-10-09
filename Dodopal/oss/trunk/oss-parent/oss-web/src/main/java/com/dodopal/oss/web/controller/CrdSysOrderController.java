package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.CrdSysOrderBean;
import com.dodopal.oss.business.bean.query.CrdSysOrderQuery;
import com.dodopal.oss.business.service.CrdSysOrderService;

@Controller
@RequestMapping("/crdSys")
public class CrdSysOrderController {
    @Autowired
    CrdSysOrderService crdSysOrderService;

    @Autowired
    private ExpTempletService expTempletService;

    /**
     * 初始化加载卡服务订单界面
     * @param request
     * @return
     */
    @RequestMapping("/crdSysOrderMgmt")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("crdSysOrder/crdSysOrderMgmt");
    }

    /***
     * 查询卡服务订单
     * @param request
     * @param crdSysOrderBean
     * @return
     */
    @RequestMapping("/findCrdSysOrderByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<CrdSysOrderBean>> findCrdSysOrderByPage(HttpServletRequest request, @RequestBody CrdSysOrderQuery crdSysOrderQuery) {
        DodopalResponse<DodopalDataPage<CrdSysOrderBean>> response = new DodopalResponse<DodopalDataPage<CrdSysOrderBean>>();
        try {
            response = crdSysOrderService.findCrdSysOrderByPage(crdSysOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 查询详情
     * @param request
     * @param crdOrderNum
     * @return
     */
    @RequestMapping("/findCrdSysOrderByCode")
    public @ResponseBody
    DodopalResponse<CrdSysOrderBean> findCrdSysOrderByCode(HttpServletRequest request, @RequestBody String crdOrderNum) {
        DodopalResponse<CrdSysOrderBean> response = new DodopalResponse<CrdSysOrderBean>();
        try {
            response = crdSysOrderService.findCrdSysOrderByCode(crdOrderNum);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @RequestMapping("/exportCrdSysOrder")
    public @ResponseBody
    DodopalResponse<String> exportCrdSysOrder(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        //卡服务订单号
        String crdOrderNum = request.getParameter("crdOrderNum");
        //产品库主订单号
        String proOrderNum = request.getParameter("proOrderNum");
        //产品编号
        String proName = request.getParameter("proName");
        //商户编号
        String merCode = request.getParameter("merCode");
        //卡服务订单交易状态
        String crdOrderStates = request.getParameter("crdOrderStates");
        //上次卡服务订单交易状态
        String crdBeforeorderStates = request.getParameter("crdBeforeorderStates");
        //POS号
        String posCode = request.getParameter("posCode");
        //卡号(验卡卡号)
        String checkCardNo = request.getParameter("checkCardNo");
        //卡类型
        String cardType = request.getParameter("cardType");
        //交易时间
        String txnDataTimEnd = request.getParameter("afterProceRateDateEnd");
        String txnDataTimStart = request.getParameter("afterProceRateDateStart");
        //交易金额
        String txnAmtStart = request.getParameter("txnAmtStart");
        String txnAmtEnd = request.getParameter("txnAmtEnd");

        CrdSysOrderQuery crdSysOrderQuery = new CrdSysOrderQuery();
        if (crdOrderNum != null && crdOrderNum != "") {
            crdSysOrderQuery.setCrdOrderNum(crdOrderNum);
        }
        if (proOrderNum != null && proOrderNum != "") {
            crdSysOrderQuery.setProOrderNum(proOrderNum);
        }
        if (proName != null && proName != "") {
            crdSysOrderQuery.setProName(proName);
        }
        if (merCode != null && merCode != "") {
            crdSysOrderQuery.setMerCode(merCode);
        }
        if (crdOrderStates != null && crdOrderStates != "") {
            crdSysOrderQuery.setCrdOrderStates(crdOrderStates);
        }
        if (crdBeforeorderStates != null && crdBeforeorderStates != "") {
            crdSysOrderQuery.setCrdBeforeorderStates(crdBeforeorderStates);
        }
        if (posCode != null && posCode != "") {
            crdSysOrderQuery.setPosCode(posCode);
        }
        if (checkCardNo != null && checkCardNo != "") {
            crdSysOrderQuery.setCheckCardNo(checkCardNo);
        }
        if (cardType != null && cardType != "") {
            crdSysOrderQuery.setCardType(cardType);
        }
        //交易前时间
        if (StringUtils.isNotBlank(txnDataTimStart)) {
            Date txnDateStart = DateUtils.stringtoDate(txnDataTimStart, DateUtils.DATE_SMALL_STR);
            String txnDateStartStr = DateUtils.dateToString(txnDateStart, DateUtils.DATE_FORMAT_YYMMDD_STR);
            crdSysOrderQuery.setTxnDataTimStart(txnDateStartStr);
        }
        //交易后时间
        if (StringUtils.isNotBlank(txnDataTimEnd)) {
            Date txnDateEnd = DateUtils.stringtoDate(txnDataTimEnd, DateUtils.DATE_SMALL_STR);
            String txnDateEndStr = DateUtils.dateToString(txnDateEnd, DateUtils.DATE_FORMAT_YYMMDD_STR);
            crdSysOrderQuery.setTxnDataTimEnd(txnDateEndStr);
        }
        if (txnAmtStart != null && txnAmtStart != "") {
            crdSysOrderQuery.setTxnAmtStart(new Double(Double.parseDouble(txnAmtStart) * 100).toString());
        }
        if (txnAmtEnd != null && txnAmtEnd != "") {
            crdSysOrderQuery.setTxnAmtEnd(new Double(Double.parseDouble(txnAmtEnd) * 100).toString());
        }
        try {
            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            DodopalResponse<List<CrdSysOrderBean>> expRep = crdSysOrderService.excelExport(response, crdSysOrderQuery);
            if (ResponseCode.SUCCESS.equals(expRep.getCode())) {
                List<CrdSysOrderBean> lstData = expRep.getResponseEntity();
                rep.setCode(ExcelUtil.excelExport(request, response, lstData, col, "卡服务充值订单"));
            } else {
                rep.setCode(expRep.getCode());
                return rep;
            }
        }
        catch (Exception e) {
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return rep;
    }

    /******************** 获取卡服务充值订单状态Start *****************************************/
    @RequestMapping("/findCardOrderStates")
    public @ResponseBody
    List<NamedEntity> findCardOrderStates(HttpServletRequest request) {
        List<NamedEntity> list = new ArrayList<NamedEntity>();
        for (CardOrderStatesEnum status : CardOrderStatesEnum.values()) {
            list.add(new NamedEntity(status.getCode(), status.getName()));
        }
        return list;
    }

    /******************** 获取卡服务充值订单状态end *****************************************/

    /******************** 获取卡服务卡片类型状态Start *****************************************/
    @RequestMapping("/findCardType")
    public @ResponseBody
    List<NamedEntity> findCardType(HttpServletRequest request) {
        List<NamedEntity> list = new ArrayList<NamedEntity>();
        for (CardTypeEnum status : CardTypeEnum.values()) {
            list.add(new NamedEntity(status.getCode(), status.getName()));
        }
        return list;
    }
    /******************** 获取卡服务卡片类型状态end *****************************************/
}

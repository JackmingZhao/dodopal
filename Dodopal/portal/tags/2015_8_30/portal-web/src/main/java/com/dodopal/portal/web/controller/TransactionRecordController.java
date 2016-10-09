package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.model.query.PayTraTransactionQuery;
import com.dodopal.portal.business.service.TransactionRecordService;

@Controller
@RequestMapping("/tran")
public class TransactionRecordController extends CommonController {
    private final static Logger log = LoggerFactory.getLogger(TransactionRecordController.class);

    @Autowired
    private TransactionRecordService recordService;

    @RequestMapping("/record")
    public ModelAndView showRecord(Model model, HttpServletRequest request) {
        return new ModelAndView("tranRecord/transactionRecord");
    }
    
    @RequestMapping("/tranDetail")
    public ModelAndView findTranRecordByCode(Model model, HttpServletRequest request ,@RequestParam String tranCode) {
        DodopalResponse<TraTransactionBean> response = new DodopalResponse<TraTransactionBean>();
        try {
            response = recordService.findTranInfoByTranCode(tranCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        TraTransactionBean payTraTransaction = response.getResponseEntity();
        model.addAttribute("payTraTransaction", payTraTransaction);
        return new ModelAndView("tranRecord/tranDetail");
    }

    @RequestMapping("/findPayTraTransactionByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<PayTraTransaction>> findPayTraTransactionByPage(HttpServletRequest request, @RequestBody PayTraTransactionQuery query) {
        log.info("TransactionRecordController findPayTraTransactionByPage:" + query);
        DodopalResponse<DodopalDataPage<PayTraTransaction>> response = new DodopalResponse<DodopalDataPage<PayTraTransaction>>();
        try {
            response = recordService.findPayTraTransactionByPage(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_HISTORY_RECORD_ERR);
        }
        return response;
    }



}

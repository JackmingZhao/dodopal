package com.dodopal.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.DirectMerChantBean;
import com.dodopal.portal.business.bean.MerDiscountBean;
import com.dodopal.portal.business.model.MerDiscountAdd;
import com.dodopal.portal.business.model.MerDiscountEdit;
import com.dodopal.portal.business.service.MerDiscountService;
import com.dodopal.portal.business.service.TransferService;
/**
 * 商户折扣管理
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/merchant")
public class MerDiscountController extends CommonController {

    @Autowired
    TransferService transferService;
    @Autowired
    MerDiscountService merDiscountService;

    @RequestMapping("merDiscount")
    public ModelAndView merDiscount(Model model, HttpServletRequest request) {
        
        return new ModelAndView("merchant/rebate/merDiscount");
    }

    @RequestMapping("merDiscountAdd")
    public ModelAndView merDiscountAdd(Model model, HttpServletRequest request) {
        String merType = getMerType(request.getSession());
        model.addAttribute("merType",merType);
        return new ModelAndView("merchant/rebate/merDiscountAdd");
    }

    @RequestMapping("merDiscountEdit")
    public ModelAndView merDiscountEdit(Model model, HttpServletRequest request, @RequestParam("id") String id) {
        DodopalResponse<MerDiscountBean> response = new DodopalResponse<MerDiscountBean>();
        String merParentCode = getCurrentMerchantCode(request.getSession());
        try {
            response = merDiscountService.findMerDiscountById(id, merParentCode, "");
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        String merType = getMerType(request.getSession());
        model.addAttribute("merType",merType);
        model.addAttribute("merDiscountIdEdit",id);
        model.addAttribute("merDiscountBean",response.getResponseEntity());
        return new ModelAndView("merchant/rebate/merDiscountEdit");
    }

    //查询分页
    @RequestMapping("/findMerDiscountList")
    public @ResponseBody DodopalResponse<DodopalDataPage<MerDiscountDTO>> findMerDiscountList(HttpServletRequest request, @RequestBody MerDiscountQueryDTO merDiscountQueryDTO) {
        DodopalResponse<DodopalDataPage<MerDiscountDTO>> response = new DodopalResponse<DodopalDataPage<MerDiscountDTO>>();
        try {
            String merCode = getCurrentMerchantCode(request.getSession());
            merDiscountQueryDTO.setMerCode(merCode);
            response = merDiscountService.findMerDiscountByPage(merDiscountQueryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    //查询详情
    @RequestMapping("/findMerDiscountDetail")
    public @ResponseBody DodopalResponse<MerDiscountBean> findMerDiscountDetail(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<MerDiscountBean> response = new DodopalResponse<MerDiscountBean>();
        try {
            String merParentCode = getCurrentMerchantCode(request.getSession());
            response = merDiscountService.findMerDiscountById(id, merParentCode, "");
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    //查询 直营网点
    @RequestMapping("findDirectMer")
    public @ResponseBody DodopalResponse<List<DirectMerChantBean>> findDirectTransfer(HttpServletRequest request, @RequestBody String merName) {
        DodopalResponse<List<DirectMerChantBean>> response = new DodopalResponse<List<DirectMerChantBean>>();
        try {
            String merType = getMerType(request.getSession());
            String merParentCode = "";
            if (MerTypeEnum.CHAIN.getCode().equals(merType)) {
                merParentCode = getCurrentMerchantCode(request.getSession());
            }
            response = transferService.findMerchantByParentCode(merParentCode, merName);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    //新增 商户折扣
    @RequestMapping("saveMerDiscount")
    public @ResponseBody DodopalResponse<Boolean> saveMerDiscount(HttpServletRequest request, @RequestBody MerDiscountAdd merDiscountAdd) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            String merCode = getCurrentMerchantCode(request.getSession());
            merDiscountAdd.setMerCode(merCode);
            response = merDiscountService.addMerDiscount(merDiscountAdd);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }

        return response;
    }
    
    
    
    //编辑 商户折扣
    @RequestMapping("editMerDiscount")
    public @ResponseBody DodopalResponse<Boolean> editMerDiscount(HttpServletRequest request, @RequestBody MerDiscountEdit merDiscountEdit) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            String merCode = getCurrentMerchantCode(request.getSession());
            merDiscountEdit.setMerCode(merCode);
            response = merDiscountService.editMerDiscount(merDiscountEdit);
        }
        catch (Exception e) {
            // TODO: handle exception
        }

        return response;
    }

    //启用
    @RequestMapping("/batchStartMerOperator")
    public @ResponseBody DodopalResponse<Integer> startMerchnatUser(HttpServletRequest request, @RequestBody List<String> ids) {
        return merDiscountService.startOrStopMerDiscount(ActivateEnum.ENABLE.getCode(), ids);
    }

    //停用
    @RequestMapping("/batchStopMerOperator")
    public @ResponseBody DodopalResponse<Integer> stopMerchnatUser(HttpServletRequest request, @RequestBody List<String> ids) {
        return merDiscountService.startOrStopMerDiscount(ActivateEnum.DISABLE.getCode(), ids);
    }

}

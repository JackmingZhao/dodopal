package com.dodopal.portal.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerLimitExtractConfirmEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.TransferFlagEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerLimitExtractConfirmBean;
import com.dodopal.portal.business.bean.MerLimitExtractConfirmBeanQueryDTO;
import com.dodopal.portal.business.model.PortalTransfer;
import com.dodopal.portal.business.service.MerLimitExtractConfirmService;
import com.dodopal.portal.business.service.TransferService;

@Controller
@RequestMapping("/ddp")
public class MerLimitExtractConfirmController extends CommonController{
    @Autowired
    MerLimitExtractConfirmService merLimitExtractConfirmService;
    @Autowired
    TransferService transferService;
    
    
    @RequestMapping("/merLimitMgmt")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("ddp/limitFetchManage");
    }
    /**
     * 根据商户号查看连锁加盟网点或者连锁商户的额度提取记录
     * Name:JOE
     * Time:2016-01-05
     * @param request
     * @param merLimitBean
     * @return
     */
    @RequestMapping(value = "/findMerLimitByPage", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody DodopalResponse<DodopalDataPage<MerLimitExtractConfirmBean>> findMerLimitExtractConfirmBeanByPage(HttpServletRequest request,@RequestBody MerLimitExtractConfirmBeanQueryDTO merLimitBeanQuery) {
        DodopalResponse<DodopalDataPage<MerLimitExtractConfirmBean>> response = new DodopalResponse<DodopalDataPage<MerLimitExtractConfirmBean>>();
        merLimitBeanQuery.setMerType(super.getMerType(request.getSession()));
        try {
            if(MerTypeEnum.CHAIN.getCode().equals(merLimitBeanQuery.getMerType())){
                merLimitBeanQuery.setParentMerCode(super.getCurrentMerchantCode(request.getSession()));
            }else if(MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merLimitBeanQuery.getMerType())){
                merLimitBeanQuery.setChildMerCode(super.getCurrentMerchantCode(request.getSession()));
            }else if(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merLimitBeanQuery.getMerType())){
                merLimitBeanQuery.setChildMerCode(super.getCurrentMerchantCode(request.getSession()));
            }
            response = merLimitExtractConfirmService.findMerLimitExtractConfirmByPage(merLimitBeanQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.MER_LIMIT_EXTRACT_ERROR);
        }
        return response;
    }
    
    @RequestMapping(value = "/upMerLimitByCode")
    public @ResponseBody DodopalResponse<Integer> upMerLimitByCode(HttpServletRequest request,@RequestBody MerLimitExtractConfirmBean merLimitBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        DodopalResponse<Boolean> resp = new DodopalResponse<Boolean>();
        String merType= super.getMerType(request.getSession());
        try {
            //第一步判断商户类型是否为连锁加盟网点的
            if(MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)){
            //第二步判断额度提取是不是 已确认 操作
            if(MerLimitExtractConfirmEnum.CONFIRM.getCode().equals(merLimitBean.getState())){
                String sourceCustType = "";
                String sourceCusCode = "";
                String currentUserId = getCurrentUserId(request.getSession());
                String userCode = getCurrentUserCode(request.getSession());//当前登录的用户code
                String merCode = merLimitBean.getParentMerCode(); //连锁商户code
                if(MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)){ //连锁加盟商户 or  连锁直营网点
                   sourceCustType = MerUserTypeEnum.MERCHANT.getCode();
                   sourceCusCode = merLimitBean.getParentMerCode(); //连锁商户code
                }
                PortalTransfer portalTransfer = new PortalTransfer();
                portalTransfer.setCreateUser(currentUserId);
                portalTransfer.setSourceCustType(sourceCustType);
                portalTransfer.setSourceCusCode(sourceCusCode);
                portalTransfer.setUserCode(userCode);
                portalTransfer.setMerCode(merCode);
                portalTransfer.setTransferFlag(TransferFlagEnum.TO_EXTRACT_LINES.getCode());//交易类型
                portalTransfer.setMoney(merLimitBean.getExtractAmt());//交易金额
                portalTransfer.setBusinessType(RateCodeEnum.ACCT_TRANSFER.getCode());
                List<Map<String,String>> directMerList = new ArrayList<Map<String,String>>();
                Map<String,String> dirMap = new HashMap<String, String>();
                dirMap.put("merType", MerUserTypeEnum.MERCHANT.getCode());//当前用户类型是个人还是企业
                dirMap.put("merCode", super.getCurrentMerchantCode(request.getSession()));//当前商户为连锁加盟商户code
                directMerList.add(dirMap);
                portalTransfer.setDirectMer(directMerList);
                resp =  transferService.accountTransfer(portalTransfer);
                if(resp.getResponseEntity().equals(true)){
                    merLimitBean.setCreateUser(getCurrentUserId(request.getSession()));
                    merLimitBean.setConfirmUser(getCurrentUserId(request.getSession()));
                    merLimitBean.setChildMerCode(super.getCurrentMerchantCode(request.getSession()));
                    response = merLimitExtractConfirmService.updateMerLimitExtractConfirmByCode(merLimitBean);     
                }else{
                        response.setCode(resp.getCode());
                        response.setMessage(resp.getMessage());
                    }
                }else{
                    merLimitBean.setCreateUser(getCurrentUserId(request.getSession()));
                    merLimitBean.setConfirmUser(getCurrentUserId(request.getSession()));
                    merLimitBean.setChildMerCode(super.getCurrentMerchantCode(request.getSession()));
                    response = merLimitExtractConfirmService.updateMerLimitExtractConfirmByCode(merLimitBean);     
                }
            }else if(MerTypeEnum.CHAIN.getCode().equals(merType)){
                    merLimitBean.setParentMerCode(super.getCurrentMerchantCode(request.getSession()));
                    merLimitBean.setConcelUser(getCurrentUserId(request.getSession()));
                    response = merLimitExtractConfirmService.updateMerLimitExtractConfirmByCode(merLimitBean); 
                }
                
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UP_MER_LIMIT_EXTRACT_ERROR);
        }
        return response;
    }
}

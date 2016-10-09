package com.dodopal.oss.web.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AuditStateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.ProductYKTBDBean;
import com.dodopal.oss.business.bean.ProductYktInfoForExport;
import com.dodopal.oss.business.bean.ProductYktLimitBatchInfoBean;
import com.dodopal.oss.business.bean.ProductYktLimitBatchInfoForExport;
import com.dodopal.oss.business.bean.ProductYktLimitInfoBean;
import com.dodopal.oss.business.bean.ProductYktLimitInfoForExport;
import com.dodopal.oss.business.bean.query.ProductYKTBDFindBean;
import com.dodopal.oss.business.bean.query.ProductYktLimitBatchInfoFindBean;
import com.dodopal.oss.business.bean.query.ProductYktLimitInfoFindBean;
import com.dodopal.oss.business.model.BusinessCity;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.IcdcPrdService;
import com.dodopal.oss.business.service.IcdcService;

/**
 * 产品库-产品供应商-通卡公司管理(基础信息管理，额度管理)
 * @author 
 *
 */
@Controller
@RequestMapping("/product/supplier/icdc")
public class IcdcController extends CommonController {
    
    private Logger logger = LoggerFactory.getLogger(IcdcController.class);
    
    @Autowired
    private IcdcService icdcService;
    @Autowired
    private IcdcPrdService icdcPrdService;
    @Autowired
    private ExpTempletService expTempletService;
    
    /**************************************************** 基础信息管理开始 *****************************************************/
    /**
     * 基础信息管理初始页面
     * @param request
     * @return
     */
    @RequestMapping("basic")
    public ModelAndView initBasicInfo(HttpServletRequest request) {
        return new ModelAndView("product/supplier/yktBasicInfo");
    }

    /**
     * 基础信息管理分页查找
     * @param request
     * @return
     */
    @RequestMapping("findProductYktByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProductYKTBDBean>> findProductYktByPage(HttpServletRequest request,@RequestBody ProductYKTBDFindBean findDto) {
        DodopalResponse<DodopalDataPage<ProductYKTBDBean>> response = new DodopalResponse<DodopalDataPage<ProductYKTBDBean>>();
        try {
            response = icdcService.findProductYktByPage(findDto);
        }
        catch (Exception e) {
            logger.error("查询通卡公司基础信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 查询通卡公司明细信息
     * @return
     */
    @RequestMapping("findProductYktById")
    public @ResponseBody DodopalResponse<ProductYKTBDBean>  findProductYktById(HttpServletRequest request,@RequestBody String id){
       
        DodopalResponse<ProductYKTBDBean> response = new DodopalResponse<ProductYKTBDBean>();
        try {
            response = icdcService.findProductYktById(id);
        }
        catch (Exception e) {
            logger.error("查询通卡公司详细信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
     return response;
   }
    
    /**
     * 启用停用
     * @param request
     * @param activate
     * @return
     */
    @RequestMapping("startOrStopYkt")
    public @ResponseBody DodopalResponse<String> startOrStopYkt(HttpServletRequest request, @RequestBody String[] yktCodes,@RequestParam("activate") String activate) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if(yktCodes != null && yktCodes.length > 0) {
                User user = generateLoginUser(request);
                List<String> yktCode =new ArrayList<String>(Arrays.asList(yktCodes));
                DodopalResponse<Integer> result =  icdcService.startOrStopYkt(yktCode, activate, user.getUserId());
                response.setCode(result.getCode());
                response.setMessage(result.getMessage());
            } else {
                response.setCode(ResponseCode.PRODUCT_YKT_START_AND_DISABLE);
            }
        }
        catch (Exception e) {
            logger.error("停启用通卡公司出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    @RequestMapping("saveProductYkt")
    public @ResponseBody DodopalResponse<Integer> saveProductYkt(HttpServletRequest request, @RequestBody ProductYKTBDBean productYKTBDBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            String currentUserId = generateLoginUserId(request);
            if(StringUtils.isEmpty(productYKTBDBean.getId())) {
                productYKTBDBean.setCreateUser(currentUserId);
            }
            
            if(StringUtils.isBlank(productYKTBDBean.getYktRechargeLimitStartTime()) && StringUtils.isNotBlank(productYKTBDBean.getYktRechargeLimitEndTime())){
            	response.setCode(ResponseCode.UNKNOWN_ERROR);
            	response.setMessage("请设置充值限制开始时间");
            	return response;
            }else if(StringUtils.isNotBlank(productYKTBDBean.getYktRechargeLimitStartTime()) && StringUtils.isBlank(productYKTBDBean.getYktRechargeLimitEndTime())){
            	response.setCode(ResponseCode.UNKNOWN_ERROR);
            	response.setMessage("请设置充值限制结束时间");
            	return response;
            }
            if(StringUtils.isBlank(productYKTBDBean.getYktConsumeLimitStartTime()) && StringUtils.isNotBlank(productYKTBDBean.getYktConsumeLimitEndTime())){
            	response.setCode(ResponseCode.UNKNOWN_ERROR);
            	response.setMessage("请设置消费限制开始时间");
            	return response;
            }else if(StringUtils.isNotBlank(productYKTBDBean.getYktConsumeLimitStartTime()) && StringUtils.isBlank(productYKTBDBean.getYktConsumeLimitEndTime())){
            	response.setCode(ResponseCode.UNKNOWN_ERROR);
            	response.setMessage("请设置消费限制结束时间");
            	return response;
            }
            
            productYKTBDBean.setUpdateUser(generateLoginUserId(request));
            response = icdcService.saveOrUpdateYkt(productYKTBDBean);
        }
        catch (Exception e) {
            logger.error("保存通卡公司信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 通卡公司基础信息导出
     * @param request
     * @return
     */
    @RequestMapping("exportExcelProductYkt")
    public @ResponseBody DodopalResponse<String> exportExcelProductYkt(HttpServletRequest req, HttpServletResponse res) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            ProductYKTBDFindBean findDto = new ProductYKTBDFindBean();
            findDto.setYktName(req.getParameter("yktName"));
            findDto.setActivate(req.getParameter("activate"));
            
            DodopalResponse<List<ProductYKTBDBean>> listResponse = icdcService.getProductYktListForExportExcel(findDto);
            
            response.setCode(listResponse.getCode());
            if (!ResponseCode.SUCCESS.equals(listResponse.getCode())) {
                return response;
            }
            List<ProductYktInfoForExport> exportList = new ArrayList<ProductYktInfoForExport>();
            ProductYktInfoForExport exportDto = null;
            DecimalFormat df = new DecimalFormat("0.00");
            for (ProductYKTBDBean productYKTBDBean:listResponse.getResponseEntity()) {
                exportDto = new ProductYktInfoForExport();
                exportDto.setYktCode(productYKTBDBean.getYktCode());
                exportDto.setYktName(productYKTBDBean.getYktName());
                exportDto.setProvinceName(productYKTBDBean.getProvinceName());
                exportDto.setCityName(productYKTBDBean.getCityName());
                exportDto.setYktAddress(productYKTBDBean.getYktAddress());
                exportDto.setBusinessCityName(productYKTBDBean.getBusinessCityName());
                exportDto.setYktPayFlag(productYKTBDBean.getYktPayFlagView());
                exportDto.setActivate(productYKTBDBean.getActivateView());
                exportDto.setYktIsRecharge(productYKTBDBean.getYktIsRechargeView());
                if (productYKTBDBean.getYktRechargeRate() != null) {
                    exportDto.setYktRechargeRate(productYKTBDBean.getYktRechargeRate().toString());
                }
                exportDto.setYktRechargeSetType(productYKTBDBean.getYktRechargeSetTypeView());
                if ("2".equals(productYKTBDBean.getYktRechargeSetType())) {
                    exportDto.setYktRechargeSetPara(productYKTBDBean.getYktRechargeSetPara() == null ? null : df.format(Double.valueOf(productYKTBDBean.getYktRechargeSetPara()) / 100));
                } else {
                    exportDto.setYktRechargeSetPara(productYKTBDBean.getYktRechargeSetPara());
                }
                exportDto.setYktRechargeLimitStartTime(productYKTBDBean.getYktRechargeLimitStartTime());
                exportDto.setYktRechargeLimitEndTime(productYKTBDBean.getYktRechargeLimitEndTime());
                exportDto.setYktIsPay(productYKTBDBean.getYktIsPayView());
                if (productYKTBDBean.getYktPayRate() != null) {
                    exportDto.setYktPayRate(productYKTBDBean.getYktPayRate().toString());
                }
                exportDto.setYktPaysetType(productYKTBDBean.getYktRechargeSetTypeView());
                if ("2".equals(productYKTBDBean.getYktPaysetType())) {
                    exportDto.setYktPaySetPara(productYKTBDBean.getYktPaySetPara() == null ? null : df.format(Double.valueOf(productYKTBDBean.getYktPaySetPara()) / 100));
                } else {
                    exportDto.setYktPaySetPara(productYKTBDBean.getYktPaySetPara());
                }
                exportDto.setYktConsumeLimitStartTime(productYKTBDBean.getYktConsumeLimitStartTime());
                exportDto.setYktConsumeLimitEndTime(productYKTBDBean.getYktConsumeLimitEndTime());
                exportDto.setYktCardMaxLimit(productYKTBDBean.getYktCardMaxLimit() == null ? null : df.format(Double.valueOf(productYKTBDBean.getYktCardMaxLimit()) / 100));
                exportDto.setYktIpAddress(productYKTBDBean.getYktIpAddress());
                if (productYKTBDBean.getYktPort() != null) {
                    exportDto.setYktPort(productYKTBDBean.getYktPort().toString());
                }
                exportDto.setYktLinkUser(productYKTBDBean.getYktLinkUser());
                exportDto.setYktMobile(productYKTBDBean.getYktMobile());
                exportDto.setYktTel(productYKTBDBean.getYktTel());
                exportDto.setRemark(productYKTBDBean.getRemark());
                exportDto.setCreateUser(productYKTBDBean.getCreateUser());
                exportDto.setCreateDate(DateUtils.formatDate(DateUtils.stringtoDate(DateUtils.dateToString(productYKTBDBean.getCreateDate(), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR));
                exportDto.setUpdateUser(productYKTBDBean.getUpdateUser());
                exportDto.setUpdateDate(DateUtils.formatDate(DateUtils.stringtoDate(DateUtils.dateToString(productYKTBDBean.getUpdateDate(), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR));
                
                exportList.add(exportDto);
            }
            
            String sheetName = new String("通卡公司基础信息");
            
            Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
            String resultCode = ExcelUtil.excelExport(req, res, exportList, col, sheetName);
            
            response.setCode(resultCode);
            
            return response;
        }
        catch (Exception e) {
            logger.error("导出通卡公司基础信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    /**************************************************** 基础信息管理结束 *****************************************************/
    
    /**************************************************** 额度信息管理开始 *****************************************************/
    /**
     * 额度管理初始页面
     * @param request
     * @return
     */
    @RequestMapping("limit")
    public ModelAndView initLimitInfo(HttpServletRequest request) {
        return new ModelAndView("product/supplier/yktLimitInfo");
    }
    
    /**
     * 额度信息管理分页查找
     * @param request
     * @return
     */
    @RequestMapping("findProductYktLimitInfoByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProductYktLimitInfoBean>> findProductYktLimitInfoByPage(HttpServletRequest request,@RequestBody ProductYktLimitInfoFindBean findDto) {
        DodopalResponse<DodopalDataPage<ProductYktLimitInfoBean>> response = new DodopalResponse<DodopalDataPage<ProductYktLimitInfoBean>>();
        try {
            response = icdcService.findProductYktLimitInfoByPage(findDto);
        }
        catch (Exception e) {
            logger.error("查询通卡公司额度信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 通卡额度信息导出
     * @param request
     * @return
     */
    @RequestMapping("exportExcelProductYktLimit")
    public @ResponseBody DodopalResponse<String> exportExcelProductYktLimit(HttpServletRequest req, HttpServletResponse res) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            ProductYktLimitInfoFindBean findDto = new ProductYktLimitInfoFindBean();
            findDto.setYktName(req.getParameter("yktName"));
            
            DodopalResponse<List<ProductYktLimitInfoBean>> listResponse = icdcService.getProductYktLimitListForExportExcel(findDto);
            response.setCode(listResponse.getCode());
            
            if (!ResponseCode.SUCCESS.equals(listResponse.getCode())) {
                return response;
            }
            
            List<ProductYktLimitInfoForExport> exportList = new ArrayList<ProductYktLimitInfoForExport>();
            ProductYktLimitInfoForExport exportDtoExport = null;
            DecimalFormat df = new DecimalFormat("0.00");
            for (ProductYktLimitInfoBean productYktLimitInfoBean: listResponse.getResponseEntity()) {
                exportDtoExport = new ProductYktLimitInfoForExport();
                exportDtoExport.setYktCode(productYktLimitInfoBean.getYktCode());
                exportDtoExport.setYktName(productYktLimitInfoBean.getYktName());
                if (productYktLimitInfoBean.getYktWarnLimit() != null) {
                    exportDtoExport.setYktWarnLimit(productYktLimitInfoBean.getYktWarnLimit() == null ? null : df.format(Double.valueOf(productYktLimitInfoBean.getYktWarnLimit()) / 100));
                }
                if (productYktLimitInfoBean.getYktStopLimit() != null) {
                    exportDtoExport.setYktStopLimit(productYktLimitInfoBean.getYktStopLimit() == null ? null : df.format(Double.valueOf(productYktLimitInfoBean.getYktStopLimit()) / 100));
                }
                if (productYktLimitInfoBean.getTotalAmtLimit() != null) {
                    exportDtoExport.setTotalAmtLimit(productYktLimitInfoBean.getTotalAmtLimit() == null ? null : df.format(Double.valueOf(productYktLimitInfoBean.getTotalAmtLimit()) / 100));
                }
                if (productYktLimitInfoBean.getSurPlusLimit() != null) {
                    exportDtoExport.setSurPlusLimit(productYktLimitInfoBean.getSurPlusLimit() == null ? null : df.format(Double.valueOf(productYktLimitInfoBean.getSurPlusLimit()) / 100));
                }
                if (productYktLimitInfoBean.getUsedLimit() != null) {
                    exportDtoExport.setUsedLimit(productYktLimitInfoBean.getUsedLimit() == null ? null : df.format(Double.valueOf(productYktLimitInfoBean.getUsedLimit()) / 100));
                }
                exportDtoExport.setYktExpireDate(DateUtils.dateToString(productYktLimitInfoBean.getYktExpireDate(),DateUtils.DATE_SMALL_STR));
                exportDtoExport.setRemark(productYktLimitInfoBean.getRemark());
                exportDtoExport.setCreateUser(productYktLimitInfoBean.getCreateUser());
                exportDtoExport.setCreateDate(DateUtils.formatDate(DateUtils.stringtoDate(DateUtils.dateToString(productYktLimitInfoBean.getCreateDate(), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR));
                exportDtoExport.setUpdateUser(productYktLimitInfoBean.getUpdateUser());
                exportDtoExport.setUpdateDate(DateUtils.formatDate(DateUtils.stringtoDate(DateUtils.dateToString(productYktLimitInfoBean.getUpdateDate(), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR));
                
                exportList.add(exportDtoExport);
            }
            
            String sheetName = new String("通卡公司额度信息");
            
            Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
            String resultCode = ExcelUtil.excelExport(req, res, exportList, col, sheetName);
            
            response.setCode(resultCode);
            
            return response;
        }
        catch (Exception e) {
            logger.error("导出通卡公司基础信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 查询额度明细信息
     * @return
     */
    @RequestMapping("findProductYktLimitInfoById")
    public @ResponseBody DodopalResponse<ProductYktLimitInfoBean>  findProductYktLimitInfoById(HttpServletRequest request,@RequestBody String id){
       
        DodopalResponse<ProductYktLimitInfoBean> response = new DodopalResponse<ProductYktLimitInfoBean>();
        try {
            response = icdcService.findProductYktLimitInfoById(id);
        }
        catch (Exception e) {
            logger.error("查询通卡公司额度明细信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
     return response;
   }
    
    /**
     * 保存额度基本信息
     * @param request
     * @return
     */
    @RequestMapping("saveProductYktLimitInfo")
    public @ResponseBody DodopalResponse<Integer> saveProductYktLimitInfo(HttpServletRequest request, @RequestBody ProductYktLimitInfoBean productYktLimitInfoBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            productYktLimitInfoBean.setUpdateUser(generateLoginUserId(request));
            response = icdcService.saveProductYktLimitInfo(productYktLimitInfoBean);
        }
        catch (Exception e) {
            logger.error("保存通卡公司额度信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**************************************************** 额度信息管理结束 *****************************************************/
    
    /**************************************************** 额度购买批次信息管理开始 *****************************************************/
    /**
     * 额度购买批次信息管理初始页面
     * @param request
     * @return
     */
    @RequestMapping("batch")
    public ModelAndView initBatchInfo(HttpServletRequest request) {
        return new ModelAndView("product/supplier/yktLimitBatchInfo");
    }
    
    /**
     * 分页查询额度购买批次信息管理
     * @param request
     * @return
     */
    @RequestMapping("findYktLimitBatchInfoByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoBean>> findProductYktLimitBatchInfoByPage(HttpServletRequest request,@RequestBody ProductYktLimitBatchInfoFindBean findDto) {
        DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoBean>> response = new DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoBean>>();
        try {
            response = icdcService.findProductYktLimitBatchInfoByPage(findDto);
        }
        catch (Exception e) {
            logger.error("分页查询通卡公司额度批次信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 查看额度购买批次信息详细
     * @param request
     * @return
     */
    @RequestMapping("findYktLimitBatchInfoById")
    public @ResponseBody DodopalResponse<ProductYktLimitBatchInfoBean> findYktLimitbatchInfoById(HttpServletRequest request,@RequestBody String id) {
        DodopalResponse<ProductYktLimitBatchInfoBean> response = new DodopalResponse<ProductYktLimitBatchInfoBean>();
        try {
            response = icdcService.findProductYktLimitBatchInfoById(id);
        }
        catch (Exception e) {
            logger.error("查看通卡公司额度批次信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
   
    /**
     * 追加额度购买申请单
     * @param request
     * @return
     */
    @RequestMapping("applyAddYktLimitBatchInfo")
    public @ResponseBody DodopalResponse<Integer> applyAddYktLimitBatchInfo(HttpServletRequest request, @RequestBody ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            User applyUser = super.generateLoginUser(request);
            productYktLimitBatchInfoBean.setCreateUser(applyUser.getId());
            productYktLimitBatchInfoBean.setApplyUser(applyUser.getId());
            productYktLimitBatchInfoBean.setApplyUserName(applyUser.getName());
            productYktLimitBatchInfoBean.setAuditState(AuditStateEnum.UN_AUDIT.getCode());
            response = icdcService.addProductYktLimitBatchInfo(productYktLimitBatchInfoBean);
        }
        catch (Exception e) {
            logger.error("申请购买额度出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 删除额度购买申请单
     * @param request
     * @return
     */
    @RequestMapping("deleteYktLimitBatchInfo")
    public @ResponseBody DodopalResponse<Integer> deleteYktLimitBatchInfo(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = icdcService.deleteProductYktLimitBatchInfo(id);
        }
        catch (Exception e) {
            logger.error("删除额度购买申请单出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 修改额度购买申请单
     * @param request
     * @return
     */
    @RequestMapping("saveYktLimitBatchInfo")
    public @ResponseBody DodopalResponse<Integer> saveYktLimitBatchInfo(HttpServletRequest request, @RequestBody ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            productYktLimitBatchInfoBean.setUpdateUser(super.generateLoginUserId(request));
            response = icdcService.saveProductYktLimitBatchInfo(productYktLimitBatchInfoBean);
        }
        catch (Exception e) {
            logger.error("修改通卡公司额度批次信息出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 审核额度购买申请单
     * @param request
     * @return
     */
    @RequestMapping("aduitYktLimitBatchInfo")
    public @ResponseBody DodopalResponse<Integer> aduitYktLimitBatchInfo(HttpServletRequest request, @RequestBody ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            productYktLimitBatchInfoBean.setUpdateUser(super.generateLoginUserId(request));
            response = icdcService.aduitYktLimitBatchInfo(productYktLimitBatchInfoBean);
        }
        catch (Exception e) {
            logger.error("审核额度购买申请单出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 复核额度购买申请单
     * @param request
     * @return
     */
    @RequestMapping("checkYktLimitBatchInfo")
    public @ResponseBody DodopalResponse<Integer> checkYktLimitBatchInfo(HttpServletRequest request, @RequestBody ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            productYktLimitBatchInfoBean.setUpdateUser(super.generateLoginUserId(request));
            response = icdcService.checkYktLimitBatchInfo(productYktLimitBatchInfoBean);
        }
        catch (Exception e) {
            logger.error("复核额度购买申请单出错："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 额度购买批次信息导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("exportYktLimitBatchInfo")
    public @ResponseBody DodopalResponse<String> exportYktLimitBatchInfo(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = new DodopalResponse<String>();
        try {
            ProductYktLimitBatchInfoFindBean queryDto = new ProductYktLimitBatchInfoFindBean();
            queryDto.setApplyAmtEnd(request.getParameter("applyAmtEnd"));
            queryDto.setApplyAmtStart(request.getParameter("applyAmtStart"));
            queryDto.setApplyDateEnd(DateUtils.stringtoDate(request.getParameter("applyDateEnd"), DateUtils.DATE_SMALL_STR));
            queryDto.setApplyDateStart(DateUtils.stringtoDate(request.getParameter("applyDateStart"), DateUtils.DATE_SMALL_STR));
            queryDto.setAuditDateEnd(DateUtils.stringtoDate(request.getParameter("auditDateEnd"), DateUtils.DATE_SMALL_STR));
            queryDto.setAuditDateStart(DateUtils.stringtoDate(request.getParameter("auditDateStart"), DateUtils.DATE_SMALL_STR));
            queryDto.setAuditState(request.getParameter("auditState"));
            queryDto.setApplyUserName(request.getParameter("applyUserName"));
            queryDto.setAuditUserName(request.getParameter("auditUserName"));
            queryDto.setYktAddLimitEnd(request.getParameter("yktAddLimitEnd"));
            queryDto.setYktAddLimitStart(request.getParameter("yktAddLimitStart"));
            queryDto.setYktName(request.getParameter("yktName"));
            queryDto.setFinancialPayAmtStart(request.getParameter("financialPayAmtStart"));
            queryDto.setFinancialPayAmtEnd(request.getParameter("financialPayAmtEnd"));
            queryDto.setCheckUserName(request.getParameter("checkUserName"));
            queryDto.setCheckDateEnd(DateUtils.stringtoDate(request.getParameter("checkDateEnd"), DateUtils.DATE_SMALL_STR));
            queryDto.setCheckDateStart(DateUtils.stringtoDate(request.getParameter("checkDateStart"), DateUtils.DATE_SMALL_STR));
            queryDto.setCheckState(request.getParameter("checkState"));
            
            DodopalResponse<List<ProductYktLimitBatchInfoBean>> list = icdcService.getProductYktLimitBatchListForExportExcel(queryDto);
            exportResponse.setCode(list.getCode());
            
            if (!ResponseCode.SUCCESS.equals(list.getCode())) {
                return exportResponse;
            }
            List<ProductYktLimitBatchInfoForExport>  listExport = new ArrayList<ProductYktLimitBatchInfoForExport>();
            ProductYktLimitBatchInfoForExport exportDto = null;
            DecimalFormat df = new DecimalFormat("0.00");
            for (ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean:list.getResponseEntity()) {
                exportDto = new ProductYktLimitBatchInfoForExport();
                exportDto.setYktCode(productYktLimitBatchInfoBean.getYktCode());
                exportDto.setYktName(productYktLimitBatchInfoBean.getYktName());
                if (productYktLimitBatchInfoBean.getBatchCode() != null) {
                    exportDto.setBatchCode(productYktLimitBatchInfoBean.getBatchCode().toString());
                }
                if (productYktLimitBatchInfoBean.getApplyAmtLimit() != null) {
                    exportDto.setApplyAmtLimit(productYktLimitBatchInfoBean.getApplyAmtLimit() == null ? null : df.format(Double.valueOf(productYktLimitBatchInfoBean.getApplyAmtLimit()) / 100));
                }
                exportDto.setApplyUserName(productYktLimitBatchInfoBean.getApplyUserName());
                exportDto.setApplyDate(DateUtils.dateToString(productYktLimitBatchInfoBean.getApplyDate(), DateUtils.DATE_SMALL_STR));
                exportDto.setApplyExplaination(productYktLimitBatchInfoBean.getApplyExplaination());
                exportDto.setAuditUserName(productYktLimitBatchInfoBean.getAuditUserName());
                exportDto.setAuditDate(DateUtils.dateToString(productYktLimitBatchInfoBean.getAuditDate(), DateUtils.DATE_SMALL_STR));
                exportDto.setAuditExplaination(productYktLimitBatchInfoBean.getAuditExplaination());
                exportDto.setAuditState(productYktLimitBatchInfoBean.getAuditStateView());
                exportDto.setPaymentChannel(productYktLimitBatchInfoBean.getPaymentChannel());
                if (productYktLimitBatchInfoBean.getYktAddLimit() != null) {
                    exportDto.setYktAddLimit(productYktLimitBatchInfoBean.getYktAddLimit() == null ? null : df.format(Double.valueOf(productYktLimitBatchInfoBean.getYktAddLimit()) / 100));
                }
                exportDto.setFinancialPlayDate(DateUtils.dateToString(productYktLimitBatchInfoBean.getFinancialPayDate(), DateUtils.DATE_SMALL_STR));
                if (productYktLimitBatchInfoBean.getFinancialPayFee() != null) {
                    exportDto.setFinancialPlayFee(productYktLimitBatchInfoBean.getFinancialPayFee() == null ? null : df.format(Double.valueOf(productYktLimitBatchInfoBean.getFinancialPayFee()) / 100));
                }
                exportDto.setRemark(productYktLimitBatchInfoBean.getRemark());
                
                if (productYktLimitBatchInfoBean.getFinancialPayAmt() != null) {
                    exportDto.setFinancialPayAmt(productYktLimitBatchInfoBean.getFinancialPayAmt() == null ? null : df.format(Double.valueOf(productYktLimitBatchInfoBean.getFinancialPayAmt()) / 100));
                }
                exportDto.setYktAddLimitDate(DateUtils.dateToString(productYktLimitBatchInfoBean.getYktAddLimitDate(), DateUtils.DATE_SMALL_STR));
                
                exportDto.setCheckUserName(productYktLimitBatchInfoBean.getCheckUserName());
                exportDto.setCheckDate(DateUtils.dateToString(productYktLimitBatchInfoBean.getCheckDate(), DateUtils.DATE_SMALL_STR));
                exportDto.setCheckExplaination(productYktLimitBatchInfoBean.getCheckExplaination());
                exportDto.setCheckState(productYktLimitBatchInfoBean.getCheckStateView());
                
                exportDto.setCreateUser(productYktLimitBatchInfoBean.getCreateUser());
                exportDto.setCreateDate(DateUtils.formatDate(DateUtils.stringtoDate(DateUtils.dateToString(productYktLimitBatchInfoBean.getCreateDate(), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR));
                exportDto.setUpdateUser(productYktLimitBatchInfoBean.getUpdateUser());
                exportDto.setUpdateDate(DateUtils.formatDate(DateUtils.stringtoDate(DateUtils.dateToString(productYktLimitBatchInfoBean.getUpdateDate(), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR));
                
                listExport.add(exportDto);
            }
            
            String sheetName = new String("通卡公司额度批次信息");
            
            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            String resultCode = ExcelUtil.excelExport(request, response, listExport, col, sheetName);
            
            exportResponse.setCode(resultCode);
            
            return exportResponse;
        }
        catch (Exception e) {
            logger.error("额度购买批次信息导出导出出错：" + e.getMessage(), e);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }
    
    /**
     * 获取通卡公司combox下拉列表信息
     * @param request
     * @return
     */
    @RequestMapping(value = "findAllYktNames")
    public @ResponseBody List<Map<String, String>> getIcdcNames(HttpServletRequest request,@RequestParam(value = "activate", required = false) String activate) {
        DodopalResponse<List<Map<String, String>>> rs = null;
        List<Map<String, String>> jsonData = new ArrayList<>();
        rs = icdcPrdService.queryIcdcNames(activate);
        List<Map<String, String>> mapList = rs.getResponseEntity();
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, String> value = new HashMap<>();
            Map<String, String> map = mapList.get(i);
            Set<String> k = map.keySet();
            for (String key : k) {
                value.put("id", key);
                value.put("name", map.get(key));
                jsonData.add(value);
                break;
            }
        }
        return jsonData;
    }
    
    /**************************************************** 额度购买批次信息管理结束 *****************************************************/
    
   
    
    /**
     * 通卡公司业务城市选择(By 拼音首字母)
     * 
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("showBussinessCity")
    public @ResponseBody DodopalResponse<BusinessCity> showBussinessCity(HttpServletRequest request, @RequestBody String[] param) {
        DodopalResponse<BusinessCity> response = new DodopalResponse<BusinessCity>();
        try {
            BusinessCity result = new BusinessCity();
            List<BusinessCity> list= icdcService.getBusinessCity(param);
            result.setList(list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 通卡公司业务城市选择(By 城市名称拼音模糊查询)
     * 
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("showBussinessCityByName")
    public @ResponseBody DodopalResponse<BusinessCity> showBussinessCityByName(HttpServletRequest request, @RequestBody String param) {
        DodopalResponse<BusinessCity> response = new DodopalResponse<BusinessCity>();
        try {
            BusinessCity result = new BusinessCity();
            List<BusinessCity> list= icdcService.getBusinessCityByName(param);
            result.setList(list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
}


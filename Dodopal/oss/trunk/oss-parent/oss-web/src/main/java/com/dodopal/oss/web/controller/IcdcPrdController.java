package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.IcdcPrdService;
import com.dodopal.oss.delegate.bean.IcdcPrdBean;
import com.dodopal.oss.delegate.bean.IcdcPrdQuery;
import com.dodopal.oss.delegate.bean.IcdcPrductInfoForExport;

/**
 * 产品库-一卡通业务
 * @author
 */
@Controller("icdcPrdController")
@RequestMapping("product/buscardbusiness")
public class IcdcPrdController extends CommonController {
    
    @Autowired
    private IcdcPrdService icdcPrdService;
    
    @Autowired
    private ExpTempletService expTempletService;

    /**
     * 主界面
     * @param request
     * @return
     */
    @RequestMapping("/icdcprd")
    public ModelAndView basic(HttpServletRequest request) {
        return new ModelAndView("product/buscardbusiness/icdcprd");
    }

    /**
     * 一卡通产品按条件分页查找
     * @param request
     * @param query 查询参数
     * @return
     */
    @RequestMapping(value = {"/queryIcdcPrdPageByCondition"})
    public @ResponseBody DodopalResponse<DodopalDataPage<IcdcPrdBean>> queryIcdcPrdPageByCondition(HttpServletRequest request, @RequestBody IcdcPrdQuery query) {
        DodopalResponse<DodopalDataPage<IcdcPrdBean>> response = icdcPrdService.queryIcdcPrdPageByCondition(query);
        return response;
    }

    /**
     * 根据一卡通产品编码查询产品信息
     * @param request
     * @param code ICDC产品编码，全局唯一
     * @return
     */
    @RequestMapping("/queryIcdcPrdByCode")
    public @ResponseBody DodopalResponse<IcdcPrdBean> queryIcdcPrdByCode(HttpServletRequest request, @RequestBody String code) {
        DodopalResponse<IcdcPrdBean> response = icdcPrdService.queryIcdcPrdByCode(code);
        return response;
    }

    /**
     * 获取通卡公司对应业务城市
     * @param request
     * @return
     */
    @RequestMapping(value = "/getIcdcBusiCities")
    public @ResponseBody List<Map<String, String>> getIcdcBusiCities(HttpServletRequest request, @RequestParam(value = "code", required = true) String code) {
        DodopalResponse<List<Map<String, String>>> rs = null;
        List<Map<String, String>> jsonData = new ArrayList<>();
        rs = icdcPrdService.queryIcdcBusiCities(code);
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

    /**
     * 获取通卡公司
     * @param request
     * @return
     */
    @RequestMapping(value = "/getIcdcNames")
    public @ResponseBody List<Map<String, String>> getIcdcNames(HttpServletRequest request, @RequestParam(value = "activate", required = false) String activate) {
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

    /**
     * 渲染一卡通充值产品编辑页面
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "/rendingIcdcPrdEditDialog")
    public @ResponseBody DodopalResponse<IcdcPrdBean> rendingIcdcPrdEditDialog(HttpServletRequest request, @RequestBody String data) {
        DodopalResponse<IcdcPrdBean> response = null;
        DodopalResponse<IcdcPrdBean> rs = icdcPrdService.queryIcdcPrdByCode(data);
        response = rs;
        return response;
    }

    /**
     * 保存一卡通产品
     * @param request
     * @param icdcPrdBean 要保存的数据
     * @return 响应体
     */
    @RequestMapping(value = "/saveIcdcPrd", method = {RequestMethod.POST})
    public @ResponseBody DodopalResponse<String> saveIcdcPrd(HttpServletRequest request, @RequestBody List<IcdcPrdBean> icdcPrdBean) {
        DodopalResponse<String> response = null;
        if (icdcPrdBean != null && icdcPrdBean.size() > 0) {
            User loginUser = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
            for (int i = 0; i < icdcPrdBean.size(); i++) {
                IcdcPrdBean bean = icdcPrdBean.get(i);
                bean.setCreateUser(loginUser.getId());
                bean.setCreateDate(new Date());
            }
            response = icdcPrdService.saveIcdcPrd(icdcPrdBean);
        } else {
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.OSS_PERSIS_UPDATE_NULL);
            response.setMessage(ResponseCode.OSS_PERSIS_UPDATE_NULL);
        }
        return response;
    }

    /**
     * 更新一卡通产品
     * @param request
     * @param icdcPrdBean 要更新的数据
     * @return 响应体
     */
    @RequestMapping(value = "/updateIcdcPrd", method = {RequestMethod.POST})
    public @ResponseBody DodopalResponse<String> updateIcdcPrd(HttpServletRequest request, @RequestBody List<IcdcPrdBean> icdcPrdBean) {
        DodopalResponse<String> response = null;
        User loginUser = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
        for (int i = 0; i < icdcPrdBean.size(); i++) {
            IcdcPrdBean bean = icdcPrdBean.get(i);
            bean.setUpdateUser(loginUser.getId());
            bean.setUpdateDate(new Date());
        }
        if (icdcPrdBean != null && icdcPrdBean.size() > 0) {
            response = icdcPrdService.updateIcdcPrd(icdcPrdBean);
        } else {
            response = new DodopalResponse<>();
            response.setCode(ResponseCode.OSS_PERSIS_UPDATE_NULL);
            response.setMessage(ResponseCode.OSS_PERSIS_UPDATE_NULL);
        }
        return response;
    }

    /**
     * 上下架一卡通产品
     * @param request
     * @param icdcCodes 产品编码
     * @param activate 是否激活
     * @return 响应体
     */
    @RequestMapping(value = "/putAwayOrSoltOutIcdcPrd")
    public @ResponseBody DodopalResponse<String> putAwayOrSoltOutIcdcPrd(HttpServletRequest request, @RequestBody List<String> icdcCodes, @RequestParam(value = "activate") String activate) {
        DodopalResponse<String> response = new DodopalResponse<>();
        User loginUser = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
        if (activate != null && activate.equals("0")) {
            response = icdcPrdService.putAwayIcdcPrd(icdcCodes, loginUser.getUserId());
        } else if (activate != null && activate.equals("1")) {
            response = icdcPrdService.soltOutIcdcPrd(icdcCodes, loginUser.getUserId());
        }
        return response;
    }
    
    /**
     * 一卡通产品信息导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportIcdcPrductInfo")
    public @ResponseBody DodopalResponse<String> exportIcdcPrductInfo(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = new DodopalResponse<String>();
        try {
            IcdcPrdQuery query = new IcdcPrdQuery();
            query.setNameQuery(request.getParameter("nameQuery"));            
            query.setCityQuery(request.getParameter("cityQuery"));                        
            query.setSupplierQuery(request.getParameter("supplierQuery"));            
            query.setValueQuery(request.getParameter("valueQuery"));
            query.setSaleUporDownQuery(request.getParameter("saleUporDownQuery"));
            
            DodopalResponse<List<IcdcPrdBean>> list = icdcPrdService.getIcdcPrductListForExportExcel(query);
            exportResponse.setCode(list.getCode());
            
            if (!ResponseCode.SUCCESS.equals(list.getCode())) {
                return exportResponse;
            }
            List<IcdcPrductInfoForExport>  listExport = new ArrayList<IcdcPrductInfoForExport>();
            IcdcPrductInfoForExport exportDto = null;
            for (IcdcPrdBean icdcPrdBean:list.getResponseEntity()) {
                exportDto = new IcdcPrductInfoForExport();
                exportDto.setProductCode(icdcPrdBean.getCode());
                exportDto.setProductName(icdcPrdBean.getName());
                exportDto.setSupplierName(icdcPrdBean.getSupplier());
                exportDto.setCityName(icdcPrdBean.getCity());
                if (icdcPrdBean.getRate() != null) {
                    exportDto.setRate(icdcPrdBean.getRate().toString());
                }
                if (icdcPrdBean.getCostPrice() != null) {
                    exportDto.setCostPrice(icdcPrdBean.getCostPrice());
                }
                if (icdcPrdBean.getValuePrice() != null) {
                    exportDto.setValuePrice(icdcPrdBean.getValuePrice());
                }
                exportDto.setSaleUporDown(icdcPrdBean.getSaleUporDown());
                exportDto.setRemark(icdcPrdBean.getComments());
                
                exportDto.setCreateUser(icdcPrdBean.getCreateUser());
                exportDto.setCreateDate(DateUtils.formatDate(DateUtils.stringtoDate(DateUtils.dateToString(icdcPrdBean.getCreateDate(), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR));
                exportDto.setUpdateUser(icdcPrdBean.getUpdateUser());
                exportDto.setUpdateDate(DateUtils.formatDate(DateUtils.stringtoDate(DateUtils.dateToString(icdcPrdBean.getUpdateDate(), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR_DB), DateUtils.DATE_FULL_STR));
                
                listExport.add(exportDto);
            }
            String sheetName = new String("一卡通产品信息");
            
            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            String resultCode = ExcelUtil.excelExport(request, response, listExport, col, sheetName);
            
            exportResponse.setCode(resultCode);
            
            return exportResponse;
        }
        catch (Exception e) {
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }
}

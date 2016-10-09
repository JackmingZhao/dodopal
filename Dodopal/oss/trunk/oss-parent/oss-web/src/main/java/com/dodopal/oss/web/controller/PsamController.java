package com.dodopal.oss.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.card.dto.PosSignInOutDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.YktPsamBean;
import com.dodopal.oss.business.bean.query.YktPsamQuery;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.IcdcPrdService;
import com.dodopal.oss.business.service.PsamService;

/**
 * PSAM管理
 * @author lenovo
 */
@Controller
@RequestMapping("/basic/psamMgmt")
public class PsamController extends CommonController {

    @Autowired
    private PsamService psamService;

    @Autowired
    private ExpTempletService expTempletService;

    @Autowired
    private IcdcPrdService icdcPrdService;

    /**
     * PSAM卡管理
     * @param request
     * @return
     */
    @RequestMapping("/psam")
    public ModelAndView type(HttpServletRequest request) {
        return new ModelAndView("basic/psamMgmt/psam");
    }

    //查询分页
    @RequestMapping("/findYktPsamByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<YktPsamBean>> findYktPsamByPage(HttpServletRequest request, @RequestBody YktPsamQuery yktPsamQuery) {
        DodopalResponse<DodopalDataPage<YktPsamBean>> response = new DodopalResponse<DodopalDataPage<YktPsamBean>>();
        try {
            response = psamService.findYktPsamByPage(yktPsamQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/importPsam")
    public @ResponseBody DodopalResponse<String> importPos(@RequestParam("psamFile") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response2) {
        response2.setContentType("text/json");
        response2.setCharacterEncoding("UTF-8");
        DodopalResponse<String> response = null;
        try {
            String currentUserId = generateLoginUserId(request);
            return psamService.importPsam(file, currentUserId);
        }
        catch (DDPException ddp) {
            ddp.printStackTrace();
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.OSS_BATCH_UPDATE_ERROR);
            response.setMessage(ddp.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    //导出
    @RequestMapping("/exportPsam")
    public @ResponseBody DodopalResponse<String> exportPsam(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        try {
            YktPsamQuery yktPsamQuery = new YktPsamQuery();
            yktPsamQuery.setSamNo(request.getParameter("samNo"));
            yktPsamQuery.setYktCode(request.getParameter("yktCode"));
            yktPsamQuery.setYktName(request.getParameter("yktName"));
            yktPsamQuery.setPosId(request.getParameter("posId"));
            yktPsamQuery.setMchntid(request.getParameter("mchntid"));
            int exportMaxNum = 5000;
            yktPsamQuery.setPage(new PageParameter(1, exportMaxNum));

            List<YktPsamBean> list = null;
            DodopalResponse<DodopalDataPage<YktPsamBean>> rtresponse = psamService.findYktPsamByPage(yktPsamQuery);
            if (ResponseCode.SUCCESS.equals(rtresponse.getCode())) {
                list = rtresponse.getResponseEntity().getRecords();
            }
            int resultSize = list.size();
            if (resultSize > 5000) {
                // logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
                rep.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                return rep;
            }
            String sheetName = new String("PSAM卡");

            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            String resultCode = ExcelUtil.excelExport(request, response, list, col, sheetName);

            rep.setCode(resultCode);

        }
        catch (Exception e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return rep;
    }

    //删除
    @RequestMapping("/batchDeleteYktPsamByIds")
    public @ResponseBody DodopalResponse<String> batchDeleteYktPsamByIds(HttpServletRequest request, @RequestBody List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            response = psamService.batchDeleteYktPsamByIds(ids);

        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    //根据id查询psam详细信息
    @RequestMapping("/findPsamById")
    public @ResponseBody DodopalResponse<YktPsamBean> findPsamById(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<YktPsamBean> response = new DodopalResponse<YktPsamBean>();
        try {
            response = psamService.findPsamById(id);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    
    
    /**
     * V61签到签退
     * @param posSignInOutDTO
     * @return
     */
    @RequestMapping("/posSignInOutForV61")
    public @ResponseBody DodopalResponse<PosSignInOutDTO> posSignInOutForV61(HttpServletRequest request, @RequestBody PosSignInOutDTO posSignInOutDTO){
        DodopalResponse<PosSignInOutDTO> response = new DodopalResponse<PosSignInOutDTO>();
        try {
            posSignInOutDTO.setReserved("0000000000");//保留域
            posSignInOutDTO.setRespcode("000000");//响应码
            posSignInOutDTO.setSysdatetime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//发送时间   当前系统时间
            response = psamService.posSignInOutForV61(posSignInOutDTO);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 保存psam卡信息
     * @param request
     * @param  要保存的数据
     * @return 响应体
     */
    @RequestMapping(value = "/addYktPsam", method = {RequestMethod.POST})
    public @ResponseBody DodopalResponse<String> addYktPsam(HttpServletRequest request, @RequestBody YktPsamBean yktPsamBean) {
        DodopalResponse<String> response = null;
        try {
            User loginUser = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
            yktPsamBean.setCreateUser(loginUser.getUserId());
            if(StringUtils.isNotEmpty(yktPsamBean.getId())){
                response = psamService.updateYktPsam(yktPsamBean);
            }else{
                yktPsamBean.setSettDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));//结算日期，当前系统时间
                yktPsamBean.setSigninFlag("0");//签退标志
                yktPsamBean.setSignoffFlag("1");//签退标志
                yktPsamBean.setNeedDownLoad("2");//2：新下载参数
                yktPsamBean.setBatchStep("4");//日切步骤控制
                response = psamService.addYktPsam(yktPsamBean);
            }
       
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return response;
    }

    
    /**
     * 启用商户
     * @param request
     * @param  要保存的数据
     * @return 响应体
     */
    @RequestMapping(value = "/batchActivateMerByIds", method = {RequestMethod.POST})
    public @ResponseBody DodopalResponse<String> batchActivateMerByIds(HttpServletRequest request, @RequestBody List<String> ids) {
        DodopalResponse<String> response = null;
        try {
            response = psamService.batchActivateMerByIds(ids);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return response;
    }
    
   
    
    
    /**
     * 批量修改查询下载参数
     * @param request
     * @param  要保存的数据
     * @return 响应体
     */
    @RequestMapping(value = "/batchNeedDownLoadPsamByIds", method = {RequestMethod.POST})
    public @ResponseBody DodopalResponse<String> batchNeedDownLoadPsamByIds(HttpServletRequest request, @RequestBody List<String> ids) {
        DodopalResponse<String> response = null;
        try {
            response = psamService.batchNeedDownLoadPsamByIds(ids);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
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
    @RequestMapping("/getIcdcNames")
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

}

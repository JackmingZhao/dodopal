package com.dodopal.oss.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.model.Pos;
import com.dodopal.oss.business.model.PosInfo;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.PosOperationDTO;
import com.dodopal.oss.business.model.dto.PosQuery;
import com.dodopal.oss.business.model.dto.PosUpdateBatch;
import com.dodopal.oss.business.service.PosService;

/**
 * POS管理
 */
@Controller
@RequestMapping("/basic")
public class PosController extends CommonController {

    @Autowired
    private PosService posService;

    @Autowired
    private ExpTempletService expTempletService;

    /**************************************************** POS开始 *****************************************************/
    /**
     * POS初始页面
     * @param request
     * @return
     */
    @RequestMapping("pos/pos")
    public ModelAndView pos(HttpServletRequest request) {
        return new ModelAndView("basic/pos/pos");
    }

    @RequestMapping("pos/savePos")
    public @ResponseBody DodopalResponse<String> savePos(HttpServletRequest request, @RequestBody Pos pos) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String currentUserId = generateLoginUserId(request);
            if (StringUtils.isEmpty(pos.getId())) {
                pos.setCreateUser(currentUserId);
            }
            pos.setUpdateUser(generateLoginUserId(request));
            String result = posService.saveOrUpdatePos(pos);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (DDPException ddp) {
            ddp.printStackTrace();
            response.setCode(ResponseCode.OSS_DDIC_VALIDATOR);
            response.setMessage(ddp.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("pos/findPos")
    public @ResponseBody DodopalResponse<DodopalDataPage<PosInfo>> findPos(HttpServletRequest request, @RequestBody PosQuery posQuery) {
        DodopalResponse<DodopalDataPage<PosInfo>> response = new DodopalResponse<DodopalDataPage<PosInfo>>();
        try {
            DodopalDataPage<PosInfo> result = posService.findPosByPage(posQuery);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("pos/findPosById")
    public @ResponseBody DodopalResponse<PosInfo> findPosById(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<PosInfo> response = new DodopalResponse<PosInfo>();
        try {
            PosInfo result = posService.findPosById(id);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("pos/deletePos")
    public @ResponseBody DodopalResponse<String> deletePos(HttpServletRequest request, @RequestBody String[] posId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            User user = generateLoginUser(request);
            response = posService.deletePos(posId, user);
        }
        catch (DDPException ddp) {
            ddp.printStackTrace();
            response.setCode(ResponseCode.OSS_POS_DELETE_BIND);
            response.setMessage(ddp.getMessage());
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * POS操作 绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param operUser 操作员信息
     * @return
     */
    @RequestMapping("pos/posOperation")
    public @ResponseBody DodopalResponse<String> posOper(HttpServletRequest request, @RequestBody PosOperationDTO operation) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            User user = generateLoginUser(request);
            response = posService.posOper(operation, user);
        }
        catch (DDPException e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("pos/modityCity")
    public @ResponseBody DodopalResponse<String> modityCity(HttpServletRequest request, @RequestBody PosUpdateBatch pos) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if (pos != null) {
                pos.setUpdateUser(generateLoginUserId(request));
                posService.modifyCity(pos);
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(ResponseCode.OSS_POS_BATCH_UPDATE_EMPTY);
            }
        }
        catch (DDPException e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("pos/modifyLimitation")
    public @ResponseBody DodopalResponse<String> modifyLimitation(HttpServletRequest request, @RequestBody PosUpdateBatch pos) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if (pos != null) {
                posService.modifyLimitation(pos.getCode(), pos.getMaxTimes(), generateLoginUserId(request));
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(ResponseCode.OSS_POS_BATCH_UPDATE_EMPTY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping("pos/modifyVersion")
    public @ResponseBody DodopalResponse<String> modifyVersion(HttpServletRequest request, @RequestBody PosUpdateBatch pos) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if (pos != null) {
                posService.modifyVersion(pos.getCode(), pos.getVersion(), generateLoginUserId(request));
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(ResponseCode.OSS_POS_BATCH_UPDATE_EMPTY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "pos/importPos")
    public @ResponseBody DodopalResponse<String> importPos(@RequestParam("posFile") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response2) {
        response2.setContentType("text/json");
        response2.setCharacterEncoding("UTF-8");
        DodopalResponse<String> response = null;
        try {
            String currentUserId = generateLoginUserId(request);
            return posService.importPos(file, currentUserId);
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

    /**
     * 导出Pos操作日志
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("pos/exportPos")
    public @ResponseBody DodopalResponse<String> exportPos(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        PosQuery posQuery = new PosQuery();
        try {
            posQuery.setBind(request.getParameter("bind"));
            posQuery.setCityCode(request.getParameter("cityCode"));
            posQuery.setCode(request.getParameter("code"));
            posQuery.setPosCompanyName(request.getParameter("posCompanyName"));
            posQuery.setPosTypeName(request.getParameter("posTypeName"));
            posQuery.setProvinceCode(request.getParameter("provinceCode"));
            posQuery.setStatus(request.getParameter("status"));
            posQuery.setVersion(request.getParameter("version"));
            int exportMaxNum = 5000;
            posQuery.setPage(new PageParameter(1, exportMaxNum));

            List<PosInfo> list = posService.findPosByList(posQuery);

            int resultSize = list.size();
            if (resultSize > 5000) {
                // logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
                rep.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                return rep;
            }
            String sheetName = new String("POS信息");

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

    /**************************************************** POS结束 *****************************************************/
}

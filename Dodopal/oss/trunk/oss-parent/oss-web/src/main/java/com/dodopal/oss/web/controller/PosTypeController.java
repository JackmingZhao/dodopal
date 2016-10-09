package com.dodopal.oss.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.model.PosType;
import com.dodopal.oss.business.model.dto.PosTypeQuery;
import com.dodopal.oss.business.service.PosTypeService;

/**
 * POSType管理
 *
 */
@Controller
@RequestMapping("/basic")
public class PosTypeController extends CommonController{

    @Autowired
    private PosTypeService typeService;

    
    /**************************************************** POS类型开始 *****************************************************/
    /**
     * POS类型初始页面
     * @param request
     * @return
     */
    @RequestMapping("pos/type")
    public ModelAndView type(HttpServletRequest request) {
        return new ModelAndView("basic/pos/posType");
    }

    @RequestMapping("pos/savePosType")
    public @ResponseBody DodopalResponse<String> savePosType(HttpServletRequest request, @RequestBody PosType type) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String currentUserId = generateLoginUserId(request);
            if (DDPStringUtil.isNotPopulated(type.getId())) {
                type.setCreateUser(currentUserId);
            }
            type.setUpdateUser(currentUserId);
            String result = typeService.saveOrUpdatePosType(type);
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

    @RequestMapping("pos/findPosType")
    public @ResponseBody DodopalResponse<DodopalDataPage<PosType>> findPosType(HttpServletRequest request, @RequestBody PosTypeQuery type) {
        DodopalResponse<DodopalDataPage<PosType>> response = new DodopalResponse<DodopalDataPage<PosType>>();
        try {
            DodopalDataPage<PosType> result = typeService.findPosTypeByPage(type);
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

    @RequestMapping("pos/findPosTypeById")
    public @ResponseBody DodopalResponse<PosType> findPosTypeById(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<PosType> response = new DodopalResponse<PosType>();
        try {
            PosType result = typeService.findPosTypeById(id);
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
    
    @RequestMapping("pos/deletePosType")
    public @ResponseBody DodopalResponse<String> deletePosType(HttpServletRequest request, @RequestBody String[] typeIds) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            typeService.deletePosType(typeIds);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(CommonConstants.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    
    /**
     * 启用POS类型
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("pos/activatePosType")
    public @ResponseBody DodopalResponse<Integer> activatePosType(HttpServletRequest request, @RequestBody String[] typeId) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            if (typeId != null && typeId.length > 0) {
                Integer num = typeService.batchActivatePosType(generateLoginUserId(request), Arrays.asList(typeId));
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(num);
            } else {
                response.setCode(ResponseCode.OSS_POS_COMPANY_EMPTY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    /**
     * 启用/禁用POS类型
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("pos/inactivatePosType")
    public @ResponseBody DodopalResponse<Integer> inactivatePosType(HttpServletRequest request, @RequestBody String[] typeId) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            if (typeId != null && typeId.length > 0) {
                Integer num = typeService.batchInactivatePosType(generateLoginUserId(request), Arrays.asList(typeId));
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(num);
            } else {
                response.setCode(ResponseCode.OSS_POS_COMPANY_EMPTY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    @RequestMapping("pos/loadPosType")
    public @ResponseBody DodopalResponse<List<NamedEntity>> loadPosType(HttpServletRequest request) {
        DodopalResponse<List<NamedEntity>> response = new DodopalResponse<List<NamedEntity>>();
        try {
            List<NamedEntity> result = typeService.loadPosType();
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
    /**************************************************** POS类型结束 *****************************************************/
}

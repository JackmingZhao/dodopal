package com.dodopal.oss.web.controller;

import java.util.Arrays;
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

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.DdicResMsg;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.DdicResMsgQuery;
import com.dodopal.oss.business.service.DdicResMsgService;

@Controller
@RequestMapping("/basic")
public class DdicResponseMsgController {

	@Autowired
	private DdicResMsgService ddicResMsgService;
	
	@Autowired
    private ExpTempletService expTempletService;
	
	/**
	 * 消息字典初始页面
	 * @param request
	 * @return 页面
	 */
	@RequestMapping("ddicMgmt/ddicReturn")
	public ModelAndView ddicResponseMsg(HttpServletRequest request) {
		return new ModelAndView("basic/ddicMgmt/ddicResponseMsg");
	}
	
	/**
	 * 按页查询消息字典
	 * @param request
	 * @param ddicResMsgQuery
	 * @return
	 */
	@RequestMapping("ddicMgmt/findDdicResMsgByPage")
	public @ResponseBody DodopalResponse<DodopalDataPage<DdicResMsg>> findDdicResMsgByPage(HttpServletRequest request, @RequestBody DdicResMsgQuery ddicResMsgQuery) {
		DodopalResponse<DodopalDataPage<DdicResMsg>> response = new DodopalResponse<DodopalDataPage<DdicResMsg>>();
		try {
			DodopalDataPage<DdicResMsg> result = ddicResMsgService.findDdicResMsgsByPage(ddicResMsgQuery);
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
	
	/**
	 * 保存或更新消息字典
	 * @param request
	 * @param ddicResMsg
	 * @return
	 */
	@RequestMapping("ddicMgmt/saveDdicResMsg")
    public @ResponseBody DodopalResponse<String> saveDdicResMsg(HttpServletRequest request, @RequestBody DdicResMsg ddicResMsg) {
		User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
		DodopalResponse<String> response = new DodopalResponse<String>();
		try {
			if(StringUtils.isNotBlank(ddicResMsg.getId())){
				ddicResMsg.setUpdateUser(user.getId());
				ddicResMsg.setUpdateDate(new Date());
			}else {
				ddicResMsg.setUpdateUser("");
				ddicResMsg.setCreateUser(user.getId());
				ddicResMsg.setCreateDate(new Date());
			}
			
			String result = ddicResMsgService.saveOrUpdateDdicResMsg(ddicResMsg);
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
	
	@RequestMapping("ddicMgmt/viewDdicResMsg")
    public @ResponseBody DodopalResponse<DdicResMsg> viewDdicResMsg(HttpServletRequest request, @RequestBody String ddicResMsgId) {
        DodopalResponse<DdicResMsg> response = new DodopalResponse<DdicResMsg>();
        try {
            if (StringUtils.isNotBlank(ddicResMsgId)) {
                DdicResMsg result = ddicResMsgService.findDdicResMsgById(ddicResMsgId);

                response.setResponseEntity(result);
            }
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

	/**
	 * 批量删除 
	 * @param request
	 * @param ids 
	 * @return
	 */
    @RequestMapping("ddicMgmt/deleteDdicResMsg")
    public @ResponseBody DodopalResponse<String> deleteDdicResMsg(HttpServletRequest request, @RequestBody String[] ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
        	ddicResMsgService.batchDelDdicResMsg(Arrays.asList(ids));
        	
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(CommonConstants.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    @RequestMapping("ddicMgmt/exportExcelDdicResMsg")
    public @ResponseBody DodopalResponse<String> exportDdicResMsg(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> dresp = new DodopalResponse<String>();
    	DdicResMsgQuery ddicResMsgQuery = new DdicResMsgQuery();
    	ddicResMsgQuery.setMsgCode(req.getParameter("ddicMsgCode"));
    	ddicResMsgQuery.setMsgType(req.getParameter("ddicMsgType"));
    	ddicResMsgQuery.setPage(new PageParameter(1, 50000));
    	List<DdicResMsg> list = ddicResMsgService.findDdicResMsgs(ddicResMsgQuery);
    	String sheetName = new String("返回消息字典");
    	
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(req, res, list, col, sheetName);
    	dresp.setCode(resultCode);
    	return dresp;
    }
    
    
}

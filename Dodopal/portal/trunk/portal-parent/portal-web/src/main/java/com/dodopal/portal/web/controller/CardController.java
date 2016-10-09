package com.dodopal.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerUserCardBDBean;
import com.dodopal.portal.business.bean.MerUserCardBDLogBean;
import com.dodopal.portal.business.bean.query.MerUserCardBDFind;
import com.dodopal.portal.business.bean.query.UserCardLogQuery;
import com.dodopal.portal.business.service.CardService;

/**
 * 卡片管理
 * @author xiongzhijing@dodopal.com
 * @version 2015年9月15日
 */
@Controller
@RequestMapping("/ddp")
public class CardController extends CommonController {
    private static Logger log = Logger.getLogger(CardController.class);

    @Autowired
    private CardService cardService;

    @RequestMapping("card")
    public ModelAndView ddpCard(Model model, HttpServletRequest request) {
        return new ModelAndView("ddp/card");
    }

 

    //查询用户绑定的卡片
    @RequestMapping("findUserCard")
    public @ResponseBody DodopalResponse<List<MerUserCardBDBean>> findUserCard(HttpServletRequest request) {
        DodopalResponse<List<MerUserCardBDBean>> response = new DodopalResponse<List<MerUserCardBDBean>>();
        MerUserCardBDFind cardBDFind = new MerUserCardBDFind();
        //用户编号
        String MerUserName = getCurrentUserName(request.getSession());
        //绑定状态
        cardBDFind.setMerUserName(MerUserName);
        cardBDFind.setBundLingType(BindEnum.ENABLE.getCode());
        try {
            response = cardService.findMerUserCardBD(cardBDFind);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    //查询用户绑定的卡片
    @RequestMapping("findUserCardById")
    public @ResponseBody DodopalResponse<MerUserCardBDBean> findUserCardById(HttpServletRequest request,@RequestBody String id) {
        log.info("根据id查询卡片信息:CardController  findUserCardById   id :"+id);
        DodopalResponse<MerUserCardBDBean> response = new DodopalResponse<MerUserCardBDBean>();
        try {
            response = cardService.findMerUserCardBDById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    
    //编辑卡片信息
    @RequestMapping("editUserCard")
    public @ResponseBody DodopalResponse<Boolean> editUserCard(HttpServletRequest request,@RequestBody MerUserCardBDBean cardBean) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        String userCode = getCurrentUserCode(request.getSession());
        cardBean.setUpdateUser(userCode);
        try {
            response = cardService.editUserCard(cardBean);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    
    //解绑
    @RequestMapping("unbindCardByUser")
    public @ResponseBody DodopalResponse<String> unbindCardByUser(HttpServletRequest request,@RequestBody List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        String userID=getCurrentUserId(request.getSession());
        String operName = getMerUserNickName(request.getSession());
        try {
            response = cardService.unbindCardByUser(userID,operName,ids,SourceEnum.PORTAL.getCode());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    @RequestMapping("log")
    public ModelAndView ddpCardLog(Model model, HttpServletRequest request) {
        return new ModelAndView("ddp/cardLog");
    }
    
    @RequestMapping("findUserCardLog")
    public @ResponseBody  DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> findUserCardLog(HttpServletRequest request,@RequestBody UserCardLogQuery query) {
        DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> response = new DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>>();
       
        //用户编号
        String userCode = getCurrentUserCode(request.getSession());
        query.setUserCode(userCode);
        
        try {
            response = cardService.findUserCardBDLogByPage( query);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    

}

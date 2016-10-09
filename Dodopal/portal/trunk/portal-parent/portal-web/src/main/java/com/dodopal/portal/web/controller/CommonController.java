package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.portal.business.constant.PortalConstants;

/**
 * 基础的cap Controller
 */
public abstract class CommonController {

    /**
     * 错误码显示规则： 1. 如果responseCode是：“000000” ，那么表示成功，正常处理业务或逻辑。 2.
     * 如果responseCode是: "999***", 那么表示系统故障或者程序未能处理到的bug，提示用户”系统故障，错误码：999***” 3.
     * 如果responseCode是其他情况下且表示业务错误，这类属于应用程序错误， 如果responseMessage
     * 有值，显示responseMessage，如果没有值显示responseCode。
     * @param code
     * @param message
     * @return
     */
    public String generateErrorMessage(String code, String message) {
        if (StringUtils.isNotEmpty(code) || StringUtils.isNotEmpty(message)) {
            if (code.startsWith("999")) {
                return "系统故障,错误码：" + code;
            } else {
                if (StringUtils.isNotEmpty(message)) {
                    return message;
                } else {
                    return "错误码：" + code;
                }
            }
        } else {
            return "出错啦,无法获取到错误码或者错误信息.";
        }
    }

    public PortalUserDTO getLoginUser(HttpSession session) {
        return (PortalUserDTO) session.getAttribute(PortalConstants.SESSION_USER);
    }

    public String getCurrentMerchantCode(HttpSession session) {
        PortalUserDTO user = getLoginUser(session);
        return user.getMerCode();
    }

    public String getCurrentUserCode(HttpSession session) {
        PortalUserDTO user = getLoginUser(session);
        return user.getUserCode();
    }

    public String getCurrentUserName(HttpSession session) {
        PortalUserDTO user = getLoginUser(session);
        return user.getMerUserName();
    }
    
    public String getMerUserNickName(HttpSession session) {
        PortalUserDTO user = getLoginUser(session);
        return user.getMerUserNickName();
    }
    public String getMerName(HttpSession session) {
    	PortalUserDTO user = getLoginUser(session);
    	return user.getMerName();
    }

    public String getCurrentUserId(HttpSession session) {
        PortalUserDTO user = getLoginUser(session);
        return user.getId();
    }
    
    public String getMerType(HttpSession session){
        PortalUserDTO user = getLoginUser(session);
        return user.getMerType();
    }
    
    public String getFundType(HttpSession session){
        PortalUserDTO user = getLoginUser(session);
        return user.getFundType();
    }
    
    public String getMerClassify(HttpSession session){
        PortalUserDTO user = getLoginUser(session);
        return user.getMerClassify();
    }
    
    public String getCurrentAcId(HttpSession session) {
        PortalUserDTO user = getLoginUser(session);
        return user.getAcid();
    }
    
    public String getCurrentCityName(HttpSession session) {
        PortalUserDTO user = getLoginUser(session);
        return user.getCityName();
    }
    
    public String getYktCode(HttpSession session) {
        PortalUserDTO user = getLoginUser(session);
        return user.getYktCode();
    }
    
    public void setCurrentAcId(HttpSession session,String Acid) {
        PortalUserDTO user = getLoginUser(session);
        user.setAcid(Acid);
    }
    
    public void setCurrentAccountCode(HttpSession session,String accountCode) {
        PortalUserDTO user = getLoginUser(session);
        user.setAccountCode(accountCode);
    }
    
    public void setCurrentFundType(HttpSession session,String fundType) {
        PortalUserDTO user = getLoginUser(session);
        user.setFundType(fundType);
    }
    
    public void setCurrentMerUserNickName(HttpSession session,String merUserNickName) {
        PortalUserDTO user = getLoginUser(session);
        user.setMerUserNickName(merUserNickName);
    }
    
    public void setCurrentCityName(HttpSession session,String cityName) {
        PortalUserDTO user = getLoginUser(session);
        user.setCityName(cityName);
    }
}

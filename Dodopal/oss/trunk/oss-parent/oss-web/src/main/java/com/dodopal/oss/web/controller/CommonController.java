package com.dodopal.oss.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.User;

/**
 * 基础的cap Controller
 */
public abstract class CommonController {

    //    private Logger logger = Logger.getLogger(CommonController.class);

    /*public DodopalResponse<Map<String, String>> authenticateSignature() {
        return null;
    }*/

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
    
    public String generateLoginUserId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
        if(user != null) {
            return user.getId();
        } else {
            return "";
        }
    }
    
    public User generateLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(OSSConstants.SESSION_USER);
        return user;
    }
}

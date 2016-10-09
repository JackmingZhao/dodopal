package com.dodopal.oss.web.tag;

import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dodopal.oss.business.constant.OSSConstants;

public class AccessControlTag extends TagSupport {
    private static final long serialVersionUID = -345631305626491314L;
    private String permission;

    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
        Collection<String> permissions = (Collection<String>) pageContext.getSession().getAttribute(OSSConstants.ALL_FUNCTIONS_IN_SESSION);
        if (permissions != null && permissions.contains(permission)) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}

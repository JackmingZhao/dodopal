package com.dodopal.product.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AccessControlTag extends TagSupport {

	private static final long serialVersionUID = -345631305626491314L;
	private String permission;

	@Override
	public int doStartTag() throws JspException {
	    // TODO 
			return EVAL_BODY_INCLUDE;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}

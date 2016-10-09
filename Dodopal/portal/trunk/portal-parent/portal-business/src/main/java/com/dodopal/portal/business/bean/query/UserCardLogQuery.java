package com.dodopal.portal.business.bean.query;

import com.dodopal.common.model.QueryBase;

/**
 * 卡片操作日志查询
 * @author xiongzhijing@dodopal.com 
 * @version 2015年9月16日
 *
 */
public class UserCardLogQuery extends QueryBase{
    private static final long serialVersionUID = 2773418061506933692L;
    //卡号
    private String code;
    //用户号
    private String userCode;
    //操作人姓名
    private String operName;
    //来源
    private String source;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getOperName() {
        return operName;
    }
    public void setOperName(String operName) {
        this.operName = operName;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    
}

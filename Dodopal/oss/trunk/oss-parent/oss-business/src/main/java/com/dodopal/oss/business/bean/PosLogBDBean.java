package com.dodopal.oss.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;

/**
 *  POS操作日志信息
 * @author 
 *
 */
public class PosLogBDBean extends BaseEntity{
    
    private static final long serialVersionUID = -7006160120960456235L;

    /** POS号 */
    private String code;
    
    /** 来源*/
    private String source;

    /** POS状态 */
    private String operStatus;

    /** 商户编号 */
    private String merCode;

    /** 商户名称 */
    private String merName;

    /** 操作人名称 */
    private String operName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
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

    /**
     * POS状态名称
     * 
     * @return
     */
    public String getOperStatusView() {
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.operStatus)) {
            return null;
        }
        return ddicService.getDdicNameByCode("POS_OPER_TYPE", this.operStatus).toString();
    }
    
    /**
     * 来源名称
     * 
     * @return
     */
    public String getSourceView() {
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.source)) {
            return null;
        }
        return ddicService.getDdicNameByCode("SOURCE", this.source).toString();
    }
}

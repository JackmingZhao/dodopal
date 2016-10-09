package com.dodopal.portal.business.model;

import java.util.List;
import java.util.Map;

public class PortalTransfer {
    
    //主账户的账户编号
    private String sourceCusCode;
    //主账户的类型 个人企业
    private String sourceCustType;
    
    //直营网点   key 是个人or 企业     ，value 是用户号或商户号
    private List<Map<String,String>> directMer;
    //金额
    private String money;
    //备注
    private String comment;
    
    // 0.转账   1.提取额度
    private String transferFlag;

    //业务类型
    private String businessType;
    
    //创建人
    private String createUser;

    //用户编号（校验用户是否被停用）
    private String userCode;
    //商户编号（校验用户是否被停用）
    private String merCode;
    

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSourceCusCode() {
        return sourceCusCode;
    }

    public void setSourceCusCode(String sourceCusCode) {
        this.sourceCusCode = sourceCusCode;
    }

    public String getSourceCustType() {
        return sourceCustType;
    }

    public void setSourceCustType(String sourceCustType) {
        this.sourceCustType = sourceCustType;
    }

 
    public List<Map<String, String>> getDirectMer() {
        return directMer;
    }

    public void setDirectMer(List<Map<String, String>> directMer) {
        this.directMer = directMer;
    }

    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    

}

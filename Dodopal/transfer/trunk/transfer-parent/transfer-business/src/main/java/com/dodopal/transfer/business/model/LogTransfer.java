package com.dodopal.transfer.business.model;

import java.io.Serializable;

public class LogTransfer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8306143735462017833L;
    private String id ;
    private String batchId; //迁移批次，例如：20160309113059，文件log中最好打印出来
    private String oldMerchantId;//老系统商户id或用户id
    private String oldMerchantType;//集团：0，网点：1，商户：2，个人用户：3，商户用户：4
    private String newMerchantCode;//新平台商户号或用户号
    private String newMerchantType;//新平台商户类型，个人为99
    private String state;//迁移状态，成功：0，失败：1
    private String memo;//失败堆栈信息
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBatchId() {
        return batchId;
    }
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    public String getOldMerchantId() {
        return oldMerchantId;
    }
    public void setOldMerchantId(String oldMerchantId) {
        this.oldMerchantId = oldMerchantId;
    }
    public String getOldMerchantType() {
        return oldMerchantType;
    }
    public void setOldMerchantType(String oldMerchantType) {
        this.oldMerchantType = oldMerchantType;
    }
    public String getNewMerchantCode() {
        return newMerchantCode;
    }
    public void setNewMerchantCode(String newMerchantCode) {
        this.newMerchantCode = newMerchantCode;
    }
    public String getNewMerchantType() {
        return newMerchantType;
    }
    public void setNewMerchantType(String newMerchantType) {
        this.newMerchantType = newMerchantType;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    
}

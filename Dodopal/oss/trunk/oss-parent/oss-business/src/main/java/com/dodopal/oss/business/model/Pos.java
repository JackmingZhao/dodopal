package com.dodopal.oss.business.model;

import java.math.BigDecimal;

import com.dodopal.common.model.BaseEntity;

public class Pos extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 3746732932260232208L;

    /**
     * POS编码 根据一定的规则进行设置（具体规则详细设计阶段定义）。全局唯一。pos 编码 和pos 号段 必须提供其中一个。
     */
    private String code;
    
    /**
     * POS号段，
     */
    private String codeStart;

    private String codeEnd;

    /**
     * POS厂商 用户根据实际情况在处于启用状态的POS厂商列表中进行选择，必须提供。
     */

    private String posCompanyCode;

    /**
     * POS型号 用户根据实际情况在处于启用状态的POS型号列表中进行选择，必须提供。
     */
    private String posTypeCode;

    /**
     * POS状态  启用、未启用、停用、作废、消费封锁、充值封锁
     */
    private String status;
    
    /**
     * 是否绑定
     */
    private String bind;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 所属城市 从城市列表中进行选择。
     */
    private String cityCode;
    
    private String cityName;
    
    private String provinceCode;
    
    private String provinceName;
    

    /**
     * 版本 POS机具的版本是标准的，从数据字典中进行选择。
     */
    private String version;

    /**
     * POS批次号 是指POS机具在POS厂商生产时的批次号，用户根据实际情况进行填写。
     */
    private String serialNo;

    /**
     * 采购成本 都都宝购买（生产）该机具的成本价。
     */
    private BigDecimal unitCost;

    /**
     * 限制笔数 出于风控目的，每台POS机都会设置每天的最大刷卡次数。
     */
    private long maxTimes;

    /**
     * 备注
     */
    private String comments;
    
    public String getPosId() {
        return getId();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPosCompanyCode() {
        return posCompanyCode;
    }

    public void setPosCompanyCode(String posCompanyCode) {
        this.posCompanyCode = posCompanyCode;
    }

    public String getPosTypeCode() {
        return posTypeCode;
    }

    public void setPosTypeCode(String posTypeCode) {
        this.posTypeCode = posTypeCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public long getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(long maxTimes) {
        this.maxTimes = maxTimes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCodeStart() {
        return codeStart;
    }

    public void setCodeStart(String codeStart) {
        this.codeStart = codeStart;
    }

    public String getCodeEnd() {
        return codeEnd;
    }

    public void setCodeEnd(String codeEnd) {
        this.codeEnd = codeEnd;
    }

}

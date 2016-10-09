package com.dodopal.oss.business.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;

public class PosInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * POS编码
     */
    private String code;
    
    /**
     * POS型号(名称)
     */
    private String posTypeName;
    
    /**
     * POS型号(code)
     */
    private String posTypeCode;
    
    /**
     * POS厂商(名称)
     */
    private String posCompanyName;
    
    /**
     * POS厂商(code)
     */
    private String posCompanyCode;
    
    /**
     * POS所属商户(名称)
     */
    private String merchantName;
    
    /**
     * POS所属商户(类型)
     */
    private String merchantType;
    
    /**
     * 所属城市 从城市列表中进行选择。
     */
    private String cityCode;
    
    private String cityName;
    
    private String provinceCode;
    
    private String provinceName;
    
    /**
     * POS版本 
     */
    private String version;
    
    /**
     * 限制笔数
     */
    private long maxTimes;

    /**
     * POS状态  启用、未启用、停用、作废、消费封锁、充值封锁
     */
    private String status;
    
    /**
     * 是否绑定
     */
    private String bind;
    
    
    // ==============  一览列表隱藏列  start  =============
    
    /**
     * posID
     */
    private String posId;
    
    /**
     * POS批次
     */
    private String serialNo;
    
    /**
     * 备注
     */
    private String comments;
    
    // ===============  一览列表隱藏列  end  ==============
    
    // ===============  查看详情显示列  start  =============
    /**
     * POS成本
     */
    private String unitCost;
    
    /**
     * POS绑定时间
     */
    private Date bundlingDate;
    // ===============  查看详情显示列  end  ===============
    
    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }

    public Date getBundlingDate() {
        return bundlingDate;
    }

    public void setBundlingDate(Date bundlingDate) {
        this.bundlingDate = bundlingDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPosTypeName() {
        return posTypeName;
    }

    public void setPosTypeName(String posTypeName) {
        this.posTypeName = posTypeName;
    }

    public String getPosCompanyName() {
        return posCompanyName;
    }

    public void setPosCompanyName(String posCompanyName) {
        this.posCompanyName = posCompanyName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(long maxTimes) {
        this.maxTimes = maxTimes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getPosTypeCode() {
        return posTypeCode;
    }

    public void setPosTypeCode(String posTypeCode) {
        this.posTypeCode = posTypeCode;
    }

    public String getPosCompanyCode() {
        return posCompanyCode;
    }

    public void setPosCompanyCode(String posCompanyCode) {
        this.posCompanyCode = posCompanyCode;
    }
    
    
    /**
     * 商户类型
     * 
     * @return
     */
    public String getMerchantTypeView() {
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.merchantType)) {
            return null;
        }
        return ddicService.getDdicNameByCode("MER_TYPE", this.merchantType).toString();
    }
    
    
    /**
     * pos状态
     * 
     * @return
     */
    public String getStatusView() {
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.status)) {
            return null;
        }
        return ddicService.getDdicNameByCode("POS_STATUS", this.status).toString();
    }
    
    
    /**
     * 是否绑定
     * 
     * @return
     */
    public String getBindView() {
        if (StringUtils.isBlank(this.bind)) {
            return null;
        }else if(BindEnum.getBindByCode(this.bind)==null){
            return null; 
        }
        return BindEnum.getBindByCode(this.bind).getName();
    }
    
    
    
}

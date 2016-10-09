package com.dodopal.product.business.bean;

import java.util.List;
import java.util.Map;

import com.dodopal.common.enums.IsShowErrorMsgEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.product.business.model.PrdProductYkt;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月20日 下午3:51:49
 */
public class CardRechargeBean extends BaseEntity{

	private static final long serialVersionUID = -8888518452254801576L;

	/**
     * 城市名称
     */
    private String cityName;
    
    /**
     * 城市Code
     */
    private String cityCode;
    /**
     * 支付提示信息
     */
    private String payWranFlag;
    /**
     * 商户编号
     */
    private String merCode;
    
    /**
     * 商户名称 
     */
    private String merName;
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 客户类型
     */
    private String merUserType;
    
    /**
     * 数据来源
     */
    private String source;
  
    /**
     * 用户id
     */
    private String userId;
    
    /**
     * 一卡通编码
     */
    private String yktCode;
    
    /**
     * 一卡通名称
     */
    private String yktName;
    /**
     * 产品库一卡通
     */
    private List<PrdProductYkt> prdProductYktList;
    
    /**
     * 面额列表
     */
    private List<Integer> proPriceList;
    
    /**
     * 开通城市
     * @return
     */
    private List<Area> areaList;
    
    /**
     * 首字母区域
     */
    private Map<String,List<Area>> allArea;
    
    /**
     * 账户余额
     */
    private double availableBalance;
    
    /**
     * 支付方式
     */
    private List<PayWayBean> payWayBean;
    
    /**
     * 常用支付方式
     */
    private List<PayWayBean> payCommonWayBean;
    
    
    /**
     * 控件的classId
     */
    private String ocxClassId;
    
    
    /**
     * 控件的版本
     */
    private String ocxVersion;
    
    
    /**
     * 商户账户支付方式id
     */
    private String merPayWayId;
    
    /**
     * 費率
     */
    private MerBusRateBean merRate;
    
    /**
     * 折扣列表
     */
    private List<MerDiscountBean> discountList;
    
    /**
     * 分时段折扣
     */
    private MerDiscountBean ddpDiscount;
    
    /**
     * 外接页面是否显示错误信息flag 0是，1否，默认是
     */
    private String isShowErrorMsg;
    
    public boolean getIsShowErrorMsgBoolean() {
        return IsShowErrorMsgEnum.IS_SHOW_ERROR_MSG.getCode().equals(isShowErrorMsg);
    }
    
    public String getIsShowErrorMsg() {
        return isShowErrorMsg;
    }

    public void setIsShowErrorMsg(String isShowErrorMsg) {
        this.isShowErrorMsg = isShowErrorMsg;
    }

    public MerDiscountBean getDdpDiscount() {
        return ddpDiscount;
    }
    public void setDdpDiscount(MerDiscountBean ddpDiscount) {
        this.ddpDiscount = ddpDiscount;
    }
    public List<MerDiscountBean> getDiscountList() {
		return discountList;
	}
	public void setDiscountList(List<MerDiscountBean> discountList) {
		this.discountList = discountList;
	}
	public String getYktName() {
		return yktName;
	}
	public void setYktName(String yktName) {
		this.yktName = yktName;
	}
	public MerBusRateBean getMerRate() {
		return merRate;
	}
	public void setMerRate(MerBusRateBean merRate) {
		this.merRate = merRate;
	}
	public String getMerPayWayId() {
		return merPayWayId;
	}
	public void setMerPayWayId(String merPayWayId) {
		this.merPayWayId = merPayWayId;
	}
	public String getOcxClassId() {
		return ocxClassId;
	}
	public void setOcxClassId(String ocxClassId) {
		this.ocxClassId = ocxClassId;
	}
	public String getOcxVersion() {
		return ocxVersion;
	}
	public void setOcxVersion(String ocxVersion) {
		this.ocxVersion = ocxVersion;
	}
	public List<PayWayBean> getPayWayBean() {
		return payWayBean;
	}
	public void setPayWayBean(List<PayWayBean> payWayBean) {
		this.payWayBean = payWayBean;
	}
	public List<PayWayBean> getPayCommonWayBean() {
		return payCommonWayBean;
	}
	public void setPayCommonWayBean(List<PayWayBean> payCommonWayBean) {
		this.payCommonWayBean = payCommonWayBean;
	}
	public double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public List<Area> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}
	public Map<String, List<Area>> getAllArea() {
		return allArea;
	}
	public void setAllArea(Map<String, List<Area>> allArea) {
		this.allArea = allArea;
	}
	public String getMerUserType() {
		return merUserType;
	}
	public void setMerUserType(String merUserType) {
		this.merUserType = merUserType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public String getYktCode() {
		return yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public List<Integer> getProPriceList() {
        return proPriceList;
    }

    public void setProPriceList(List<Integer> proPriceList) {
        this.proPriceList = proPriceList;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

	public List<PrdProductYkt> getPrdProductYktList() {
		return prdProductYktList;
	}

	public void setPrdProductYktList(List<PrdProductYkt> prdProductYktList) {
		this.prdProductYktList = prdProductYktList;
	}
	public String getPayWranFlag() {
		return payWranFlag;
	}
	public void setPayWranFlag(String payWranFlag) {
		this.payWranFlag = payWranFlag;
	}
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CardRechargeBean [cityName=" + cityName + ", cityCode=" + cityCode + ", payWranFlag=" + payWranFlag + ", merCode=" + merCode + ", merName=" + merName + ", userCode=" + userCode + ", merUserType=" + merUserType + ", source=" + source + ", userId=" + userId + ", yktCode=" + yktCode + ", yktName=" + yktName + ", prdProductYktList=" + prdProductYktList + ", proPriceList=" + proPriceList + ", areaList=" + areaList + ", allArea=" + allArea + ", availableBalance=" + availableBalance
            + ", payWayBean=" + payWayBean + ", payCommonWayBean=" + payCommonWayBean + ", ocxClassId=" + ocxClassId + ", ocxVersion=" + ocxVersion + ", merPayWayId=" + merPayWayId + ", merRate=" + merRate + ", discountList=" + discountList + ", ddpDiscount=" + ddpDiscount + "]";
    }

}

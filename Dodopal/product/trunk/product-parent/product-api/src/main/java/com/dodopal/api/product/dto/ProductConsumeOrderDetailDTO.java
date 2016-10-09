package com.dodopal.api.product.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ConsumeOrderInternalStatesEnum;
import com.dodopal.common.enums.ConsumeOrderStatesEnum;
import com.dodopal.common.model.BaseEntity;
/**
 * 一卡通消费订单详情
 * @author xiongzhijing@dodopal.com
 */
public class ProductConsumeOrderDetailDTO extends BaseEntity{
    private static final long serialVersionUID = 4391768265736540765L;

    //商户名称
    private String merName;

    // 订单编号:
    private String orderNum;

    // 卡号   CARD_NUM
    private String cardNum;

    // 卡面号
    private String cardFaceno;
    
    // 城市名称
    private String cityName;
    
    
    // POS号:冗余保存，提高查询效率目的。
    private String posCode;
    
    //应收金额
    private String  originalPrice;  
    
    //实收金额
    private String  receivedPrice; 
    
    // 操作员:用户的数据库ID。是商户的哪个操作人操作了这个公交卡充值业务。
    private String userId;

    // 操作员名称(用户的名称)
    private String userName;
    
    // 订单时间:式yyyy-MM-dd HH:MM:SS,实际为订单的创建时间。
    private Date proOrderDate;

    // 订单日期:格式yyyyMMdd,这里实际上为订单的创建日期（有点类似订单时间）。冗余的主要目的是为了满足数据库系统的分区。
    private String proOrderDay;
    
    // 消费前金额
    private String befbal;

    // 消费后金额
    private String blackAmt;
    
    // 商户利润
    private String merGain;
    
    //订单状态 
    private String states; 
    

    // 通卡公司名称
    private String yktName;


    // 服务费率
    private double serviceRate;

    //服务费率类型
    private String serviceRateType;
    
    //商户折扣
    private double merDiscount;

  //内部状态
    private String innerStates;
    
    //备注
    private String comments;
    
    // 商户费率:在进行这一笔公交卡充值的时候，商户与DDP之间的公交卡充值业务费率。
    private double merRate;
    
    //pos备注
    private String posComments;
    
    
    public String getPosComments() {
        return posComments;
    }


    public void setPosComments(String posComments) {
        this.posComments = posComments;
    }


    public double getMerRate() {
        return merRate;
    }


    public void setMerRate(double merRate) {
        this.merRate = merRate;
    }


    public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public String getInnerStates() {
    	if(StringUtils.isBlank(this.innerStates)){
            return null;
        }else if(ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(this.innerStates)==null){
            return null;
        }
        return ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(this.innerStates).getName();
	}


	public void setInnerStates(String innerStates) {
		this.innerStates = innerStates;
	}





    public double getMerDiscount() {
        return merDiscount;
    }


    public void setMerDiscount(double merDiscount) {
        this.merDiscount = merDiscount;
    }


    public String getServiceRateType() {
        return serviceRateType;
    }


    public void setServiceRateType(String serviceRateType) {
        this.serviceRateType = serviceRateType;
    }


    public String getMerName() {
        return merName;
    }


    public void setMerName(String merName) {
        this.merName = merName;
    }


    public String getOrderNum() {
        return orderNum;
    }


    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }


    public String getCardNum() {
        return cardNum;
    }


    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }


    public String getCityName() {
        return cityName;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getPosCode() {
        return posCode;
    }


    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }


    public String getOriginalPrice() {
        return originalPrice;
    }


    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }


    public String getReceivedPrice() {
        return receivedPrice;
    }


    public void setReceivedPrice(String receivedPrice) {
        this.receivedPrice = receivedPrice;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Date getProOrderDate() {
        return proOrderDate;
    }


    public void setProOrderDate(Date proOrderDate) {
        this.proOrderDate = proOrderDate;
    }


    public String getProOrderDay() {
        return proOrderDay;
    }


    public void setProOrderDay(String proOrderDay) {
        this.proOrderDay = proOrderDay;
    }


    public String getBefbal() {
        return befbal;
    }


    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }


    public String getBlackAmt() {
        return blackAmt;
    }


    public void setBlackAmt(String blackAmt) {
        this.blackAmt = blackAmt;
    }


    public String getMerGain() {
        return merGain;
    }


    public void setMerGain(String merGain) {
        this.merGain = merGain;
    }



    public String getStates() {
        if(StringUtils.isBlank(this.states)){
            return null;
        }else if(ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(this.states)==null){
            return null;
        }
        return ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(this.states).getName();
    }


    public void setStates(String states) {
        this.states = states;
    }


    public String getYktName() {
        return yktName;
    }


    public void setYktName(String yktName) {
        this.yktName = yktName;
    }


    public double getServiceRate() {
        return serviceRate;
    }


    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }


    public String getCardFaceno() {
        return cardFaceno;
    }


    public void setCardFaceno(String cardFaceno) {
        this.cardFaceno = cardFaceno;
    }


   
    
}

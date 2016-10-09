package com.dodopal.product.web.param;

public class LoadOrderRequest extends BaseRequest{

    //请求时间  yyyyMMddHHmmss
    private String reqdate;
    
    // 一卡通卡面号
    private String cardNum;
    
    //城市ID
    private String cityCode;
    
    //商户号
    private String mercode;
    

    public String getReqdate() {
        return reqdate;
    }

    public void setReqdate(String reqdate) {
        this.reqdate = reqdate;
    }

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getMercode() {
		return mercode;
	}

	public void setMercode(String mercode) {
		this.mercode = mercode;
	}

 
    
}

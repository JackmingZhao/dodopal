package com.dodopal.product.web.param;

public class PayRefundRequest extends BaseRequest{

    //请求时间  yyyyMMddHHmmss
    private String reqdate;
    
    // 自助终端：10
    private String source;
    
    //退款交易流水号
    private String paymenttranNo;
    
    //退款操作员
    private String userid;

    public String getReqdate() {
        return reqdate;
    }

    public void setReqdate(String reqdate) {
        this.reqdate = reqdate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

	public String getPaymenttranNo() {
		return paymenttranNo;
	}

	public void setPaymenttranNo(String paymenttranNo) {
		this.paymenttranNo = paymenttranNo;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    
    
    
}

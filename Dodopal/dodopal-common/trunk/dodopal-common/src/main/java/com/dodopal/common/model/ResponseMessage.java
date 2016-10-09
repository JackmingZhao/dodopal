package com.dodopal.common.model;

/**
 * 响应信息
 * @author Administrator
 *
 */
public class ResponseMessage extends BaseEntity {

	private static final long serialVersionUID = -8623358035296743471L;

	/** msg编码*/
	private String msgCode;

	/** msg类型：系统代码*/
    private String msgType;

    /** msg内容*/
    private String message;

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}    
    

}

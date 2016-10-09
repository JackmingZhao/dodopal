package com.dodopal.product.web.param;

/**
 * 自助终端参数下载返回参数
 * 
 * @author dodopal
 *
 */
public class AutoTerminalParaDownloadResponse extends BaseResponse {
    
    /**
     * 产品版本号
     */
    private String proversion;
    
    /**
     * 终端商户号
     */
    private String mercode;
    
    
    /**
     * 终端打印小票使用：商户名称
     */
    private String mername;
    
    /**
     * 终端打印小票使用：网点名称
     */
    private String netname;    
    
    /**
     * 下载参数编号
     */
    private String parno;
    
    /**
     * 各个参数编号对应的对象集合
     */
    private String listpars;
    
    /**
     * 产品参数对象
     */
    private String listproduct;
    
    /**
     * 支付方式参数对象
     */
    private String listpayaway;

    public String getProversion() {
        return proversion;
    }

    public void setProversion(String proversion) {
        this.proversion = proversion;
    }

    public String getMercode() {
        return mercode;
    }

    public void setMercode(String mercode) {
        this.mercode = mercode;
    }

    public String getParno() {
        return parno;
    }

    public void setParno(String parno) {
        this.parno = parno;
    }

    public String getListpars() {
        return listpars;
    }

    public void setListpars(String listpars) {
        this.listpars = listpars;
    }

    public String getListproduct() {
        return listproduct;
    }

    public void setListproduct(String listproduct) {
        this.listproduct = listproduct;
    }

    public String getListpayaway() {
        return listpayaway;
    }

    public void setListpayaway(String listpayaway) {
        this.listpayaway = listpayaway;
    }

	public String getMername() {
		return mername;
	}

	public void setMername(String mername) {
		this.mername = mername;
	}

	public String getNetname() {
		return netname;
	}

	public void setNetname(String netname) {
		this.netname = netname;
	}    
    

}

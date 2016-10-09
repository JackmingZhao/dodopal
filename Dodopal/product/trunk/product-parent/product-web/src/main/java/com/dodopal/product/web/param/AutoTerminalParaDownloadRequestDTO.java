package com.dodopal.product.web.param;

/**
 * 自助终端参数下载请求参数
 * 
 * @author dodopal
 *
 */
public class AutoTerminalParaDownloadRequestDTO extends BaseRequest {
    
    /**
     * POS号
     */
    private String posid;
    
    /**
     * 设配类型
     */
    private String postype;
    
    /**
     * 城市编码
     */
    private String citycode;
    
    /**
     * 产品版本号
     */
    private String proversion;
    
    /**
     * 产品版本号
     */
    private String psamno;

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getPostype() {
        return postype;
    }

    public void setPostype(String postype) {
        this.postype = postype;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getProversion() {
        return proversion;
    }

    public void setProversion(String proversion) {
        this.proversion = proversion;
    }

	public String getPsamno() {
		return psamno;
	}

	public void setPsamno(String psamno) {
		this.psamno = psamno;
	}
    
    
    
}

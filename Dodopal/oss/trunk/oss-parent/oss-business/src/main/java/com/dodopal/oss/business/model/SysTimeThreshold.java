package com.dodopal.oss.business.model;

/**
 * 根据业务代码 查询 时间阀值
 * @author lenovo
 */
public class SysTimeThreshold {

    //自增主键
    private String id;

    //01:一卡通充值；02：生活缴费；03：一卡通消费；99：账户充值；98：转账
    private String code;

    //时间阀值，单位为秒，例如如果是120，则表示120秒
    private int threshold;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

}

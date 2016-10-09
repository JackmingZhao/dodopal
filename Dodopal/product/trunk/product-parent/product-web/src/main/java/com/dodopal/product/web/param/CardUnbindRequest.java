package com.dodopal.product.web.param;

//用户解绑卡片
public class CardUnbindRequest extends BaseRequest{
    //操作人Id  登录后session保存的userid
    private String userid;
    
    //来源 手机端：设值为2,VC端：设值为3
    private String source;
    
    //需要解绑的邦卡数据库id  ,读取的一卡通卡号
    private String[] ids;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
    

}

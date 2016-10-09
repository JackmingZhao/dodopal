package com.dodopal.product.web.param;

//用户绑定的卡片列表
public class CardListRequest extends BaseRequest{
    //用户名  个人注册的用户名（登录帐号）
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

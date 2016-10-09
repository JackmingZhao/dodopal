package com.dodopal.product.web.param;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.product.business.bean.ProxyCardAddResultBean;

public class ProxyCardAddResponse extends BaseResponse{

    List<ProxyCardAddResultBean> proxyCardAddResultBeanList = new ArrayList<ProxyCardAddResultBean>();

    public List<ProxyCardAddResultBean> getProxyCardAddResultBeanList() {
        return proxyCardAddResultBeanList;
    }

    public void setProxyCardAddResultBeanList(List<ProxyCardAddResultBean> proxyCardAddResultBeanList) {
        this.proxyCardAddResultBeanList = proxyCardAddResultBeanList;
    }
    

}

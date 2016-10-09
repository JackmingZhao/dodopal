package com.dodopal.product.web.param;

import java.util.List;

import com.dodopal.product.web.mobileBean.AreaModel;

public class CityFindResponse extends BaseResponse {
    List<AreaModel> areaList;

    public List<AreaModel> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaModel> areaList) {
        this.areaList = areaList;
    }

}

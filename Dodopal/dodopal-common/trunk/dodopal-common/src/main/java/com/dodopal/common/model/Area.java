package com.dodopal.common.model;

import java.util.List;

public class Area extends BaseEntity {

    private static final long serialVersionUID = -2149594524044003808L;

    private String parentCode;

    private String cityCode;

    private String cityName;

    private String cityAbridge;

    private String area;

    private String bzCityCode;

    private List<Area> subAreas;

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityAbridge() {
        return cityAbridge;
    }

    public void setCityAbridge(String cityAbridge) {
        this.cityAbridge = cityAbridge;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBzCityCode() {
        return bzCityCode;
    }

    public void setBzCityCode(String bzCityCode) {
        this.bzCityCode = bzCityCode;
    }

    public List<Area> getSubAreas() {
        return subAreas;
    }

    public void setSubAreas(List<Area> subAreas) {
        this.subAreas = subAreas;
    }

}

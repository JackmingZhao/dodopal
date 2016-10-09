package com.dodopal.oss.business.bean;

import com.dodopal.oss.business.model.Pos;

public class PosBean extends Pos {

    /**
     * 
     */
    private static final long serialVersionUID = -5782832153675768183L;
   
    private int rowNum;

    private String unitCostStr;
    
    private String maxTimesStr;
    
    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getUnitCostStr() {
        return unitCostStr;
    }

    public void setUnitCostStr(String unitCostStr) {
        this.unitCostStr = unitCostStr;
    }

    public String getMaxTimesStr() {
        return maxTimesStr;
    }

    public void setMaxTimesStr(String maxTimesStr) {
        this.maxTimesStr = maxTimesStr;
    }

}

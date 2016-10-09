package com.dodopal.portal.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerCountBean;
import com.dodopal.portal.business.bean.PosBean;
import com.dodopal.portal.business.bean.query.MerCountQuery;
import com.dodopal.portal.business.bean.query.PosQuery;

public interface SupplierService {
    
    /**
     * 查询商户在某城市的pos信息
     * @param PosQueryDTO 
     * @return
     */
    public DodopalResponse<DodopalDataPage<PosBean>> countMerchantPosForSupplier(PosQuery posQuery);

     /**
      * 供应商(通卡)用户统计城市下所有的商户的信息
      * @param MerCountQueryDTO
      * @return
      */
    public DodopalResponse<DodopalDataPage<MerCountBean>> countMerchantForSupplier(MerCountQuery merCountQuery);
    
    
    
    
    /**
     *  POS操作
     *     绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param userId 操作员ID
     * @param userName 操作员姓名
     * @return
     */
    DodopalResponse<String> posOper(PosOperTypeEnum posOper, String merCode, String[] pos,String userId,String userName);

    /**
     * 导出城市商户信息
     * @param response
     * @param queryDTO
     * @return
     */
    public DodopalResponse<String> excelExport(HttpServletRequest request,HttpServletResponse response, MerCountQuery queryDTO);
    
    
    /**
     * 导出城市商户pos信息
     * @param response
     * @param queryDTO
     * @return
     */
    public DodopalResponse<String> exportPos(HttpServletRequest request,HttpServletResponse response, PosQuery queryDTO);

}

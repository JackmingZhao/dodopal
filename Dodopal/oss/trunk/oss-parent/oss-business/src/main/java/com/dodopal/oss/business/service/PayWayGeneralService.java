package com.dodopal.oss.business.service;

import java.util.List;
import java.util.Map;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.PayAwayBean;
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.bean.PayWayGeneralBean;
import com.dodopal.oss.business.model.dto.PayWayGeneralQuery;

public interface PayWayGeneralService {

    /**
     * 查询
     * @param payQuery 查询条件
     * @return DodopalResponse<DodopalDataPage<PayAwayBean>>
     */
    public  DodopalResponse<DodopalDataPage<PayAwayBean>> findPayAwayList (PayWayGeneralQuery  payQuery);
      
    /**
     * 新增
     * @param payBean
     * @param payAwayType 支付方式类型
     * @return
     */
    public  String savePayAway(PayWayGeneralBean payBean, String payAwayType);
    /**
     * 根据id显示信息
     * @param id
     * @param payAwayType 支付方式类型
     * @return DodopalResponse<PayWayGeneralBean>
     */
    public DodopalResponse<PayWayGeneralBean> findPayAwayById(String id,String payAwayType);
    /**
     * 修改
     * @param payBean
     * @param payAwayType 支付方式类型
     * @return DodopalResponse<Boolean>
     */
    public DodopalResponse<Boolean> updatePayAway(PayWayGeneralBean payBean, String payAwayType);
    /**
     * 删除
     * @param ids
     * @param payAwayType 支付方式类型
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> deletePayAway(List<String> ids, String payAwayType);
    /**
     * 停用，启用
     * @param ids
     * @param activate 启用标识
     * @param updateUser
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> startOrStop(List<String> ids, String activate,String updateUser);
    
    /**
     * 根据支付类型查询相关的支付方式
     * @param payType 支付类型
     * @return List<PayConfigBean>
     */
    public List<PayConfigBean> findPayWayNameByPayType(String payType);
    
}

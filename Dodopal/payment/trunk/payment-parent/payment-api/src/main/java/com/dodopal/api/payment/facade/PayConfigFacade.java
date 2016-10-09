package com.dodopal.api.payment.facade;

import java.util.List;
import java.util.Map;

import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.PaymentDTO;
import com.dodopal.api.payment.dto.query.PayConfigQueryDTO;
import com.dodopal.api.payment.dto.query.PaymentQueryDTO;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午8:18:46
 */
public interface PayConfigFacade {
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: savePayConfig 
     * @Description: 保存支付配置信息
     * @param payConfigDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> savePayConfig(PayConfigDTO payConfigDTO,PayTypeEnum payTypeEnum);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayConfigByPage 
     * @Description: 
     * @param queryDTO
     * @return    设定文件 
     * DodopalResponse<DodopalDataPage<PayConfigDTO>>    返回类型 
     * @throws 
     */
    DodopalResponse<DodopalDataPage<PayConfigDTO>> findPayConfigByPage(PayConfigQueryDTO queryDTO);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: viewPayConfigInfo 
     * @Description: 查看支付配置信息
     * @param id
     * @return    设定文件 
     * DodopalResponse<PayConfigDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<PayConfigDTO> findPayConfigById(String id); 
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: icdcPayCreate 
     * @Description: 一卡通
     * @param list
     * cityCode 城市Code
     * cityName    城市名称
     * rate    费率
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> icdcPayCreate(List<Map<String,Object>> list);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: batchActivatePayConfig 
     * @Description: 批量停启用
     * @param flag
     * @param ids
     * @param updateUser
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> batchActivatePayConfig(String flag,List<String> ids,String updateUser);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updatePayConfig 
     * @Description: 更新操作
     * @param payConfigDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> updatePayConfig(PayConfigDTO payConfigDTO);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: changeGatewayNumber 
     * @Description: 银行网关切换
     * @param ids
     * @param updateUser
     * @param payConfigId 切换的id 财付通/支付宝
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> changeGatewayNumber(List<String> ids,String updateUser,String payConfigId,BankGatewayTypeEnum toBankGateWayType);
}

package com.dodopal.oss.delegate;

import java.util.List;
import java.util.Map;

import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.PayWayGeneralDTO;
import com.dodopal.api.payment.dto.query.PayWayGeneralQueryDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.PayAwayEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface PayWayGeneralDelegate {

    /**
     * 
     * 查询
     * @param queryDTO 查询条件
     * @return DodopalResponse<DodopalDataPage<PayWayGeneralDTO>>
     */
    public DodopalResponse<DodopalDataPage<PayWayGeneralDTO>> findPayAwayListByPage(PayWayGeneralQueryDTO queryDTO);


    /**
     * 新增
     * @param payWayGeneralDTO
     * @param payAwayType 支付方式类型
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> savePayAway (PayWayGeneralDTO payWayGeneralDTO, String payAwayType);
    
    /**
     * 修改
     * @param payWayGeneralDTO
     * @param payAwayType 支付方式类型
     * @return DodopalResponse<Boolean>
     */
    public DodopalResponse<Boolean> updatePayAway (PayWayGeneralDTO payWayGeneralDTO, String payAwayType);
    
    /**
     * 删除
     * @param ids
     * @param payAwayType 支付方式类型
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> deletePayAway (List<String> ids, String payAwayType);
    
    /**
     *  启用停用
     * @param ids
     * @param atEnum 启用标识
     * @param updateUser
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> startOrStop (List<String> ids,ActivateEnum atEnum,String updateUser);
    
    /**
     * 根据id查询
     * @param id
     * @param payAwayType 支付方式类型
     * @return DodopalResponse<PayWayGeneralDTO>
     */
    public DodopalResponse<PayWayGeneralDTO> findPayGeneralById(String id, String payAwayType);
    
    /**
     * 根据支付类型查询支付名称
     * @param payType 支付类型
     * @return List<PayConfigDTO>
     */
    public List<PayConfigDTO> findPayWayNameByPayType(String payType);
    
    /**
     * 查询payConfig记录数
     * @param payConfigId
     * @return int
     */
    public int countPayGeneral(String payConfigId);
}

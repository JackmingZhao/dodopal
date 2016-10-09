package com.dodopal.api.payment.facade;

import java.util.List;
import java.util.Map;

import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.PayWayGeneralDTO;
import com.dodopal.api.payment.dto.query.PayWayGeneralQueryDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.PayAwayEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 
 * @author Administrator
 *
 */
public interface PayWayGeneralFacade {

    /**
     * 批量启用停用
     * @param activateEnum 启用标识
     * @param idList
     * @param updateUser
     * @return
     */
    DodopalResponse<Integer> batchStartOrStop(ActivateEnum activateEnum,List <String> idList,String updateUser);
    /**
     * 删除
     * @param ids
     * @return
     */
    DodopalResponse<Integer> deletePayWayGeneral(List<String> ids);
    /**
     * 保存
     * @param generalDT
     * @return
     */
    DodopalResponse<Integer> savePayWayGeneral(PayWayGeneralDTO generalDT);
    /**
     * 查询
     * @param payAwayQueryDTO
     * @return
     */
    DodopalResponse<DodopalDataPage<PayWayGeneralDTO>> findPayWayGeneralByPage(PayWayGeneralQueryDTO payAwayQueryDTO);
    /**
     * 修改
     * @param payWayGeneralDTO
     * @return
     */
    DodopalResponse<Boolean> updatePayWayGeneral(PayWayGeneralDTO payWayGeneralDTO);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    DodopalResponse<PayWayGeneralDTO> findGeneralById(String id);
    /**
     * 查询所有支付方式名称根据支付类型
     * @param payType 支付类型
     * @return
     */
    List<PayConfigDTO> findPayWayNameByPayType(String payType);
    /**
     * 查询payConfig记录数
     * @param payConfigId
     * @return
     */
    public int countByPayConfigId(String payConfigId);
}

package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.MerDiscountReferDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.api.users.facade.MerDiscountFacade;
import com.dodopal.api.users.facade.MerDiscountReferFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerDiscountDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("merDiscountDelegate")
public class MerDiscountDelegateImpl extends BaseDelegate implements MerDiscountDelegate {

    //查询商户的折扣（分页）
    public DodopalResponse<DodopalDataPage<MerDiscountDTO>> findMerDiscountByPage(MerDiscountQueryDTO merDiscountQueryDTO) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerDiscountByPage(merDiscountQueryDTO);
    }

    //批量or启用商户折扣
    public DodopalResponse<Integer> startOrStopMerDiscount(String activate, List<String> ids) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.startOrStopMerDiscount(activate, ids);
    }

    //修改商户折扣
    public DodopalResponse<Integer> updateMerDiscount(MerDiscountDTO merDiscountDTO) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.updateMerDiscount(merDiscountDTO);
    }

    //新增商户折扣
    public DodopalResponse<Integer> saveMerDiscount(MerDiscountDTO merDiscountDTO) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.saveMerDiscount(merDiscountDTO);
    }

    //查询详情
    public DodopalResponse<MerDiscountDTO> findMerDiscountById(String id) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerDiscountById(id);
    }

   
    /**
     * 新增商户折扣中间表记录（直营网点）
     * @param merDiscount
     * @return
     */
    public DodopalResponse<Integer> insertMerDiscountRefer(MerDiscountReferDTO merDiscountReferDTO) {
        MerDiscountReferFacade facade = getFacade(MerDiscountReferFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.insertMerDiscountRefer(merDiscountReferDTO);
    }
    
    /**
     * 根据 商户折扣 id 查询对应的直营网点（用于反选）
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerDiscountReferDTO>> findMerDiscountReferByList(String merDiscountId) {
        MerDiscountReferFacade facade = getFacade(MerDiscountReferFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerDiscountReferByList(merDiscountId);
    }

    /**
     * 查询商户折扣是否存在
     */
    public DodopalResponse<Integer> findMerDiscountNum(String merCode, String discount) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerDiscountNum(merCode, discount);
    }

    //根据商户号和 折扣 查询该商户折扣记录的详细信息
    public DodopalResponse<MerDiscountDTO> findMerDiscountByCode(String merCode, String discount) {
        MerDiscountFacade facade = getFacade(MerDiscountFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerDiscountByCode(merCode, discount);
    }

    @Override
    public DodopalResponse<Integer> deleteMerDiscountRefer(String merDiscountId) {
        MerDiscountReferFacade facade = getFacade(MerDiscountReferFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.deleteMerDiscountRefer(merDiscountId);
    }
    
    
}

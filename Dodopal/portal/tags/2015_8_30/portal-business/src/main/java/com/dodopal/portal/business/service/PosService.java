package com.dodopal.portal.business.service;

import org.springframework.security.core.userdetails.User;

import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.PosBean;
import com.dodopal.portal.business.bean.PosOperationBean;
import com.dodopal.portal.business.bean.query.PosQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年6月24日 上午9:46:02
 */
public interface PosService {
    
    /**
     *  POS操作
     *     绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param operUser 操作员信息
     * @param source 来源
     * @param comments 备注
     * @return
     */
    DodopalResponse<String> posOper(PosOperationBean operation, MerchantUserBean user);

    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPosListByPage 
     * @Description: 查询分页list
     * @param findDTO
     * @return    设定文件 
     * DodopalResponse<DodopalDataPage<PosDTO>>    返回类型 
     * @throws 
     */
    DodopalResponse<DodopalDataPage<PosBean>> findPosListByPage(PosQuery posQuery);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findChildrenPosListByPage 
     * @Description: 查询子商户pos
     * @param posQuery
     * @return    设定文件 
     * DodopalResponse<DodopalDataPage<PosBean>>    返回类型 
     * @throws 
     */
    DodopalResponse<DodopalDataPage<PosBean>> findChildrenPosListByPage(PosQuery posQuery);
}

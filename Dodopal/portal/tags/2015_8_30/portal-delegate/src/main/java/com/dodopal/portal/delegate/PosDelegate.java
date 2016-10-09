package com.dodopal.portal.delegate;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年6月24日 上午9:38:14
 */
public interface PosDelegate {
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
    DodopalResponse<String> posOper(PosOperTypeEnum posOper ,String merCode,
               String[] pos,OperUserDTO operUser,String comments);
    
    /**
     * 门户查询POS信息
     * @param findDto 查询条件
     * @return
     */
    DodopalResponse<DodopalDataPage<PosDTO>> findPosListPage(PosQueryDTO findDto);

    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findChildrenMerPosListPage 
     * @Description:  门户查询子商户POS列表
     * @param findDto
     * @return    设定文件 
     * DodopalResponse<DodopalDataPage<PosDTO>>    返回类型 
     * @throws 
     */
    DodopalResponse<DodopalDataPage<PosDTO>> findChildrenMerPosListPage(PosQueryDTO findDto);
}

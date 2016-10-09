package com.dodopal.portal.business.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerUserCardBDBean;
import com.dodopal.portal.business.bean.MerUserCardBDLogBean;
import com.dodopal.portal.business.bean.UserCardRecord;
import com.dodopal.portal.business.bean.query.MerUserCardBDFind;
import com.dodopal.portal.business.bean.query.UserCardLogQuery;
import com.dodopal.portal.business.model.query.UserCardRecordQuery;

public interface CardService {
    /**
     * 查询用户卡片
     * @param cardBDFind
     * @return
     */
    public DodopalResponse<List<MerUserCardBDBean>> findMerUserCardBD(MerUserCardBDFind cardBDFind);

    /**
     * 查询卡片操作日志（分页）
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> findUserCardBDLogByPage(UserCardLogQuery query);
    
    /**
     * 编辑卡片信息
     * @param cardBean
     * @return
     */
    public DodopalResponse<Boolean> editUserCard(MerUserCardBDBean cardBean);
    
    /**
     * 解绑
     * @param userName 登录用户名
     * @param ids 卡片管理表数据库id
     * @param source 来源
     * @return
     */
    public DodopalResponse<String> unbindCardByUser(String userName,String operName,List<String> ids,String source);
    
    /**
     * 查询卡片信息
     * @param id 用户卡片数据库id
     * @return
     */
    public DodopalResponse<MerUserCardBDBean> findMerUserCardBDById(String id);
    
    /**
     * 查询个人卡片充值和消费信息（分页）
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<UserCardRecord>> findUserCardRecordByPage(UserCardRecordQuery query);
    
    /**
     * @author Mika
     * 根据充值还是消费的类型和订单号来查询一单的充值和消费信息
     * @param type
     * @param orderNum
     * @return
     */
    public DodopalResponse<UserCardRecord>findCardRecordInfoByTypeOrderNum(String type, String orderNum);
    
    /**
     * @author Mika
     * 门户个人 绑定卡交易记录 导出
     * @param response
     * @param query
     * @return
     */
    public DodopalResponse<String> excelExport(HttpServletRequest request, HttpServletResponse response, UserCardRecordQueryDTO queryDTO);
}

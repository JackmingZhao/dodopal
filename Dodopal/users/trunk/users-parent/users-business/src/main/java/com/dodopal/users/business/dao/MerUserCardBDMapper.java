package com.dodopal.users.business.dao;

import java.util.List;
import java.util.Map;

import com.dodopal.users.business.model.MerUserCardBD;
import com.dodopal.users.business.model.UserCardRecord;
import com.dodopal.users.business.model.query.MerUserCardBDQuery;
import com.dodopal.users.business.model.query.UserCardRecordQuery;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月4日 下午1:23:31
 */
public interface MerUserCardBDMapper {
    //查询
    public List<MerUserCardBD> findMerUserCardBD(MerUserCardBD merUserCardBd);
    //查询给定用户绑卡总数
    public int findMerUserCardBDCount(MerUserCardBD merUserCardBd);
    //绑定卡片
    public void saveMerUserCardBD(MerUserCardBD merUserCardBd);
    //解绑
    public int unBundlingCard(Map map);
    
    public List<MerUserCardBD> findMerUserCardBDByPage(MerUserCardBDQuery merUserCardBd);
    //根据卡号查询此卡片信息
    public List<MerUserCardBD> findMerUserCardBDByCardCode(String cardCode);
    //根据卡片管理表 id 查询卡片信息
    public MerUserCardBD findMerUserCardById(String id);
    //编辑卡片
    public int editUserCard(MerUserCardBD card);
    
    //修改用户绑定卡片的状态
    public int updateBindType(MerUserCardBD cardBDDTO);
    public List<MerUserCardBD> getExportExcelMerUserCardList(MerUserCardBDQuery userCard);
    
    /**
     * @author Mika
     * 查询个人卡片充值和消费信息（分页）
     * @param query
     * @return
     */
    public List<UserCardRecord> findUserCardRecordByPage(UserCardRecordQuery query);
    
    /**
     * @author Mika
     * 根据订单号来查询一单的充值信息
     * @param type
     * @param orderNum
     * @return
     */
    public UserCardRecord findCardCZRecordInfoByOrderNum(String orderNum);
    
    /**
     * @author Mika
     * 根据订单号来查询一单的消费信息
     * @param type
     * @param orderNum
     * @return
     */
    public UserCardRecord findCardXFRecordInfoByOrderNum(String orderNum);
}

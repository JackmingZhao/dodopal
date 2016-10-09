package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.model.MerUserCardBD;
import com.dodopal.users.business.model.MerUserCardBDLog;
import com.dodopal.users.business.model.UserCardRecord;
import com.dodopal.users.business.model.query.MerUserCardBDQuery;
import com.dodopal.users.business.model.query.UserCardLogQuery;
import com.dodopal.users.business.model.query.UserCardRecordQuery;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月4日 下午2:18:52
 */
public interface MerUserCardBDService {
    //查询绑卡信息列表
    public List<MerUserCardBD> findMerUserCardBDList(MerUserCardBD userCard);
    //查询用户绑定卡数
    public int findMerUserCardBDCount(MerUserCardBD userCard);
    //保存绑卡
    public void saveMerUserCardBD(MerUserCardBD userCard);
    //解绑
    public int unBundlingCard(String name,List list);
    //分页查询列表
    public DodopalDataPage<MerUserCardBD> findMerUserCardBDListByPage(MerUserCardBDQuery userCard);

    //校验卡片是否已经绑定了用户
    public List<MerUserCardBD> findMerUserCardByCardCode(String cardCode);
    
    //添加操作日志
    public void addCardOperLog(MerUserCardBDLog userCardLog);
    
    /**
     * 查询操作日志
     * @param query
     * @return DodopalDataPage<MerUserCardBDLog>
     */
    public DodopalDataPage<MerUserCardBDLog> findUserCardBDLogByPage(UserCardLogQuery query);
    //根据卡片管理表id 查询卡片信息
    public MerUserCardBD findMerUserCardById(String id);
    
    //编辑卡片
    public int editUserCard(MerUserCardBD card);
    
    //修改用户绑卡状态
    public int updateBindType(MerUserCardBD cardBDDTO);
    public List<MerUserCardBD> getExportExcelMerUserCardList(MerUserCardBDQuery cardBdModel);
    public List<MerUserCardBDLog> getExportExcelUserCardLog(UserCardLogQuery userCardLogQuery);
    
    // 查询个人卡片充值和消费信息（分页） by Mika
    public DodopalDataPage<UserCardRecord> findUserCardRecordByPage(UserCardRecordQuery query);
    
    //根据单号查询一单	充值 交易详情 by Mika
    public UserCardRecord findCardCZRecordInfoByOrderNum(String orderNum);
    
    //根据单号查询一单	消费 交易详情  by Mika
  	public UserCardRecord findCardXFRecordInfoByOrderNum(String orderNum);

}

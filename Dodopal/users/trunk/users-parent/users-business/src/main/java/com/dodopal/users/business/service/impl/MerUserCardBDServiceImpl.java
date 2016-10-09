package com.dodopal.users.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.dao.MerUserCardBDLogMapper;
import com.dodopal.users.business.dao.MerUserCardBDMapper;
import com.dodopal.users.business.model.MerUserCardBD;
import com.dodopal.users.business.model.MerUserCardBDLog;
import com.dodopal.users.business.model.UserCardRecord;
import com.dodopal.users.business.model.query.MerUserCardBDQuery;
import com.dodopal.users.business.model.query.UserCardLogQuery;
import com.dodopal.users.business.model.query.UserCardRecordQuery;
import com.dodopal.users.business.service.MerUserCardBDService;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月4日 下午2:21:07
 */
@Service
public class MerUserCardBDServiceImpl implements MerUserCardBDService {
    
    @Autowired
    private MerUserCardBDMapper merUserCardMapper;
    @Autowired
    private MerUserCardBDLogMapper cardLogMapper;
    //查询卡片List记录
    @Transactional(readOnly = true)
    public List<MerUserCardBD> findMerUserCardBDList(MerUserCardBD userCard) {
      return merUserCardMapper.findMerUserCardBD(userCard);
    }
    //查询用户绑定卡数
    @Transactional(readOnly = true)
    public int findMerUserCardBDCount(MerUserCardBD userCard){
        return merUserCardMapper.findMerUserCardBDCount(userCard);
    }
    //用户绑定卡片
    @Transactional
    public void saveMerUserCardBD(MerUserCardBD userCard) {
        userCard.setBundLingType("0");
        userCard.setCreateDate(new Date());
        merUserCardMapper.saveMerUserCardBD(userCard);
    }
    
    //解绑
    @Transactional
    public int unBundlingCard(String userId, List idList) {
        int updateRow = 0;
        List list = new ArrayList();
        if(!idList.isEmpty()){
            Map map  =  new HashMap();
            map.put("name", userId);//解绑人
            map.put("list", idList);//解绑id
            updateRow = merUserCardMapper.unBundlingCard(map);
        }
        return updateRow;
    }
    
    //查询卡片（分页）
    @Override
    public  DodopalDataPage<MerUserCardBD> findMerUserCardBDListByPage(MerUserCardBDQuery userCard) {
        List<MerUserCardBD> result = merUserCardMapper.findMerUserCardBDByPage(userCard);
        DodopalDataPage<MerUserCardBD> pages = new DodopalDataPage<MerUserCardBD>(userCard.getPage(), result);
        return pages;
    }
   //根据卡号查找 卡片信息
    @Transactional(readOnly = true)
    public List<MerUserCardBD> findMerUserCardByCardCode(String cardCode) {
        return  merUserCardMapper.findMerUserCardBDByCardCode(cardCode);
    }
    
    //添加操作日志
    @Transactional
    public void addCardOperLog(MerUserCardBDLog userCardLog) {
        cardLogMapper.addUserCodeCordBindlog(userCardLog);
    }
    
    
    //查询操作日志
    @Transactional(readOnly = true)
	public DodopalDataPage<MerUserCardBDLog> findUserCardBDLogByPage(UserCardLogQuery query) {
    	List<MerUserCardBDLog> response = cardLogMapper.findUserCardBDLogByPage(query);
    	 DodopalDataPage<MerUserCardBDLog> pages = new DodopalDataPage<MerUserCardBDLog>(query.getPage(), response);
    	return pages;
	}
    
    @Transactional(readOnly = true)
    public List<MerUserCardBDLog> getExportExcelUserCardLog(UserCardLogQuery query) {
        List<MerUserCardBDLog> response = cardLogMapper.getExportExcelUserCardLog(query);
        return response;
    }
    @Transactional(readOnly = true)
    public MerUserCardBD findMerUserCardById(String id) {
        return merUserCardMapper.findMerUserCardById(id);
    }
    @Transactional
    public int editUserCard(MerUserCardBD card) {
        return merUserCardMapper.editUserCard(card);
    }
    @Transactional
    public int updateBindType(MerUserCardBD cardBDDTO) {
        return  merUserCardMapper.updateBindType(cardBDDTO);
    }
    
  //导出卡片
    @Override
    public  List<MerUserCardBD> getExportExcelMerUserCardList(MerUserCardBDQuery userCard) {
        List<MerUserCardBD> result = merUserCardMapper.getExportExcelMerUserCardList(userCard);
        return result;
    }
    
	// 查询个人卡片充值和消费信息（分页）by Mika
	public DodopalDataPage<UserCardRecord> findUserCardRecordByPage(UserCardRecordQuery query) {
		List<UserCardRecord> result = merUserCardMapper.findUserCardRecordByPage(query);
		DodopalDataPage<UserCardRecord> pages = new DodopalDataPage<>(query.getPage(), result);
		return pages;
	}

	//根据  单号 查询一单充值 交易详情 by Mika
	public UserCardRecord findCardCZRecordInfoByOrderNum(String orderNum) {
		return merUserCardMapper.findCardCZRecordInfoByOrderNum(orderNum);
	}
	
	//根据  单号 查询一单消费 交易详情  by Mika
	public UserCardRecord findCardXFRecordInfoByOrderNum(String orderNum) {
		return merUserCardMapper.findCardXFRecordInfoByOrderNum(orderNum);
	}
	
}

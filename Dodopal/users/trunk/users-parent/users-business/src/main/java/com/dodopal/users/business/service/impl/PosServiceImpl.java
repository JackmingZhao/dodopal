package com.dodopal.users.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.users.business.dao.PosLogMapper;
import com.dodopal.users.business.dao.PosMapper;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.Pos;
import com.dodopal.users.business.model.PosLog;
import com.dodopal.users.business.model.PosMerTb;
import com.dodopal.users.business.model.PosQuery;
import com.dodopal.users.business.service.PosService;

@Service("posService")
public class PosServiceImpl implements PosService {

	@Autowired
	private PosMapper posMapper;
	
	@Autowired
	private PosLogMapper posLogMapper;
	
    /**
     * 门户查询POS信息
     *          分页
     * @param findBean 查询条件
     * @return
     */
	@Override
	@Transactional(readOnly = true)
	public List<Pos> findPosListPage(PosQuery findBean){
    	return posMapper.findPosListPage(findBean);
    }
	
	@Override
	@Transactional(readOnly = true)
	public int getPosCount(String posCode){
		
    	return posMapper.getPosCount(posCode);
    }
	
    @Override
    @Transactional(readOnly = true)
    public int getPosBindedCountByCodes(String[] posCodes){
        
        return posMapper.getPosBindedCountByCodes(posCodes);
    }
	   
	@Override
	@Transactional
	public void updatePosBundling(Merchant mer,PosOperTypeEnum posOper, String[] posCodes, 
			                         OperUserDTO operUser,SourceEnum source,String comments) {
		Pos pos = new Pos();
		pos.setMerchantCode(mer.getMerCode());
		pos.setMerchantName(mer.getMerName());
		pos.setUpdateUser(operUser.getId());
		
		List<String> posCodeList = new ArrayList<>();
		List<Pos> posList = new ArrayList<>();
		for(String str: posCodes){			
			Pos posTemp = new Pos();			
			posTemp.setMerchantCode(mer.getMerCode());
			posTemp.setMerchantName(mer.getMerName());
			posTemp.setUpdateUser(operUser.getId());
			posTemp.setCode(str);
			posTemp.setComments(comments);
			posList.add(posTemp);
			posCodeList.add(str);
			
		}

		posMapper.updatePosBundling(pos,posCodeList);
		//插入操作日志
		this.savePosLog(posOper,posList,operUser,source);
		//中间表维护
		this.savePosMerTb(posList);
	}
	
	@Override
	@Transactional
	public void updatePosUnBundling(PosOperTypeEnum posOper,String[] posCodes, 
			OperUserDTO operUser,SourceEnum source) {
		List<Pos> posList = new ArrayList<>();
		posList = posMapper.findPosList(PosOperTypeEnum.OPER_BUNDLING.getCode(),posCodes);
		if (null ==posList || posList.size()==0){ return;}
		//解绑
		posMapper.updatePosUnBundling(operUser.getId(),posList);
		//插入操作日志
		this.savePosLog(posOper,posList,operUser,source);
		//中间表解绑维护
		this.deletePosMerTb(posList);
	}
	
	@Override
	@Transactional
	public void updatePosActivate(PosOperTypeEnum posOper,String[] posCodes,
			OperUserDTO operUser,SourceEnum source) {
		List<Pos> posList = new ArrayList<>();
		posList = posMapper.findPosList(null,posCodes);
		if(PosOperTypeEnum.OPER_ENABLE ==posOper){
			//启用
			posMapper.updatePosEnable(operUser.getId(),posCodes);
		}else{
			//禁用
			posMapper.updatePosDisable(operUser.getId(),posCodes);
		}
		//插入操作日志
		this.savePosLog(posOper,posList,operUser,source);
	}
	
	@Transactional(readOnly = true)
	public List<Pos> findChildrenMerPosListPage(PosQuery findBean){
	    return posMapper.findChildrenMerPosListPage(findBean);
	}
	
	/**
	 * 写入POS操作日志
	 * @param posOper 操作类型
	 * @param posList 操作POS列表
	 * @param operUser 操作人
	 */
	@Transactional
	private void savePosLog(PosOperTypeEnum posOper,List<Pos> posList, OperUserDTO operUser,SourceEnum source){
		//写POS操作日志
		for(Pos pos: posList){
			PosLog posLog = new PosLog();
			posLog.setCode(pos.getCode());
			posLog.setUpdateUser(operUser.getId());
			posLog.setMerCode(pos.getMerchantCode());
			posLog.setMerName(pos.getMerchantName());
			posLog.setOperStatus(posOper.getCode());
			posLog.setCreateUser(operUser.getId());
			posLog.setOperName(operUser.getOperName());
			posLog.setSource(source.getCode());
			posLogMapper.insertPosLog(posLog);
		}
	}
	
	/**
	 * POS绑定中间表维护
	 * @param posList 
	 */
	@Transactional
	private void savePosMerTb(List<Pos> posList){
		//写POS操作日志
		for(Pos pos: posList){
			PosMerTb posTb = new PosMerTb();
			posTb.setCode(pos.getCode());
			posTb.setMerCode(pos.getMerchantCode());
			posTb.setComments(pos.getComments());
			posMapper.insertPosMerTb(posTb);
		}
	}
	
	/**
	 * POS解绑中间表维护(删除)
	 * @param posList 
	 */
	@Transactional
	private void deletePosMerTb(List<Pos> posList){
		//写POS操作日志
		for(Pos pos: posList){
			PosMerTb posTb = new PosMerTb();
			posTb.setCode(pos.getCode());
			posTb.setMerCode(pos.getMerchantCode());
			posMapper.deletePosMerTb(posTb);
		}
	}
	/**
	 * OSS删除POS信息
	 */
    @Override
    @Transactional
    public void deletePos(PosOperTypeEnum posOper, String[] posId, OperUserDTO operUser, SourceEnum source) {
        List<Pos> posList = new ArrayList<>();
        posList = posMapper.findPosList(null,posId);
        posMapper.deletePos(posId);
        this.savePosLog(posOper,posList,operUser,source);
    }

    @Override
    @Transactional(readOnly = true)
    public int getPosBindedCountByCodesAndMerCode(String[] posCodes, String merCode) {
        return posMapper.getPosBindedCountByCodesAndMerCode(posCodes, merCode);
    }

	@Override
	@Transactional(readOnly = true)
	public Pos findPosByCode(String code) {
		return posMapper.findPosByCode(code);
	}

    @Override
    @Transactional(readOnly = true)
    public List<Pos> findPosinfoByPage(PosQuery findBean) {
        return posMapper.findPosinfoByPage(findBean);
    }

    @Override
    @Transactional(readOnly = true)
    public int posCount(String merCode) {
        return posMapper.posCount(merCode);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Pos findMerchantCodeByPosCode(String posCode) {
        return posMapper.findMerchantCodeByPosCode(posCode);
    }

	@Override
	 @Transactional(readOnly = true)
	public Pos findPosInfoByCode(String posCode, String merCode) {
		
		return posMapper.findPosInfoByCode(posCode, merCode);
	}

	@Override
	 @Transactional(readOnly = true)
	public void savePosComments(String posCode, String comments) {
		String sql = " update pos set comments = '"+comments+"' where code = '"+posCode+"' "  ;
		posMapper.updatePosInfo(sql);
	}
}

package com.dodopal.common.dao;

import java.util.List;

import com.dodopal.common.model.ResponseMessage;

public interface SysRespMessageMapper {

	public List<ResponseMessage> findMsgList();
	
	public ResponseMessage findMsgByCode(String code);
}
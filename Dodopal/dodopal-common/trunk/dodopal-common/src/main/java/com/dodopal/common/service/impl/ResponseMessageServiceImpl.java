package com.dodopal.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.dao.SysRespMessageMapper;
import com.dodopal.common.model.ResponseMessage;
import com.dodopal.common.service.ResponseMessageService;
import com.dodopal.common.service.SysOperationService;
import com.dodopal.common.util.DDPStringUtil;

@Service("responseMessageService")
public class ResponseMessageServiceImpl implements ResponseMessageService, InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(ResponseMessageServiceImpl.class);
    
    @Autowired
    private SysRespMessageMapper sysRespMessageManager;

    @Autowired
    private SysOperationService sysOperationService;
    
    @Autowired
    MemcachedClient client;
    
    Map<String, String> sysRespoMessageMap = new HashMap<String, String>();

    private void loadResponseMessageMap() {
    	List<ResponseMessage> items = sysRespMessageManager.findMsgList();
                for (ResponseMessage item : items) {
//                    sysRespoMessageMap.put(item.getMsgCode(), item.getMessage());
                    client.add(item.getMsgCode(), 0, item.getMessage());
                }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadResponseMessageMap();
    }

    @Override
    public String getMessgaeByCode(String code) {
        String message = null;
//        String message = sysRespoMessageMap.get(code);
        try {    	
             message = (String)client.get(code);
             if (DDPStringUtil.isNotPopulated(message)) {
                ResponseMessage resMsg = sysRespMessageManager.findMsgByCode(code);
                if (resMsg != null) {
                    logger.info("更新response message--> code:" + resMsg.getMsgCode() + ",message : " + resMsg.getMessage());
                    client.add(resMsg.getMsgCode(), 0, resMsg.getMessage());
    //                sysRespoMessageMap.put(resMsg.getMsgCode(), resMsg.getMessage());
                    message = resMsg.getMessage();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return message;
    }

    @Override
    public void doReload() {
        // 由memcached 替换该功能， 轮循功能去除
//        try {
//            if (sysOperationService.isUpdated(CommonConstants.RESPONSE_MESSAGE)) {
//                sysRespoMessageMap.clear();
//                loadResponseMessageMap();
//            }
//        } catch (Exception e) {
//            logger.error("重新加载respoMessageMap出错:", e);
//        }
    }

}

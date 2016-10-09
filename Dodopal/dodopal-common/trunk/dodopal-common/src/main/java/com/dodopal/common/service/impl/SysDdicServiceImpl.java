package com.dodopal.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.DdicConstant;
import com.dodopal.common.dao.DdicVoMapper;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.service.SysDdicService;
import com.dodopal.common.service.SysOperationService;
import com.dodopal.common.util.DDPStringUtil;

/**
 * 系统字典服务
 */
@Service("sysDdicService")
public class SysDdicServiceImpl implements SysDdicService  {

    private final static Logger logger = LoggerFactory.getLogger(SysDdicServiceImpl.class);
    
    @Autowired
    private DdicVoMapper ddicMapper;
    
    @Autowired
    private SysOperationService sysOperationService;

    @Autowired
    MemcachedClient client;
    
    Map<String, String> sysDdicMap = new HashMap<String, String>();

    @Override
    public String getScriptUrl() {
        return findDdicNameByCode(DdicConstant.SCRIPT_URL);
    }

    @Override
    public String getStyleUrl() {
        return findDdicNameByCode(DdicConstant.STYLE_URL);
    }

    @Override
    public String getImgUrl() {
        return findDdicNameByCode(DdicConstant.IMG_URL);
    }

    @Override
    public String getOcxUrl() {
        return findDdicNameByCode(DdicConstant.OCX_URL);
    }
    
    @Transactional(readOnly = true)
    private String findDdicNameByCode(String code) { 
        String ddicName = null;    
        try {
              ddicName = (String) client.get(code);
//            sysDdicMap.get(code);
            if (DDPStringUtil.isNotPopulated(ddicName)) {
                DdicVo ddic = ddicMapper.findDdicByCode(code);
                if (ddic != null) {
                    ddicName = ddic.getName();
    //                sysDdicMap.put(code, ddic.getName());
                    client.add(code, 0, ddic.getName());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ddicName;
    }

    @Override
    public void doReload() {
        try {
            if (sysOperationService.isUpdated(CommonConstants.SYS_DDIC)) {
                sysDdicMap.clear();
            }
        } catch (Exception e) {
            logger.error("重新加载respoMessageMap出错:", e);
        }
    }

}

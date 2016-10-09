package com.dodopal.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.dao.DdicVoMapper;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.service.DdicService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月10日 下午2:05:10
 */
@Service("ddicService")
public class DdicServiceImpl implements DdicService,InitializingBean{
    @Autowired
    private DdicVoMapper ddicMapper;
    

    Map<String, List<DdicVo>> ddicMap = new HashMap<String, List<DdicVo>>();
    
    @Transactional(readOnly = true)
    public List<DdicVo> findDdicList() {
        return ddicMapper.findDdics();
    }

    @Transactional(readOnly = true)
    public List<String> findDDICCategoryCode() {
        return ddicMapper.findDDICCategoryCode();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ddicMap = createDdicMap();
    }
    
    private Map<String,List<DdicVo>> createDdicMap() {
        Map <String,List<DdicVo>> ddicMap = new HashMap<String,List<DdicVo>>();
        List<String>codeList = ddicMapper.findDDICCategoryCode();
        List<DdicVo> ddicList = ddicMapper.findDdics();
        if(CollectionUtils.isNotEmpty(codeList)&&CollectionUtils.isNotEmpty(ddicList)){
            for(String code:codeList){
                List<DdicVo> mapList = new ArrayList<DdicVo>();
                for(DdicVo ddicVo:ddicList){
                    if(code.equals(ddicVo.getCategoryCode())){
                        mapList.add(ddicVo);
                    }
                }
                ddicMap.put(code, mapList);
            }
        }
        return ddicMap;
    }

    @Transactional(readOnly = true)
    public Map<String, List<DdicVo>> getDdicMap() {
        if(null==ddicMap){
            ddicMap = createDdicMap();
        }
        return ddicMap;
    }

    @Override
    public String getDdicNameByCode(String categoryCode,String code) {
        if(null==ddicMap){
            ddicMap =  createDdicMap();
        }
        List<DdicVo>  ddicList= ddicMap.get(categoryCode);
        if(CollectionUtils.isNotEmpty(ddicList)){
        	for(DdicVo ddic : ddicList){
                if(code.equals(ddic.getCode())){
                    return ddic.getName(); 
                }
            }
        }
        //没有找到，就再进行一次查询
        List<DdicVo> agaList = ddicMapper.findDdicByCategoryCode(categoryCode);
        if(CollectionUtils.isNotEmpty(agaList)){
        	for(DdicVo ddic : agaList){
                if(code.equals(ddic.getCode())){
                	ddicMap.put(categoryCode,agaList);
                    return ddic.getName(); 
                }
            }
        }
        return "";
    }

	@Override
	public Map<String, List<DdicVo>> resetDdicMap() {
	    ddicMap = createDdicMap();
	    return ddicMap;
	}	
	
	@Override
    public String getDdicNameByCodeFormDB(String categoryCode,String code) {        
        List<DdicVo> agaList = ddicMapper.findDdicByCategoryCode(categoryCode);
        if(CollectionUtils.isNotEmpty(agaList)){
        	for(DdicVo ddic : agaList){
                if(code.equals(ddic.getCode())){                	
                    return ddic.getName(); 
                }
            }
        }
        return "";
    }
    
}

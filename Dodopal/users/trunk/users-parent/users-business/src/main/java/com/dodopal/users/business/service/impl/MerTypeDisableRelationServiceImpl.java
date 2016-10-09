package com.dodopal.users.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerTypeDisableRelationMapper;
import com.dodopal.users.business.model.MerTypeDisableRelation;
import com.dodopal.users.business.service.MerTypeDisableRelationService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class MerTypeDisableRelationServiceImpl implements MerTypeDisableRelationService {
    @Autowired
    private MerTypeDisableRelationMapper merTypeDisableRelationMapper;

    @Override
    @Transactional(readOnly = true)
    public Map<String, List<String>> findDisableRelationType() {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        List<MerTypeDisableRelation> allRelationType = merTypeDisableRelationMapper.findAllRelationType();
        if (CollectionUtils.isNotEmpty(allRelationType)) {
            for (MerTypeDisableRelation temp : allRelationType) {
                String merType = temp.getMerType();
                if (!result.containsKey(merType)) {
                    result.put(merType, new ArrayList<String>());
                }

                List<String> merRelationTypeList = result.get(merType);
                merRelationTypeList.add(temp.getDisableRelationType());
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Map<String, String>> findDisableRelationTypeMap() {
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        List<MerTypeDisableRelation> allRelationType = merTypeDisableRelationMapper.findAllRelationType();
        if (CollectionUtils.isNotEmpty(allRelationType)) {
            for (MerTypeDisableRelation temp : allRelationType) {
                String merType = temp.getMerType();
                if (!result.containsKey(merType)) {
                    result.put(merType, new HashMap<String, String>());
                }

                Map<String, String> merRelationType = result.get(merType);
                merRelationType.put(temp.getDisableRelationType(), null);
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> findEnableRelationType() {
        Map<String, String> result = new HashMap<String, String>();
        List<MerTypeDisableRelation> allRelationType = merTypeDisableRelationMapper.findAllRelationType();
        if (CollectionUtils.isNotEmpty(allRelationType)) {
            for (MerTypeDisableRelation temp : allRelationType) {
                result.put(temp.getDisableRelationType(), temp.getMerType());
            }
        }
        return result;
    }

}

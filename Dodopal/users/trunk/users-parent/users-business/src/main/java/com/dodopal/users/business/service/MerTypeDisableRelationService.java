package com.dodopal.users.business.service;

import java.util.List;
import java.util.Map;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerTypeDisableRelationService {
    public Map<String, List<String>> findDisableRelationType();

    public Map<String, Map<String, String>> findDisableRelationTypeMap();

    public Map<String, String> findEnableRelationType();
}

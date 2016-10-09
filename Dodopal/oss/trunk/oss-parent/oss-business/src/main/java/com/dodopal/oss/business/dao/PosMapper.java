package com.dodopal.oss.business.dao;

import java.util.List;
import java.util.Map;

import com.dodopal.oss.business.model.Pos;
import com.dodopal.oss.business.model.PosInfo;
import com.dodopal.oss.business.model.dto.PosQuery;

public interface PosMapper {

    public void insertPos(Pos pos);
    
    public void insertPosBatch(List<Pos> list);
    
    public void updatePos(Pos pos);
    
    public void deletePos(String[] id);
    
    public List<Pos> findPosByIds(String[] id);
    
    public List<Pos> findPoss(Pos pos);

    public PosInfo findPosById(String id);
    
    int countPos(String code);
    
    public List<PosInfo> findPosByPage(PosQuery posQuery);
    
    void modifyCity(Map<String, Object> map2);
    
    void modifyVersion(Map<String, Object> map2);
    
    void modifyLimitation(Map<String, Object> map2);
    
    List<String> selectMultipleKeys(int numberOfKeysRequired);
    
    List<Pos> findPosByCode(String[] posCode);
    
    int countPosByCompanyCode(String companyCode);
    
    List<Pos> findPosByPosTypeCode(String[] posTypeCode);
    
    public List<PosInfo> findPosByList(PosQuery posQuery);
}
    

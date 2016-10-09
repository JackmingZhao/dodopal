package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.Pos;
import com.dodopal.users.business.model.PosQuery;

/**
 * POS
 * @author
 *
 */
public interface PosService {
	
   /**
    * POS绑定
    * @param mer 商户信息
    * @param posCodes pos号
    * @param operUser 操作人
    * @param source 来源
    */
    void updatePosBundling(Merchant mer,PosOperTypeEnum posOper,
    		String[] posCodes,OperUserDTO operUser,SourceEnum source,String comments);
    
    /**
     * POS解绑
     * @param posCodes pos号
     * @param operUser 操作人
     * @param source 来源
     */
     void updatePosUnBundling(PosOperTypeEnum posOper,String[] posCodes,OperUserDTO operUser,SourceEnum source);
    
     /**
      * POS停用/启用
      * @param posOper
      * @param posCodes
      * @param operUser
      * @param source 来源
      */
     void updatePosActivate(PosOperTypeEnum posOper,String[] posCodes, OperUserDTO operUser,SourceEnum source);
     
     /**
      * 门户查询POS信息
      * @param findBean 查询条件
      * @return
      */
     int getPosCount(String posCode);
     
     /**
      * 门户查询已绑定商户的POS信息
      * @param posCodes
      * @return
      */
     int getPosBindedCountByCodes(String[] posCodes);
     
     /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: getPosBindedCountByCodesAndMerCode 
     * @Description: 根据商户号和pos号查询是否是重复绑定
     * @param posCodes
     * @param merCode
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    int getPosBindedCountByCodesAndMerCode(String[] posCodes,String merCode);
     /**
      * 门户查询POS信息
      * @param findBean 查询条件
      * @return
      */
     List<Pos> findPosListPage(PosQuery findBean);
     
     /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findChildrenMerPosListPage 
     * @Description: 子商户POS管理
     * @param findBean
     * @return    设定文件 
     * List<Pos>    返回类型 
     * @throws 
     */
     List<Pos> findChildrenMerPosListPage(PosQuery findBean);
     /**
      * OSS删除POS信息
      * @param posOper
      * @param posCodes
      * @param operUser
      * @param source
      */
    void deletePos(PosOperTypeEnum posOper,String[] posCodes, OperUserDTO operUser,SourceEnum source);

    /**
     * 根据POS编码查询POS信息
     * @param code
     * @return
     */
    public Pos findPosByCode(String code);
    
    /**
     * 根据POS编码查询商户编号
     * @param posCode
     * @return
     */
    public Pos findMerchantCodeByPosCode(String posCode);
    
    /**
     * 供应商查询商户城市的pos信息
     * @param posCode
     * @param merName
     * @param cityName
     * @return
     */
    List<Pos> findPosinfoByPage(PosQuery findBean);
    
    /**
     * 查询商户pos数
     * @param merCode
     * @return
     */
    int  posCount(String merCode);
    
    
    /**
     * 查询POS信息
     * @author Mika
     * @param posCode
     * @param merCode
     * @return
     */
    public Pos findPosInfoByCode(String posCode, String merCode);
    
    /**
     * @author Mika
     * @param posCode
     * @param comments
     */
    public void savePosComments(String posCode, String comments);
    
}


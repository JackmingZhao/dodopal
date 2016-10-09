package com.dodopal.card.business.dao;

import java.util.List;
import java.util.Map;

import com.dodopal.card.business.model.query.ParameterQuery;
import com.dodopal.api.card.dto.*;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年3月18日 上午11:11:58 
  * @version 1.0 
  * 参数下载
  * @parameter    
  */
public interface ParameterMapper {
//    01参数 SELECT * FROM beijingtk.tlblackcardtb
//    02参数 SELECT * FROM beijingtk.tlfhcardtypetb
//    03参数 SELECT * FROM beijingtk.tlposoperationtb
//    04参数 SELECT * FROM beijingtk.tlregionblackcardtb
//    05参数 SELECT * FROM beijingtk.tlincrementalblackcardtb
//    06参数 SELECT * FROM beijingtk.tlposmenutb
//    07参数 SELECT * FROM beijingtk.tlfhgraylisttb
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:11:46 
      * @return  01黑名单参数
      */
    public List<BlankListParameter> findBlankListParameterByPage(ParameterQuery query);

    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:12:27 
      * @return  02消费可用卡类型参数
      */
    public List<ConsumeCardTypeParameter> findConsumeCardTypeParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:21:16 
      * @return  03终端运营参数（机具运营参数）
      */
    public List<TerminalParameter> findTerminalParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:21:51 
      * @return  04区域黑名单参数
      */
    public List<AreaBlankListParameter> findAreaBlankListParameterByPage(ParameterQuery query);
    
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月18日 上午11:22:31 
     * @return  05增量黑名单参数
     */
    public List<IncrementBlankListParameter> findIncrementBlankListParameterByPage(ParameterQuery query);
   
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:37:20 
      * @return  06终端菜单参数
      */
    public List<TerminalMenuParameter> findTerminalMenuParameterByPage(ParameterQuery query);
    
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:38:06 
      * @return  07灰名单参数
      */
    public List<GrayListParameter> findGrayListParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:38:46 
      * @return  33消费折扣参数
      */
    public List<ConsumeDiscountParameter> findConsumeDiscountParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:39:44 
      * @return  34分时段消费折扣参数
      */
    public List<SubPeriodDiscountParameter> findSubPeriodDiscountParameterByPage(ParameterQuery query);
    
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月24日 下午2:55:46 
      * @描述 分时段消费折扣的总计 
      * @return  
      */
    public String findSubPeriodDiscountParameterCount(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月21日 下午4:26:40 
      * @parameter  更新条件 posId 更新字段 DOWNLOADFLAG
      * @return  更新条数
      */
    public int updateDownloadFlag(Map <String,String> map);

}

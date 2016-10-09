package com.dodopal.card.business.service;

import com.dodopal.card.business.model.query.ParameterQuery;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.api.card.dto.*;

public interface ParameterService {
     /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 下午2:20:15 
      * @return  01黑名单参数
      */
    public DodopalDataPage<BlankListParameter> findBlankListParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:12:27 
      * @return  02消费可用卡类型参数
      */
    public DodopalDataPage<ConsumeCardTypeParameter> findConsumeCardTypeParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:21:16 
      * @return  03终端运营参数（机具运营参数）
      */
    public DodopalDataPage<TerminalParameter> findTerminalParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:21:51 
      * @return  04区域黑名单参数
      */
    public DodopalDataPage<AreaBlankListParameter> findAreaBlankListParameterByPage(ParameterQuery query);
    
    /** 
     * @author  Dingkuiyuan@dodopal.com 
     * @date 创建时间：2016年3月18日 上午11:22:31 
     * @return  05增量黑名单参数
     */
    public DodopalDataPage<IncrementBlankListParameter> findIncrementBlankListParameterByPage(ParameterQuery query);
   
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:37:20 
      * @return  06终端菜单参数
      */
    public DodopalDataPage<TerminalMenuParameter> findTerminalMenuParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:38:06 
      * @return  07灰名单参数
      */
    public DodopalDataPage<GrayListParameter> findGrayListParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:38:46 
      * @return  33消费折扣参数
      */
    public DodopalDataPage<ConsumeDiscountParameter> findConsumeDiscountParameterByPage(ParameterQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月18日 上午11:39:44 
      * @return  34分时段消费折扣参数
      */
    public DodopalDataPage<SubPeriodDiscountParameter> findSubPeriodDiscountParameterByPage(ParameterQuery query);
    
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月24日 下午2:59:17 
      * @描述 分时段消费折扣的总计
      */
    public String findSubPeriodDiscountParameterCount(ParameterQuery query);
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年3月21日 下午4:59:22 
      * @version 1.0 
      * @parameter   参数编号 pno ,设备id posid ,原下载标志downloadFlag
      * @描述 根据设备编号更新下载参数
      * @return  更新数目
      */
    public int updateDownloadFlag(String pno,String downloadFlag,String posId);
     
}

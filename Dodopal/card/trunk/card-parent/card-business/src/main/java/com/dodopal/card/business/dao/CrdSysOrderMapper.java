package com.dodopal.card.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.card.business.model.Bjtlfhupfiletb;
import com.dodopal.card.business.model.CrdSys100000Mobile;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.model.ProductPurchaseOrder;
import com.dodopal.card.business.model.ProductPurchaseOrderRecord;
import com.dodopal.card.business.model.SamSignInOffTb;
import com.dodopal.card.business.model.TlpPosMenuTb;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;

public interface CrdSysOrderMapper {
    /**
     * 根据yktcode获得访问城市前置具体的ip地址和端口号
     * @param map
     * @return
     */
    public Map<String, Object> queryYktIpAndPort(String yktCode);

    /**
     * 根据cityCode获得访问城市前置具体的ip地址和端口号
     * @param map
     * @return
     */
    public Map<String, Object> queryCityIpAndPort(String cityCode);

    /**
     * 根据产品库订单号查询卡服务订单条数
     * @param prdordernum
     * @return
     */
    public int checkCrdOrderByPrdId(String prdordernum);

    /**
     * 插入卡服务订单表
     * @param crdSysOrder
     */
    public void insertCrdSysOrder(CrdSysOrder crdSysOrder);

    /**
     * 插入卡服务补充表
     * @param crdSysSupplement
     */
    public void insertCrdSysSupplement(CrdSysSupplement crdSysSupplement);
    
    /**
     * 插入卡服务北京手机补充表
     * @param crdSysSupplement
     */
    public void insertCrdSys100000Mobile(CrdSys100000Mobile crdSys100000Mobile);
    
    /**
     * 查询卡服务循环序列
     * @return
     */
    public String queryCrdSysOrdercodeSeq();

    /**
     * @Title findCrdSysOrder
     * @Description 根据条件查询卡服务订单内容
     * @param crdSysOrder
     * @return
     */
    public List<CrdSysOrder> findCrdSysOrderByPage(CrdSysOrderQuery crdSysOrderQuery);

    /**
     * @Title findCrdSysOrderByCode
     * @Description 根据卡服务订单号查询详情
     * @param crdOrderNum
     * @return
     */
    public CrdSysOrder findCrdSysOrderByCode(String crdOrderNum);

    /**
     * @Title findCrdSysOrderAll
     * @Description 导出卡服务订单内容
     * @return
     */
    public List<CrdSysOrder> findCrdSysOrderAll(CrdSysOrderQuery crdSysOrderQuery);

    /**
     * 根据产品库订单号查询卡服务订单
     * @param prdordernum
     * @return
     */
    public CrdSysOrder findCrdSysOrderByPrdnum(String prdordernum);

    /**
     * 根据卡服务订单号更改卡服务订单
     * @param order
     * @return
     */
    public void updateCrdSysOrderByCrdnum(CrdSysOrder order);

    /**
     * 根据卡服务订单号更改卡服务订单补充表(申请秘钥前)
     * @param supplement
     */
    public void updateCrdSysSupplementByCrdnumBef(CrdSysSupplement supplement);
    
    /**
     * 根据卡服务订单号更改卡服务订单补充表(申请秘钥后)
     * @param supplement
     */
    public void updateCrdSysSupplementByCrdnumAfr(CrdSysSupplement supplement);

    /**
     * 重新获取chargeKey
     * @param crdOrderNum
     */
    public void updateCrdSysSupplementChargeKeyByCrdnum(@Param("crdOrderNum") String crdOrderNum, @Param("lastChargeKeyTime") String lastChargeKeyTime);

    /*
     * 创建充值卡服务主订单(北京)
     */
    public void insertCrdSysOrderForBJ(CrdSysOrder crdSysOrder);
    
    /*
     * 创建充值卡服务主订单(北京nfc)
     */
    public void insertCrdSysOrderForBJNfc(CrdSysOrder crdSysOrder);

    /*
     * 查询签到签退表数据
     */
    public SamSignInOffTb querySamInOffTbByPosId(String posId);

    /*
     * 查询商户折扣信息
     */
    public int queryMerDiscountByMerCode(String merCode);

    /*
     * 查询菜单数据
     */
    public TlpPosMenuTb queryTlpPosMenuTbBySamNo(String samNo);
    /*
     * 查询菜单数据消费
     */
    public TlpPosMenuTb queryTlpPosMenuTbConsBySamNo(String samNo);

    /*
     * 更新卡服务pos通讯流水号
     */
    public void updatePosTranSeqByCrdnum(@Param("crdnum") String crdnum, @Param("posTranSeq") String posTranSeq);
    
    /*
     * 
     * 更新卡服务pos通讯流水号(消费)
     */
    public void updatePosTranSeqConsByCrdnum(@Param("crdnum") String crdnum, @Param("posTranSeq") String posTranSeq);

    /*
     * 查询商户号
     */
    public String queryMerCodeByPosNo(@Param("posNo") String posNo);

    /*
     * 查询脱机标识
     */
    public String queryOfflineBySamNo(@Param("samNo") String samNo, @Param("cardPhyType") String cardPhyType, @Param("cardType") String cardType);

    /*
     * 查询脱机金额
     */
    public String queryOfflineAmt(String samNo);
    /*
     * 更新通讯流水号
     */
    public void updateSamsigninfo(@Param("posId") String posId, @Param("posIcSeq") String posIcSeq, @Param("posTransSeq") String posTransSeq);
    /*
     * 查询商户秘钥
     */
    public Map<String, Object> queryKeyset(String merCode);
    
    /**
     * 脱机结果上传卡服务校验重复订单
     * @param consOrder
     * @return
     */
    public int checkCrdOrderCount(CrdSysConsOrder consOrder);
    
    /**
     * 脱机结果上传通卡订单校验重复订单
     * @param bjtlfhupfiletb
     * @return
     */
    public int checkTkOrderCount(Bjtlfhupfiletb bjtlfhupfiletb);
    
    /**
     * 插入通卡订单表
     * @param bjtlfhupfiletb
     */
    public void insertTkOrder(Bjtlfhupfiletb bjtlfhupfiletb);
    
    /**
    * 创建公交卡消费主订单
    * @param purchaseOrder
    * @return
    */
   public int addProductPurchaseOrder(ProductPurchaseOrder purchaseOrder);
   
   
   /**
    * 创建公交卡收单记录
    * @param orderRecord
    * @return
    */
   public int addProductPurchaseOrderRecord(ProductPurchaseOrderRecord orderRecord);
   
   /**
    * 产品库公交卡消费主订单编号后五位:五位数据库cycle sequence（循环使用）
    * @return
    */
   public String getPrdPurchaseOrderNumSeq();
}

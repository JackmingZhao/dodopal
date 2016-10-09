package com.dodopal.oss.business.service;

import java.util.List;
import java.util.Map;

import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.ProductConsumeOrder;
import com.dodopal.oss.business.bean.ProductConsumeOrderDetail;
import com.dodopal.oss.business.bean.query.ProductConsumeOrderQuery;
import com.dodopal.oss.delegate.bean.IcdcPrdBean;
import com.dodopal.oss.delegate.bean.IcdcPrdQuery;

public interface IcdcPrdService {
    
    /**
     * 按条件查询一卡通充值产品信息
     * @param icdcPrdQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<IcdcPrdBean>> queryIcdcPrdPageByCondition(IcdcPrdQuery icdcPrdQuery);

    /**
     * 获取一卡通充值产品导出信息
     * @param icdcPrdQuery
     * @return
     */
    public DodopalResponse<List<IcdcPrdBean>> getIcdcPrductListForExportExcel(IcdcPrdQuery icdcPrdQuery);

    /**
     * 按条件查询一卡通充值产品信息
     * @param code
     * @return
     */
    public DodopalResponse<IcdcPrdBean> queryIcdcPrdByCode(String code);

    /**
     * 上架
     * @param icdcPrdCode 产品编码
     * @return
     */
    public DodopalResponse<String> putAwayIcdcPrd(List<String> icdcPrdCode, String upateUser);

    /**
     * 下架
     * @param icdcPrdCode
     * @return
     */
    public DodopalResponse<String> soltOutIcdcPrd(List<String> icdcPrdCode, String upateUser);

    /**
     * 更新一卡通充值产品信息
     * @param icdcPrdBean
     * @return
     */
    public DodopalResponse<String> updateIcdcPrd(List<IcdcPrdBean> icdcPrdBean);

    /**
     * 保存一卡通充值产品入库
     * @param icdcPrdBean
     * @return
     */
    public DodopalResponse<String> saveIcdcPrd(List<IcdcPrdBean> icdcPrdBean);

    /**
     * 查询一卡通公司
     * @return
     */
    public DodopalResponse<List<Map<String, String>>> queryIcdcNames(String type);

    /**
     * 查询一卡通公司业务城市
     * @param code
     * @return
     */
    public DodopalResponse<List<Map<String, String>>> queryIcdcBusiCities(String code);

    /**
     * 查询 一卡通消费 收单记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrder>> findProductConsumeOrderByPage(ProductConsumeOrderQuery prdOrderQuery);

    /**
     * 根据消费订单号 orderNum 查询一卡通消费订单详情
     * @param orderNum 订单号
     * @return
     */
    public DodopalResponse<ProductConsumeOrderDetail> findProductConsumeOrderDetails(String orderNum);

    /**
     * 消费订单导出
     * @param prdOrderQuery
     * @return 
     */
    public DodopalResponse<List<ProductConsumerOrderForExport>> getConsumerOrderListForExportExcel(ProductConsumeOrderQuery prdOrderQuery);
    
    /**
     * 查询 一卡通消费 异常记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrder>> findProductConsumeOrdersExptionByPage(ProductConsumeOrderQuery prdOrderQuery);

    /**
     * 导出消费异常记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductConsumeOrder>> excelExceptionProductOrderExport(ProductConsumeOrderQuery prdOrderQuery);

}

package com.dodopal.oss.delegate;

import com.dodopal.api.product.dto.ProductConsumeOrderDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDetailDTO;
import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.bean.IcdcPrdBean;
import com.dodopal.oss.delegate.bean.IcdcPrdQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by dodopal on 2015/7/22.
 */
public interface IcdcPrdDelegate {
    /**
     * 分页查询符合条件一卡通充值产品信息
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
     * 更新一卡通充值产品信息
     * @param icdcPrdDto
     * @return
     */
    public DodopalResponse<String> updateIcdcPrd(List<IcdcPrdBean> icdcPrdDto);

    /**
     * 上架
     * @param icdcPrdCode
     * @return
     */
    public DodopalResponse<String> putAwayIcdcPrd(List<String> icdcPrdCode,String updateUser);

    /**
     * 下架
     * @param icdcPrdCode
     * @return
     */
    public DodopalResponse<String> soltOutIcdcPrd(List<String> icdcPrdCode,String updateUser);

    /**
     * 保存一卡通充值产品入库
     * @param icdcPrdDto
     * @return
     */
    public DodopalResponse<String> saveIcdcPrd(List<IcdcPrdBean> icdcPrdDto);

    /**
     * 根据编码查询一卡通充值产品
     * @param code
     * @return
     */
    public DodopalResponse<IcdcPrdBean> queryIcdcPrdByCode(String code);

    /**
     * 查询一卡通公司名称信息
     * @return
     */
    public DodopalResponse<List<Map<String,String>>> queryIcdcNames(String type);

    /**
     * 查询一卡通公司业务城市
     * @param code
     * @return key:城市编码 value:城市名称 的链表
     */
    public DodopalResponse<List<Map<String,String>>> queryIcdcBusiCities(String code);
    
    /**
     * 判断通卡公司是否存在
     * @param icdcPrdBean
     * @return
     */
    public DodopalResponse<Boolean> checkPrdProductYktExist(List<IcdcPrdBean> icdcPrdBean);
    
    /**
     * 查询 一卡通消费 收单记录（分页）
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderByPage(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 根据消费订单号 orderNum 查询一卡通消费订单详情
     * @param orderNum 订单号
     * @return
     */
    public DodopalResponse<ProductConsumeOrderDetailDTO> findProductConsumeOrderDetails(String orderNum);

    /**
     * 查询 一卡通消费 收单记录（导出）
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductConsumerOrderForExport>> getConsumerOrderListForExportExcel(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 导出消费异常记录
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<List<ProductConsumeOrderDTO>> excelExceptionProductOrderExport(ProductConsumeOrderQueryDTO prdOrderQuery);
    
    /**
     * 查询 一卡通消费 收单记录（导出）
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrdersExptionByList(ProductConsumeOrderQueryDTO prdOrderQuery);
}

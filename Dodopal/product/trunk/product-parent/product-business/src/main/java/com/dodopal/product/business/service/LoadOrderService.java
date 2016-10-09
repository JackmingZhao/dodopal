package com.dodopal.product.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.dodopal.api.product.dto.IcdcLoadOrderRequestDTO;
import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.api.product.dto.LoadOrderExtResponseDTO;
import com.dodopal.api.product.dto.LoadOrderQueryResponseDTO;
import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.api.product.dto.LoadOrderResponseDTO;
import com.dodopal.api.product.dto.LoadOrderResponseDTO2;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.LoadOrderRecord;
import com.dodopal.product.business.model.query.LoadOrderQuery;
import com.dodopal.product.business.model.query.ProLoadOrderQuery;

public interface LoadOrderService {

    /**
     * 个人门户web端____创建公交卡充值圈存订单
     * @param loadOrderRequestDTO
     * @return
     */
    public DodopalResponse<LoadOrder> bookLoadOrder(IcdcLoadOrderRequestDTO icdcLoadOrderRequestDTO);
    
    /**
     * 自助终端____创建公交卡充值圈存订单
     * @param loadOrderRequestDTO
     * @return
     */
    public DodopalResponse<LoadOrder> bookLoadOrderForPaySys(IcdcLoadOrderRequestDTO icdcLoadOrderRequestDTO);

    /**
     * 手机APP端____创建公交卡充值圈存订单
     * @param loadOrderRequestDTO
     * @return
     */
    public DodopalResponse<LoadOrder> bookMobileLoadOrder(IcdcLoadOrderRequestDTO loadOrderRequestDTO);

    /**
     * 外接商户____创建公交卡充值圈存订单
     * @param loadOrderRequestDTO
     * @return
     */
    public DodopalResponse<LoadOrderResponseDTO> bookExtMerLoadOrder(LoadOrderRequestDTO loadOrderRequestDTO);

    /**
     * 查询公交卡充值圈存订单
     * @param loadOrderNum 圈存订单编号
     * @return
     */
    public LoadOrder getLoadOrderByLoadOrderNum(String loadOrderNum);

    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年1月26日 下午6:03:13 
      * @version 1.0 
      * @parameter  
      * @since  查询一卡通订单详情
      * @return  
      */
    public LoadOrder viewLoadOrderByLoadOrderNum(String loadOrderNum);

    
    /**
     * 调用支付交易成功后__修改公交卡充值圈存订单状态
     * @param loadOrderNum 圈存订单编号
     * @return
     */
    public DodopalResponse<LoadOrder> updateloadOrderStateAfterAccountDeduct(String loadOrderNum);

    /**
     * 公交卡充值流程中检验圈存订单合法性 根据圈存订单号去查询圈存订单是否存在（联合参数中的卡号和圈存订单编号进行查询） 提供方 :
     * 产品库圈存订单管理类 访问范围: 产品库公交卡充值业务流程类 如果验证通过，则返回000000。同时返回：产品编号和充值金额。
     */

    public DodopalResponse<LoadOrderDTO> validateLoadOrderForIcdcRecharge(LoadOrderQuery loadOrder);

    /*********************************************************************************************************/
    /************************************* 华丽分割线 ******************************* *****/
    /*********************************************************************************************************/

    /**
     * 6.1 查询公交卡充值圈存订单
     * @param loadOrderRequestDTO
     * @return
     */
    public DodopalResponse<List<LoadOrderResponseDTO2>> findLoadOrder();

    /**
     * 6.2 根据卡号获取可用于一卡通充值的圈存订单 产品库内部调用
     * @param cardNum
     * @return
     */
    public DodopalResponse<List<LoadOrderQueryResponseDTO>> findAvailableLoadOrdersByCardNum(String cardNum);

    /**
     * 6.3 根据外接商户的订单号查询圈存订单状态
     * 产品库对外提供http接口来接收第三方的请求，根据第三方提供的外部订单号，查询相应的圈存订单的状态信息。
     * @param loadOrderRequestDTO
     * @return
     */
    public DodopalResponse<LoadOrderExtResponseDTO> findLoadOrderStatus(LoadOrderRequestDTO loadOrderRequestDTO);

    /**
     * 6.4 查询公交卡充值圈存订单
     */
    public DodopalResponse<DodopalDataPage<LoadOrderDTO>> findLoadOrdersByPage(LoadOrderQueryDTO queryDto);

    /**
     * 查询圈存订单(分页)
     * @param loadOrderRequestDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<LoadOrderRecord>> findLoadOrderListByPage(ProLoadOrderQuery prdOrderQuery);

    /**
     * Title:一卡通充值业务订单导出 Time:2015-10-16 14:01 Name:Joe
     */
    public DodopalResponse<String> excelLoadOrder(HttpServletResponse response, ProLoadOrderQuery prdOrderQuery);
    
    public List<LoadOrder> findLoadOrdersExport(LoadOrderQueryDTO query);
    
    /**
     * 更新圈存订单状态
     * @param loadOrderStatus
     * @param loadOrderNum
     * @param updateUser
     * @return
     */
    public int updateLoadOrderStatus(String loadOrderStatus, String loadOrderNum, String updateUser);
    
    
}

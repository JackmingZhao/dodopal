package com.dodopal.product.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.query.ProLoadOrderQuery;

public interface LoadOrderMapper {
    
    
    // 获取圈存订单SEQ
    public String selectOrderNumSeq();
    
    // 创建充值圈存订单
    public void insertLoadOrder(LoadOrder loadOrder);
    
    // 更新圈存订单状态
    public int updateLoadOrderStatus(@Param("loadOrderStatus") String loadOrderStatus, @Param("loadOrderNum") String loadOrderNum, @Param("updateUser") String updateUser);
    
    // 根据圈存订单编号获取圈存订单信息
    public LoadOrder getLoadOrderByLoadOrderNum(@Param("orderNum")String loadOrderNum);
    
    //查看圈存订单详情
    public LoadOrder viewLoadOrderByLoadOrderNum(@Param("orderNum")String loadOrderNum);
    
    // 根据外部订单号获取获取圈存订单信息（验证外部订单号是否重复下单）
    public LoadOrder getLoadOrderByExtMerOrderNum(@Param("extMerOrderNum")String extMerOrderNum);
    
    
    // 根据城市ID获取该城市的自定义产品编号
    public String findPrdProductYktByCityCode(String cityId);
    
    /***********************************************/
    
    public String findCityName(String cityId);
    
    public String findMerchantName(String merCode);
    
    //public int validateProduct(String proCode);
    

    
    public String yktIsRecharge(String cityId);
    
    public String yktIsRechargeByProCode(String proCode);
    
    public String yktActivate(String proCode);
    
    public String yktActivateByCityCode(String cityId);
    
    
   
   
    /***********************************************/
    
    public List<LoadOrder> findLoadOrder();

    public List<LoadOrder> findAvailableLoadOrdersByCardNum(String cardNum);
    
    public LoadOrder findLoadOrderStatus(LoadOrderRequestDTO requestDto);

    
    public List<LoadOrder> findLoadOrdersByPage(LoadOrderQueryDTO loadOrderQueryDTO);
    
    //查询圈存订单(分页)
    public List<LoadOrder> findLoadOrderListByPage(ProLoadOrderQuery prdOrderQuery);
    
    public List<LoadOrder> excelLoadOrder(ProLoadOrderQuery prdOrderQuery);
    
    public List<LoadOrder> findLoadOrdersExport(LoadOrderQueryDTO query);
    
}

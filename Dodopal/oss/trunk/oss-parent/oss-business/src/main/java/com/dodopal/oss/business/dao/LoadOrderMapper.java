package com.dodopal.oss.business.dao;

import org.apache.ibatis.annotations.Param;

public interface LoadOrderMapper {
    
    // 更新圈存订单状态
    public int updateLoadOrderStatus(@Param("loadOrderStatus") String loadOrderStatus, @Param("loadOrderNum") String loadOrderNum, @Param("updateUser") String updateUser);
}

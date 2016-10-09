package com.dodopal.running.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 *  异常充值订单处理界面传值DTO
 */
public class ExceptionOrderHandleDto extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 鉴定结果（0：失败——需要执行资金解冻流程；1：成功——需要执行资金扣款流程）
     */
    private String judgeResult;
    
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult;
    }

}

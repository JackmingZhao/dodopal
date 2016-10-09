package com.dodopal.oss.business.model;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ConsumeOrderInternalStatesEnum;
import com.dodopal.common.enums.ConsumeOrderStatesEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;

/**
 * 账户充值异常清分DTO
 * 
 * @author dodopal
 */

public class AcctReChargeClearingDTO extends ClearingBasicDataDTO {
	private static final long serialVersionUID = 1L;
	// 创建人
	private String createUser;
	// 编辑人
	private String updateUser;
	// 外部交易状态为(0：待支付1：已取消2：支付中3：已支付4：待退款5：已退款6：待转帐7：转帐成功8：关闭)
	private String tranOutStatus;
	// 内部交易状态为(0：待处理1：处理中3：账户冻结成功4：账户冻结失败5：账户解冻成功6：账户解冻失败7：账户扣款成功8：账户扣款失败10：账户加值成功11：账户加值失败)
	private String tranInStatus;

	public String getCreateUser() {
		return createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public String getTranOutStatus() {
		if (StringUtils.isBlank(tranOutStatus)) {
			return null;
		}
		if (ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(this.tranOutStatus) == null) {
			return null;
		} else {
			return ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(this.tranOutStatus).getName();
		}
	}

	public String getTranInStatus() {
		if (StringUtils.isBlank(tranInStatus)) {
			return null;
		}
		if (ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(this.tranInStatus) == null) {
			return null;
		} else {
			return ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(this.tranInStatus).getName();
		}
	}
}

package com.dodopal.oss.business.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ConsumeOrderInternalStatesEnum;
import com.dodopal.common.enums.ConsumeOrderStatesEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayGatewayEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.model.BaseEntity;

/**
 * 一卡通消费异常DTO
 *
 */
public class CardConsumeClearingDTO extends ClearingBasicDataDTO {
	/**
	 * @author dodopal
	 */
	private static final long serialVersionUID = 1L;

	// 产品库消费主订单表:状态
	private String tranOutStatus;
	// 产品库公交卡收单记录表:内部状态
	private String tranInStatus;

	public String getTranOutStatus() {
		 if (StringUtils.isBlank(tranOutStatus)) {
	            return null;
	        }       
	        if(ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(this.tranOutStatus)==null){
	            return null;
	        }else{
	            return ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(this.tranOutStatus).getName();
	        }
	}

	public String getTranInStatus() {
		if (StringUtils.isBlank(tranInStatus)) {
            return null;
        }       
        if(ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(this.tranInStatus)==null){
            return null;
        }else{
            return ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(this.tranInStatus) .getName();
        }
	}

}

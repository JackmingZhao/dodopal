package com.dodopal.transfer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.service.TransferChilrenMerService;

/**
 * 普通直营网点和加盟网点数据迁移程序
 * @author xiongzhijing@dodopal.com
 *
 */
@Controller
@RequestMapping("/transfer")
public class ChilrenMerchantController extends CommonController {

    @Autowired
    TransferChilrenMerService transferChilrenMerService;

    
    
    @RequestMapping("/chilrenMerTransfer")
    public DodopalResponse<String> ChilrenMertransfer() {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            transferChilrenMerService.transferChilrenMerService();
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;

    }

}

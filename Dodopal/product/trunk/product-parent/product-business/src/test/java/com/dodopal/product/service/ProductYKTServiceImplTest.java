package com.dodopal.product.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dodopal.common.util.DateUtils;

@Service
public class ProductYKTServiceImplTest {
    public static void  main(String[] orgs){
     
        
        // 2，检验服务器时间是否处于消费限制时间段以内（时间段内，不允许做消费业务）
        boolean sign = false;// 处于维护时间段内标识，默认false:不处于时间段内
        String consumeLimitStartTime = "15:30:32";// 消费限制开始时间
        String consumeLimitEndTime = "1:30:30";// 消费限制结束时间
        if (StringUtils.isNotBlank(consumeLimitStartTime) && StringUtils.isNotBlank(consumeLimitEndTime)) {

            // 服务器系统当前时间(格式：HH:mm:ss)
            String currentTime = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_HHMMSS_STR);
            currentTime="2:30:31";
            
            // 判断服务器当前时间是否处于消费限制时间段内，设置sign值
            if (DateUtils.timeSub(consumeLimitStartTime, consumeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) > 0) {
                if (DateUtils.timeSub(consumeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, consumeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    sign = true;
                }
            } else if (DateUtils.timeSub(consumeLimitStartTime, consumeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) == 0 ) {
                if (DateUtils.timeSub(consumeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) == 0) {
                    sign = true;
                }
            } else {
                boolean repairSgin1 = false;
                if (DateUtils.timeSub(consumeLimitStartTime, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, DateUtils.DAY_TIME_END, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    repairSgin1 = true;
                }
                boolean repairSgin2 = false;
                if (DateUtils.timeSub(DateUtils.DAY_TIME_START, currentTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0 
                    && DateUtils.timeSub(currentTime, consumeLimitEndTime, DateUtils.DATE_FORMAT_HHMMSS_STR) >= 0) {
                    repairSgin2 = true;
                }
                sign = repairSgin1 || repairSgin2;
            }
        }

        // 当服务器当前时间处于消费限制时间段内，返回错误码。不允许做消费业务
        if (sign) {
            System.out.println("通卡公司系统维护中");
        } else {
            System.out.println("通卡公司系统正常");
        }
        
    }
    
    
}

package com.dodopal.common.viewResolver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.dodopal.common.util.WebUtils;

/**
 * 功能描述：dodopal web层捕获异常，简单处理
 */
public class DodopalSimpleExceptionResolver extends SimpleMappingExceptionResolver {

    private static DateFormat ALERT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DodopalSimpleExceptionResolver() {
        setWarnLogCategory("DodopalSimpleExceptionResolver");
    }

    @Override
    protected String buildLogMessage(Exception ex, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("time：");
            sb.append(ALERT_DATE_FORMAT.format(new Date()));
            sb.append("\n");
            sb.append("rquestInfo:\t");
            sb.append(request.getParameterMap());
            sb.append("ClientIP:");
            sb.append(WebUtils.getIp(request));
            sb.append("\tServerIP:");
            sb.append(WebUtils.getServerIPsStr());
            sb.append("\n");
            sb.append(WebUtils.getFullRequestInfo(request));
            sb.append("\n");
            sb.append("exception_message:");
            sb.append(ex.getMessage());
            sb.append("\n");
        }
        catch (Exception e) {
            sb.append("buildLogMessage error:" + e);
        }
        return sb.toString();
    }
}
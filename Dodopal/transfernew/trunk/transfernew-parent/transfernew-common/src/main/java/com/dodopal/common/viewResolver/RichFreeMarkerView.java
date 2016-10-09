package com.dodopal.common.viewResolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 扩展spring的FreemarkerView，加上base属性。
 */
public class RichFreeMarkerView extends FreeMarkerView {
    private Logger logger = LoggerFactory.getLogger(RichFreeMarkerView.class);
    /**
     * 部署路径属性名称
     */
    public static final String CONTEXT_PATH = "base";

    /**
     * 请求uri
     */
    public static final String REQUEST_URI = "uri";

    /**
     * 在model中增加部署路径base，方便处理部署路径问题。
     */
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
//        SysDdicService sysDdic = (SysDdicService) SpringBeanUtil.getBean(SysDdicService.BEAN_NAME);
//        model.put(CONTEXT_PATH, request.getContextPath());//获取上下文路径
//        model.put(REQUEST_URI, request.getRequestURI());//请求uri
//		model.put("styleUrl", sysDdic.getStyleUrl());
//		model.put("scriptUrl", sysDdic.getScriptUrl());
//		model.put("imgUrl", sysDdic.getImgUrl());
//		model.put("ocxUrl", sysDdic.getOcxUrl());
    }
    
}
package com.dodopal.common.viewResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * ViewResolver for RichFreeMarkerView Override buildView, if viewName start
 * with / , then ignore prefix.
 */
public class RichFreeMarkerViewResolver extends AbstractTemplateViewResolver {

    private final static Logger logger = LoggerFactory.getLogger(RichFreeMarkerViewResolver.class);

    /**
     * Set default viewClass
     */
    public RichFreeMarkerViewResolver() {
        setViewClass(RichFreeMarkerView.class);
    }

    /**
     * if viewName start with / , then ignore prefix.
     */
    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView view = super.buildView(viewName);
        // start with / ignore prefix
        if (viewName.startsWith("/")) {
            if (viewName.endsWith(getSuffix())) {
                view.setUrl(viewName);
            } else {
                view.setUrl(viewName + getSuffix());
            }
        }
        logger.info("forward view's url:" + view.getUrl());
        return view;
    }
}
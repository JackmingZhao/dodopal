package com.dodopal.hessian;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 */
public class HessianDispatcherServlet extends HttpServlet {

    /**
	 */
    private static final long serialVersionUID = -7625335213634856414L;

    /**
	 */
    @Autowired
    private IHessianServiceManager serviceManager;

    /**
	 */
    private Pattern pattern = Pattern.compile("(.+)");

    //	private Map<String, HessianServiceExporter> exporters = new HashMap<String, HessianServiceExporter>();

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.initSpringContext();
    }

    /**
     * @param springConfig
     */
    private void initSpringContext() {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        //初始化放在ServletContextLoader中
        //DefaultBeanFactory.setSpringApplicationContext(ctx);

        serviceManager = (IHessianServiceManager) ctx.getBean("hessianServiceManager");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        throw new ServletException("only support post method.");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            invokeService(resp, req);
        }
        catch (Throwable e) {
            throw new ServletException(e);
        }
    }

    /**
     * @param response
     * @param request
     * @throws ServletException
     */
    protected void invokeService(final HttpServletResponse response, final HttpServletRequest request) throws ServletException {
        String requestPath = request.getParameter("id");
        String serviceId = getServiceId(requestPath);
        invokeBean(request, response, serviceId);
    }

    /**
     * @param request
     * @param response
     * @param serviceId
     * @throws ServletException
     */
    protected void invokeBean(final HttpServletRequest request, final HttpServletResponse response, final String serviceId) throws ServletException {
        HessianServiceExporter exporter = findHttpInvokerServiceExporter(serviceId);
        if (null == exporter) {
            // LoggerUtil.error("Hessian service not found:" + serviceId);
            throw new ServletException("Can not find http invoke service object.");
        }
        try {
            exporter.handleRequest(request, response);
        }
        catch (IOException e) {
            throw new ServletException(e);
        }
    }

    /**
     * @param serviceId String
     * @return HttpInvokerServiceExporter
     */
    protected HessianServiceExporter findHttpInvokerServiceExporter(final String serviceId) {
        HessianServiceExporter exporter = serviceManager.findService(serviceId);
        return exporter;
    }

    /**
     * @param requestPath
     * @return
     */
    protected String getServiceId(final String requestPath) {
        Matcher match = pattern.matcher(requestPath);
        match.matches();
        match.group();
        String serviceId = match.group(1);
        return serviceId;
    }
}

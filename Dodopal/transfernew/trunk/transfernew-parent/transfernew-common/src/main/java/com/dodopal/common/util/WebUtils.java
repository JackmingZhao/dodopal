package com.dodopal.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WEB应用辅助类 2014-8-5
 */
public class WebUtils {

    private final static Logger log = LoggerFactory.getLogger(WebUtils.class);

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    /**
     * dispatch request to uri 本方法 request 为 null
     * 采用请求重定向（response.sendRedirect()）方式 uri 以“http://” 或
     * “https://”开头，采用请求重定向（response.sendRedirect()）方式 其他的uri都认为是站内请求转发，
     * 切记不能有类似与“shop1.sdo.com/index.htm”的uri 这种方式将出错 应该是用“/index.htm”
     * 或者“http://shop1.sdo.com/index.htm”
     * @param uri url or uri
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    public static void dispatch(String uri, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (StringUtils.isBlank(uri)) {
            if (log.isWarnEnabled()) {
                log.warn("uri must not be null or empty");
            }
            return;
        }
        if (response == null) {
            if (log.isErrorEnabled()) {
                log.error("HttpServletResponse parameter must not be null");
            }
            return;
        }
        String tempUri = uri.trim();
        if ((tempUri.length() > 7 && tempUri.substring(0, 7).equalsIgnoreCase((HTTP_PREFIX))) || (tempUri.length() > 8 && tempUri.substring(0, 8).equalsIgnoreCase((HTTPS_PREFIX)))) {
            response.sendRedirect(uri);
            return;
        }
        if (request == null) {
            if (log.isWarnEnabled()) {
                log.warn("HttpServletRequest parameter is null," + "so response.sendRedirect() method is used");
            }
            response.sendRedirect(uri);
            return;
        }
        request.getRequestDispatcher(uri).forward(request, response);
    }

    /**
     * 返回服务器IP字符串
     * @return
     * @throws SocketException
     */
    public static String getServerIPsStr() throws SocketException {
        StringBuilder sb = new StringBuilder();
        List<String> ips = getServerIpsExceptLocalHost();
        for (String ip : ips) {
            sb.append(ip);
            sb.append("   ");
        }
        return sb.toString();
    }

    private static String getHeader(HttpServletRequest request, String name) {
        if (log.isInfoEnabled()) {
            String value = request.getHeader(name);
            log.info(name + "=" + value + " from RequestHeader");
        }
        return request.getHeader(name);
        // Enumeration enum = request.getHeaders(name);
        // if (enum.hasMoreElements())
        // {
        // return (String)enum.nextElement();
        // }
        // return "";
    }

    /**
     * 获取本机除127.0.0.1外的所有IP
     * @return
     * @throws SocketException
     */
    public static List<String> getServerIpsExceptLocalHost() throws SocketException {
        List<String> allIps = getServerIps();
        List<String> ips = new ArrayList<String>();
        for (String ip : allIps) {
            if (!StringUtils.equalsIgnoreCase(ip, "127.0.0.1")) {
                ips.add(ip);
            }
        }
        return ips;
    }

    /**
     * 获取本机所有IP
     * @return
     * @throws SocketException
     */
    public static List<String> getServerIps() throws SocketException {
        List<String> ips = new ArrayList<String>();
        // 根据网卡取本机配置的IP
        Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
        while (e1.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) e1.nextElement();
            Enumeration<?> e2 = ni.getInetAddresses();
            while (e2.hasMoreElements()) {
                InetAddress ia = (InetAddress) e2.nextElement();
                if (ia instanceof Inet6Address)
                    continue;
                String ip = ia.getHostAddress();
                ips.add(ip);
            }
        }
        return ips;
    }

    /**
     * 获得客户端Ip.
     * @param request
     * @return
     */
    public static String getIp(final HttpServletRequest request) {
        String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级反向代理
        if (null != ip && !"".equals(ip.trim())) {
            StringTokenizer st = new StringTokenizer(ip, ",");
            String ipTmp = "";
            if (st.countTokens() > 1) {
                while (st.hasMoreTokens()) {
                    ipTmp = st.nextToken();
                    if (ipTmp != null && ipTmp.length() != 0 && !"unknown".equalsIgnoreCase(ipTmp)) {
                        ip = ipTmp;
                        break;
                    }
                }
            }
        }
        return ip;
    }

    /**
     * the value of the specified request header
     * @param request
     * @param name
     * @return
     */
    public static String getHeaderFromRequest(HttpServletRequest request, String name) {
        return getHeader(request, name);
    }

    /**
     * 获取 App 路径, 例如:
     * @param request
     */
    public static String getApplicationContextPath(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            sb.append(":" + request.getServerPort());
        }
        sb.append(request.getContextPath());
        return sb.toString();
    }

    /**
     * @param str
     * @return
     */
    public static String mask(String str) {
        String mask = "";
        if (!org.springframework.util.StringUtils.isEmpty(str)) {
            if (str.length() > 3) {
                int index = str.indexOf("@");
                if (index > 0) {
                    mask = str.substring(0, 1) + "****" + str.substring(index);
                } else {
                    mask = str.substring(0, 2) + "****" + str.substring(str.length() - 1);
                }
            } else {
                mask = str.substring(0, 1) + "****";
            }
        }
        return mask;
    }

    @SuppressWarnings("unchecked")
    public static String getQueryString(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuffer sb = new StringBuffer();
        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement().toString();
                String prmValue = request.getParameter(name);
                sb.append(name).append("=").append(encodeUTF8WithEscape(prmValue)).append("&");
            }
        }
        sb.append("loginPa=").append((int) (Math.random() * 10000));
        return sb.toString();
    }

    /**
     * 打印详细请求信息
     * @param req
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getFullRequestInfo(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("HttpHeaders:\n");
        Enumeration headerNames = req.getHeaderNames();
        for (; headerNames.hasMoreElements();) {
            String headerName = (String) headerNames.nextElement();
            sb.append("\t");
            sb.append(headerName);
            sb.append(":");
            sb.append(req.getHeader(headerName));
            sb.append("\n");
        }
        sb.append(getRequestInfo(req));
        return sb.toString();
    }

    /**
     * 打印请求信息
     * @param req
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestInfo(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("RequestURL:");
        sb.append(req.getRequestURL());
        String queryString = req.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            sb.append("?");
            sb.append(queryString);
        }
        sb.append("\t");
        sb.append("Method:");
        sb.append(req.getMethod());
        sb.append("\n");
        sb.append("Parameters[");
        Enumeration params = req.getParameterNames();
        for (; params.hasMoreElements();) {
            sb.append("[");
            String param = (String) params.nextElement();
            sb.append(param);
            sb.append(":");
            sb.append(req.getParameter(param));
            sb.append("]");
        }
        return sb.toString();
    }

    public static String encodeUTF8WithEscape(String ori) {
        String result = ori;
        try {
            result = URLEncoder.encode(result, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            log.error("encodeUTF8WithEscape ", e);
        }
        return result;
    }
}

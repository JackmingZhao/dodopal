/*
 * Sdo.com Inc.
 * Copyright (c) 2009 All Rights Reserved.
 */
package com.dodopal.common.util;

import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取系统参数（从Appvar.properties里面获取）的工具类。
 */
public final class DodopalAppVarPropsUtil {

    private final static Logger log = LoggerFactory.getLogger(DodopalAppVarPropsUtil.class);

    private static Properties netbarAppProperties = new Properties();

    public final static String getStringProp(String key) {
        String v = netbarAppProperties.getProperty(key);
        return (v == null) ? null : v.trim();
    }

    /**
     * 获取属性的值，如果不存在，则返回默认的值。
     * @param key
     * @param defaultVal
     * @return
     */
    public final static String getStringProp(String key, String defaultVal) {
        try {
            return getStringProp(key);
        }
        catch (Exception ex) {
            log.error("getStringProp", ex);
            return defaultVal;
        }
    }

    public final static int getIntProp(String key) {
        String val = getStringProp(key);
        return Integer.parseInt(val);
    }

    /**
     * 获取整型属性的值，如果属性不存在或者类型错误，则返回默认值。
     * @param key
     * @param defaultVal
     * @return
     */
    public final static int getIntProp(String key, int defaultVal) {
        try {
            return getIntProp(key);
        }
        catch (Exception ex) {
            log.error("getIntProp", ex);
            return defaultVal;
        }
    }

    public final static long getLongProp(String key) {
        String val = getStringProp(key);
        return Long.parseLong(val);
    }

    public final static boolean getBooleanProp(String key) {
        String val = getStringProp(key);
        return Boolean.valueOf(val);
    }

    public final static boolean getBooleanProp(String key, boolean defaultVal) {
        try {
            return getBooleanProp(key);
        }
        catch (Exception ex) {
            log.error("getBooleanProp", ex);
            return defaultVal;
        }
    }

    /**
     * 获取Long类型属性的值，如果属性不存在或者类型错误，则返回默认值。
     * @param key
     * @param defaultVal
     * @return
     */
    public final static long getLongProp(String key, long defaultVal) {
        try {
            return getLongProp(key);
        }
        catch (Exception ex) {
            log.error("getLongProp", ex);
            return defaultVal;
        }
    }

    public static void copyProperties(Properties props) {
        Set<Object> keySet = props.keySet();
        for (Object key : keySet) {
            netbarAppProperties.setProperty(key.toString(), props.getProperty(key.toString()));
        }
    }
}

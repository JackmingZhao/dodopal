package com.dodopal.common.util;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DDPStringBuilder {

    public static String toString(Object obj) {
        return (new ReflectionToStringBuilder(obj, ToStringStyle.MULTI_LINE_STYLE) {
            protected boolean accept(Field f) {
                return super.accept(f) && !f.getName().equals("password");
            }

        }).toString();

    }

}

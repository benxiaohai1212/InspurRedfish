package com.inspur.redfish.common.context;


import java.util.Map;

import org.springframework.context.ApplicationContext;


/**
 * AppContext.
 *
 * @author hewanxian
 * Copyright © 2016 hewanxian. All rights reserved.
 */
public class AppContext {
    public static final String HTTP_SESSION = "HTTP_SESSION";

    public static final String HTTP_SERVLET_REQUEST = "HTTP_SERVLET_REQUEST";
    public static final String HTTP_SERVLET_RESPONSE = "HTTP_SERVLET_RESPONSE";
    

    private static ApplicationContext context;

    public static void init(ApplicationContext context) {
        AppContext.context = context;
    }

    public static ApplicationContext context() {
        return context;
    }
    
    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return context().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return context().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return context().getBean(name, clazz);
    }

    /**
     * 获取指定接口的所有Class.
     * @param clazz 接口名称
     * @param <T> 获取Class的接口
     * @return Map
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        Map<String, T> clazzMap = context().getBeansOfType(clazz);
        return clazzMap;
    }
}

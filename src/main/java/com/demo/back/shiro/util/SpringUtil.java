package com.demo.back.shiro.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }


    public static String getAppName() {
        return context.getEnvironment().getProperty("spring.application.name");
    }

    //根据spring代理的类名 从spring容器中获取已经实例化和代理的类
    public static <T> T getBean(Class<T> tClass) {
        T bean = context.getBean(tClass);
        return bean;
    }

    public static <T> T getBean(String var1, Class<T> var2) {
        return context.getBean(var1, var2);
    }


    //根据spring代理的类的id和name 从spring容器中获取已经实例化和代理的类
    public static Object getBean(String idOrName) {
        return context.getBean(idOrName);
    }


}

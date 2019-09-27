package com.demo.back.common.logaop.systemlogaop;


import java.lang.annotation.*;

/**
 * @Author yakun.shi
 * @Description //日志搜索注解
 * @Date 2019/6/26 8:53
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogCollect {
    //日志具体内容
    String logValue();

    //日志概述
    String simpleValue() default "";

}

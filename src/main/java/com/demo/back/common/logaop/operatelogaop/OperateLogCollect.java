package com.demo.back.common.logaop.operatelogaop;


import java.lang.annotation.*;

/**
 * @Author yakun.shi
 * @Description //日志搜索注解
 * @Date 2019/6/26 8:53
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLogCollect {
    //日志具体内容
    String logValue();

    //日志概述  如果type为false需要填写值
    String simpleValue() default "";

    //日志类型  如果为false 则为增加或者查看，true为更新删除审核操作
    boolean type() default false;
}

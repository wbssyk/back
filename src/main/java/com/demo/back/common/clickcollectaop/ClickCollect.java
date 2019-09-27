package com.demo.back.common.clickcollectaop;

import java.lang.annotation.*;

/**
 * @Author yakun.shi
 * @Description //统计点击量
 * @Date 2019/6/28 15:11
 **/

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickCollect {
    ClickElementType value();
}

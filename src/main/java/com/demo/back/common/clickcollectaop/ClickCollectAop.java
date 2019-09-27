package com.demo.back.common.clickcollectaop;

import com.demo.back.exception.ParamNullException;
import com.demo.back.exception.ParamTypeException;
import com.demo.back.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;


/**
 * @ClassName ClickCollectAop
 * @Author yakun.shi
 * @Date 2019/6/28 15:15
 * @Version 1.0
 **/
@Aspect
@Component
public class ClickCollectAop {

    @Autowired
    private RedisTemplate redisTemplate;


    @Pointcut("@annotation(com.demo.back.common.clickcollectaop.ClickCollect)")
    public void clickCollect() {
    }

    @Around("clickCollect()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //得到拦截的方法
        Method method = signature.getMethod();
        String key = DateUtils.dateToString(LocalDateTime.now());
//        String method_name = method.getName();
//        String class_name = method.getDeclaringClass().toString();
        ClickCollect annotation = method.getAnnotation(ClickCollect.class);
        String value = annotation.value().name();
        /** 一大类点击量 key  如广告类*/
        String key_all = key + ":" + value;
        redisTemplate.opsForValue().increment(key_all, 1);



        /** 处理一大类 下的小类  如广告类下的 a广告*/
        Object[] args = joinPoint.getArgs();
        ClickRequest clickRequest = null;
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Class<?> arg = args[i].getClass();
                if (arg.equals(ClickRequest.class)) {
                    clickRequest = (ClickRequest) args[i];
                    break;
                }
            }
        }
        if (clickRequest == null) {
            throw new ParamTypeException();
        }
        String id = "";
        /** 使用此注解的方法 参数必须为 ClickRequest*/
        id = clickRequest.getId();
        if (StringUtils.isEmpty(id)) {
            throw new ParamNullException();
        }
        /** 一大类 下的小类key  如广告类下的 a广告*/
        String key_one = key + ":" + value + ":" + id;
        redisTemplate.opsForValue().increment(key_one, 1);
        return joinPoint;
    }
}

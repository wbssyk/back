package com.demo.back.common.logaop.systemlogaop;

import com.demo.back.controller.system.authusercontroller.request.LoginUserRequest;
import com.demo.back.entity.AuthUser;
import com.demo.back.entity.SystemLog;
import com.demo.back.exception.ParamTypeException;
import com.demo.back.service.ISystemLogService;
import com.demo.back.utils.IPUtils;
import com.demo.back.utils.UUIDUtil;
import com.demo.back.utils.UserInfoUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @ClassName LogCollectAop
 * @Author yakun.shi
 * @Date 2019/6/26 8:55
 * @Version 1.0
 **/
@Aspect
@Component
public class LogCollectAop {

    private static Logger logger = LoggerFactory.getLogger(LogCollectAop.class);
    @Autowired
    private IPUtils ipUtils;

    @Autowired
    private ISystemLogService systemLogService;

    /**
     * 定义有一个切入点，范围为web包下的类
     */
    @Pointcut("@annotation(com.demo.back.common.logaop.systemlogaop.LogCollect)")
    public void logCollect() {
    }

    @Before("logCollect()")
    public void doBefore(JoinPoint joinPoint) {
    }

    /**
     * 检查参数是否为空
     */
    @AfterReturning("logCollect()")
    public void doAfterReturning(JoinPoint pjp) throws Throwable {
        logger.info("开始日志");
        MethodSignature signature = ((MethodSignature) pjp.getSignature());
        //得到拦截的方法
        Method method = signature.getMethod();
        String method_name = method.getName();
        String class_name = method.getDeclaringClass().toString();
        LogCollect annotation = method.getAnnotation(LogCollect.class);
        String value = annotation.logValue();
        String operateType = annotation.simpleValue();
        String loginUser = null;
        SystemLog systemLog = new SystemLog();
        Object[] args = pjp.getArgs();
        LoginUserRequest authUser = null;
        /** 处理登录接口,因为aop开始的时候,shiro还没开始,从参数中获取用户名 */
        if (method_name.equals("login")) {
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    Class<?> arg = args[i].getClass();
                    if (arg.equals(LoginUserRequest.class)) {
                        authUser = (LoginUserRequest) args[i];
                        break;
                    }
                }
            }
            if (authUser == null) {
                throw new ParamTypeException();
            }
            loginUser = authUser.getLoginUser();

        } else {
            /** 处理其他接口,从shiro中获取用户名 */
            loginUser = UserInfoUtil.getLoginUser();
        }

        //处理系统日志，用户登录登出
        systemLog.setId(UUIDUtil.getUUID()).setLogValue(value).setOperateType(operateType).
                setIpAddress(ipUtils.getIpAddress()).setClassName(class_name).setLoginUser(loginUser).
                setMethodName(method_name).setCreateTime(new Date());
        logger.info("拦截系统日志内容=={}", systemLog);
        systemLogService.insertLog(systemLog);

    }

}

package com.demo.back.common.logaop.operatelogaop;

import com.demo.back.entity.OperateLog;
import com.demo.back.service.IOperateLogService;
import com.demo.back.utils.IPUtils;
import com.demo.back.utils.UUIDUtil;
import com.demo.back.utils.UserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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
public class OperateLogCollectAop {

    private static Logger logger = LoggerFactory.getLogger(OperateLogCollectAop.class);
    @Autowired
    private IPUtils ipUtils;


    @Autowired
    private IOperateLogService operateLogService;

    /**
     * 定义有一个切入点，范围为web包下的类
     */
    @Pointcut("@annotation(com.demo.back.common.logaop.operatelogaop.OperateLogCollect)")
    public void logCollect() {
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
        OperateLogCollect annotation = method.getAnnotation(OperateLogCollect.class);
        String logvalue = annotation.logValue();
        String simplevalue = annotation.simpleValue();
        String loginUser = null;
        OperateLog operateLog = new OperateLog();
        Object[] args = pjp.getArgs();
        Integer check_status = 0;
        String review_Status = "";
        if (annotation.type()) {
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    Class<?> arg = args[i].getClass();
                    Field checkStatus = arg.getDeclaredField("checkStatus");
                    Field reviewStatus = arg.getDeclaredField("reviewStatus");
                    if (checkStatus != null) {
                        checkStatus.setAccessible(true);
                        check_status = (Integer) checkStatus.get(args[i]);
                    }
                    if (reviewStatus != null) {
                        reviewStatus.setAccessible(true);
                        review_Status = (String) reviewStatus.get(args[i]);
                    }
                }
            }
            if (check_status != null) {
                if (check_status.equals(1)) {
                    logvalue = logvalue + " : 禁用功能";
                    simplevalue = "功能禁用";
                } else if (check_status.equals(2)) {
                    logvalue = logvalue + " : 删除";
                    simplevalue = "功能删除";
                }
            }
            if (StringUtils.isNotEmpty(review_Status)) {
                if (review_Status.equals("2")) {
                    logvalue = logvalue + " : 审核通过";
                    simplevalue = "审核通过";
                } else if (review_Status.equals("3")) {
                    logvalue = logvalue + " : 审核拒绝";
                    simplevalue = "审核拒绝";
                }
            }
        }

        /** 处理其他接口,从shiro中获取用户名 */
        loginUser = UserInfoUtil.getLoginUser();
        //处理系统日志，用户登录登出
        operateLog.setId(UUIDUtil.getUUID()).setLogValue(logvalue).setOperateType(simplevalue).
                setIpAddress(ipUtils.getIpAddress()).setClassName(class_name).setLoginUser(loginUser).
                setMethodName(method_name).setCreateTime(new Date());
        logger.info("拦截系统日志内容=={}", operateLog);
        //处理操作日志，增删改查
        operateLogService.insertLog(operateLog);

    }


}

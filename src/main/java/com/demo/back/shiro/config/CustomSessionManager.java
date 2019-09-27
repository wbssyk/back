package com.demo.back.shiro.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;


/**
 * @ClassName CustomSessionManager
 * @Author yakun.shi
 * @Date 2019/7/3 18:05
 * @Version 1.0
 **/
public class CustomSessionManager extends DefaultWebSessionManager {

    private static Logger logger = LoggerFactory.getLogger(CustomSessionManager.class);

    private static final String AUTHORIZATION = "Authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";


    /**
     * @return java.io.Serializable
     * @Author yakun.shi
     * @Description //重写父类方法，从请求头中获取token，也就是sessionid
     * @Date 2019/7/4 9:04
     * @Param [request, response]
     **/
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        HttpServletResponse resp = (HttpServletResponse) response;
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(sessionId)) {
            logger.info("请求头中获取sessionId");

            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;

        } else {
//            getResult(resp,ResponseEnum.TOKEN_TIMEOUT.getCode(),ResponseEnum.TOKEN_TIMEOUT.getMsg());
//            return null;
            //否则按默认规则从cookie取sessionId
//            logger.info("默认方式获取sessionId");
            return super.getSessionId(request, response);
        }
    }



}

package com.demo.back.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.demo.back.enums.ResponseEnum;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author yakun.shi
 * @Description //处理前后端分离 shiro自动重定向到 login.jsp
 * @Date 2019/7/11 15:39
 **/

public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
    private static Logger log  = LoggerFactory.getLogger(ShiroFormAuthenticationFilter.class);

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        //处理跨域问题，跨域的请求首先会发一个options类型的请求
        if (servletRequest.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

//            WebUtils.issueRedirect(request, response, "/login.html");
//            return false;
            // 这里重定向到登录画面,修改为返回前端状态码
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json; charset=utf-8");
            resp.setStatus(200);
            resp.setContentType("application/json;charset=UTF-8");
            resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Methods", req.getMethod());
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setHeader("Access-Control-Allow-Headers", "*");
            resp.setHeader("Vary", "Origin");
            PrintWriter out = null;

            JSONObject res = new JSONObject();
            res.put("result", ResponseEnum.USER_NOTLOGIN.getCode());
            res.put("message", ResponseEnum.USER_NOTLOGIN.getMsg());
            out = response.getWriter();
            out.append(res.toString());
            out.flush();
            out.close();
            return false;
        }
    }

}

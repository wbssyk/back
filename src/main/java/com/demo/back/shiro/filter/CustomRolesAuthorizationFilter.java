package com.demo.back.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.enums.ResponseEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author yakun.shi
 * @Description //TODO
 * @Date 2019/7/11 15:39
 **/
public class CustomRolesAuthorizationFilter extends RolesAuthorizationFilter {

    private static final String AUTHORIZATION = "Authorization";

    private static final String SESSION_KEY = "shiro:session:*";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {
        Subject subject = getSubject(req, resp);
        String[] rolesArray = (String[]) mappedValue;
        //如果没有角色限制，直接放行
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (String aRolesArray : rolesArray) {
            //若当前用户是rolesArray中的任何一个，则有权限访问
            boolean b = subject.hasRole(aRolesArray);
            if (b) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        //处理跨域问题，跨域的请求首先会发一个options类型的请求
        if (servletRequest.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        boolean isAccess = isAccessAllowed(request, response, mappedValue);
        if (isAccess) {
            return true;
        }

        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        HttpServletResponse resp = (HttpServletResponse) response;
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(sessionId)) {
            Set<String> set = redisTemplate.keys(SESSION_KEY);
            //判断缓存中是否存在set集合
            boolean flag = false;
            if (CollectionUtils.isNotEmpty(set)) {
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (key != null) {
                        String redisSessionId = getRedisSessionId(key);
                        if (sessionId.equals(redisSessionId)) {
                            flag = true;
                            break;
                        }
                    }
                }
            }

            if (flag) {
                return false;
            } else {
                return true;
            }
        }
        servletResponse.setCharacterEncoding("UTF-8");
        Subject subject = getSubject(request, response);
        PrintWriter printWriter = servletResponse.getWriter();
        servletResponse.setContentType("application/json;charset=UTF-8");
        servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"));
        servletResponse.setHeader("Access-Control-Allow-Methods", servletRequest.getMethod());
        servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        servletResponse.setHeader("Access-Control-Allow-Headers", "*");
        servletResponse.setHeader("Vary", "Origin");
        String respStr;
        ServiceResponse serviceResponse = new ServiceResponse();
//        Object principal = subject.getPrincipal();
        if (subject.getPrincipal() == null) {
            serviceResponse.setResult(ResponseEnum.USER_NOTLOGIN.getCode());
            serviceResponse.setMessage(ResponseEnum.USER_NOTLOGIN.getMsg());
            respStr = JSONObject.toJSONString(serviceResponse);
        } else {
            serviceResponse.setResult(ResponseEnum.USER_NOAUTHORITY.getCode());
            serviceResponse.setMessage(ResponseEnum.USER_NOAUTHORITY.getMsg());
            respStr = JSONObject.toJSONString(serviceResponse);
        }
        printWriter.write(respStr);
        printWriter.flush();
        servletResponse.setHeader("content-Length", respStr.getBytes().length + "");
        return false;
    }

    public String getRedisSessionId(String key) {
        int i = StringUtils.lastIndexOf(key, ":");
        String substring = StringUtils.substring(key, i + 1, key.length());
        return substring;
    }
}

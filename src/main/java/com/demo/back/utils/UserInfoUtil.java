package com.demo.back.utils;

import com.demo.back.entity.AuthUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;

/**
 * @Author yakun.shi
 * @Description //用户信息工具类
 * @Date 2019/6/27 10:05
 **/
public class UserInfoUtil {

    /**
     * @return com.demo.back.entity.AuthUser
     * @Author yakun.shi
     * @Description //获取当前用户所有信息
     * @Date 2019/6/27 10:04
     * @Param []
     **/
    public static AuthUser getMyUser() {
        Subject subject = SecurityUtils.getSubject();
        AuthUser user = (AuthUser) subject.getPrincipal();

        if (user == null) {
            throw new UnauthorizedException();
        }
        user.setPassword(null);
        return user;
    }

    /**
     * @return java.lang.String
     * @Author yakun.shi
     * @Description //获取当前登录用户
     * @Date 2019/6/27 10:04
     * @Param []
     **/
    public static String getLoginUser() {
        return getMyUser().getLoginUser();
    }

    /**
     * @return java.lang.String
     * @Author yakun.shi
     * @Description //获取当前用户ID
     * @Date 2019/6/27 10:05
     * @Param []
     **/
    public static String getUserId() {
        return getMyUser().getId();
    }
}

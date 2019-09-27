package com.demo.back.shiro.util;

import com.demo.back.entity.AuthUser;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Objects;

public class ShiroUtil {

    private static SessionDAO sessionDAO = (SessionDAO)SpringUtil.getBean(SessionDAO.class);

    private ShiroUtil() {
    }

    /**
     * 获取指定用户名的Session
     * @param username
     * @return
     */
    private static Session getSessionByUsername(String username){
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        Object attribute;
        for(Session session : sessions){
            attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            Object primaryPrincipal = ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            AuthUser user = new AuthUser();
            try {
                BeanUtils.copyProperties(user,primaryPrincipal);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (user == null) {
                continue;
            }
            if (Objects.equals(user.getUsername(), username)) {
                return session;
            }
        }
        return null;
    }

    /**
     * 删除用户缓存信息
     * @param username 用户名
     * @param isRemoveSession 是否删除session，删除后用户需重新登录
     */
    public static void kickOutUser(String username, boolean isRemoveSession){
        Session session = getSessionByUsername(username);
        if (session == null) {
            return;
        }

        Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (attribute == null) {
            return;
        }
        Object primaryPrincipal = ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
        AuthUser user = new AuthUser();
        try {
            BeanUtils.copyProperties(user,primaryPrincipal);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (!username.equals(user.getUsername())) {
            return;
        }
        //删除session
        if (isRemoveSession) {
            sessionDAO.delete(session);
        }
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authc = securityManager.getAuthenticator();
        //删除cache，在访问受限接口时会重新授权
        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);
    }

}

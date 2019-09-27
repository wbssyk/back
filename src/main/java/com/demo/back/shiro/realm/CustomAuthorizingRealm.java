package com.demo.back.shiro.realm;

import com.demo.back.entity.AuthUser;
import com.demo.back.exception.UserNotExistException;
import com.demo.back.service.IAuthUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;


public class CustomAuthorizingRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(CustomAuthorizingRealm.class);

    //解决shrio和redis缓存不生效问题
    @Autowired
    private IAuthUserService authUserService;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AuthUser authUser = (AuthUser) principalCollection.getPrimaryPrincipal();
//        AuthUser authUser = new AuthUser();
//        BeanUtils.copyProperties(authUser, principal);
        logger.info(authUser.getUsername() + "进行授权操作");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String id = authUser.getId();
        //获取用户角色
        Set<String> roles = authUserService.findRolesByUserId(id);
        //获取用户权限
        Set<String> permissions = authUserService.findPermissionsByUserId(id);
        //添加roles
        info.addRoles(roles);
        //添加permissions
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 认证回调函数，登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //toke强转
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String loginUser = usernamePasswordToken.getUsername();
        //根据用户名查询密码，由安全管理器负责对比查询出的数据库中的密码和页面输入的密码是否一致
        AuthUser user = authUserService.findPasswordByLoginUser(loginUser);
        if (user == null) {
            throw new UserNotExistException();
        }
        //单用户登录
        //处理session
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        //获取当前已登录的用户session列表
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        for (Session session : sessions) {
            //清除该用户以前登录时保存的session，强制退出
            Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            AuthUser primaryPrincipal = (AuthUser) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (loginUser.equals(primaryPrincipal.getLoginUser())) {
                sessionManager.getSessionDAO().delete(session);
            }
        }
        String password = user.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getLoginUser() + password);
        //最后的比对需要交给安全管理器,三个参数进行初步的简单认证信息对象的包装,由安全管理器进行包装运行
        return new SimpleAuthenticationInfo(user, password, credentialsSalt, getName());
    }
}

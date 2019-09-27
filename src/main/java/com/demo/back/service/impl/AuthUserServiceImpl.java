package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authrolecontroller.request.AuthRoleRequest;
import com.demo.back.controller.system.authusercontroller.request.AuthUserPageRequest;
import com.demo.back.controller.system.authusercontroller.request.LoginUserRequest;
import com.demo.back.dao.AuthUserMapper;
import com.demo.back.dao.AuthUserRoleMapper;
import com.demo.back.dao.SystemLogMapper;
import com.demo.back.entity.AuthRole;
import com.demo.back.entity.AuthUser;
import com.demo.back.entity.AuthUserRole;
import com.demo.back.entity.SystemLog;
import com.demo.back.enums.ResponseEnum;
import com.demo.back.exception.ParamNullException;
import com.demo.back.exception.ParamTypeException;
import com.demo.back.service.IAuthUserService;
import com.demo.back.shiro.util.PasswordHelper;
import com.demo.back.utils.IPUtils;
import com.demo.back.utils.UUIDUtil;
import com.demo.back.utils.UserInfoUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName AuthUserServiceImpl
 * @Author yakun.shi
 * @Date 2019/6/13 13:11
 * @Version 1.0
 **/
@Service
public class AuthUserServiceImpl implements IAuthUserService {

    private static final String SESSION_KEY = "shiro:session:";

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SystemLogMapper systemLogMapper;

    /**
     * @return java.util.Set<java.lang.String>
     * @Author yakun.shi
     * @Description //根据用户id获取用户拥有的角色
     * @Date 2019/6/13 17:40
     * @Param [userId]
     **/
    @Override
    public Set<String> findRolesByUserId(String userId) {
        List<AuthUser> authUsers = authUserMapper.findRolesByUserId(userId);
        if (CollectionUtils.isNotEmpty(authUsers)) {
            Set<String> collect = authUsers.stream().map(authUser -> authUser.getRoleName()).collect(Collectors.toSet());
            return collect;
        }
        return new HashSet<>();
    }

    /**
     * @return java.util.Set<java.lang.String>
     * @Author yakun.shi
     * @Description //根据用户id 获取用户拥有的权限
     * @Date 2019/6/13 17:41
     * @Param [userId]
     **/
    @Override
    public Set<String> findPermissionsByUserId(String userId) {
        List<AuthUser> authUsers = authUserMapper.findPermissionsByUserId(userId);
        if (CollectionUtils.isNotEmpty(authUsers)) {
            Set<String> collect = authUsers.stream().map(AuthUser::getResourceUrl).distinct().collect(Collectors.toSet());
            Set<String> result = new HashSet<>();
            for (String permission : collect) {
                if (StringUtils.isBlank(permission)) {
                    continue;
                }
                permission = StringUtils.trim(permission);
                result.addAll(Arrays.asList(permission.split("\\s*;\\s*")));
            }
            return result;
        }
        return new HashSet<>();
    }

    @Override
    public AuthUser findPasswordByLoginUser(String username) {
        return authUserMapper.findPasswordByLoginUser(username);
    }

    /**
     * @return com.demo.back.common.page.ServiceResponse
     * @Author yakun.shi
     * @Description //用户登录逻辑
     * @Date 2019/6/13 17:41
     * @Param [authUser]
     **/
    @Override
    public ServiceResponse login(LoginUserRequest request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getPassword()) && StringUtils.isEmpty(request.getLoginUser())) {
            serviceResponse.setMessage(ResponseEnum.USERNAME_PASSWORDNULL.getMsg());
            serviceResponse.setResult(ResponseEnum.USERNAME_PASSWORDNULL.getCode());
            return serviceResponse;
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                request.getLoginUser(), PasswordHelper.encryptPassword(request.getLoginUser(),request.getPassword()));
        subject.login(token);
        String userId = UserInfoUtil.getUserId();
        List<AuthUser> rolesByUserId = authUserMapper.findRolesByUserId(userId);
        String s = subject.getSession().getId().toString();
        HashMap<String, String> map = new HashMap<>();
        map.put("loginUser", request.getLoginUser());
        map.put("id", UserInfoUtil.getUserId());
        map.put("token", s);
        if(CollectionUtils.isNotEmpty(rolesByUserId)){
            map.put("roleType",rolesByUserId.get(0).getRoleType().toString());
        }
        serviceResponse.setData(map);
        serviceResponse.setMessage(ResponseEnum.SUCCESS.getMsg());
        return serviceResponse;

    }

    @Autowired
    private IPUtils ipUtils;
    /**
     * @return com.demo.back.common.page.ServiceResponse
     * @Author yakun.shi
     * @Description //用户退出
     * @Date 2019/6/13 17:42
     * @Param []
     **/
    @Override
    public ServiceResponse logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new ParamNullException();
        }
        SystemLog systemLog = new SystemLog();
        systemLog.setId(UUIDUtil.getUUID()).setLogValue("收集用户退出系统日志").setOperateType("退出").
                setIpAddress(ipUtils.getIpAddress()).
                setClassName("com.demo.back.controller.system.authusercontroller.UserController")
                .setLoginUser(UserInfoUtil.getLoginUser()).setMethodName("logout").setCreateTime(new Date());
        String key = new StringBuilder().append(SESSION_KEY).append(authorization).toString();
        ServiceResponse serviceResponse = new ServiceResponse();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        if(redisTemplate.opsForValue().get(key)!=null){
            redisTemplate.delete(key);
        }
        serviceResponse.setMessage(ResponseEnum.SUCCESS.getMsg());
        systemLogMapper.insertLog(systemLog);
        return serviceResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse register(AuthUser request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (request.getUsername() == null || request.getPassword() == null || request.getLoginUser() == null) {
            throw new ParamNullException();
        }
        AuthUser authUser = authUserMapper.findPasswordByLoginUser(request.getLoginUser());
        if (authUser != null) {
            serviceResponse.setResult(ResponseEnum.DATA_REPATE.getCode());
            serviceResponse.setMessage(ResponseEnum.DATA_REPATE.getMsg());
            return serviceResponse;
        }
        String userId = UUIDUtil.getUUID();
        request.setId(userId);
        request.setCreateTime(new Date());
        request.setPassword(PasswordHelper.encryptPassword(request));
        authUserMapper.insert(request);
        //用户添加角色
        if (request.getRoleId() != null) {
            AuthRoleRequest authRoleRequest = new AuthRoleRequest();
            authRoleRequest.setUserRoleId(UUIDUtil.getUUID());
            authRoleRequest.setRoleId(request.getRoleId());
            authRoleRequest.setUserId(userId);
            authUserRoleMapper.userRoleInsert(authRoleRequest);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        return serviceResponse.setData(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse update(AuthUser request,HttpServletRequest servletRequest) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (request==null||request.getId() == null) {
            throw new ParamNullException();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getId());
        // 修改密码
        if (request.getUp() != null && request.getUp().intValue() == 1) {
            if(StringUtils.isEmpty(request.getOldPassword())){
                throw new ParamNullException();
            }
            String authorization = servletRequest.getHeader("Authorization");
            if (StringUtils.isEmpty(authorization)) {
                throw new ParamNullException();
            }
            AuthUser authUser = new AuthUser();
            authUser.setPassword(request.getOldPassword());
            authUser.setLoginUser(request.getLoginUser());
            String oldPassword = PasswordHelper.encryptPassword(authUser);
            AuthUser authUser1 = authUserMapper.selectByUserId(request.getId());
            if(authUser1.getPassword().equals(oldPassword)){
                String password = PasswordHelper.encryptPassword(request);
                request.setPassword(password);
                authUserMapper.updateByPrimaryKey(request);
                logout(servletRequest);
                return serviceResponse;
            }else {
                serviceResponse.setResult(ResponseEnum.OLD_PASSWORD_ERROR.getCode());
                serviceResponse.setMessage(ResponseEnum.OLD_PASSWORD_ERROR.getMsg());
                return serviceResponse;
            }
        }
        //用户编辑
        authUserMapper.updateByPrimaryKey(request);

        //用户更新角色
        if (StringUtils.isNotEmpty(request.getRoleId())) {
            AuthUserRole authUserRole = authUserRoleMapper.selectCountByUserIdAndRoleId(request.getId(), request.getRoleId());
            AuthRole authRole = new AuthRole();
            authRole.setRoleId(request.getRoleId());
            authRole.setUserRoleId(authUserRole.getId());
            authUserRoleMapper.updateById(authRole);
        }
        return serviceResponse.setData(map);
    }

    @Override
    public PageResponse userList(AuthUserPageRequest request) {
        List<AuthUser> authUsers = authUserMapper.userList(request);
        Integer count = authUserMapper.userListCount(request);
        return PageResponse.createResponse(count, authUsers, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse userDelete(AuthUserPageRequest request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        int integer = request.getCheckStatus();
        if (integer > 3 || integer < 0) {
            throw new ParamTypeException();
        }
        AuthUser authUser = authUserMapper.selectByUserId(request.getId());
        if (authUser != null && authUser.getUserType().intValue() == 1) {
            serviceResponse.setResult(ResponseEnum.IS_SUPERADMIN.getCode());
            serviceResponse.setMessage(ResponseEnum.IS_SUPERADMIN.getMsg());
            return serviceResponse;
        }
        Map<String, Object> map = new HashMap<>();
        authUserMapper.userDelete(request);
        authUserRoleMapper.deleteByUserId(request);
        map.put("id", request.getId());
        return serviceResponse.setData(map);
    }

    @Override
    public ServiceResponse userDetail(AuthUserPageRequest request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (request.getId() == null) {
            throw new ParamNullException();
        }
        AuthUser authUser = authUserMapper.userDetail(request.getId());
        return serviceResponse.setData(authUser);

    }


}

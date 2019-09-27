package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authrolecontroller.request.AuthRoleRequest;
import com.demo.back.dao.AuthRoleMapper;
import com.demo.back.dao.AuthUserRoleMapper;
import com.demo.back.entity.AuthRole;
import com.demo.back.enums.ResponseEnum;
import com.demo.back.exception.ParamNullException;
import com.demo.back.exception.ParamTypeException;
import com.demo.back.service.IAuthRoleService;
import com.demo.back.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AuthRoleServiceImpl
 * @Author yakun.shi
 * @Date 2019/6/13 13:11
 * @Version 1.0
 **/
@Service
public class AuthRoleServiceImpl implements IAuthRoleService {

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;

    @Override
    public ServiceResponse roleInsert(AuthRole request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (request.getRoleName() == null) {
            throw new ParamNullException();
        }
        request.setCreateTime(new Date());
        AuthRole authRole = authRoleMapper.selectByRoleName(request);
        if(authRole!=null){
            serviceResponse.setResult(ResponseEnum.DATA_REPATE.getCode());
            serviceResponse.setMessage(ResponseEnum.DATA_REPATE.getMsg());
            return serviceResponse;
        }
        String id = UUIDUtil.getUUID();
        request.setId(id);
        authRoleMapper.roleInsert(request);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return serviceResponse.setData(map);
    }

    @Override
    public ServiceResponse roleUpdate(AuthRole request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (request.getId() == null) {
            throw new ParamNullException();
        }
        authRoleMapper.roleUpdate(request.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getId());
        return serviceResponse.setData(map);
    }

    @Override
    public ServiceResponse selectRoleListForUser(AuthRoleRequest request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        List<Map> maps = authRoleMapper.selectRoleListForUser(request);
        serviceResponse.setData(maps);
        return serviceResponse;
    }


    @Override
    public PageResponse roleDataList(AuthRoleRequest request) {
        List<Map> authRoles = authRoleMapper.roleDataList(request);
        Integer count = authRoleMapper.roleDataListCount(request);
        return PageResponse.createResponse(count,authRoles,request);
    }


    @Override
    public ServiceResponse roleDeleteByUserIdAndRoleId(AuthRoleRequest request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getRoleId())) {
            throw new ParamNullException();
        }
        AuthRole authRole = authRoleMapper.selectByRoelId(request.getRoleId());
        Integer roleType = authRole.getRoleType();
        if(roleType.equals(1)||roleType.equals(2)||roleType.equals(3)){
            serviceResponse.setMessage(ResponseEnum.ROLE_ISSUPERADMIN.getMsg());
            serviceResponse.setResult(ResponseEnum.ROLE_ISSUPERADMIN.getCode());
            return serviceResponse;
        }

        int integer = request.getCheckStatus().intValue();
        if (integer > 3 || integer < 0) {
            throw new ParamTypeException();
        }
        authRoleMapper.roleDelete(request);
        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getUserRoleId());
        return serviceResponse.setData(map);
    }


    @Override
    public ServiceResponse userRoleInsert(AuthRoleRequest request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (request.getUserId() == null||request.getRoleId()==null) {
            throw new ParamNullException();
        }
//        Integer count = authUserRoleMapper.selectCountByUserIdAndRoleId(request.getUserId(),request.getRoleId());
        List<AuthRole> authRoles = authUserRoleMapper.selectRoleByUserId(request.getUserId());
        if(CollectionUtils.isEmpty(authRoles)){
            // 添加用户角色
            String uuid = UUIDUtil.getUUID();
            request.setUserRoleId(uuid);
            authUserRoleMapper.userRoleInsert(request);
            Map<String, Object> map = new HashMap<>();
            map.put("userRoleId",uuid);
            return serviceResponse.setData(map);
        }else {
            //更新用户角色
            AuthRole authRole = authRoles.get(0);
            authRole.setRoleId(request.getRoleId());
            authUserRoleMapper.updateById(authRole);
            Map<String, Object> map = new HashMap<>();
            map.put("userRoleId",authRole.getUserRoleId());
            return serviceResponse.setData(map);
        }
    }

}

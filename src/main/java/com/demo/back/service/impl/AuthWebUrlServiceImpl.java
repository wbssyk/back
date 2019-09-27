package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authresourcecontroller.request.AuthWebUrlRequest;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionInsertParam;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionRequest;
import com.demo.back.dao.AuthRoleMapper;
import com.demo.back.dao.AuthRoleWebUrlMapper;
import com.demo.back.dao.AuthUserRoleMapper;
import com.demo.back.dao.AuthWebUrlMapper;
import com.demo.back.entity.AuthRole;
import com.demo.back.entity.AuthWebUrl;
import com.demo.back.enums.ResponseEnum;
import com.demo.back.exception.ParamNullException;
import com.demo.back.exception.ParamTypeException;
import com.demo.back.service.IAuthWebUrlService;
import com.demo.back.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName AuthWeburlServiceImpl
 * @Author yakun.shi
 * @Date 2019/7/9 17:37
 * @Version 1.0
 **/
@Service
public class AuthWebUrlServiceImpl implements IAuthWebUrlService {

    @Autowired
    private AuthWebUrlMapper authWebUrlMapper;

    @Autowired
    private AuthRoleWebUrlMapper authRoleWebUrlMapper;

    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Override
    public ServiceResponse resourceInsert(AuthWebUrl request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if(request==null||StringUtils.isEmpty(request.getHtmlUrl())
                ||StringUtils.isEmpty(request.getParentName())||StringUtils.isEmpty(request.getUrlName())){
            throw new ParamNullException();

        }
        AuthWebUrl authWebUrl = authWebUrlMapper.selectByHtmlUrl(request.getHtmlUrl());
        if(authWebUrl!=null){
            serviceResponse.setMessage(ResponseEnum.DATA_REPATE.getMsg());
            serviceResponse.setResult(ResponseEnum.DATA_REPATE.getCode());
            return serviceResponse;
        }
        String id = UUIDUtil.getUUID();
        request.setId(id);
        request.setCreateTime(new Date());
        int sort = 100;
        if(request.getSort()!=null){
           sort = request.getSort();
        }
        request.setSort(sort);
        authWebUrlMapper.resourceInsert(request);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return serviceResponse.setData(map);
    }

    @Override
    public PageResponse resourceList(AuthWebUrlRequest request) {
        List<Map<String, Object>> list = authWebUrlMapper.resourceList(request);
        Integer count = authWebUrlMapper.resourceListCount(request);
        return PageResponse.createResponse(count, list, request);
    }

    @Override
    public ServiceResponse resourceDelete(AuthWebUrl request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        int integer = request.getCheckStatus().intValue();
        if (integer > 3 || integer < 0) {
            throw new ParamTypeException();
        }
        authWebUrlMapper.resourceDelete(request);
        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getId());
        return serviceResponse.setData(map);
    }

    @Override
    public ServiceResponse resourceUpdate(AuthWebUrl request) {
        if (request == null || StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        authWebUrlMapper.resourceUpdate(request);
        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getId());
        return serviceResponse.setData(map);
    }


    @Override
    public ServiceResponse LeftSideBar(AuthWebUrl request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        String userId = request.getUserId();
        List<AuthRole> authRoles = authUserRoleMapper.selectRoleByUserId(userId);
        List<Map<String, Object>> leftSideBarResult = getLeftSideBarResult(authRoles);
        serviceResponse.setData(leftSideBarResult);
        return serviceResponse;
    }

    @Override
    @Transactional
    public ServiceResponse roleWebPermissionInsert(PermissionRequest request) {
        if (request.getRoleId() == null) {
            throw new ParamNullException();
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        List<PermissionInsertParam> params = request.getParams();
        authRoleWebUrlMapper.deleteByRoleId(request.getRoleId());
        if(!CollectionUtils.isEmpty(request.getParams())) {
            params.forEach(t -> {
                t.setResourceRoleId(UUIDUtil.getUUID());
                t.setRoleId(request.getRoleId());
            });
            authRoleWebUrlMapper.roleWebPermissionBatchInsert(params);
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse roleWebResourceList(PermissionRequest request) {
        if (request.getRoleId() == null) {
            throw new ParamNullException();
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        List<Map<String, Object>> allmaps = authWebUrlMapper.selectAllForLeftSideBar();
        List<Map<String, Object>> rolemaps = authWebUrlMapper.selectForLeftSideBar(request);
        AuthRole authRole = authRoleMapper.selectByRoelId(request.getRoleId());
        Integer roleType = authRole.getRoleType();

        List<Map<String, Object>> resources = getResources(allmaps, rolemaps);
        //如果角色为管理员,默认所有权限开启
        if (roleType.equals(3)) {
            allmaps.forEach(t -> {
                t.put("selectEd", 1);
            });
        }
        List<Map<String, Object>> result = getResult(resources);
        serviceResponse.setData(result);
        return serviceResponse;
    }

    /**
     * @param maps
     * @return java.util.List<java.util.Map                               <                               java.lang.String                               ,                               java.lang.Object>>
     * @Author yakun.shi
     * @Description //解析格式，
     * @Date 2019/6/17 16:56
     * @Param [maps, all] all为ture 代表没有被选中，1代表选中
     */
    private List<Map<String, Object>> getResult(List<Map<String, Object>> maps) {
        Map<Object, List<Map>> resourceType = maps.stream().collect(Collectors.groupingBy(t -> t.get("parentName")));
        Iterator<Map.Entry<Object, List<Map>>> iterator = resourceType.entrySet().iterator();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        while (iterator.hasNext()) {
            map = new HashMap();
            Object key = iterator.next().getKey();
            List<Map> value = resourceType.get(key);
            map.put("resourceType", key.toString());
            map.put("resourceList", value);
            result.add(map);
        }
        return result;
    }


    private List<Map<String, Object>> getResources(List<Map<String, Object>> allResources,
                                                   List<Map<String, Object>> roleResources) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < allResources.size(); i++) {
            Map<String, Object> allMaps = allResources.get(i);
            String htmlUrl = allMaps.get("htmlUrl").toString();
            allMaps.put("selectEd", 0);
            if (!CollectionUtils.isEmpty(roleResources) || roleResources.size() > 0) {
                for (int j = 0; j < roleResources.size(); j++) {
                    Map<String, Object> roleMaps = roleResources.get(j);
                    String url = roleMaps.get("htmlUrl").toString();
                    if (htmlUrl.equals(url)) {
                        allMaps.put("selectEd", 1);
                    }
                }
            }
            result.add(allMaps);
        }
        return result;
    }


    /**
     * @Author yakun.shi
     * @Description //获取页面左侧栏
     * @Date 2019/7/10 11:26
     * @Param [authRoles]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String, Object>> getLeftSideBarResult(List<AuthRole> authRoles) {

        boolean flag = false;
        if(CollectionUtils.isEmpty(authRoles)){
            flag = true;
        }
        for (int i = 0; i < authRoles.size(); i++) {
            AuthRole authRole = authRoles.get(i);
            if (authRole.getRoleType().equals(3)) {
                flag = true;
                break;
            }
        }
        List<Map<String, Object>> LeftSideBars = new ArrayList<>();
        Integer type = 0;
        if (flag) {
            // 处理管理员的角色，返回全部
             LeftSideBars = authWebUrlMapper.selectAllForLeftSideBar();
        } else {
            // 处理其他的角色，返回全部
            AuthRole authRole = authRoles.get(0);
            PermissionRequest request = new PermissionRequest();
            request.setRoleId(authRole.getId());
            request.setType(type);
            LeftSideBars = authWebUrlMapper.selectForLeftSideBar(request);
        }
        Map<Object, List<Map<String, Object>>> parentName = LeftSideBars.stream().collect(Collectors.groupingBy(t -> t.get("parentName")));
        Iterator<Map.Entry<Object, List<Map<String, Object>>>> iterator = parentName.entrySet().iterator();

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        while (iterator.hasNext()) {
            map = new HashMap();
            Object key = iterator.next().getKey();
            List<Map<String, Object>> maps = parentName.get(key);
            map.put("name", key.toString());
            map.put("iconName",maps.get(0).get("iconName"));
            map.put("sort",maps.get(0).get("sort"));
            map.put("children", maps);
            result.add(map);
        }
        result.sort(Comparator.comparingInt(t -> Integer.parseInt(t.get("sort").toString())));
        return result;
    }
}



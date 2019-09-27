package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionRequest;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionInsertParam;
import com.demo.back.controller.system.authusercontroller.request.AuthUserPageRequest;
import com.demo.back.dao.AuthResourcesMapper;
import com.demo.back.dao.AuthRoleMapper;
import com.demo.back.dao.AuthRoleResourcesMapper;
import com.demo.back.dao.AuthUserRoleMapper;
import com.demo.back.entity.AuthResources;
import com.demo.back.entity.AuthRole;
import com.demo.back.enums.ResponseEnum;
import com.demo.back.exception.ParamNullException;
import com.demo.back.service.IAuthResourcesService;
import com.demo.back.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName AuthResourcesServiceImpl
 * @Author yakun.shi
 * @Date 2019/6/13 13:11
 * @Version 1.0
 **/
@Service
public class AuthResourcesServiceImpl implements IAuthResourcesService {

    @Autowired
    private AuthResourcesMapper authResourcesMapper;

    @Autowired
    private AuthRoleResourcesMapper authRoleResourcesMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;

    @Override
    public ServiceResponse resourceInsert(AuthResources request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (request.getResouceName() == null || request.getResouceUrl() == null) {
            throw new ParamNullException();
        }
        String id = UUIDUtil.getUUID();
        request.setId(id);
        request.setCreateTime(new Date());
        authResourcesMapper.resourceInsert(request);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return serviceResponse.setData(map);
    }

    @Override
//    @Cacheable(keyGenerator = "wiselyKeyGenerator",value = "resourceList")
    public PageResponse resourceList(PermissionRequest request) {
        List<Map> authResources = authResourcesMapper.resourceList(request);
        Integer count = authResourcesMapper.resourceListCount(request);
        return PageResponse.createResponse(count, authResources, request);
    }

    @Override
    public ServiceResponse resourceDelete(PermissionRequest request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (request.getRoleId() == null) {
            throw new ParamNullException();
        }
        AuthRole authRole = authRoleMapper.selectByRoelId(request.getRoleId());
        String roleName = authRole.getRoleName();
        //如果角色为管理员,不能删除
        if ("admin".equals(roleName)) {
            serviceResponse.setMessage(ResponseEnum.IS_SUPERADMIN.getMsg());
            serviceResponse.setResult(ResponseEnum.IS_SUPERADMIN.getCode());
            return serviceResponse;
        }
        authResourcesMapper.resourceDelete(request.getRoleId());
        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getRoleId());
        return serviceResponse.setData(map);
    }

    @Override
    @Transactional
    public ServiceResponse rolePermissionInsert(PermissionRequest request) {
        if (CollectionUtils.isEmpty(request.getParams()) || request.getRoleId() == null) {
            throw new ParamNullException();
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        List<PermissionInsertParam> params = request.getParams();
//        List<PermissionInsertParam> collect = params.stream().filter(t -> t.getRoleId() != null).collect(Collectors.toList());
//        authRoleResourcesMapper.deleteByBatchId(collect);
        authRoleResourcesMapper.deleteByRoleId(request.getRoleId());
        params.forEach(t -> {
            t.setResourceRoleId(UUIDUtil.getUUID());
            t.setRoleId(request.getRoleId());
        });
        authRoleResourcesMapper.rolePermissionBatchInsert(params);
        return serviceResponse;
    }

    @Override
    public ServiceResponse roleResourceList(PermissionRequest request) {
        if (request.getRoleId() == null) {
            throw new ParamNullException();
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        Integer flag = 1;
        request.setType(flag);
        List<Map<String, Object>> allmaps = authResourcesMapper.roleResourceList(flag);
        List<Map<String, Object>> rolemaps = authResourcesMapper.roleResourceListByRoleId(request);
        AuthRole authRole = authRoleMapper.selectByRoelId(request.getRoleId());
        String roleName = authRole.getRoleName();

        List<Map<String, Object>> resources = getResources(allmaps, rolemaps);
        //如果角色为管理员,默认所有权限开启
        if ("admin".equals(roleName)) {
            allmaps.forEach(t -> {
                t.put("selectEd", 1);
            });
        }
        List<Map<String, Object>> result = getResult(resources);
        serviceResponse.setData(result);
        return serviceResponse;
    }

    @Override
    public ServiceResponse leftData(AuthUserPageRequest request) {
        ServiceResponse response = new ServiceResponse();
        String userId = request.getUserId();

        List<Map<String, Object>> result = new ArrayList();
        List<AuthRole> authRoles = authUserRoleMapper.selectRoleByUserId(userId);
        if (CollectionUtils.isEmpty(authRoles)) {
            return response.setData(result);
        }
        List<Map<String, Object>> leftResult = getLeftResult(authRoles);
        return response.setData(leftResult);
    }


    /**
     * @return java.util.List<java.util.Map   <   java.lang.String   ,   java.lang.Object>>
     * @Author yakun.shi
     * @Description //获取非管理员的左边侧框
     * @Date 2019/7/8 11:59
     * @Param [authRoles]
     **/
    private List<Map<String, Object>> getLeftResult(List<AuthRole> authRoles) {

        boolean flag = false;
        for (int i = 0; i < authRoles.size(); i++) {
            AuthRole authRole = authRoles.get(i);
            if ("admin".equals(authRole.getRoleName())) {
                flag = true;
                break;
            }
        }
        List<Map<String, Object>> resultMap = new ArrayList<>();
        Integer type = 0;
        if (flag) {
            // 处理管理员的角色，返回全部
            resultMap = authResourcesMapper.roleResourceList(type);
        } else {
            // 处理其他的角色，返回全部
            AuthRole authRole = authRoles.get(0);
            PermissionRequest request = new PermissionRequest();
            request.setRoleId(authRole.getId());
            request.setType(type);
            resultMap = authResourcesMapper.roleResourceListByRoleId(request);
        }
        Map<Object, List<Map<String, Object>>> leftName = resultMap.stream().collect(Collectors.groupingBy(t -> t.get("leftName")));
        Iterator<Map.Entry<Object, List<Map<String, Object>>>> iterator = leftName.entrySet().iterator();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        while (iterator.hasNext()) {
            map = new HashMap();
            Object key = iterator.next().getKey();
            List<Map<String, Object>> maps = leftName.get(key);
            map.put("name", key.toString());
            map.put("children", maps);
            result.add(map);
        }
        return result;
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
        Map<Object, List<Map>> resourceType = maps.stream().collect(Collectors.groupingBy(t -> t.get("resourceType")));
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
            String resourceName = allMaps.get("resourceName").toString();
            allMaps.put("selectEd", 0);
            if (!CollectionUtils.isEmpty(roleResources) || roleResources.size() > 0) {
                for (int j = 0; j < roleResources.size(); j++) {
                    Map<String, Object> roleMaps = roleResources.get(j);
                    String roleresourceName = roleMaps.get("resourceName").toString();
                    if (resourceName.equals(roleresourceName)) {
                        allMaps.put("selectEd", 1);
                    }
                }
            }
            result.add(allMaps);
        }
        return result;
    }

}

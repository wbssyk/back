package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionRequest;
import com.demo.back.controller.system.authusercontroller.request.AuthUserPageRequest;
import com.demo.back.entity.AuthResources;

public interface IAuthResourcesService {
    ServiceResponse resourceInsert(AuthResources request);

    PageResponse resourceList(PermissionRequest request);

    ServiceResponse resourceDelete(PermissionRequest request);

    ServiceResponse rolePermissionInsert(PermissionRequest request);

    ServiceResponse roleResourceList(PermissionRequest request);

    ServiceResponse leftData(AuthUserPageRequest request);
}

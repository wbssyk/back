package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authresourcecontroller.request.AuthWebUrlRequest;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionRequest;
import com.demo.back.entity.AuthWebUrl;

public interface IAuthWebUrlService {
    ServiceResponse resourceInsert(AuthWebUrl request);

    PageResponse resourceList(AuthWebUrlRequest request);

    ServiceResponse resourceDelete(AuthWebUrl request);

    ServiceResponse resourceUpdate(AuthWebUrl request);

    ServiceResponse LeftSideBar(AuthWebUrl request);

    ServiceResponse roleWebPermissionInsert(PermissionRequest request);

    ServiceResponse roleWebResourceList(PermissionRequest request);
}

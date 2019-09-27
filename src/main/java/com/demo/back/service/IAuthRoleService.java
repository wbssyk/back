package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authrolecontroller.request.AuthRoleRequest;
import com.demo.back.entity.AuthRole;

public interface IAuthRoleService {
    ServiceResponse roleInsert(AuthRole request);

    ServiceResponse selectRoleListForUser(AuthRoleRequest request);

    ServiceResponse roleUpdate(AuthRole request);

    ServiceResponse userRoleInsert(AuthRoleRequest request);

    ServiceResponse roleDeleteByUserIdAndRoleId(AuthRoleRequest request);

    PageResponse roleDataList(AuthRoleRequest request);
}

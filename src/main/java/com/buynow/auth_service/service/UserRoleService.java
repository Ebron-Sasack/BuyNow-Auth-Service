package com.buynow.auth_service.service;

import com.buynow.auth_service.enums.RoleType;

public interface UserRoleService {

    void updateUserRole(Long userId, RoleType role);

}

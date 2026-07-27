package com.buynow.auth_service.controller;

import com.buynow.auth_service.dto.request.UpdateRoleRequest;
import com.buynow.auth_service.payload.ApiResponse;
import com.buynow.auth_service.service.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth/users")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PutMapping("/{userId}/role")
    public ResponseEntity<ApiResponse<Void>> updateRole(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateRoleRequest request) {

        userRoleService.updateUserRole(userId, request.getRole());
        return ResponseEntity.ok(
                new ApiResponse<>("User role updated successfully", null));
    }
}
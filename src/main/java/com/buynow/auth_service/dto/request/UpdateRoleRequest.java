package com.buynow.auth_service.dto.request;

import com.buynow.auth_service.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRoleRequest {

    @NotNull
    private RoleType role;

}
package com.buynow.auth_service.dto.response;
import com.buynow.auth_service.entity.Role;
import com.buynow.auth_service.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponse {

    private boolean valid;
    private Long userId;
    private String username;
    private RoleType role;
}
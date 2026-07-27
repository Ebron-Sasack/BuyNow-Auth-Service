package com.buynow.auth_service.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {

    private Long userId;

    private String email;

    private Boolean enabled;

    private List<String> roles;
}
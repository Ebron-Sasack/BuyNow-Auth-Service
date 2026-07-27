package com.buynow.auth_service.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
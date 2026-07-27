package com.buynow.auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateTokenRequest {

    @NotBlank(message = "Token is required")
    private String token;
}
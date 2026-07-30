package com.buynow.auth_service.controller;

import com.buynow.auth_service.dto.request.LoginRequest;
import com.buynow.auth_service.dto.request.RegisterRequest;
import com.buynow.auth_service.dto.request.ValidateTokenRequest;
import com.buynow.auth_service.dto.response.LoginResponse;
import com.buynow.auth_service.dto.response.RegisterResponse;
import com.buynow.auth_service.dto.response.UserInfoResponse;
import com.buynow.auth_service.dto.response.ValidateTokenResponse;
import com.buynow.auth_service.exception.InvalidTokenException;
import com.buynow.auth_service.payload.ApiResponse;
import com.buynow.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(
                new ApiResponse<>("Registration successful", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(
                new ApiResponse<>("Login successful", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getCurrentUser() {

        UserInfoResponse response = authService.getCurrentUser();
        return ResponseEntity.ok(new ApiResponse<>("User fetched successfully", response));
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<ValidateTokenResponse>> validateToken(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        ValidateTokenResponse response = authService.validateToken(token);

        return ResponseEntity.ok(
                new ApiResponse<>("Token validation successful", response)
        );
    }
}
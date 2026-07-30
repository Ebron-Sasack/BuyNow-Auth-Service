package com.buynow.auth_service.service;

import com.buynow.auth_service.dto.request.LoginRequest;
import com.buynow.auth_service.dto.request.RegisterRequest;
import com.buynow.auth_service.dto.response.LoginResponse;
import com.buynow.auth_service.dto.response.RegisterResponse;
import com.buynow.auth_service.dto.response.UserInfoResponse;
import com.buynow.auth_service.dto.response.ValidateTokenResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserInfoResponse getCurrentUser();

    ValidateTokenResponse validateToken(String token);

}
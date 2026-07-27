package com.buynow.auth_service.service;

import com.buynow.auth_service.security.CustomUserDetails;
import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {

    String generateToken(CustomUserDetails userDetails);

    String generateToken(
            Map<String, Object> extraClaims,
            CustomUserDetails userDetails
    );

    String extractUsername(String token);

    Long extractUserId(String token);

    Claims extractAllClaims(String token);

    boolean isTokenValid(String token, CustomUserDetails userDetails);

    boolean isTokenExpired(String token);

}
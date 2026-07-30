package com.buynow.auth_service.service.implementation;

import com.buynow.auth_service.dto.request.LoginRequest;
import com.buynow.auth_service.dto.request.RegisterRequest;
import com.buynow.auth_service.dto.response.LoginResponse;
import com.buynow.auth_service.dto.response.RegisterResponse;
import com.buynow.auth_service.dto.response.UserInfoResponse;
import com.buynow.auth_service.dto.response.ValidateTokenResponse;
import com.buynow.auth_service.entity.Role;
import com.buynow.auth_service.entity.UserCredential;
import com.buynow.auth_service.enums.RoleType;
import com.buynow.auth_service.exception.AlreadyExistsException;
import com.buynow.auth_service.exception.InvalidTokenException;
import com.buynow.auth_service.exception.ResourceNotFoundException;
import com.buynow.auth_service.feign.UserServiceClient;
import com.buynow.auth_service.dto.request.CreateUserRequest;
import com.buynow.auth_service.repository.RoleRepository;
import com.buynow.auth_service.repository.UserCredentialRepository;
import com.buynow.auth_service.security.CustomUserDetails;
import com.buynow.auth_service.service.AuthService;
import com.buynow.auth_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserServiceClient userServiceClient;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userCredentialRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already registered.");
        }

        Role role = roleRepository.findByRoleName(RoleType.BUYER)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Default role not found."));

        UserCredential credential = UserCredential.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .enabled(true)
                .build();

        credential = userCredentialRepository.save(credential);

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .id(credential.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        userServiceClient.createUser(createUserRequest);

        return RegisterResponse.builder()
                .userId(credential.getId())
                .email(credential.getEmail())
                .message("User registered successfully.")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserCredential credential = userCredentialRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        CustomUserDetails userDetails = new CustomUserDetails(credential);

        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .expiresIn(86400000L)
                .userId(credential.getId())
                .email(credential.getEmail())
                .roles(List.of(
                        credential.getRole().getRoleName().name()
                ))
                .build();
    }

    @Override
    public UserInfoResponse getCurrentUser() {

        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        UserCredential credential =
                userCredentialRepository.findByEmail(userDetails.getEmail())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found."));

        return UserInfoResponse.builder()
                .userId(credential.getId())
                .email(credential.getEmail())
                .enabled(credential.getEnabled())
                .roles(List.of(
                        credential.getRole().getRoleName().name()
                ))
                .build();
    }

    @Override
    public ValidateTokenResponse validateToken(String token) {

        if (!jwtService.isTokenValid(token)) {
            throw new InvalidTokenException("Invalid JWT Token");
        }

        String username = jwtService.extractUsername(token);

        UserCredential user = userCredentialRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        RoleType role = user.getRole().getRoleName();

        return ValidateTokenResponse.builder()
                .valid(true)
                .userId(user.getId())
                .username(user.getEmail())
                .role(role)
                .build();
    }
}
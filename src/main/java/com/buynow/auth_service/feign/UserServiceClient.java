package com.buynow.auth_service.feign;

import com.buynow.auth_service.dto.request.CreateUserRequest;
import com.buynow.auth_service.dto.response.UserResponse;
import com.buynow.auth_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @PostMapping("${api.prefix}/users")
    ApiResponse<UserResponse> createUser(@RequestBody CreateUserRequest request);

}
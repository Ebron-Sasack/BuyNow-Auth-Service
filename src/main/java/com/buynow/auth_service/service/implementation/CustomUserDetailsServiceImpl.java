package com.buynow.auth_service.service.implementation;

import com.buynow.auth_service.entity.UserCredential;
import com.buynow.auth_service.repository.UserCredentialRepository;
import com.buynow.auth_service.security.CustomUserDetails;
import com.buynow.auth_service.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl
        implements CustomUserDetailsService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public CustomUserDetails loadUserByUsername(@NonNull String email)
            throws UsernameNotFoundException {

        UserCredential credential =
                userCredentialRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "User not found with email: " + email));

        return new CustomUserDetails(credential);
    }
}
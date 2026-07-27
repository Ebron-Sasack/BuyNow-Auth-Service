package com.buynow.auth_service.configuration;

import com.buynow.auth_service.entity.Role;
import com.buynow.auth_service.enums.RoleType;
import com.buynow.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles() {

        return args -> {

            if (roleRepository.findByRoleName(RoleType.BUYER).isEmpty()) {
                roleRepository.save(
                        Role.builder()
                                .roleName(RoleType.BUYER)
                                .build()
                );
            }

            if (roleRepository.findByRoleName(RoleType.ADMIN).isEmpty()) {
                roleRepository.save(
                        Role.builder()
                                .roleName(RoleType.ADMIN)
                                .build()
                );
            }

            if (roleRepository.findByRoleName(RoleType.SELLER).isEmpty()) {
                roleRepository.save(
                        Role.builder()
                                .roleName(RoleType.SELLER)
                                .build()
                );
            }
        };
    }
}

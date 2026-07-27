package com.buynow.auth_service.repository;

import com.buynow.auth_service.entity.Role;
import com.buynow.auth_service.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleType roleName);

}
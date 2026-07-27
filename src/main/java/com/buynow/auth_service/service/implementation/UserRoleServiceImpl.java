package com.buynow.auth_service.service.implementation;

import com.buynow.auth_service.entity.Role;
import com.buynow.auth_service.entity.UserCredential;
import com.buynow.auth_service.enums.RoleType;
import com.buynow.auth_service.exception.ResourceNotFoundException;
import com.buynow.auth_service.repository.RoleRepository;
import com.buynow.auth_service.repository.UserCredentialRepository;
import com.buynow.auth_service.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserCredentialRepository userCredentialRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void updateUserRole(Long userId, RoleType roleType) {
        UserCredential credential = userCredentialRepository.findById(userId).orElseThrow(
                ()->  new ResourceNotFoundException("User not found with id : " + userId)
        );

        Role role = roleRepository.findByRoleName(roleType).orElseThrow(
                ()->  new ResourceNotFoundException("Role not found : " + roleType)
        );

        credential.setRole(role);
        userCredentialRepository.save(credential);
    }
}

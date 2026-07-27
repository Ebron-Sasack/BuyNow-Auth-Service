package com.buynow.auth_service.security;

import com.buynow.auth_service.entity.UserCredential;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final Boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserCredential credential) {

        this.id = credential.getId();
        this.email = credential.getEmail();
        this.password = credential.getPassword();
        this.enabled = credential.getEnabled();

        this.authorities = List.of(
                new SimpleGrantedAuthority(
                        credential.getRole().getRoleName().name()
                )
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
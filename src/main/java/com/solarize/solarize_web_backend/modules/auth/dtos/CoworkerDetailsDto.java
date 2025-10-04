package com.solarize.solarize_web_backend.modules.auth.dtos;

import com.solarize.solarize_web_backend.modules.permissionGroup.BitMaskResolver;
import com.solarize.solarize_web_backend.modules.permissionGroup.PermissionGroup;
import com.solarize.solarize_web_backend.modules.user.Coworker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Permission;
import java.util.Collection;

@Getter
@Setter
public class CoworkerDetailsDto implements UserDetails {

    private Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final PermissionGroup permission;

    public CoworkerDetailsDto(Coworker coworker) {
        this.id = coworker.getId();
        this.firstName = coworker.getFirstName();
        this.lastName = coworker.getLastName();
        this.email = coworker.getEmail();
        this.password = coworker.getPassword();
        this.permission = coworker.getPermission();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new BitMaskResolver().resolve(this.permission);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    @Override
    public boolean isEnabled() {
        return true;
    }

}

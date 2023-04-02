package org.example.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    RULER,
    TEACHER;

    @Override
    public String getAuthority() {
        return name();
    }
}

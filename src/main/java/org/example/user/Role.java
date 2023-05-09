package org.example.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("Администратор"),
    RULER("Руководитель"),
    TEACHER("Учитель");

    @Override
    public String getAuthority() {
        return name();
    }

    private String title;

    Role(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

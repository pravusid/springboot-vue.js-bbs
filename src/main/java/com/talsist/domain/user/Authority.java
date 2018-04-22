package com.talsist.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return this.toString();
    }

}

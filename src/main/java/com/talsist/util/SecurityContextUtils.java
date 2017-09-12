package com.talsist.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.talsist.domain.User;

public class SecurityContextUtils {

    public static User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

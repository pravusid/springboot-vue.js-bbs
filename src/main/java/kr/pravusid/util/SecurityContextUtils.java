package kr.pravusid.util;

import kr.pravusid.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtils {

    public static User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

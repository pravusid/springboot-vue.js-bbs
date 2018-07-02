package kr.pravusid.util;

import kr.pravusid.domain.UserVerifiable;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.user.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UserSessionUtil {

    public static String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static void applyAuthToCtxHolder(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void permissionCheck(UserVerifiable entity) {
        if (!entity.verifyUser(getAuthenticatedUsername())) {
            throw new AuthenticationCredentialsNotFoundException("권한이 없습니다");
        }
    }

}

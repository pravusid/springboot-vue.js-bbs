package kr.pravusid.service;

import kr.pravusid.domain.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionUserService {

    public String getAuthUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void applyAuthToCtxHolder(User user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}

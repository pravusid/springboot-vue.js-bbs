package com.talsist.security;

import com.talsist.domain.User;
import com.talsist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public CustomUserDetailService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepo.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }
        return user;
    }

}

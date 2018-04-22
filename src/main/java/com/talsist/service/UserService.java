package com.talsist.service;

import com.talsist.domain.user.User;
import com.talsist.domain.user.UserRepository;
import com.talsist.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(UserDto userDto) {
        User user = userRepo.save(userDto.toEntity(passwordEncoder));
        applyAuthToCtxHolder(user);
    }

    public User findOne(Long id) {
        return userRepo.findOne(id);
    }

    public void update(UserDto userDto) {
        User user = userRepo.findOne(userDto.getId());
        user.update(userDto.toEntity(passwordEncoder));
        userRepo.save(user);
        applyAuthToCtxHolder(user);
    }

    private void applyAuthToCtxHolder(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}

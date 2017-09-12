package com.talsist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talsist.domain.User;
import com.talsist.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);
        // 로그인 처리
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public User findOne(Long id) {
        return userRepo.findOne(id);
    }

    public void update(Long id, User reqUser) {
        User user = userRepo.findOne(id);
        user.update(reqUser);
        userRepo.save(user);
    }

}

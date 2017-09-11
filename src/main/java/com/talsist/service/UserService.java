package com.talsist.service;

import com.talsist.domain.User;
import com.talsist.exception.NotAllowedException;
import com.talsist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        userRepo.save(user);
        // 로그인 처리
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public User findOne(Long id) {
        return userRepo.findOne(id);
    }

    public void update(User sessionUser, Long id, User reqUser) throws NotAllowedException {
        permissionCheck(sessionUser, id);
        User user = userRepo.findOne(id);
        user.update(reqUser);
        userRepo.save(user);
    }

    private void permissionCheck(User sessionUser, Long reqId) throws NotAllowedException {
        if (!sessionUser.verifyId(reqId)) {
            throw new NotAllowedException();
        }
    }

}

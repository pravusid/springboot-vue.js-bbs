package com.talsist.service;

import com.talsist.domain.User;
import com.talsist.exception.NotAllowedException;
import com.talsist.exception.NotLoggedInException;
import com.talsist.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        // TODO validation chk
        userRepo.save(user);
    }

    public User findOne(User sessionUser, Long id) throws NotAllowedException {
        permissionCheck(sessionUser, id);
        return userRepo.findOne(id);
    }

    public void update(User sessionUser, Long id, User reqUser) throws NotAllowedException {
        permissionCheck(sessionUser, id);
        User user = userRepo.findOne(id);
        user.update(reqUser);
        userRepo.save(user);
    }

    public User login(User reqUser) throws NotLoggedInException {
        User user = userRepo.findByUserId(reqUser.getUserId());
        if (user == null || !user.verifyPassword(reqUser)) {
            throw new NotLoggedInException();
        }
        return user;
    }

    private void permissionCheck(User sessionUser, Long reqId) throws NotAllowedException {
        if (!sessionUser.verifyId(reqId)) {
            throw new NotAllowedException();
        }
    }

}

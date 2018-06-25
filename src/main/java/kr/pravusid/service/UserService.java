package kr.pravusid.service;

import kr.pravusid.domain.user.User;
import kr.pravusid.domain.user.UserRepository;
import kr.pravusid.dto.UserDto;
import kr.pravusid.util.UserSessionUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(UserDto userDto) {
        User user = userRepo.save(userDto.toEntity());
        UserSessionUtil.applyAuthToCtxHolder(user);
    }

    public User findOne(Long id) {
        return userRepo.findOne(id);
    }

    public void update(UserDto userDto) {
        User user = userRepo.findOne(userDto.getId());
        user.update(userDto.toEntity());
        userRepo.save(user);
        UserSessionUtil.applyAuthToCtxHolder(user);
    }

}

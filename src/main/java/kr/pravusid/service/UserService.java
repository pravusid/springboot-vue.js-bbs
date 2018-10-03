package kr.pravusid.service;

import kr.pravusid.domain.UserVerifiable;
import kr.pravusid.domain.user.User;
import kr.pravusid.domain.user.UserRepository;
import kr.pravusid.dto.UserDto;
import kr.pravusid.dto.exception.CustomValidationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(UserDto dto) {
        duplicateCheckForUsername(dto);
        duplicateCheckForEmail(dto);
        return userRepository.save(dto.toEntity());
    }

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User update(UserDto dto) {
        User user = userRepository.findOne(dto.getId());
        if (!dto.getEmail().equals(user.getEmail())) {
            duplicateCheckForEmail(dto);
        }
        user.update(dto.toEntity());
        return user;
    }

    public void permissionCheck(String username, UserVerifiable entity) {
        if (!entity.verifyUser(username)) {
            throw new AuthenticationCredentialsNotFoundException("권한이 없습니다");
        }
    }

    private void duplicateCheckForUsername(UserDto dto) {
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new CustomValidationException("userDto", "username", "이미 존재하는 아이디 입니다");
        }
    }

    private void duplicateCheckForEmail(UserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new CustomValidationException("userDto", "email", "사용중인 이메일 입니다");
        }
    }

}

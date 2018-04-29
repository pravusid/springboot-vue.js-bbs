package kr.pravusid.api.v1.user;

import kr.pravusid.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.pravusid.domain.user.User;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

    private UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public User userList() {
        return null;
    }

    @PostMapping("")
    public boolean signup(User user) {
        return true;
    }

}

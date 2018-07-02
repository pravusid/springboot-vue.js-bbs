package kr.pravusid.api.v1.user;

import kr.pravusid.dto.UserDto;
import kr.pravusid.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import kr.pravusid.domain.user.User;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

    private UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> userList() {
        return userService.findAll();
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserDto userDto) {
        userService.save(userDto);
    }

    @GetMapping("/{username}")
    public User userDetails(@PathVariable String username) {
        return userService.findOne(username);
    }

}

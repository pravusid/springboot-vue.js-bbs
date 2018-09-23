package kr.pravusid.api.v1.user;

import kr.pravusid.dto.UserDto;
import kr.pravusid.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

    private UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserDto> list() {
        return userService.findAll()
                .stream().map(UserDto::of).collect(Collectors.toList());
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserDto userDto) {
        userService.save(userDto);
    }

    @GetMapping("/{username}")
    public UserDto detail(@PathVariable String username) {
        return UserDto.of(userService.findOne(username));
    }

    @PutMapping("/{id}")
    public boolean modify() {
        return false;
    }

}

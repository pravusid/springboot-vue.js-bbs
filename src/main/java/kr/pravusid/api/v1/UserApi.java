package kr.pravusid.api.v1;

import kr.pravusid.domain.user.exception.InvalidUserException;
import kr.pravusid.dto.UserDto;
import kr.pravusid.service.JwtUserService;
import kr.pravusid.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    private JwtUserService jwtUserService;
    private UserService userService;

    public UserApi(JwtUserService jwtUserService, UserService userService) {
        this.jwtUserService = jwtUserService;
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
    public UserDto detail(@PathVariable String username,
                          @RequestHeader(value = "Authorization") String authorization) {
        if (jwtUserService.isValidUser(authorization, username)) {
            return UserDto.of(userService.findOne(username));
        } else {
            throw new InvalidUserException();
        }
    }

    @PutMapping("/{username}")
    public void modify(@PathVariable String username,
                       @RequestBody UserDto userDto,
                       @RequestHeader(value = "Authorization") String authorization) {
        if (username.equals(userDto.getUsername()) && jwtUserService.isValidUser(authorization, username)) {
            userDto.setId(userService.findOne(username).getId());
            userService.update(userDto);
        } else {
            throw new InvalidUserException();
        }
    }

}

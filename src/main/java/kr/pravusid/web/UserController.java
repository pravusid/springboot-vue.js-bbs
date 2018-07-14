package kr.pravusid.web;

import kr.pravusid.dto.UserDto;
import kr.pravusid.service.UserService;
import kr.pravusid.service.SessionUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private SessionUserService sessionUserService;
    private UserService userService;

    public UserController(SessionUserService sessionUserService, UserService userService) {
        this.sessionUserService = sessionUserService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/user")
    public String list(Model model) {
        List<UserDto> userList = userService.findAll()
                .stream().map(UserDto::of).collect(Collectors.toList());
        model.addAttribute("list", userList);
        return "user/list";
    }

    @PostMapping("/user")
    public String signup(@Valid UserDto userDto, BindingResult binding) {
        if (binding.hasErrors()) {
            return "user/signup";
        }
        sessionUserService.applyAuthToCtxHolder(userService.save(userDto));
        return "redirect:/";
    }

    @PreAuthorize("#id==principal.id")
    @GetMapping("/user/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("detail", UserDto.of(userService.findOne(id)));
        return "user/detail";
    }

    @PreAuthorize("#id==principal.id")
    @PutMapping("/user/{id}")
    public String modify(@PathVariable Long id, UserDto userDto) {
        userDto.setId(id);
        sessionUserService.applyAuthToCtxHolder(userService.update(userDto));
        return "redirect:/user";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

}

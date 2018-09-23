package kr.pravusid.web;

import kr.pravusid.dto.UserDto;
import kr.pravusid.dto.exception.CustomValidationException;
import kr.pravusid.service.SessionUserService;
import kr.pravusid.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @PreAuthorize("#id==principal.id")
    @GetMapping("/user/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("detail", UserDto.of(userService.findOne(id)));
        return "user/detail";
    }

    @PreAuthorize("#id==principal.id")
    @PutMapping("/user/{id}")
    public String modify(@PathVariable Long id, UserDto userDto) {
        sessionUserService.applyAuthToCtxHolder(userService.update(userDto));
        return "redirect:/user";
    }

    @GetMapping("/signup")
    public String signup(UserDto userDto) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserDto userDto, BindingResult binding) {
        if (binding.hasErrors()) {
            return "user/signup";
        }
        try {
            sessionUserService.applyAuthToCtxHolder(userService.save(userDto));
        } catch (CustomValidationException e) {
            binding.addError(e.getError());
            return "user/signup";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

}

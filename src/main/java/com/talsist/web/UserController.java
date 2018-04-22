package com.talsist.web;

import com.talsist.dto.UserDto;
import com.talsist.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {

    private UserService userSvc;

    public UserController(UserService userSvc) {
        this.userSvc = userSvc;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/user")
    public String list(Model model) {
        model.addAttribute("list", userSvc.findAll());
        return "user/list";
    }

    @PostMapping("/user")
    public String signup(UserDto userDto) {
        userSvc.save(userDto);
        return "redirect:/";
    }

    @PreAuthorize("#id==principal.id")
    @GetMapping("/user/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("detail", userSvc.findOne(id));
        return "user/detail";
    }

    @PreAuthorize("#id==principal.id")
    @PutMapping("/user/{id}")
    public String modify(@PathVariable Long id, UserDto userDto) {
        userDto.setId(id);
        userSvc.update(userDto);
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

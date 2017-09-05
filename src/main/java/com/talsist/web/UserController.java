package com.talsist.web;

import com.talsist.domain.User;
import com.talsist.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public String list(Model model) {
        model.addAttribute("list", userRepository.findAll());
        return "list";
    }

    @GetMapping("/user/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("detail", userRepository.findOne(id));
        return "detail";
    }

    @PutMapping("/user/{id}")
    public String update(@PathVariable Long id, User newUser) {
        User user = userRepository.findOne(id);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/user";
    }

    @PostMapping("/user")
    public String create(User user) {
        System.out.println("유저정보"+user);
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

}

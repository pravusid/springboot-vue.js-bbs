package com.talsist.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.talsist.domain.User;
import com.talsist.exception.NotLoggedInException;
import com.talsist.service.UserService;
import com.talsist.util.HttpUtils;

@Controller
public class UserController {

    private UserService userSvc;
    
    @Autowired
    public UserController(UserService userSvc) {
        this.userSvc = userSvc;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user")
    public String list(Model model) {
        model.addAttribute("list", userSvc.findAll());
        return "user/list";
    }

    @PostMapping("/user")
    public String signup(User user) {
        userSvc.save(user);
        return "redirect:/login";
    }

    @PreAuthorize("(#id==principal.id)")
    @GetMapping("/user/{id}")
    public String detail(@PathVariable Long id, Model model, HttpServletRequest request, HttpSession session) {
        try {
            model.addAttribute("detail", userSvc.findOne(id));
            return "user/detail";

        } catch (Exception e) {
            return HttpUtils.redirctToLoginPage(request);
        }
    }

    @PutMapping("/user/{id}")
    public String modify(@PathVariable Long id, User reqUser, HttpServletRequest request, HttpSession session) {
        try {
            userSvc.update(HttpUtils.getSessionUser(session), id, reqUser);
            return "redirect:/user";

        } catch (NotLoggedInException e) {
            return HttpUtils.redirctToLoginPage(request);
        }
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

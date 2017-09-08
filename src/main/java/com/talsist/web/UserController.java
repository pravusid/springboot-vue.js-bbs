package com.talsist.web;

import com.talsist.domain.User;
import com.talsist.exception.NotLoggedInException;
import com.talsist.service.UserService;
import com.talsist.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userSvc;

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

    @GetMapping("/user/{id}")
    public String detail(@PathVariable Long id, Model model, HttpServletRequest request, HttpSession session) {
        try {
            model.addAttribute("detail",
                    userSvc.findOne(HttpSessionUtils.getSessionUser(session), id));
            return "user/detail";

        } catch (Exception e) {
            return HttpSessionUtils.redirctToLoginPage(request, session);
        }
    }

    @PutMapping("/user/{id}")
    public String modify(@PathVariable Long id, User reqUser, HttpServletRequest request, HttpSession session) {
        try {
            userSvc.update(HttpSessionUtils.getSessionUser(session), id, reqUser);
            return "redirect:/user";

        } catch (NotLoggedInException e) {
            return HttpSessionUtils.redirctToLoginPage(request, session);
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

    @PostMapping("/login")
    public String login(User reqUser, HttpSession session) {
        try {
            User user = userSvc.login(reqUser);
            session.setAttribute("user", user);
            if (session.getAttribute("prevPage") != null) {
                String prev = (String) session.getAttribute("prevPage");
                session.removeAttribute("prevPage");
                return "redirect:" + prev;
            }
            return "redirect:/";

        } catch (NotLoggedInException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

}

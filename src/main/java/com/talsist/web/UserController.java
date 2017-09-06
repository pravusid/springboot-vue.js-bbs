package com.talsist.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.talsist.domain.User;
import com.talsist.domain.UserRepository;
import com.talsist.exception.NotLoggedInException;
import com.talsist.util.HttpSessionUtils;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/user")
    public String list(Model model) {
        model.addAttribute("list", userRepo.findAll());
        return "user/list";
    }
    
    @PostMapping("/user")
    public String insert(User user) {
        userRepo.save(user);
        return "redirect:/user";
    }

    @GetMapping("/user/{id}")
    public String detail(@PathVariable Long id, Model model, HttpServletRequest request, HttpSession session) {
        try {
        	permissionCheck(session, id);
        	model.addAttribute("detail", userRepo.findOne(id));
        	return "user/detail";
			
		} catch (NotLoggedInException e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
    }

    @PutMapping("/user/{id}")
    public String update(@PathVariable Long id, User newUser, HttpServletRequest request, HttpSession session) {
	   try {
           	permissionCheck(session, id);
           	User user = userRepo.findOne(id);
            user.update(newUser);
            userRepo.save(user);
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
        User user = userRepo.findByUserId(reqUser.getUserId());
        if (user == null || !user.verifyPassword(reqUser)) {
            return "redirect:/login";
        }
        session.setAttribute("user", user);
        
        String prevURI = (String) session.getAttribute("prevPage");
        session.removeAttribute("prevPage");
        return (prevURI != null)? "redirect:"+prevURI: "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
    
    private void permissionCheck(HttpSession session, Long id) throws NotLoggedInException {
    	User sessionUser = HttpSessionUtils.getSessionUser(session);
    	if (sessionUser == null || !sessionUser.verifyId(id)) {
    		throw new NotLoggedInException();
        }
    }

}

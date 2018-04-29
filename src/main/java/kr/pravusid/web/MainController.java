package kr.pravusid.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/board";
    }

    @GetMapping("/denied")
    public @ResponseBody
    String accessDenied() {
        return "403ERROR";
    }

}

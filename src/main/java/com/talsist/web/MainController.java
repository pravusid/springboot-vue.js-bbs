package com.talsist.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public @ResponseBody
    String mainPage() {
        return "Hello World!";
    }

    @GetMapping("/test")
    public @ResponseBody
    String test() {
        return "여기는 한글 TEST";
    }

}

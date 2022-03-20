package com.shundalov.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {


    @GetMapping("/user")
    public String userInfo() {
        return "user.html";
    }
}

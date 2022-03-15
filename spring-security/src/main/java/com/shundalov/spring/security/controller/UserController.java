package com.shundalov.spring.security.controller;

import com.shundalov.spring.security.model.User;
import com.shundalov.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class UserController {


    @GetMapping("/user")
    public String userInfo() {
        return "user.html";
    }
}

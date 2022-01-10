package com.shundalov.spring.security.controller;

import com.shundalov.spring.security.exceptionHandling.NoSuchUserException;
import com.shundalov.spring.security.model.User;
import com.shundalov.spring.security.service.RoleService;
import com.shundalov.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RESTController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> allUsers(){
        List<User> userList = userService.listUsers();
        System.out.println(userList);
        return userList;
    }

    @GetMapping("/userAuth")
    public User getUserAuthorize(){
        User admin = (User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return admin;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.getUserByID(id);
        if(user == null) {
            throw new NoSuchUserException("The is no user with ID =" + id +
                    " in Database");
        }
        return user;
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        userService.add(user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        userService.edit(user);
        return user;
    }

    @DeleteMapping("/user/{id}")
    public List<User> deleteMapping(@PathVariable Long id) {
        User user = userService.getUserByID(id);
        if(user == null) {
            throw new NoSuchUserException("user with " + id + " is doesn't exist");
        }
        userService.delete(id);
        return userService.listUsers();
    }
}

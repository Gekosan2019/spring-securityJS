package com.shundalov.spring.security.controller;


import com.shundalov.spring.security.exceptionHandling.NoSuchUserException;
import com.shundalov.spring.security.exceptionHandling.UserIncorrectData;
import com.shundalov.spring.security.model.User;
import com.shundalov.spring.security.service.RoleService;
import com.shundalov.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<List<User>> allUsers(){
        List<User> userList = userService.listUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/userAuth")
    public ResponseEntity<User> getUserAuthorize(){
        User admin = (User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserIncorrectData> addNewUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new UserIncorrectData(), HttpStatus.BAD_REQUEST);
        } else {
            userService.add(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/users")
    public ResponseEntity<NoSuchUserException> updateUser(@RequestBody User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(new NoSuchUserException("Error"), HttpStatus.BAD_REQUEST);
        } else {
            userService.edit(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteMapping(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>("User was deleted", HttpStatus.OK);
    }
}

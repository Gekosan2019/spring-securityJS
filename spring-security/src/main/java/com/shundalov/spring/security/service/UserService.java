package com.shundalov.spring.security.service;

import com.shundalov.spring.security.model.User;

import java.util.List;


public interface UserService {

    void add(User user);
    void delete(Long id);
    void edit(User user);
    List<User> listUsers();
    User getUserByID(Long id);
    User getUserByUsername(String username);
}

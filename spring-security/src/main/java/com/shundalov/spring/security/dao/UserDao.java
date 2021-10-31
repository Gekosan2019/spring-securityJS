package com.shundalov.spring.security.dao;

import com.shundalov.spring.security.model.User;

import java.util.List;

public interface UserDao {

    void add(User user);
    void delete(Long id);
    void edit(User user);
    List<User> listUsers();
    User getUserByUsername(String username);
    User getUserByID(Long id);
}

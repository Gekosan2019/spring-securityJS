package com.shundalov.spring.security.service;

import com.shundalov.spring.security.dao.UserDao;
import com.shundalov.spring.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    private void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    // Поскольку мы достаём пользователя,а список ролей у нас коллекция и она не грузится EAGER, то нужно поставить
    // аннотацию @Transactional - то есть, коллекция будет полностью выгружаться
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        user.setAuthorities();
        return user;
    }

}

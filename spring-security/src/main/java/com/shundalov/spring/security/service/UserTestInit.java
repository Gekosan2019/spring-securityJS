package com.shundalov.spring.security.service;

import com.shundalov.spring.security.model.Role;
import com.shundalov.spring.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Service
@Transactional
public class UserTestInit {

    private final RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    public UserTestInit(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        roleService.saveRole(new Role(1L, "ROLE_ADMIN"));
        roleService.saveRole(new Role(2L, "ROLE_USER"));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(1L));
        User admin = new User("Shamil", "Shundalov", "super.shomka@mail.ru", "alex2002"
        , 19L, roles);
        userService.add(admin);
    }

}

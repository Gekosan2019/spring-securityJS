package com.shundalov.spring.security.config;

import com.shundalov.spring.security.model.Role;
import com.shundalov.spring.security.model.User;
import com.shundalov.spring.security.service.RoleService;
import com.shundalov.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
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
        Set<Role> roles1 = new HashSet<>();
        Set<Role> roles2 = new HashSet<>();
        roles1.add(roleService.getRoleById(1L));
        roles1.add(roleService.getRoleById(2L));
        User admin = new User("Shamil", "Shundalov", "super.shomka@mail.ru", "alex2002"
        , 19L, roles1, null);
        roles2.add(roleService.getRoleById(2L));
        User user = new User("Seva", "Bulava", "el.primo@mail.ru", "seva"
                , 20L, roles2, null);
        userService.add(admin);
        userService.add(user);
    }

}

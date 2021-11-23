package com.shundalov.spring.security.controller;

import com.shundalov.spring.security.model.Role;
import com.shundalov.spring.security.model.User;
import com.shundalov.spring.security.service.RoleService;
import com.shundalov.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private void setRoleService(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String allUsers(Model model)  {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User admin = userService.getUserByUsername(((UserDetails)principal).getUsername());
        List<Role> roleSet = roleService.getRoles();
        model.addAttribute("roles", roleSet);
        model.addAttribute("user", admin);
        model.addAttribute("userNew", new User());
        List<User> userList = userService.listUsers();
        model.addAttribute("userList", userList);
        model.addAttribute("userService", userService);
        return "admin";
    }

    // Получаем значения из формы, там создаем объект и добавляем в бд
    @PostMapping("/admin")
    public String addInDB(@ModelAttribute("userNew") User user, @RequestParam("testOrder") Set<String> roleSet) {
        Set<Role> roleHashSet = new HashSet<>();
        for (String roleStr : roleSet) {
            Role role = roleService.getRoleByName(roleStr);
            roleHashSet.add(role);
        }
        user.setRoles(roleHashSet);
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}")
    public String update(@ModelAttribute("userNew") User user, @RequestParam("testOrderEdit") Set<String> roleSet) {
        Set<Role> roleHashSet = new HashSet<>();
        for (String roleStr : roleSet) {
            Role role = roleService.getRoleByName(roleStr);
            roleHashSet.add(role);
        }
        user.setRoles(roleHashSet);
        userService.edit(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

}

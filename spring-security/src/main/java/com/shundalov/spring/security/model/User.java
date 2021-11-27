package com.shundalov.spring.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

// Для того, чтобы в дальнейшим использовать класс User в Spring Security, он должен реализовывать интерфейс UserDetails.
// UserDetails можно представить, как адаптер между БД пользователей и тем что требуется Spring Security внутри SecurityContextHolder
// UserDetails - Предоставляет основную информацию о пользователе.
@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username; // уникальное значение

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Long age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name ="users_id"),
    inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles;

    @Transient
    Collection<? extends GrantedAuthority> authorities;

    public User() {

    }

    public User(String username, String surname,
                String email,
                String password, Long age, Set<Role> roles,Collection<? extends GrantedAuthority> authorities ) {
        this.username = username;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.roles = roles;
        this.authorities = authorities;
    }

    public StringBuilder getRolesInString() {
        StringBuilder strAllRoles = new StringBuilder("");
        for (Role role : roles) {
            StringBuilder str = new StringBuilder(role.getUsername());
            str.replace(0, 5, "");
            strAllRoles.append( str + " ");
        }
        return strAllRoles;
    }

    // Возвращает полномочия, предоставленные пользователю.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities() {
        authorities = mapRolesToAuthorities(roles);
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
    }

    // Указывает, истек ли срок действия учетной записи пользователя.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Указывает, заблокирован пользователь или разблокирован.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Указывает, истек ли срок действия учетных данных (пароля) пользователя.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Указывает, включен или отключен пользователь.
    @Override
    public boolean isEnabled() {
        return true;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getAuthority())).collect(Collectors.toList());
    }
}

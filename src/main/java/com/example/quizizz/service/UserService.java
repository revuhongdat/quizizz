package com.example.quizizz.service;

import com.example.quizizz.model.User;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void save(User user);

    Iterable<User> findAll();

    User findByUsername(String username);

    User getCurrentUser();

    Optional<User> findById(Long id);

    UserDetails loadUserById(Long id);

    boolean checkLogin(User user);

    boolean isRegister(User user);

    boolean isCorrectConfirmPassword(User user);
    Iterable<User> findUserByNameContains (String name);
    Iterable<User> findUsersByRoleName(int roleId, int status, boolean enable);
    Iterable<User> SortByCreationTime(int roleId, int status, boolean enable);
}

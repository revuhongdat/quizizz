package com.example.quizizz.service.impl;


import com.example.quizizz.DTO.ChangePasswordRequest;
import com.example.quizizz.Exception.Constant;
import com.example.quizizz.Exception.ResourceNotFoundException;
import com.example.quizizz.Exception.ResponseCode;
import com.example.quizizz.model.User;
import com.example.quizizz.model.UserPrinciple;
import com.example.quizizz.repository.UserRepository;
import com.example.quizizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        if (this.checkLogin(user)) {
            return UserPrinciple.build(user);
        }
        boolean enable = false;
        boolean accountNonExpired = false;
        boolean credentialsNonExpired = false;
        boolean accountNonLocked = false;
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), enable, accountNonExpired, credentialsNonExpired,
                accountNonLocked, null);
    }


    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public User getCurrentUser() {
        User user;
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        user = this.findByUsername(email);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NullPointerException();
        }
        return UserPrinciple.build(user.get());
    }

    @Override
    public boolean checkLogin(User user) {
        Iterable<User> users = this.findAll();
        boolean isCorrectUser = false;
        for (User currentUser : users) {
            if (currentUser.getUsername().
                    equals(user.getUsername()) && user.getPassword().equals(currentUser.getPassword())
                    && currentUser.isEnabled()) {
                isCorrectUser = true;
                break;
            }
        }
        return isCorrectUser;
    }

    @Override
    public boolean isRegister(User user) {
        boolean isRegister = false;
        Iterable<User> users = this.findAll();
        for (User currentUser : users) {
            if (user.getUsername().equals(currentUser.getUsername())) {
                isRegister = true;
                break;
            }
        }
        return isRegister;
    }

    @Override
    public boolean isCorrectConfirmPassword(User user) {
        return !user.getPassword().equals(user.getConfirmPassword());
    }

    @Override
    public Iterable<User> findUserByNameContains(String name) {
        return userRepository.findUserByNameContains(name);
    }

    @Override
    public Iterable<User> findUsersByRoleName(int roleId, int status, boolean enable) {
        return userRepository.findUsersByRoleName(roleId, status, enable);
    }

    @Override
    public Iterable<User> SortByCreationTime(int roleId, int status, boolean enable) {
        return userRepository.SortByCreationTime(roleId, status, enable);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) throws IllegalAccessException {
        var userPrinciple = (UserPrinciple) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var user = findByUsername(userPrinciple.getUsername());
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalAccessException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalAccessException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setConfirmPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public ResourceNotFoundException updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByUsername(email);

        user.setResetPasswordToken(token);
        userRepository.save(user);
        return new ResourceNotFoundException(ResponseCode.CODE_200, Constant.RESTPASWORD_SUCCESS, email);
    }

    @Override
    public void updatePassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}


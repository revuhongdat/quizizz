package com.example.quizizz.controller;

import com.example.quizizz.DTO.ChangePasswordRequest;
import com.example.quizizz.model.JwtResponse;
import com.example.quizizz.model.Role;
import com.example.quizizz.model.User;
import com.example.quizizz.service.RoleService;
import com.example.quizizz.service.UserService;
import com.example.quizizz.service.impl.EmailService;
import com.example.quizizz.service.impl.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin("*")
public class UserController {
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Iterable<User>> showAllUserByAdmin() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userService.findAll();
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(user.getUsername())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (userService.isCorrectConfirmPassword(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user.getRoles().iterator().next().getId() == 1) {
            Role role = roleService.findByName("ADMIN");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else if (user.getRoles().iterator().next().getId() == 2) {
            Role role = roleService.findByName("TEACHER");
            Set<Role> roles = new HashSet<>();
            user.setStatus(2);
            user.setEnabled(false);
            roles.add(role);
            user.setRoles(roles);
        } else if (user.getRoles().iterator().next().getId() == 3) {
            Role role = roleService.findByName("STUDENT");
            Set<Role> roles = new HashSet<>();
            user.setStatus(1);
            roles.add(role);
            user.setRoles(roles);
        }
//        user.setImage();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                currentUser.getId(),
                userDetails.getUsername(),
                currentUser.getName(),
                currentUser.getImage(),
                userDetails.getAuthorities()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = this.userService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userOptional.get().setId(user.getId());
        userOptional.get().setName(user.getName());
        userOptional.get().setImage(user.getImage());
        userOptional.get().setRoles(user.getRoles());
        userService.save(userOptional.get());
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/admin/teachers/active")
    public ResponseEntity<Iterable<User>> showAllTeacherActiveByAdmin() {
        Iterable<User> users = userService.findUsersByRoleName(2, 1, true);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/teachers/pending")
    public ResponseEntity<Iterable<User>> showAllTeacherPendingByAdmin() {
        Iterable<User> users = userService.findUsersByRoleName(2, 2, false);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/teachers/active/search/{name}")
    public ResponseEntity<Iterable<User>> searchTeacherActiveByName(@PathVariable String name) {
        Iterable<User> users = userService.findUsersByRoleName(2, 1, true);

        return this.getIterableResponseEntity(name, users);
    }

    @GetMapping("/admin/teachers/pending/search/{name}")
    public ResponseEntity<Iterable<User>> searchTeacherPendingByName(@PathVariable String name) {
        Iterable<User> users = userService.findUsersByRoleName(2, 2, false);

        return this.getIterableResponseEntity(name, users);
    }

    @GetMapping("/admin/teachers/active/searchUsername/{username}")
    public ResponseEntity<Iterable<User>> searchTeacherActiveByUsername(@PathVariable String username) {
        Iterable<User> users = userService.findUsersByRoleName(2, 1, true);
        List<User> filteredUsername = new ArrayList<>();

        for (User user : users) {
            if (user.getUsername().contains(username)) {
                filteredUsername.add(user);
            }
        }

        return new ResponseEntity<>(filteredUsername, HttpStatus.OK);
    }

    @GetMapping("/admin/teachers/pending/searchUsername/{username}")
    public ResponseEntity<Iterable<User>> searchTeacherPendingByUsername(@PathVariable String username) {
        Iterable<User> users = userService.findUsersByRoleName(2, 2, false);

        List<User> filteredUsername = new ArrayList<>();

        for (User user : users) {
            if (user.getUsername().contains(username)) {
                filteredUsername.add(user);
            }
        }

        return new ResponseEntity<>(filteredUsername, HttpStatus.OK);
    }

    @GetMapping("/admin/teachers/active/sort")
    public ResponseEntity<Iterable<User>> sortTeacherActiveByAdmin() {
        Iterable<User> users = userService.SortByCreationTime(2, 1, true);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/teachers/pending/sort")
    public ResponseEntity<Iterable<User>> sortTeacherPendingByAdmin() {
        Iterable<User> users = userService.SortByCreationTime(2, 2, false);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/students")
    public ResponseEntity<Iterable<User>> showAllStudentByAdmin() {
        Iterable<User> users = this.userService.findUsersByRoleName(3, 1, true);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/students/search/{name}")
    public ResponseEntity<Iterable<User>> searchStudentByName(@PathVariable String name) {
        Iterable<User> users = userService.findUsersByRoleName(3, 1, true);
        return this.getIterableResponseEntity(name, users);
    }

    @GetMapping("/admin/students/searchUsername/{username}")
    public ResponseEntity<Iterable<User>> searchStudentByUsername(@PathVariable String username) {
        Iterable<User> users = userService.findUsersByRoleName(3, 1, true);
        List<User> filteredUsername = new ArrayList<>();

        for (User user : users) {
            if (user.getUsername().contains(username)) {
                filteredUsername.add(user);
            }
        }

        return new ResponseEntity<>(filteredUsername, HttpStatus.OK);
    }

    @GetMapping("/admin/students/sort")
    public ResponseEntity<Iterable<User>> sortStudentByAdmin() {
        Iterable<User> users = userService.SortByCreationTime(3, 1, true);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/admin/teachers/{id}")
    public ResponseEntity<User> approveTeacherUser(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userOptional.get().setStatus(1);
        userOptional.get().setEnabled(true);
        userService.save(userOptional.get());
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userOptional.get().setEnabled(false);
        userService.save(userOptional.get());
        emailService.sendEmailLockUser(userOptional.get().getUsername());
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/users/changePassword")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) throws IllegalAccessException {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.accepted().build();

    }

    private ResponseEntity<Iterable<User>> getIterableResponseEntity(@PathVariable String name, Iterable<User> users) {
        List<User> filteredUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getName().contains(name)) {
                filteredUsers.add(user);
            }
        }

        return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
    }

    @GetMapping("/users/fbu/{email}")
    public ResponseEntity<?> findByUsername(@PathVariable String email) {
        User user = userService.findByUsername(email);
        if (user != null) {
            return new ResponseEntity<>("OK",HttpStatus.OK);
        }
        return new ResponseEntity<>("NOT FOUND",HttpStatus.NOT_FOUND);
    }
}
package com.example.quizizz.controller;

import com.example.quizizz.model.User;
import com.example.quizizz.service.UserService;
import com.example.quizizz.service.impl.EmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class EmailController {
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/forgot")
    public ResponseEntity<?> requestPasswordReset(@RequestBody String email) {
        User user = userService.findByUsername(email);
        if (user == null) {
            return new ResponseEntity<>("Tài khoản không tồn tại", HttpStatus.NOT_FOUND);
        }
        String newPassword = RandomStringUtils.randomAlphanumeric(10);
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
        String emailContent = "Mật khẩu mới của bạn là: " + newPassword;
        emailService.sendEmail(email, "Email cấp lại mật khẩu", emailContent);
        return new ResponseEntity<>("Email đã được gửi", HttpStatus.OK);
    }
}

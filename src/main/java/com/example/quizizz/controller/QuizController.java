package com.example.quizizz.controller;

import com.example.quizizz.model.Quiz;
import com.example.quizizz.service.QuizService;
import com.example.quizizz.service.RoleService;
import com.example.quizizz.service.UserService;
import com.example.quizizz.service.impl.EmailService;
import com.example.quizizz.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/quizzes")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
       this.quizService =quizService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Quiz>> showAllQuiz() {
        Iterable<Quiz> quizzes = quizService.findAll();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> findQuizById(@PathVariable Long id){
        Optional<Quiz> quizOptional = quizService.findById(id);
        return quizOptional.map(quiz -> new ResponseEntity<>(quiz, HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}

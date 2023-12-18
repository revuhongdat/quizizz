package com.example.quizizz.controller;

import com.example.quizizz.model.LevelQuestion;
import com.example.quizizz.model.LevelQuiz;
import com.example.quizizz.service.LevelQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/levelQuizzes")
public class LevelQuizController {
    final
    LevelQuizService levelQuizService;

    @Autowired
    public LevelQuizController(LevelQuizService levelQuizService) {
        this.levelQuizService = levelQuizService;
    }
    @GetMapping()
    public ResponseEntity<?> findAllLevelQuiz () {
        Iterable<LevelQuiz> levelQuizzes = levelQuizService.findAll();
        return new ResponseEntity<>(levelQuizzes, HttpStatus.OK);
    }
}

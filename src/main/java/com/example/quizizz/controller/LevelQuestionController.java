package com.example.quizizz.controller;

import com.example.quizizz.model.LevelQuestion;
import com.example.quizizz.service.LevelQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/levelQuestions")
public class LevelQuestionController {
    final LevelQuestionService levelQuestionService;

    @Autowired
    public LevelQuestionController(LevelQuestionService levelQuestionService) {
        this.levelQuestionService = levelQuestionService;
    }

    @GetMapping()
    public ResponseEntity<?> findAllLevelQuestions () {
        Iterable<LevelQuestion> levelQuestions = levelQuestionService.findAll();
        return new ResponseEntity<>(levelQuestions, HttpStatus.OK);
    }
}

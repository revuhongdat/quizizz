package com.example.quizizz.controller;

import com.example.quizizz.model.Answer;
import com.example.quizizz.service.impl.AnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/answers")
public class AnswerController {
    final
    AnswerServiceImpl answerService;

    @Autowired
    public AnswerController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<Iterable<?>> findAll () {
        return new ResponseEntity<>(answerService.findAll(), HttpStatus.OK);
    }
}


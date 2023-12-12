package com.example.quizizz.controller;

import com.example.quizizz.model.TypeQuestion;
import com.example.quizizz.service.TypeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/typeQuestions")
public class TypeQuestionController {
    final TypeQuestionService typeQuestionService;

    @Autowired
    public TypeQuestionController(TypeQuestionService typeQuestionService) {
        this.typeQuestionService = typeQuestionService;
    }

    @GetMapping()
    public ResponseEntity<?> findAllTypeQuestions () {
        Iterable<TypeQuestion> typeQuestions = typeQuestionService.findAll();
        return new ResponseEntity<>(typeQuestions, HttpStatus.OK);
    }
}

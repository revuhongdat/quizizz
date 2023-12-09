package com.example.quizizz.controller;

import com.example.quizizz.model.Question;
import com.example.quizizz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/questions")
public class QuestionController {
    final
    QuestionService questionService;
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping
    public ResponseEntity<?> findAllQuestion() {
        Iterable<Question> questions = questionService.findAll();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Question> question = questionService.findById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createQuestion(Question question) {
        Question savedQuestion = questionService.save(question);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, Question question) {
        Optional<Question> questionById = questionService.findById(id);
        if (questionById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            questionById.get().setCategoryQuestion(question.getCategoryQuestion());
            questionById.get().setTypeQuestion(question.getTypeQuestion());
            questionById.get().setLevelQuestion(question.getLevelQuestion());
            questionById.get().setContent(question.getContent());
            Question updateQuestion = questionService.save(questionById.get());
            return new ResponseEntity<>(updateQuestion, HttpStatus.OK);
        }
    }
}

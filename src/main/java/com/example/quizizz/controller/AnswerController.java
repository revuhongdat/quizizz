package com.example.quizizz.controller;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import com.example.quizizz.service.QuestionService;
import com.example.quizizz.service.impl.AnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/answers")
public class AnswerController {
    final AnswerServiceImpl answerService;
    final QuestionService questionService;
    @Autowired
    public AnswerController(AnswerServiceImpl answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }
    @GetMapping
    public ResponseEntity<Iterable<Answer>> findAllAnswer() {
        Iterable<Answer> answers = answerService.findAll();
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Answer>> findById(@PathVariable Long id) {
        Optional<Answer> answer = answerService.findById(id);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createAnswer (@RequestBody Answer answer) {
        Answer savedAnswer = answerService.save(answer);
        return new ResponseEntity<>(savedAnswer, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long id, Answer answer) {
        Optional<Answer> answerOptional = answerService.findById(id);
        if (answerOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            answerOptional.get().setContent(answer.getContent());
            answerOptional.get().setImg(answer.getImg());
            answerOptional.get().setStatus(answer.getStatus());
        }
        return new ResponseEntity<>(answerOptional, HttpStatus.OK);
    }

    @GetMapping("/fbq/{id}")
    public ResponseEntity<Iterable<Answer>> findAllAnswerByQuestionId(@PathVariable Long id) {
        Optional<Question> question = questionService.findById(id);
        if (question.isEmpty()) {
            return new ResponseEntity<>(question.get().getAnswers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteAllByContentIsEmpty() {
        answerService.deleteByContent("");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}


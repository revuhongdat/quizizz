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

    public AnswerController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
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
    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable Long id) {
        answerService.delete(id);
    }
}

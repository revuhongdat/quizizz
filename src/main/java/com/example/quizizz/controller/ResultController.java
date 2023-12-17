package com.example.quizizz.controller;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import com.example.quizizz.model.Result;
import com.example.quizizz.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/results")
public class ResultController {
    final
    ResultService resultService;
    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Result> results = (List<Result>) resultService.findAll();
        if (results.isEmpty()) {
            return new ResponseEntity<>("Không có", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    @GetMapping("/fbq/{id}")
    public ResponseEntity<?> findAllByQuizId(@PathVariable Long id) {
        List<Result> results = (List<Result>) resultService.findAllByQuizId(id);
        if (results.isEmpty()) {
            return new ResponseEntity<>("Không có", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
//    @PostMapping
//    public ResponseEntity<?> createResult(@RequestBody Result result) {
//        Set<Answer> answers = result.getAnswers();
//        Set<Question> questions = result.getQuiz().getQuestions();
//        int numberTrue = 0;
//        double totalScore = 0;
//        for(Answer chooseAnswer: answers) {
//            if (chooseAnswer.getStatus() == 1) {
//                for (Question question : questions) {
//                    for (Answer answer : question.getAnswers()) {
//                        if (answer.getId().equals(chooseAnswer.getId())) {
//                            if (1 == question.getTypeQuestion().getId() ||  2 == question.getTypeQuestion().getId()) {
//                                numberTrue += 1;
//                            }
//                            for (Answer answerOfQuestion : question.getAnswers()) {
//                                if (answerOfQuestion.getId() == )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return new ResponseEntity<>(resultService.save(result), HttpStatus.CREATED);
//    }
}
package com.example.quizizz.controller;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import com.example.quizizz.model.Quiz;
import com.example.quizizz.model.Result;
import com.example.quizizz.service.AnswerService;
import com.example.quizizz.service.QuestionService;
import com.example.quizizz.service.QuizService;
import com.example.quizizz.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/results")
public class ResultController {
    final
    ResultService resultService;
    final
    AnswerService answerService;
    final
    QuizService quizService;

    @Autowired
    public ResultController(ResultService resultService, AnswerService answerService, QuizService quizService) {
        this.resultService = resultService;
        this.answerService = answerService;
        this.quizService = quizService;
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
    @PostMapping
    public ResponseEntity<?> createResult(@RequestBody Result result) {
        Set<Answer> answerSet = result.getAnswers();
        Set<Answer> answers = new HashSet<>();
        for (Answer answer : answerSet) {
            answers.add(answerService.findById(answer.getId()).get());
        }
        result.setAnswers(answers);
        result.setQuiz(quizService.findById(result.getQuiz().getId()).get());
        Set<Question> questions = result.getQuiz().getQuestions();
        int numberTrue = 0;
        int totalScore = 0;
        for (Question question: questions) {
            Set<Answer> answerSet1 = question.getAnswers();
            if (question.getTypeQuestion().getId() == 1 || question.getTypeQuestion().getId() == 2) {
                for (Answer answer: answers) {
                    for (Answer answer1: answerSet1) {
                        if (answer.getId().equals(answer1.getId()) && answer.getStatus() == 1) {
                            numberTrue += 1;
                            break;
                        }
                    }
                }
            } else {
                List<Answer> listAnswerTrue = new ArrayList<>();
                for (Answer answer: answerSet1) {
                    if (answer.getStatus() == 1) {
                        listAnswerTrue.add(answer);
                    }
                }
                int numberAnswerTrue = listAnswerTrue.size();
                int numberAnswer = 0;
                for (Answer answer: answers) {
                    if (question.getId().equals(answerService.findQuestionByAnswerId(answer.getId()).get().getId())) {
                        numberAnswer += 1;
                    }
                }
                if (numberAnswer == numberAnswerTrue) {
                    for (Answer answer: answers) {
                        for (Answer answer2: listAnswerTrue) {
                            if (answer.getId().equals(answer2.getId())) {
                                numberAnswer -= 1;
                            }
                        }
                    }
                }
                if ( numberAnswer == 0) {
                    numberTrue += 1;
                }
            }
        }
        result.setNumberTrue(numberTrue);
        totalScore = numberTrue * 100 / questions.size();
        result.setTotalScore(totalScore);
        return new ResponseEntity<>(resultService.save(result), HttpStatus.CREATED);
    }
}
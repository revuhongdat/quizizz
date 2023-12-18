package com.example.quizizz.controller;

import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import com.example.quizizz.model.Quiz;
import com.example.quizizz.model.Result;
import com.example.quizizz.service.AnswerService;
import com.example.quizizz.service.QuizService;
import com.example.quizizz.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/results")
public class ResultController {
    final
    ResultService resultService;
    final
    QuizService quizService;
    final
    AnswerService answerService;

    @Autowired
    public ResultController(ResultService resultService, QuizService quizService, AnswerService answerService) {
        this.resultService = resultService;
        this.quizService = quizService;
        this.answerService = answerService;
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
    @PostMapping("/hung")
    public ResponseEntity<?> createResult(@RequestBody Result result) {
        Set<Answer> answers = result.getAnswers();
        Optional<Quiz> quiz = quizService.findById(result.getQuiz().getId());
        Set<Question> questions = quiz.get().getQuestions();
        double count = 0;
        double numberTrue = 0;
        double totalScore = 0;
        double totalAnswerTure = 0;
        double avg = 0.0;
        for (Question question1 : questions){
            if (question1.getTypeQuestion().getId() == 3) {
                for (Answer answer2 : question1.getAnswers()) {
                    int numberAnswer = question1.getAnswers().size();
                    if (answer2.getStatus() == 1) {
                        totalAnswerTure += 1;
                    }
                    avg = totalAnswerTure / numberAnswer;
                }
            }
        }
        for (Answer chooseAnswer : answers) {
            Optional<Answer> answer1 = answerService.findById(chooseAnswer.getId());
            if (answer1.get().getStatus() == 1) {
                for (Question question : questions) {
                    for (Answer answer : question.getAnswers()) {
                        if (answer.getId().equals(answer1.get().getId())) {
                            if (1 == question.getTypeQuestion().getId() || 2 == question.getTypeQuestion().getId()) {
                                numberTrue += 1;
                            } else {
                                outerLoop:
                                for (Answer answer2 : question.getAnswers()) {
                                    if (answer1.get().getId() == answer2.getId()) {
                                        count += avg;
                                    }
                                }
                            }
                        }

                    }
                }
            }else {
                count -= avg;
                if (count < 0){
                    count = 0;
                }
            }
        }
        numberTrue += count;
        totalScore = (double) ((Math.ceil((double) 100 / questions.size())) * numberTrue);
        result.setNumberTrue(numberTrue);
        result.setTotalScore(totalScore);

        return new ResponseEntity<>(resultService.save(result), HttpStatus.CREATED);
    }
}
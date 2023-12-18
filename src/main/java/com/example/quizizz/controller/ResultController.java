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

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
//        if (results.isEmpty()) {
//            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createResultV2(@RequestBody Result result) {
        Set<Answer> answerSet = result.getAnswers();
        Set<Answer> answers = new HashSet<>();
        for (Answer answer: answerSet) {
            answers.add(answerService.findById(answer.getId()).get());
        }
        Optional<Quiz> quiz = quizService.findById(result.getQuiz().getId());
        Set<Question> questions = quiz.get().getQuestions();
        double numberTrue = 0;
        for (Question question1 : questions) {
            double avg = 0;
            double numberTrueTypeMulti = 0;
            double numberTrueTypeOne = 0;
            double numberTrue1 = 0;
            if (question1.getTypeQuestion().getId() == 3) {
                double totalAnswerTure = 0;
                for (Answer answer2 : question1.getAnswers()) {
                    if (answer2.getStatus() == 1) {
                        totalAnswerTure += 1;
                    }
                    avg = 1 / totalAnswerTure;
                }

               loop: for (Answer answer : answers) {
                    for (Answer answer2 : question1.getAnswers()) {
                        if (answer.getId().equals(answer2.getId())) {
                            if (answer.getStatus() == 1) {
                                numberTrue1 += avg;
                            } else {
                                numberTrue1 = 0;
                                break loop;
                            }
                        }

                    }
                }
                numberTrueTypeMulti += numberTrue1;
            }
            if (question1.getTypeQuestion().getId() == 1 || question1.getTypeQuestion().getId() == 2) {
                for (Answer answer: answers) {
                    for (Answer answer1: question1.getAnswers()) {
                        if (answer.getStatus() == 1 && answer.getId().equals(answer1.getId())) {
                            numberTrueTypeOne += 1;
                        }
                    }
                }
            }
             numberTrue += numberTrueTypeMulti + numberTrueTypeOne;
        }
        double totalScore = numberTrue * 100 / questions.size();
        result.setTotalScore(totalScore);
        result.setNumberTrue(numberTrue);
        return new ResponseEntity<>(resultService.save(result), HttpStatus.CREATED);
    }
    @GetMapping("/fbuaqan")
    public ResponseEntity<?> findByUserIdAndQuizIdNewest(@RequestParam(name = "idUser") Long idUser, @RequestParam(name = "idQuiz") Long idQuiz) {
        return new ResponseEntity<>(resultService.findByUserIdAndQuizIdAndNewest(idUser, idQuiz), HttpStatus.OK);
    }
    @GetMapping("/fbu/{idUser}")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long idUser) {
        List<Result> results = (List<Result>) resultService.findAllByUserId(idUser);
        if (results.isEmpty()) {
            return new ResponseEntity<>("Không có", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    @GetMapping("/fbuaq")
    public ResponseEntity<?> findAllByUserIdAndQuizId(@RequestParam(name = "idUser") Long idUser, @RequestParam(name = "idQuiz") Long idQuiz) {
        return new ResponseEntity<>(resultService.findAllByUserIdAndQuizId(idUser, idQuiz), HttpStatus.OK);
    }
}
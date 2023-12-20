package com.example.quizizz.controller;

import com.example.quizizz.model.Quiz;
import com.example.quizizz.model.Result;
import com.example.quizizz.service.QuizService;
import com.example.quizizz.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/quizzes")
public class QuizController {
    private final QuizService quizService;
    private final ResultService resultService;

    @Autowired
    public QuizController(QuizService quizService, ResultService resultService) {
        this.quizService = quizService;
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Quiz>> showAllQuiz() {
        Iterable<Quiz> quizzes = quizService.findAll();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> findQuizById(@PathVariable Long id) {
        Optional<Quiz> quizOptional = quizService.findById(id);
        return quizOptional.map(quiz -> new ResponseEntity<>(quiz, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createQuiz(@RequestBody Quiz quiz) {
        Iterable<Quiz> quizzes = quizService.findAll();
        for (Quiz q : quizzes) {
            if (q.getTitle().equals(quiz.getTitle())) {
                return new ResponseEntity<>("Trùng tên quiz", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(quizService.save(quiz), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        Optional<Quiz> quizOptional = quizService.findById(id);
        if (quizOptional.isPresent()) {
            Iterable<Result> results = resultService.findAll();
            for (Result result : results) {
                if (result.getUser().getId().equals(quizOptional.get().getId())) {
                    return new ResponseEntity<>("Bài thi đã có người làm", HttpStatus.BAD_REQUEST);
                }
            }
            Iterable<Quiz> quizzes = quizService.findAll();
            for (Quiz q : quizzes) {
                if (Objects.equals(q.getId(), quiz.getId())) {
                    break;
                }
                if (q.getTitle().equals(quiz.getTitle())) {
                    return new ResponseEntity<>("Trùng tên quiz", HttpStatus.BAD_REQUEST);
                }
            }
            quiz.setId(id);
            return new ResponseEntity<>(quizService.save(quiz), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("K tìm thấy quiz", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/fbu/{id}")
    public ResponseEntity<?> findAllByUser(@PathVariable Long id) {
        List<Quiz> quizzes = (List<Quiz>) quizService.findAllByUserId(id);
//        if (quizzes.isEmpty()) {
//            return new ResponseEntity<>("Không tìm thấy", HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }
    @GetMapping("/fbcq/{id}")
    public ResponseEntity<?> findAllByCategoryQuizMostResult(@PathVariable Long id) {
        List<Quiz> quizzes = quizService.findAllByCategoryQuizMostResult(id);
        if (quizzes.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }
    @GetMapping("/hot")
    public ResponseEntity<?> findHotQuizEachCategoryQuiz() {
        List<Quiz> quizzes = quizService.findQuizWithMostResultsInEachCategory();
        if (quizzes.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }
    @GetMapping("/fbn/{name}")
    public ResponseEntity<?> findAllByTitleContaining(@PathVariable String name) {
        List<Quiz> quizzes = (List<Quiz>) quizService.findAllByTitleContaining(name);
        if (quizzes.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }
}
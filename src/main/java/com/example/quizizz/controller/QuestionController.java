package com.example.quizizz.controller;

import com.example.quizizz.model.CategoryQuestion;
import com.example.quizizz.model.Question;
import com.example.quizizz.model.Quiz;
import com.example.quizizz.model.User;
import com.example.quizizz.service.QuestionService;
import com.example.quizizz.service.QuizService;
import com.example.quizizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/questions")
public class QuestionController {
    final QuestionService questionService;
    final QuizService quizService;
    private final UserService userService;

    @Autowired
    public QuestionController(QuestionService questionService, QuizService quizService, UserService userService) {
        this.questionService = questionService;
        this.quizService = quizService;
        this.userService = userService;
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
    public ResponseEntity<?> createQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.save(question);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, Question question) {
        Optional<Question> questionById = questionService.findById(id);
        if (questionById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            question.setCategoryQuestion(question.getCategoryQuestion() == null ? questionById.get().getCategoryQuestion() : question.getCategoryQuestion());
            question.setTypeQuestion(question.getTypeQuestion() == null ? questionById.get().getTypeQuestion() : question.getTypeQuestion());
            question.setLevelQuestion(question.getLevelQuestion() == null ? questionById.get().getLevelQuestion() : question.getLevelQuestion());
            question.setContent(question.getContent() == null ? questionById.get().getContent() : question.getContent());
            Question updateQuestion = questionService.save(questionById.get());
            return new ResponseEntity<>(updateQuestion, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable Long id) {
        // Check if the user is authenticated
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Or handle as per your authentication logic
        }

        Optional<Question> questionOptional = this.questionService.findById(id);
        if (questionOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (Objects.equals(currentUser.getId(), questionOptional.get().getUser().getId())) {
            if (questionOptional.get().getQuiz() == null) {
                questionService.delete(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Iterable<Question>> findByCategoryQuestion(@PathVariable Long categoryId) {
        Iterable<Question> questions = questionService.findAllByCategoryQuestion_Id(categoryId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<Iterable<Question>> findByQuiz(@PathVariable Long quizId) {
        Iterable<Question> questions = questionService.findAllByQuizId(quizId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @GetMapping("/content/{content}")
    public ResponseEntity<Iterable<Question>> findByQuiz(@PathVariable String content) {
        Iterable<Question> questions = questionService.findAllByContentContains(content);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<Question>> findQuestionByUser(@PathVariable Long userId) {
        Iterable<Question> questions = questionService.findAllByUser_Id(userId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}

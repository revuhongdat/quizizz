package com.example.quizizz.controller;

import com.example.quizizz.DTO.QuestionDTO;
import com.example.quizizz.model.Answer;
import com.example.quizizz.model.Question;
import com.example.quizizz.model.Quiz;
import com.example.quizizz.model.User;
import com.example.quizizz.service.AnswerService;
import com.example.quizizz.service.QuestionService;
import com.example.quizizz.service.QuizService;
import com.example.quizizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private final AnswerService answerService;

    @Autowired
    public QuestionController(QuestionService questionService, QuizService quizService, UserService userService, AnswerService answerService) {
        this.questionService = questionService;
        this.quizService = quizService;
        this.userService = userService;
        this.answerService = answerService;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id,@RequestBody QuestionDTO questionDTO) {
        Question question = questionDTO.getQuestion();
        Optional<Question> questionById = questionService.findById(id);
        if (questionById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        questionById.get().setLevelQuestion(question.getLevelQuestion());
        questionById.get().setTypeQuestion(question.getTypeQuestion());
        questionById.get().setCategoryQuestion(question.getCategoryQuestion());
        questionById.get().setContent(question.getContent());
        questionById.get().setStatus(question.getStatus());
        Set<Answer> answerSet = questionDTO.getAnswers();
        for (Answer item : answerSet) {
            answerService.save(item);
        }
        return new ResponseEntity<>(questionService.save(questionById.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable Long id) {
        Optional<Question> questionOptional = questionService.findById(id);
        if (questionOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Answer> answers = questionOptional.get().getAnswers();
        Iterable<Quiz> quizzes = quizService.findAll();
        for (Quiz quiz : quizzes) {
            for (Question question : quiz.getQuestions()) {
                if (question.getId().equals(questionOptional.get().getId())) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        questionService.delete(id);
        for (Answer answer : answers) {
            answerService.delete(answer.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<Iterable<Question>> findByContent(@PathVariable String content) {
        Iterable<Question> questions = questionService.findAllByContentContains(content);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<Question>> findQuestionByUser(@PathVariable Long userId) {
        Iterable<Question> questions = questionService.findAllByUser_Id(userId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO questionDTO) {
        Set<Answer> answerSet = questionDTO.getAnswers();
        for (Answer item : answerSet) {
            questionDTO.getQuestion().getAnswers().add(answerService.save(item));
        }
        return new ResponseEntity<>(questionService.save(questionDTO.getQuestion()), HttpStatus.CREATED);
    }
    @GetMapping("/fba/{idAnswer}")
    public ResponseEntity<?> findQuestionByAnswerId(@PathVariable Long idAnswer) {
        Optional<Question> questionOptional = answerService.findQuestionByAnswerId(idAnswer);
        return new ResponseEntity<>(questionOptional, HttpStatus.OK);
    }
}

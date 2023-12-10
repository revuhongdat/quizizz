package com.example.quizizz.controller;

import com.example.quizizz.model.CategoryQuestion;
import com.example.quizizz.model.Question;
import com.example.quizizz.model.Quiz;
import com.example.quizizz.service.QuestionService;
import com.example.quizizz.service.QuizService;
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

    @Autowired
    public QuestionController(QuestionService questionService, QuizService quizService) {
        this.questionService = questionService;
        this.quizService = quizService;
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
        Optional<Question> questionOptional = this.questionService.findById(id);
        if (questionOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Quiz> quizzes = quizService.findAll();
        for (Quiz quiz : quizzes) {
            Set<Question> quizQuestions = quiz.getQuestions();
            for (Question quizQuestion : quizQuestions) {
                if (Objects.equals(quizQuestion.getId(), questionOptional.get().getId())) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        questionService.delete(id);
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
}

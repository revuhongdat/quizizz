package com.example.quizizz.controller;

import com.example.quizizz.model.CategoryQuestion;
import com.example.quizizz.model.CategoryQuestion;
import com.example.quizizz.model.Question;
import com.example.quizizz.service.CategoryQuestService;
import com.example.quizizz.service.CategoryQuizService;
import com.example.quizizz.service.QuestionService;
import com.example.quizizz.service.impl.CategoryQuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin("*")
@RequestMapping("/categoryQuestion")
public class CategoryQuestionController {

    private final CategoryQuestService categoryQuestionService;
    private final QuestionService questionService;

    @Autowired
    public CategoryQuestionController(CategoryQuestService categoryQuestionService, QuestionService questionService) {
        this.categoryQuestionService = categoryQuestionService;
        this.questionService = questionService;
    }


    @GetMapping
    public ResponseEntity<Iterable<CategoryQuestion>> showAllCateQuiz() {
        Iterable<CategoryQuestion>
                categoryQuestions = categoryQuestionService.findAll();
        return new ResponseEntity<>(categoryQuestions, HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<Iterable<CategoryQuestion>> sortAllCateQuiz() {
        Iterable<CategoryQuestion> categoryQuestions = categoryQuestionService.findAll();

        Iterable<CategoryQuestion> sortedCategories = StreamSupport.stream(categoryQuestions.spliterator(), false)
                .sorted(Comparator.comparing(CategoryQuestion::getName))
                .collect(Collectors.toList());

        return new ResponseEntity<>(sortedCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryQuestion> findCateQuizById(@PathVariable Long id) {
        Optional<CategoryQuestion> categoryQuestion = categoryQuestionService.findById(id);
        return categoryQuestion.map(categoryQuestion1 -> new ResponseEntity<>(categoryQuestion1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CategoryQuestion> createCateQuiz(@RequestBody CategoryQuestion categoryQuestion, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<CategoryQuestion> categoryQuestions = categoryQuestionService.findAll();
        for (CategoryQuestion categoryQuestion1 : categoryQuestions) {
            if (categoryQuestion1.getName().equals(categoryQuestion.getName().toUpperCase())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        categoryQuestion.setName(categoryQuestion.getName().replaceFirst("^.", String.valueOf(categoryQuestion.getName().charAt(0)).toUpperCase()));
        categoryQuestion.setUser(categoryQuestion.getUser());
        categoryQuestionService.save(categoryQuestion);
        return new ResponseEntity<>(categoryQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryQuestion> updateCategoryQuiz(@PathVariable Long id, @RequestBody CategoryQuestion categoryQuestion) {
        Optional<CategoryQuestion> categoryQuizOptional = this.categoryQuestionService.findById(id);
        if (categoryQuizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (categoryQuizOptional.get().getUser() == null || !categoryQuizOptional.get().getUser().equals(categoryQuestion.getUser())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (categoryQuestion.getName() != null) {
            categoryQuizOptional.get().setName(categoryQuestion.getName());
        }
        if (categoryQuestion.getUser() != null) {
            categoryQuizOptional.get().setUser(categoryQuestion.getUser());
        }
        return new ResponseEntity<>(categoryQuizOptional.get(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryQuestion> deleteCategoryQuestion (@PathVariable Long id) {
        Optional<CategoryQuestion> categoryQuizOptional = this.categoryQuestionService.findById(id);
        if (categoryQuizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Question> questions = questionService.findAll();
        for (Question question : questions) {
            if (Objects.equals(question.getCategoryQuestion().getId(), categoryQuizOptional.get().getId())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        categoryQuestionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

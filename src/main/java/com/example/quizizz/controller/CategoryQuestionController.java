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
            if (categoryQuestion1.getName().equalsIgnoreCase(categoryQuestion.getName().toUpperCase())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        String name = capitalizeWordsKeepHtmlTags(categoryQuestion.getName());
        categoryQuestion.setUser(categoryQuestion.getUser());
        categoryQuestion.setName(name);
        categoryQuestionService.save(categoryQuestion);
        return new ResponseEntity<>(categoryQuestion, HttpStatus.CREATED);
    }

    private String capitalizeWordsKeepHtmlTags(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Tách chuỗi thành các phần với các thẻ HTML
        String[] parts = input.split("(?=<)|(?<=>)");

        StringBuilder result = new StringBuilder();
        boolean insideTag = false;

        for (String part : parts) {
            if (part.startsWith("<")) {
                // Nếu là thẻ HTML, không viết hoa chữ cái đầu
                result.append(part);
                if (!part.endsWith(">")) {
                    insideTag = true;
                }
            } else {
                // Nếu là văn bản bên trong thẻ, viết hoa chữ cái đầu của mỗi từ
                String[] words = part.split("\\s+");
                StringBuilder capitalizedText = new StringBuilder();
                for (String word : words) {
                    String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                    capitalizedText.append(capitalizedWord).append(" ");
                }
                result.append(capitalizedText.toString().trim());
                insideTag = false;
            }
        }
        return result.toString();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryQuestion> updateCategoryQuiz(@PathVariable Long id, @RequestBody CategoryQuestion categoryQuestion) {
        Optional<CategoryQuestion> categoryQuizOptional = this.categoryQuestionService.findById(id);
        if (categoryQuizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<CategoryQuestion> categoryQuestions = categoryQuestionService.findAll();
        for (CategoryQuestion categoryQuestion1 : categoryQuestions) {
            if (categoryQuestion1.getName().equalsIgnoreCase(categoryQuestion.getName().toUpperCase())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        String name = capitalizeWordsKeepHtmlTags(categoryQuestion.getName());
        categoryQuestion.setUser(categoryQuestion.getUser());
        categoryQuestion.setName(name);
        categoryQuestion.setId(id);
        categoryQuestionService.save(categoryQuestion);
        return new ResponseEntity<>(categoryQuestion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryQuestion> deleteCategoryQuestion(@PathVariable Long id) {
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

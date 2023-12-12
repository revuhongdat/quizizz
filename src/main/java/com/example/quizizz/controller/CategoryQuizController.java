package com.example.quizizz.controller;

import com.example.quizizz.model.CategoryQuiz;
import com.example.quizizz.model.Quiz;
import com.example.quizizz.service.CategoryQuizService;
import com.example.quizizz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/categoryQuizzes")
public class CategoryQuizController {

    @Autowired
    CategoryQuizService categoryQuizService;
    @Autowired
    QuizService quizService;

    @GetMapping
    public ResponseEntity<Iterable<CategoryQuiz>> showAllCateQuiz() {
        Iterable<CategoryQuiz> categoryQuizzes = categoryQuizService.findAll();
        return new ResponseEntity<>(categoryQuizzes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryQuiz> findCateQuizById(@PathVariable Long id) {
        Optional<CategoryQuiz> categoryQuizOptional = categoryQuizService.findById(id);
        return categoryQuizOptional.map(categoryQuiz -> new ResponseEntity<>(categoryQuiz, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CategoryQuiz> createCateQuiz(@RequestBody CategoryQuiz categoryQuiz, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<CategoryQuiz> categoryQuizzes = categoryQuizService.findAll();
        for (CategoryQuiz currentCateQuiz : categoryQuizzes) {
            if (currentCateQuiz.getName().equalsIgnoreCase(categoryQuiz.getName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        // Viết hoa chữ cái đầu của mỗi từ trong categoryQuiz.getName()
        String name = capitalizeWords(categoryQuiz.getName());
        categoryQuiz.setName(name);
        categoryQuizService.save(categoryQuiz);
        return new ResponseEntity<>(categoryQuiz, HttpStatus.CREATED);
    }
    // Phương thức để viết hoa chữ cái đầu của mỗi từ trong chuỗi
    private String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            result.append(capitalizedWord).append(" ");
        }
        return result.toString().trim();
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryQuiz> updateCategoryQuiz(@PathVariable Long id, @RequestBody CategoryQuiz categoryQuiz) {
        Optional<CategoryQuiz> categoryQuizOptional = this.categoryQuizService.findById(id);
        if (categoryQuizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<CategoryQuiz> categoryQuizServiceByName = this.categoryQuizService.findCategoryQuizByName(categoryQuiz.getName());
        if (!categoryQuizServiceByName.isPresent()) {
            if (categoryQuiz.getName() != null && !categoryQuiz.getName().isEmpty()) {
                categoryQuizOptional.get().setName(categoryQuiz.getName().substring(0, 1).toUpperCase() + categoryQuiz.getName().substring(1));
            }
            if (categoryQuiz.getDescription() != null) {
                categoryQuizOptional.get().setDescription(categoryQuiz.getDescription());
            }
            categoryQuizService.save(categoryQuizOptional.get());
            return new ResponseEntity<>(categoryQuizOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryQuiz> deleteCategoryQuiz(@PathVariable Long id) {
        Optional<CategoryQuiz> categoryQuiz = categoryQuizService.findById(id);
        if (categoryQuiz.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Iterable<Quiz> quizzes = quizService.findAll();
            for ( Quiz quiz : quizzes) {
                if (Objects.equals(quiz.getCategoryQuiz().getId(), categoryQuiz.get().getId())) {
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
            }
            categoryQuizService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}

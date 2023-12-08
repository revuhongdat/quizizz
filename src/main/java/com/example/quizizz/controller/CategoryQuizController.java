package com.example.quizizz.controller;

import com.example.quizizz.model.CategoryQuiz;
import com.example.quizizz.service.CategoryQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/categoryQuizzes")
public class CategoryQuizController {

    @Autowired
    CategoryQuizService categoryQuizService;

    @GetMapping
    public ResponseEntity<Iterable<CategoryQuiz>> showAllCateQuiz() {
        Iterable<CategoryQuiz> categoryQuizzes = categoryQuizService.findAll();
        return new ResponseEntity<>(categoryQuizzes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryQuiz> findCateQuizById(@PathVariable Long id) {
        Optional<CategoryQuiz> categoryQuizOptional = categoryQuizService.findById(id);
        return categoryQuizOptional.map(categoryQuiz -> new ResponseEntity<>(categoryQuiz, HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CategoryQuiz> createCateQuiz(@RequestBody CategoryQuiz categoryQuiz, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<CategoryQuiz> categoryQuizzes = categoryQuizService.findAll();
        for (CategoryQuiz currentCateQuiz : categoryQuizzes) {
            if (currentCateQuiz.getName().equals(categoryQuiz.getName())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        categoryQuiz.setName(categoryQuiz.getName().replaceFirst("^.", String.valueOf(categoryQuiz.getName().charAt(0)).toUpperCase()));
        categoryQuizService.save(categoryQuiz);
        return new ResponseEntity<>(categoryQuiz, HttpStatus.CREATED);
    }
}
package com.example.quizizz.controller;

import com.example.quizizz.model.CategoryQuiz;
import com.example.quizizz.service.CategoryQuizService;
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
        String name = capitalizeWordsKeepHtmlTags(categoryQuiz.getName());
        categoryQuiz.setName(name);
        categoryQuizService.save(categoryQuiz);
        return new ResponseEntity<>(categoryQuiz, HttpStatus.CREATED);
    }

    // Phương thức để viết hoa chữ cái đầu của mỗi từ trong chuỗi
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
    public ResponseEntity<CategoryQuiz> updateCategoryQuiz(@PathVariable Long id, @RequestBody CategoryQuiz categoryQuiz) {
        Iterable<CategoryQuiz> categoryQuizzes = categoryQuizService.findAll();
        for (CategoryQuiz currentCateQuiz : categoryQuizzes) {
            if (Objects.equals(currentCateQuiz.getId(), id)) {
                continue;
            }
            if (currentCateQuiz.getName().equalsIgnoreCase(categoryQuiz.getName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        // Viết hoa chữ cái đầu của mỗi từ trong categoryQuiz.getName()
        String name = capitalizeWordsKeepHtmlTags(categoryQuiz.getName());
        categoryQuiz.setName(name);
        categoryQuiz.setId(id);
        categoryQuizService.save(categoryQuiz);
        return new ResponseEntity<>(categoryQuiz, HttpStatus.CREATED);
    }

}

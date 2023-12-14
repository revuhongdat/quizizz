package com.example.quizizz.service.impl;

import com.example.quizizz.model.CategoryQuiz;
import com.example.quizizz.repository.CategoryQuizRepository;
import com.example.quizizz.service.CategoryQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CategoryQuizServiceImpl implements CategoryQuizService {

    @Autowired
    CategoryQuizRepository categoryQuizRepository;
    @Override
    public Iterable<CategoryQuiz> findAll() {
        return categoryQuizRepository.findAll();
    }

    @Override
    public Optional<CategoryQuiz> findById(Long id) {
        return categoryQuizRepository.findById(id);
    }

    @Override
    public CategoryQuiz save(CategoryQuiz categoryQuiz) {
        return categoryQuizRepository.save(categoryQuiz);
    }

    @Override
    public void delete(Long id) {
        categoryQuizRepository.deleteById(id);
    }

    @Override
    public Optional<CategoryQuiz> findCategoryQuizByName(String name) {
        return categoryQuizRepository.findCategoryQuizByName(name);
    }

    @Override
    public Iterable<CategoryQuiz> findAllByNameContains(String name) {
        return categoryQuizRepository.findAllByNameContains(name);
    }

    @Override
    public Iterable<CategoryQuiz> findAllByDescriptionContains(String description) {
        return categoryQuizRepository.findAllByDescriptionContains(description);
    }
}

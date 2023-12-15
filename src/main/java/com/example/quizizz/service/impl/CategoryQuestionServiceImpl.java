package com.example.quizizz.service.impl;

import com.example.quizizz.model.CategoryQuestion;
import com.example.quizizz.repository.CategoryQuestionRepository;
import com.example.quizizz.service.CategoryQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryQuestionServiceImpl implements CategoryQuestService {
    private final CategoryQuestionRepository categoryQuestionRepository;

    @Autowired
    public CategoryQuestionServiceImpl(CategoryQuestionRepository categoryQuestionRepository) {
        this.categoryQuestionRepository = categoryQuestionRepository;
    }

    @Override
    public Iterable<CategoryQuestion> findAll() {
        return categoryQuestionRepository.findAll();
    }

    @Override
    public Optional<CategoryQuestion> findById(Long id) {
        return categoryQuestionRepository.findById(id);
    }

    @Override
    public CategoryQuestion save(CategoryQuestion categoryQuestion) {
        return categoryQuestionRepository.save(categoryQuestion);
    }

    @Override
    public void delete(Long id) {
        categoryQuestionRepository.deleteById(id);
    }

    @Override
    public Iterable<CategoryQuestion> findAllByUser_Id(Long id) {
        return categoryQuestionRepository.findAllByUser_Id(id);
    }

    @Override
    public Iterable<CategoryQuestion> findAllByNameContains(String name) {
        return categoryQuestionRepository.findAllByNameContains(name);
    }

    @Override
    public Iterable<CategoryQuestion> findAllByDescriptionContains(String description) {
        return categoryQuestionRepository.findAllByDescriptionContains(description);
    }
}

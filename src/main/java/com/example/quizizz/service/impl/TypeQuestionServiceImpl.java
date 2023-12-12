package com.example.quizizz.service.impl;

import com.example.quizizz.model.TypeQuestion;
import com.example.quizizz.repository.TypeQuestionRepository;
import com.example.quizizz.service.TypeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeQuestionServiceImpl implements TypeQuestionService {
    final TypeQuestionRepository typeQuestionRepository;

    @Autowired
    public TypeQuestionServiceImpl(TypeQuestionRepository typeQuestionRepository) {
        this.typeQuestionRepository = typeQuestionRepository;
    }

    @Override
    public Iterable<TypeQuestion> findAll() {
        return typeQuestionRepository.findAll();
    }

    @Override
    public Optional<TypeQuestion> findById(Long id) {
        return typeQuestionRepository.findById(id);
    }

    @Override
    public TypeQuestion save(TypeQuestion typeQuestion) {
        return typeQuestionRepository.save(typeQuestion);
    }

    @Override
    public void delete(Long id) {
        typeQuestionRepository.deleteById(id);
    }
}

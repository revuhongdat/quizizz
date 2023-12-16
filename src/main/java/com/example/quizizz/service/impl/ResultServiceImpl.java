package com.example.quizizz.service.impl;

import com.example.quizizz.model.Result;
import com.example.quizizz.repository.ResultRepository;
import com.example.quizizz.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {
    final
    ResultRepository resultRepository;

    @Autowired
    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public Iterable<Result> findAll() {
        return resultRepository.findAll();
    }

    @Override
    public Optional<Result> findById(Long id) {
        return resultRepository.findById(id);
    }

    @Override
    public Result save(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public void delete(Long id) {
        resultRepository.deleteById(id);
    }

    @Override
    public Iterable<Result> findAllByQuizId(Long id) {
        return resultRepository.findAllByQuizId(id);
    }
}

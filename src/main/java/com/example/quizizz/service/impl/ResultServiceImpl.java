package com.example.quizizz.service.impl;

import com.example.quizizz.model.Result;
import com.example.quizizz.repository.ResultRepository;
import com.example.quizizz.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @Override
    public Iterable<Result> findAllByUserIdAndQuizId(Long idUser, Long idQuiz) {
        Iterable<Result> results = resultRepository.findAllByUserIdAndQuizId(idUser, idQuiz);
        // Chuyển Iterable thành List
        List<Result> resultList = new ArrayList<>();
        results.forEach(resultList::add);
        // Sắp xếp danh sách
        List<Result> sortedResults = resultList.stream()
                .sorted(Comparator.comparing(Result::getId).reversed())
                .toList();
        return sortedResults;
    }

    @Override
    public Iterable<Result> findAllByUserId(Long idUser) {
        return resultRepository.findAllByUserId(idUser);
    }

    @Override
    public Result findByUserIdAndQuizIdAndNewest(Long idUser, Long idQuiz) {
        List<Result> sortedResults = (List<Result>) findAllByUserIdAndQuizId(idUser, idQuiz);
        return sortedResults.get(0);
    }
}

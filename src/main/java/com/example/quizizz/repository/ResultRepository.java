package com.example.quizizz.repository;

import com.example.quizizz.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Iterable<Result> findAllByQuizId(Long id);
}


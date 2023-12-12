package com.example.quizizz.repository;

import com.example.quizizz.model.LevelQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelQuestionRepository extends JpaRepository<LevelQuestion, Long> {
}

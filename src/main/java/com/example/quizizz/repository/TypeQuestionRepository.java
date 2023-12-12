package com.example.quizizz.repository;

import com.example.quizizz.model.TypeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeQuestionRepository extends JpaRepository<TypeQuestion, Long> {
}

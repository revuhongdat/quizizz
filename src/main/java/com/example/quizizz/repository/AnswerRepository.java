package com.example.quizizz.repository;

import com.example.quizizz.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Iterable<Answer> findAllByQuestion_Id (Long questionId);
    void deleteAllByQuestion_Id (Long questionId);
}

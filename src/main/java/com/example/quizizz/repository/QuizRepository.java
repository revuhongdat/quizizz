package com.example.quizizz.repository;

import com.example.quizizz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Iterable<Quiz> findAllByUserId(Long id);
    @Modifying
    @Query(value =
            "SELECT q.*, COUNT(r.id) AS soLuongKetQua " +
            "FROM quiz q " +
            "LEFT JOIN result r ON q.id = r.id_quiz " +
            "JOIN category_quiz cq ON q.id_category_quiz = cq.id " +
            "WHERE cq.id = :id " +
            "GROUP BY q.id " +
            "ORDER BY soLuongKetQua DESC", nativeQuery = true)
    @Transactional
    List<Quiz> findAllByCategoryQuizMostResult(@Param("id") Long id);
}

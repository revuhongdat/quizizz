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
    List<Quiz> findAllByCategoryQuizMostResult(@Param("id") Long id);

    @Query(value =
            "SELECT q.* " +
                    "FROM quiz q " +
                    "JOIN category_quiz cq ON q.id_category_quiz = cq.id " +
                    "JOIN ( " +
                    "   SELECT id_category_quiz, MAX(soLuongKetQua) AS maxSoLuongKetQua " +
                    "   FROM ( " +
                    "       SELECT cq.id AS id_category_quiz, q.id AS id_quiz, COUNT(r.id) AS soLuongKetQua " +
                    "       FROM quiz q " +
                    "       LEFT JOIN result r ON q.id = r.id_quiz " +
                    "       JOIN category_quiz cq ON q.id_category_quiz = cq.id " +
                    "       GROUP BY cq.id, q.id " +
                    "   ) AS subquery " +
                    "   GROUP BY id_category_quiz " +
                    ") AS maxResults " +
                    "ON cq.id = maxResults.id_category_quiz AND q.id = ( " +
                    "    SELECT q2.id " +
                    "    FROM quiz q2 " +
                    "    LEFT JOIN result r2 ON q2.id = r2.id_quiz " +
                    "    WHERE q2.id_category_quiz = cq.id " +
                    "    GROUP BY q2.id " +
                    "    ORDER BY COUNT(r2.id) DESC " +
                    "    LIMIT 10 " +
                    ")", nativeQuery = true)
    List<Quiz> findQuizWithMostResultsInEachCategory();
    Iterable<Quiz> findAllByTitleContaining(String name);
}

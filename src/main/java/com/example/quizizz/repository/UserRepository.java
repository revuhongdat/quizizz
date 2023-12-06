package com.example.quizizz.repository;


import com.example.quizizz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Iterable<User> findUserByNameContains(String name);

    @Modifying
    @Query(value = "select * from user join quiz.user_role ur on user.id = ur.id_user where id_role = :roleId and status = :status and  enabled = :enable", nativeQuery = true)
    Iterable<User> findUsersByRoleName(int roleId, int status, boolean enable);

    @Modifying
    @Query(value = "select * from user join quiz.user_role ur on user.id = ur.id_user where id_role = :roleId and status = :status and  enabled = :enable order by user.time_create desc ", nativeQuery = true)
    Iterable<User> SortByCreationTime(int roleId, int status, boolean enable);
    User findByResetPasswordToken(String token);

}
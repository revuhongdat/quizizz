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
    @Query(value = "select * from user join quiz.user_role ur on user.id = ur.id_user where id_role = :roleId", nativeQuery = true)
    Iterable<User> findUsersByRoleName(@Param("roleId") int roleId);

    @Modifying
    @Query(value = "select * from user join quiz.user_role ur on user.id = ur.id_user where id_role = :roleId order by user.time_create", nativeQuery = true)
    Iterable<User> SortByCreationTime(@Param("roleId") int roleId);
}
package com.example.user_module.repository;

import com.example.user_module.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserEntityByEmail(String email);

    Optional<User> findUserEntityByLogin(String login);

    User findByLogin(String login);

    List<User> findAll();

    User save(User userEntity);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);


    User getById(Long id);


    Optional<User> getByLogin(String login);

    Optional<User> getByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM user_refresh_token WHERE user_id = :userId ", nativeQuery = true)
    void deleteToken(@Param("userId") Long id);

    @Modifying
    @Query(value = "UPDATE user_info  SET is_verified = true WHERE user_id = :id", nativeQuery = true)
    void verifyUserById(@Param("id") Long id);


}

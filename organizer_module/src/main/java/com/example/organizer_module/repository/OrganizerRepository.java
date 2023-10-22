package com.example.organizer_module.repository;

import com.example.organizer_module.model.entity.Organizer;
import com.example.organizer_module.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    List<Organizer> findAll();

    Organizer save(Organizer userEntity);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);


    Organizer getById(Long id);


    Optional<Organizer> getByLogin(String login);

    Optional<Organizer> getByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM organizer_refresh_token WHERE organizer_id = :id ", nativeQuery = true)
    void deleteToken(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE organizer_info  SET is_verified = true WHERE organizer_id = :id", nativeQuery = true)
    void verifyUserById(@Param("id") Long id);


}

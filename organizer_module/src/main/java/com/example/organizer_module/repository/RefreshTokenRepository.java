package com.example.organizer_module.repository;

import com.example.organizer_module.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    @Modifying
    @Query(value = """
                    INSERT INTO organizer_refresh_token(token, valid_till, organizer_id)
                    VALUES(:refreshToken, :expireDate, :organizerId)""", nativeQuery = true)
    void saveNewRefreshToken(@Param("refreshToken") UUID refreshToken,
                             @Param("expireDate") Instant expireDate,
                             @Param("organizerId") Long organizerId);

    @Query(value = """
                    SELECT COUNT(b) FROM organizer_refresh_token b WHERE  b.organizer_id =:organizerId """, nativeQuery = true)
    long getAllByUser_UserId( @Param("organizerId") Long organizerId);
}

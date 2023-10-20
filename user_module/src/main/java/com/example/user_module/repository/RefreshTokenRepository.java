package com.example.user_module.repository;

import com.example.user_module.model.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    @Modifying
    @Query(value = """
                    INSERT INTO user_refresh_token(token, valid_till, user_id)
                    VALUES(:refreshToken, :expireDate, :userId)""", nativeQuery = true)
    void saveNewRefreshToken(@Param("refreshToken") UUID refreshToken,
                             @Param("expireDate") Instant expireDate,
                             @Param("userId") Long userId);

    @Query(value = """
                    SELECT COUNT(b) FROM user_refresh_token b WHERE  b.user_id =:userId """, nativeQuery = true)
    long getAllByUser_UserId( @Param("userId") Long userId);
}

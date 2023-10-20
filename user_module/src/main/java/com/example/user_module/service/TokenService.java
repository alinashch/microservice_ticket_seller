package com.example.user_module.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.user_module.exception.EntityDoesNotExistException;
import com.example.user_module.exception.TokenValidationException;
import com.example.user_module.model.dto.TokensDTO;
import com.example.user_module.model.dto.UserDTO;
import com.example.user_module.model.entity.*;
import com.example.user_module.model.request.RefreshTokenRequest;
import com.example.user_module.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String LOGIN_CLAIM="login";

    private static final String DATE_OF_BIRTH="date-of-birth";
    private static final String TICKET_SELLER_SERVER = "ticket-seller-server";
    private static final String EXPIRATION_CLAIM = "exp";
    private static final String ROLES_CLAIM = "roles";
    private static final String EMAIL_CLAIM = "email";
    private static final String FULL_NAME_CLAIM = "fullName";

    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access-token.expire-date-ms}")
    private Integer accessTokenExpireTimeInMs;

    @Value("${security.jwt.refresh-token.expire-date-days}")
    private Integer refreshTokenExpireTimeInDays;

    public void authenticate(String authHeader) {
        DecodedJWT decodedJWT = decodeJWT(authHeader);
        Map<String, Claim> claims = decodedJWT.getClaims();
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get(LOGIN_CLAIM).asString());
        checkIfTokenIsExpired(claims.get(EXPIRATION_CLAIM).asDate());
        setAuthToken(userDetails);
    }

    private DecodedJWT decodeJWT(String authHeader) {
        String token = authHeader;
        if (authHeader.startsWith(TOKEN_PREFIX)) {
            token = authHeader.substring(TOKEN_PREFIX.length());
        }
        var algorithm = getAlgorithm();
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey.getBytes());
    }

    private void checkIfTokenIsExpired(Date tokenExpireDate) {
        if (tokenExpireDate.before(new Date())) {
            throw new TokenExpiredException("Старый невалидный токен", Instant.now());
        }
    }

    private void checkIfTokenIsExpired(Instant tokenExpireDate) {
        if (tokenExpireDate.isBefore(Instant.now())) {
            throw new TokenExpiredException("Старый невалидный токен", Instant.now());
        }
    }

    private void setAuthToken(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Transactional
    public TokensDTO createTokens(UserDTO user) {
        return new TokensDTO(generateAccessToken(user), generateRefreshToken(user).toString());
    }

    private String generateAccessToken(UserDTO user) {
        return JWT.create()
                .withSubject(user.getUserId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpireTimeInMs))
                .withIssuer(TICKET_SELLER_SERVER)
                .withClaim(ROLES_CLAIM, user.getRoles().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .withClaim(EMAIL_CLAIM, user.getEmail())
                .withClaim(LOGIN_CLAIM, user.getLogin())
                .withClaim(DATE_OF_BIRTH, String.valueOf(user.getDateOfBirth()))
                .withClaim(FULL_NAME_CLAIM, user.getFirstName().concat(" ")
                        .concat(user.getSecondName())
                        .concat(" ").concat(user.getSurname()))
                .sign(getAlgorithm());
    }

    private UUID generateRefreshToken(UserDTO user) {
        UUID refreshToken = UUID.randomUUID();
        refreshTokenRepository.saveNewRefreshToken(
                refreshToken,
                Instant.now().plusSeconds(fromDaysToSeconds(refreshTokenExpireTimeInDays)),
                user.getUserId());
        return refreshToken;
    }

    private static int fromDaysToSeconds(int days) {
        return days * 24 * 60 * 60;
    }

    public TokensDTO refreshAccessToken(RefreshTokenRequest request) {
        RefreshTokenEntity refreshToken = findRefreshTokenById(request.getRefreshToken());
        checkIfTokenIsExpired(refreshToken.getValidTill());
        return new TokensDTO(
                generateAccessToken(userService.getUserByEmail(refreshToken.getUser().getEmail())),
                refreshToken.getToken().toString()
        );
    }

    private RefreshTokenEntity findRefreshTokenById(String refreshToken) {
        return refreshTokenRepository.findById(String.valueOf(UUID.fromString(refreshToken))).orElseThrow(
                () -> new EntityDoesNotExistException("Токен не существует")
        );
    }
}
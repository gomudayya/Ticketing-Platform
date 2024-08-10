package com.ticketland.ticketland.auth.util;

import com.ticketland.ticketland.auth.dto.JwtClaimDto;
import com.ticketland.ticketland.user.constant.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String USER_ROLE_CLAIM = "userRole";
    public static final String USER_ID_CLAIM = "userId";
    public static final String BEARER_PREFIX = "Bearer ";
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt-secret-key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 토큰 생성
    public String createToken(Long userId, UserRole userRole) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .claim(USER_ID_CLAIM, userId) // 사용자 id 저장
                        .claim(USER_ROLE_CLAIM, userRole.name()) // 사용자 권한 저장
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }


    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public JwtClaimDto getJwtClaims(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        Long userId = claims.get(USER_ID_CLAIM, Long.class);
        UserRole userRole = UserRole.valueOf(claims.get(USER_ROLE_CLAIM, String.class));

        return new JwtClaimDto(userId, userRole);
    }
}
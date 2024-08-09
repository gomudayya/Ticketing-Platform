package com.ticketland.ticketland.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // @Secured 어노테이션 사용 가능
public class SecurityConfig {

    @Value("${aes-secret-key}")
    private String aesSecretKey;

    @Value("${aes-salt}")
    private String aesSalt;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource)); 나중에 cors 설정 해줘야함.

        http.csrf(CsrfConfigurer::disable); // JWT 를 사용하는 경우, CSRF 공격의 위험이 적으므로 비활성화 함. (헤더로 JWT토큰을 보낼 때 기준)
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // JWT 사용 -> 세션 STATELESS 설정
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable); // Spring Security의 기본 폼 로그인 페이지 비활성화
        http.logout(AbstractHttpConfigurer::disable); // Spring Security의 기본 로그아웃 처리 비활성화

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AesBytesEncryptor aesBytesEncryptor() {
        return new AesBytesEncryptor(aesSecretKey, aesSalt);
    }
}

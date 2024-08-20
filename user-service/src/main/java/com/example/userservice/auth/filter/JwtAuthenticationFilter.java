package com.example.userservice.auth.filter;

import com.example.userservice.auth.UserDetailsImpl;
import com.example.userservice.auth.dto.LoginRequest;
import com.example.userservice.auth.util.JwtUtil;
import com.example.userservice.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, JwtUtil jwtUtil) {
        super("/api/users/login", authenticationManager);
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException,
            HttpRequestMethodNotSupportedException {
        if (!request.getMethod().equalsIgnoreCase("POST")) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod()); // POST 이외의 메서드는 예외발생
        }

        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        log.info("로그인 성공");

        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String jwtToken = jwtUtil.createToken(user.getId(), user.getUserRole());
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("로그인 실패");
        response.setStatus(401);
    }

}

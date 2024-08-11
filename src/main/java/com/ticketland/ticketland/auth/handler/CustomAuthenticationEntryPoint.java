package com.ticketland.ticketland.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketland.ticketland.global.exception.core.ErrorCode;
import com.ticketland.ticketland.global.exception.core.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");

        ExceptionResponse exceptionResponse = ExceptionResponse.of(ErrorCode.ACCESS_DENIED);
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}

package com.example.apigateway.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER(List.of(Authority.USER)), ADMIN(List.of(Authority.MANAGER));

    private final List<String> authority;

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String MANAGER = "ROLE_ADMIN";
    }
}

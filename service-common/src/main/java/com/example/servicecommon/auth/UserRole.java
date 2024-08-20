package com.example.servicecommon.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER(List.of(Authority.USER)), ADMIN(List.of(Authority.USER, Authority.ADMIN));

    private final List<String> authorities;

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}

package com.example.userservice.user.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "verify_code")
public class JoinVerification {

    @Id
    private final String email;

    private final int code;

    private boolean isVerified = false;

    @TimeToLive
    private long ttl = 3600;

    public JoinVerification(String email, int code) {
        this.email = email;
        this.code = code;
    }

    public void setVerified() {
        isVerified = true;
    }

    public boolean codeEquals(int code) {
        return this.code == code;
    }
}

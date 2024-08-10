package com.ticketland.ticketland.global.config;

import com.ticketland.ticketland.global.util.AesUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AesConfig {
    @Value("${aes-secret-key}")
    private String aesSecretKey;

    @Value("${aes-salt}")
    private String aesSalt;
    @PostConstruct
    public void aesUtilInit() {
        AesUtil.setAesBytesEncryptor(new AesBytesEncryptor(aesSecretKey, aesSalt));
    }
}

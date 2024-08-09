package com.ticketland.ticketland.user.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

@Entity
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private boolean isDeleted;

    @Builder
    private User(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isDeleted = false;
    }

    protected User(){}

    public void encryptUser(PasswordEncoder passwordEncoder, AesBytesEncryptor aesBytesEncryptor) {
        password = passwordEncoder.encode(this.password);
        this.email = Base64.getEncoder().encodeToString(aesBytesEncryptor.encrypt(this.email.getBytes()));
        this.name = Base64.getEncoder().encodeToString(aesBytesEncryptor.encrypt(this.name.getBytes()));
        this.phoneNumber = Base64.getEncoder().encodeToString(aesBytesEncryptor.encrypt(this.phoneNumber.getBytes()));
    }
}

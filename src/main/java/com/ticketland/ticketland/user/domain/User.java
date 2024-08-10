package com.ticketland.ticketland.user.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import com.ticketland.ticketland.global.util.AesUtil;
import com.ticketland.ticketland.user.constant.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

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
    private UserRole userRole;

    @Builder
    private User(String email, String password, String name, String phoneNumber, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isDeleted = false;
        this.userRole = userRole;
    }

    protected User() {
    }

    public String getEmail() {
        return AesUtil.decode(email);
    }

    public String getName() {
        return AesUtil.decode(name);
    }

    public String getPhoneNumber() {
        return AesUtil.decode(phoneNumber);
    }

}

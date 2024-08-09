package com.ticketland.ticketland.user.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.persister.collection.mutation.UpdateRowsCoordinator;

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
    public User(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isDeleted = false;
    }

    protected User(){}
}

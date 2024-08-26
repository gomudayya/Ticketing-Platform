package com.example.userservice.wishlist.domain;

import com.example.userservice.global.domain.BaseTimeEntity;
import com.example.userservice.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@SQLDelete(sql = "UPDATE wishlist SET is_deleted = true WHERE id = ?")
@Where(clause = "is_Deleted = 0")
public class Wishlist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long showId;
    private boolean isDeleted = false;
    @Builder
    public Wishlist(User user, Long showId) {
        this.user = user;
        this.showId = showId;
    }

    protected Wishlist() {}
}

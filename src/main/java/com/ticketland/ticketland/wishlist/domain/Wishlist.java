package com.ticketland.ticketland.wishlist.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Wishlist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;
    private boolean isDeleted = false;
    @Builder
    public Wishlist(User user, Show show) {
        this.user = user;
        this.show = show;
    }

    protected Wishlist() {}
}

package com.example.showservice.wishlist.domain;

import com.example.showservice.global.domain.BaseTimeEntity;
import com.example.showservice.show.domain.Show;
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

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;
    private boolean isDeleted = false;
    @Builder
    public Wishlist(Long userId, Show show) {
        this.userId = userId;
        this.show = show;
    }

    protected Wishlist() {}
}

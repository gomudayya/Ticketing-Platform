package com.ticketland.ticketland.show.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "shows")
public class Show extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private String performer;
    private String title;
    private String descriptionImage;
    private LocalDateTime ticketingTime;
    private LocalDateTime startTime;
    private Integer duration; // 공연시간 (분)
    private boolean isDeleted = false;

    public String getGenreName() {
        return genre.getGenreName();
    }
}

package com.ticketland.ticketland.performance.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String performer;
    private String title;
    private String descriptionImage;
    private LocalDateTime ticketingTime;
    private LocalDateTime startDate;
    private Integer duration; // 공연시간 (분)
    private boolean isDeleted;
}

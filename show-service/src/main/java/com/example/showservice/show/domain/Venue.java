package com.example.showservice.show.domain;

import com.example.showservice.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Venue extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String venueName;
    private String address;
    private String seatLayoutData;
    private Integer seatCount;

    protected Venue() {}
    @Builder
    public Venue(String venueName, String address, String seatLayoutData, Integer seatCount) {
        this.venueName = venueName;
        this.address = address;
        this.seatLayoutData = seatLayoutData;
        this.seatCount = seatCount;
    }
}

package com.example.showservice.venue.domain;

import com.example.showservice.venue.domain.Venue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
    private String section;
    private Integer number;
    protected Seat(){}
    @Builder
    public Seat(Venue venue, String section, Integer number) {
        this.venue = venue;
        this.section = section;
        this.number = number;
    }
}

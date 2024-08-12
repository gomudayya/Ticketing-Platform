package com.ticketland.ticketland.show.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Seat {
    @Id
    @GeneratedValue
    private Long id;
    private String section;
    private Integer number;
}

package com.ticketland.ticketland.show.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Entity
public class TicketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    private String seatSection;
    private Integer price;

    protected TicketPrice(){}
    @Builder
    public TicketPrice(Show show, String seatSection, Integer price) {
        this.show = show;
        this.seatSection = seatSection;
        this.price = price;
    }
}

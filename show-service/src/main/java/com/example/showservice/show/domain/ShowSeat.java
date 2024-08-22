package com.example.showservice.show.domain;

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
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    private String seatSection;
    private Integer price;
    private Integer stock;

    protected ShowSeat(){}
    @Builder
    public ShowSeat(Show show, String seatSection, Integer price, Integer stock) {
        this.show = show;
        this.seatSection = seatSection;
        this.price = price;
        this.stock = stock;

        show.getShowSeats().add(this); // 연관관계 편의 로직
    }
}

package com.ticketland.ticketland.show.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@SQLDelete(sql = "UPDATE ticket SET is_deleted = true WHERE id = ?")
@Where(clause = "is_Deleted = 0")
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private Integer price;

    private boolean isSold = false;
    private boolean isDeleted = false;
    protected Ticket() {}
    @Builder
    public Ticket(Show show, Seat seat, Integer price) {
        this.show = show;
        this.seat = seat;
        this.price = price;
    }


}

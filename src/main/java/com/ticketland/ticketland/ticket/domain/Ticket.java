package com.ticketland.ticketland.ticket.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import com.ticketland.ticketland.order.domain.Order;
import com.ticketland.ticketland.show.constant.ShowStatus;
import com.ticketland.ticketland.show.domain.Show;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Entity
@SQLDelete(sql = "UPDATE ticket SET is_deleted = true WHERE id = ?")
@Where(clause = "is_Deleted = 0")
public class Ticket extends BaseTimeEntity {

    @Id
    private String id; // TicketId = {공연Id}_{좌석섹션}_{좌석번호}

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
    private Integer price;
    private boolean isSold = false;
    private boolean isDeleted = false;

    protected Ticket() {
    }

    @Builder
    public Ticket(String id, Show show, Integer price) {
        this.id = id;
        this.show = show;
        this.price = price;
    }

    public void beOrdered(Order order) {
        this.order = order;
        isSold = true;
        order.getTickets().add(this);
    }

    public void beCanceled() {
        isSold = false;
        order.getTickets().remove(this);
        order = null;
    }

    public String getSeatSection() {
        return id.split("_")[1];
    }

    public Integer getSeatNumber() {
        return Integer.parseInt(id.split("_")[2]);
    }
}
